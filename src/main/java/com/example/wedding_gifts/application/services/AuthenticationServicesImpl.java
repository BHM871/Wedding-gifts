package com.example.wedding_gifts.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.wedding_gifts.core.usecases.account.AccountRepository;
import com.example.wedding_gifts.core.usecases.auth.AuthenticationService;

@Service
public class AuthenticationServicesImpl implements AuthenticationService {

    @Autowired
    AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.getByEmail(username);
    }
    
}
