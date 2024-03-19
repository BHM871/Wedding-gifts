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
import com.example.wedding_gifts_api.core.domain.model.Account;
import com.example.wedding_gifts_api.core.domain.model.ChangeRequest;
import com.example.wedding_gifts_api.core.usecases.account.IAccountRepository;
import com.example.wedding_gifts_api.core.usecases.auth.IAuthenticationService;
import com.example.wedding_gifts_api.core.usecases.change_request.IChangeRequestUseCase;
import com.example.wedding_gifts_api.infra.dtos.authentication.ChangePassDTO;
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
    public String changePassword(boolean isAccount, UUID id, ChangePassDTO change) throws Exception {
        try{
            Account account;
            ChangeRequest request = null;
            if(isAccount){
                account = repository.getAccountById(id);
                
                if(account.getEmail() != change.email()) throw new AccountForbiddenException("Invalid");
            } else {
                request = changeService.getRequestById(id);

                account = request.getAccount();

                if(!change.email().equals(account.getEmail())) throw new Exception("Request is not your");
                if(request.getLimitHour().isBefore(LocalDateTime.now(MyZone.zoneId()))) throw new Exception("Request expired");
            }

            String pass = new BCryptPasswordEncoder().encode(change.password());
            account.setPassword(pass);

            repository.save(account);
            if(request != null) changeService.deleteRequestById(request.getId());

            return "Succefully";
        } catch (Exception e) {
            throw e;
        }
    }

}
