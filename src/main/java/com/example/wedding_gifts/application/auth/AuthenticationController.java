package com.example.wedding_gifts.application.auth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wedding_gifts.adapters.security.TokenManagerAdapter;
import com.example.wedding_gifts.common.Validation;
import com.example.wedding_gifts.core.domain.dtos.account.AccountResponseAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.LoginDTO;
import com.example.wedding_gifts.core.domain.dtos.authentication.AuthenticationResponseDTO;
import com.example.wedding_gifts.core.domain.dtos.commun.MessageDTO;
import com.example.wedding_gifts.core.domain.dtos.exception.ExceptionResponseDTO;
import com.example.wedding_gifts.core.domain.exceptions.account.AccountExecutionException;
import com.example.wedding_gifts.core.domain.exceptions.account.AccountForbiddenException;
import com.example.wedding_gifts.core.domain.exceptions.account.AccountInvalidValueException;
import com.example.wedding_gifts.core.domain.exceptions.account.AccountNotNullableException;
import com.example.wedding_gifts.core.domain.exceptions.common.MyException;
import com.example.wedding_gifts.core.domain.exceptions.token.TokenInvalidValueException;
import com.example.wedding_gifts.core.domain.model.Account;
import com.example.wedding_gifts.core.usecases.account.IAccountRepository;
import com.example.wedding_gifts.core.usecases.auth.IAuthenticationController;
import com.example.wedding_gifts.core.usecases.token.ITokenUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.var;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth")
public class AuthenticationController implements IAuthenticationController {

    @Autowired
    private IAccountRepository repository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenManagerAdapter tokenManager;
    @Autowired
    private ITokenUseCase tokenService;

    @Override
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a Account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully", content = @Content(schema = @Schema(type = "object", implementation = AccountResponseAccountDTO.class))),
        @ApiResponse(responseCode = "400", description = "Some error in processable request", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "406", description = "Some value is null", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "409", description = "Already email"),
        @ApiResponse(responseCode = "422", description = "Invalid value in request body", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class)))
    })
    public ResponseEntity<AccountResponseAccountDTO> register(
        @RequestBody CreateAccountDTO account
    ) throws Exception {
        try{
            validData(account);

            if(repository.getByEmail(account.email()) != null) return ResponseEntity.status(HttpStatus.CONFLICT).build();

            String encrypPassword = new BCryptPasswordEncoder().encode(account.password());

            CreateAccountDTO createAccount = new CreateAccountDTO(
                account.firstName(), 
                account.lastName(), 
                account.brideGroom(), 
                account.weddingDate(), 
                account.email(), 
                encrypPassword, 
                account.pixKey()
            );

            Account savedAccount = repository.createAccount(createAccount);

            AccountResponseAccountDTO newAccount = new AccountResponseAccountDTO(
                savedAccount.getId(), 
                savedAccount.getBrideGroom(), 
                savedAccount.getWeddingDate(), 
                savedAccount.getFirstName(), 
                savedAccount.getLastName(), 
                savedAccount.getEmail());


            return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);
        } catch (MyException e){
            e.setPath("/auth/register");
            throw e;
        } catch (Exception e){
            AccountExecutionException exception = new AccountExecutionException("Some error", e);
            exception.setPath("/auth/register");
            throw exception;
        }
    }

    @Override
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "To do login")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully", content = @Content(schema = @Schema(type = "object", implementation = AuthenticationResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Some error in processable request", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "406", description = "Some value is null", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "422", description = "Invalid value in request body", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class)))
    })
    public ResponseEntity<AuthenticationResponseDTO> login(
        @RequestBody LoginDTO login
    ) throws Exception {
        try{
            validData(login);

            var usernamePassword = new UsernamePasswordAuthenticationToken(login.email(), login.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            String token = tokenManager.generatorToken((Account) auth.getPrincipal());

            if(auth.isAuthenticated()) return ResponseEntity.ok(new AuthenticationResponseDTO(token));

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (MyException e){
            e.setPath("/auth/login");
            throw e;
        } catch (InternalAuthenticationServiceException e){
            MyException exception = new AccountForbiddenException("Not authenticate", e);
            exception.setPath("/auth/login");
            throw exception;
        } catch (Exception e){
            AccountExecutionException exception = new AccountExecutionException("Some error", e);
            exception.setPath("/auth/login");
            throw exception;
        }
    }

    @Override
    @PatchMapping(value = "/logout/{token}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "To do logout")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully", content = @Content(schema = @Schema(type = "object", implementation = MessageDTO.class))),
        @ApiResponse(responseCode = "422", description = "Invalid param", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class)))
    })
    public ResponseEntity<MessageDTO> logout(
        @PathVariable String token
    ) throws Exception {
        try{
            if(tokenManager.validateToken(token) == null ) throw new TokenInvalidValueException("Token is invalid");
            
            tokenService.deleteToken(token);

            return ResponseEntity.ok(new MessageDTO("successfully"));
        } catch (MyException e){
            e.setPath("/auth/logout");
            throw e;
        } catch (Exception e){
            AccountExecutionException exception = new AccountExecutionException("Some error", e);
            exception.setPath("/auth/logout");
            throw exception;
        }
    }

    private void validData(CreateAccountDTO data) throws Exception {
        String invalid = "%s is invalid";
        String isNull = "%s value is null";
        
        if(data.brideGroom() == null || data.brideGroom().isEmpty()) throw new AccountNotNullableException(String.format(isNull, "brideGroom"));
        if(data.weddingDate() == null) throw new AccountNotNullableException(String.format(isNull, "weddingDate"));
        if(data.firstName() == null || data.firstName().isEmpty()) throw new AccountNotNullableException(String.format(isNull, "firstName"));
        if(data.lastName() == null || data.lastName().isEmpty()) throw new AccountNotNullableException(String.format(isNull, "lastName"));
        if(data.email() == null || data.email().isEmpty()) throw new AccountNotNullableException(String.format(isNull, "email"));
        if(data.password() == null || data.password().isEmpty()) throw new AccountNotNullableException(String.format(isNull, "password"));
        if(data.pixKey() == null || data.pixKey().isEmpty()) throw new AccountNotNullableException(String.format(isNull, "pixKey"));
        
        if(!Validation.brideGroom(data.brideGroom())) throw new AccountInvalidValueException(String.format(invalid, "brideGroom"));
        if(!Validation.date(data.weddingDate()) || data.weddingDate().before(new Date())) throw new AccountInvalidValueException(String.format(invalid, "weddingDate"));
        if(!Validation.name(data.firstName(), data.lastName())) throw new AccountInvalidValueException(String.format(invalid, "firstName", "and/or", "lastName").replace("is", "are"));
        if(!Validation.email(data.email())) throw new AccountInvalidValueException(String.format(invalid, "email"));
        if(!Validation.password(data.password())) throw new AccountInvalidValueException(String.format(invalid, "password"));
        if(!Validation.pixKey(data.pixKey())) throw new AccountInvalidValueException(String.format(invalid, "pixKey"));
   
    }

    private void validData(LoginDTO data) throws Exception {
        String invalid = "%s is invalid";
        String isNull = "%s is null";

        if(data.email() == null || data.email().isEmpty()) throw new AccountNotNullableException(String.format(isNull, "email"));
        if(data.password() == null || data.password().isEmpty()) throw new AccountNotNullableException(String.format(isNull, "password"));
        
        if(!Validation.email(data.email())) throw new AccountInvalidValueException(String.format(invalid, "email"));
        if(!Validation.password(data.password())) throw new AccountInvalidValueException(String.format(invalid, "password"));
        
    }
    
}
