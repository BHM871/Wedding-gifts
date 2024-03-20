package com.example.wedding_gifts_api.application.auth;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.wedding_gifts_api.common.MyZone;
import com.example.wedding_gifts_api.core.domain.exceptions.account.AccountForbiddenException;
import com.example.wedding_gifts_api.core.domain.exceptions.account.AccountNotFoundException;
import com.example.wedding_gifts_api.core.domain.exceptions.change_request.ChangeRequestInvalidValueException;
import com.example.wedding_gifts_api.core.domain.model.Account;
import com.example.wedding_gifts_api.core.domain.model.ChangeRequest;
import com.example.wedding_gifts_api.core.usecases.account.IAccountRepository;
import com.example.wedding_gifts_api.core.usecases.auth.IAuthenticationService;
import com.example.wedding_gifts_api.core.usecases.change_request.IChangeRequestUseCase;
import com.example.wedding_gifts_api.infra.dtos.authentication.ChangePassLoggedDTO;
import com.example.wedding_gifts_api.infra.dtos.authentication.ChangePassNotLoggedDTO;
import com.example.wedding_gifts_api.infra.dtos.authentication.ForgotPassDTO;
import com.example.wedding_gifts_api.infra.dtos.change_request.ChangeRequestDTO;

@Service
public class AuthenticationServices implements IAuthenticationService {

    @Autowired
    private IAccountRepository repository;
    @Autowired
    private IChangeRequestUseCase changeService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return repository.getByEmail(username);
        } catch (AccountNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Account not found", e);
        }
    }

    @Override
    public ChangeRequestDTO forgotPassword(ForgotPassDTO forgotRequest) throws Exception {
        return changeService.forgotPassword(forgotRequest);
    }

    @Override
    public String changePassword(UUID request, ChangePassNotLoggedDTO change) throws Exception {
        ChangeRequest rq = changeService.getRequestById(request);
        Account account = rq.getAccount();

        if(!change.email().equals(account.getEmail())) throw new AccountForbiddenException("Request is not your");
        if(rq.getLimitHour().isBefore(LocalDateTime.now(MyZone.zoneId()))){
            changeService.deleteRequestById(request);
            throw new ChangeRequestInvalidValueException("Request expired");
        }

        String crypPass = new BCryptPasswordEncoder().encode(change.password());
        account.setPassword(crypPass);

        repository.save(account);
        changeService.deleteRequestById(rq.getId());

        return "Succefully";
    }

    @Override
    public String changePassword(UUID account, ChangePassLoggedDTO change) throws Exception {
        Account acc = repository.getAccountById(account);
        
        if(!acc.getEmail().equals(change.email())) throw new AccountForbiddenException("'email' is not same");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(!encoder.matches(change.lastPass(), acc.getPassword())) throw new ChangeRequestInvalidValueException("last password is not correct");

        String crypPass = encoder.encode(change.newPass());

        acc.setPassword(crypPass);
        repository.save(acc);

        return "Succeffuly";
    }    

}
