
package com.example.wedding_gifts.application.account;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wedding_gifts.adapters.security.TokenManagerAdapter;
import com.example.wedding_gifts.common.Validation;
import com.example.wedding_gifts.core.domain.exceptions.account.AccountExecutionException;
import com.example.wedding_gifts.core.domain.exceptions.account.AccountInvalidValueException;
import com.example.wedding_gifts.core.domain.exceptions.common.MyException;
import com.example.wedding_gifts.core.domain.model.Account;
import com.example.wedding_gifts.core.usecases.account.IAccountController;
import com.example.wedding_gifts.core.usecases.account.IAccountUseCase;
import com.example.wedding_gifts.infra.dtos.account.AccountResponseAccountDTO;
import com.example.wedding_gifts.infra.dtos.account.AccountResponseIdDTO;
import com.example.wedding_gifts.infra.dtos.account.UpdateAccountDTO;
import com.example.wedding_gifts.infra.dtos.commun.MessageDTO;
import com.example.wedding_gifts.infra.dtos.exception.ExceptionResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/account")
@Tag(name = "Account")
public class AccountController implements IAccountController {

    @Autowired
    private IAccountUseCase services;
    @Autowired
    private TokenManagerAdapter tokenManager;

    @Override
    @GetMapping(value = "/brideGroom/{brideGroom}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Account Id by 'brideGroom'")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully", content = @Content(schema = @Schema(type = "object", implementation = AccountResponseIdDTO.class))),
        @ApiResponse(responseCode = "404", description = "Account not found", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "422", description = "Invalid param", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class)))
    })
    public ResponseEntity<AccountResponseIdDTO> gifterBegin(
        @PathVariable String brideGroom
    ) throws Exception {
        try{
            validData(brideGroom);
            
            UUID id = services.verificAccountForGifter(brideGroom);
            return ResponseEntity.ok(new AccountResponseIdDTO(id));
        } catch (MyException e){
            e.setPath("/account/brideGroom");
            throw e;
        } catch (Exception e){
            AccountExecutionException exception = new AccountExecutionException("Some error");
            exception.setPath("/account/bridegroom");
            throw exception;
        }
    }

    @Override
    @GetMapping(value = "/{account}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Account by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully", content = @Content(schema = @Schema(type = "object", implementation = AccountResponseAccountDTO.class))),
        @ApiResponse(responseCode = "404", description = "Account not found", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "422", description = "Invalid param", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class)))
    })
    public ResponseEntity<AccountResponseAccountDTO> getAccountById(
        @PathVariable UUID id
    ) throws Exception {
        try{
            Account account = services.getAccountById(id);

            AccountResponseAccountDTO accountResponse = new AccountResponseAccountDTO(
                account.getId(), 
                account.getBrideGroom(), 
                account.getWeddingDate(), 
                account.getFirstName(), 
                account.getLastName(), 
                account.getEmail());

            return ResponseEntity.ok(accountResponse);
        } catch (MyException e){
            e.setPath("/account");
            throw e;
        } catch (Exception e){
            AccountExecutionException exception = new AccountExecutionException("Some error");
            exception.setPath("/account");
            throw exception;
        }
    }

    @Override
    @PutMapping(value = "/update/{account}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Update Account by ID",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(schema = @Schema(
                                type = "object", 
                                implementation = UpdateAccountDTO.class))))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully", content = @Content(schema = @Schema(type = "object", implementation = AccountResponseIdDTO.class))),
        @ApiResponse(responseCode = "400", description = "Some error in processable request", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "403", description = "Unauthorizated", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Account not found", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "422", description = "Invalid param or invalid value in request body", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class)))
    })
    public ResponseEntity<AccountResponseAccountDTO> updateAccount(
        @RequestHeader("Authorization") String token,
        @PathVariable UUID account,
        @RequestBody UpdateAccountDTO update
    ) throws Exception {
        try{
            tokenManager.validateSessionId(token, account);
            validData(update);            

            Account upAccount = services.updateAccount(account, update);

            AccountResponseAccountDTO accountResponse = new AccountResponseAccountDTO(
                upAccount.getId(), 
                upAccount.getBrideGroom(), 
                upAccount.getWeddingDate(), 
                upAccount.getFirstName(), 
                upAccount.getLastName(), 
                upAccount.getEmail());

            return ResponseEntity.ok(accountResponse);
        } catch (MyException e){
            e.setPath("/account/update");
            throw e;
        } catch (Exception e){
            AccountExecutionException exception = new AccountExecutionException("Some error");
            exception.setPath("/account/update");
            throw exception;
        }
    }

    @Override
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete Account by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully", content = @Content(schema = @Schema(type = "object", implementation = AccountResponseIdDTO.class))),
        @ApiResponse(responseCode = "400", description = "Some error in processable request", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "403", description = "Unauthorizated", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Account, Gift or Image not found. Gift and Image are delete with Account", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class))),
        @ApiResponse(responseCode = "422", description = "Invalid param", content = @Content(schema = @Schema(type = "object", implementation = ExceptionResponseDTO.class)))
    })
    public ResponseEntity<MessageDTO> deleteAccount(
        @RequestHeader("Authorization") String token,
        @PathVariable UUID id
    ) throws Exception {
        try{
            tokenManager.validateSessionId(token, id);
            
            services.deleteAccount(id);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageDTO("successfully"));
        } catch (MyException e){
            e.setPath("/account/delete");
            throw e;
        } catch (Exception e){
            AccountExecutionException exception = new AccountExecutionException("Some error");
            exception.setPath("/account/delete");
            throw exception;
        }
    }

    private void validData(String data) throws Exception {
        
        if(!Validation.brideGroom(data)) throw new AccountInvalidValueException("brideGroom is invalid");

    }

    private void validData(UpdateAccountDTO data) throws Exception {
        String isInvalid = "%s is invalid";
        
        if(data.brideGroom() != null && !Validation.brideGroom(data.brideGroom())) throw new AccountInvalidValueException(String.format(isInvalid, "brideGroom"));
        if(data.weddingDate() != null && (!Validation.date(data.weddingDate()) || data.weddingDate().before(new Date()))) throw new AccountInvalidValueException(String.format(isInvalid, "weddingDate"));
        if(data.firstName() != null && !Validation.name(data.firstName(), data.lastName())) throw new AccountInvalidValueException(String.format(isInvalid, "firstName"));
        if(data.password() != null && !Validation.password(data.password())) throw new AccountInvalidValueException(String.format(isInvalid, "password"));
        if(data.pixKey() != null && !Validation.pixKey(data.pixKey())) throw new AccountInvalidValueException(String.format(isInvalid, "pixKey"));
        
    }
    
}