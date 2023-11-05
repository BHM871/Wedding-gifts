package com.example.wedding_gifts.application.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.wedding_gifts.core.domain.exceptions.account.AccountNotFoundException;
import com.example.wedding_gifts.core.usecases.account.IAccountRepository;
import com.example.wedding_gifts.core.usecases.auth.IAuthenticationService;

@Service
public class AuthenticationServices implements IAuthenticationService {

    @Autowired
    private IAccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return repository.getByEmail(username);
        } catch (AccountNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new UsernameNotFoundException("Account not found");
        }
    }

}
