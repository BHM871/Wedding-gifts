package com.example.wedding_gifts.core.domain.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.wedding_gifts.common.LimitDateForAccount;
import com.example.wedding_gifts.core.domain.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.UpdateAccountDTO;
import com.example.wedding_gifts.core.domain.exceptions.account.AccountInvalidValueException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NonNull
    @Column(unique = true)
    private String brideGroom;

    @NonNull
    private Date weddingDate;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    @Column(unique = true)
    private String email;

    @NonNull
    private String password;

    @NonNull
    @Column(unique = true)
    private String pixKey;

    public Account(CreateAccountDTO account){
        this.brideGroom = account.brideGroom();
        this.weddingDate = account.weddingDate();
        this.firstName = account.firstName();
        this.lastName = account.lastName();
        this.email = account.email();
        this.password = account.password();
        this.pixKey = account.pixKey();
    }

    public void update(UpdateAccountDTO account) throws Exception {
        String message = "Error in update. %s is invalid. ";

        if(account.brideGroom() != null && account.brideGroom().length() <= 3) throw new AccountInvalidValueException(String.format(message, "brideGroom"));
        if(account.weddingDate() != null && account.weddingDate().getTime() < new Date().getTime()) throw new AccountInvalidValueException(String.format(message, "weddingDate"));
        if(account.firstName() != null && account.firstName().length() <= 3) throw new AccountInvalidValueException(String.format(message, "firstName"));
        if(account.lastName() != null && account.lastName().length() <= 3) throw new AccountInvalidValueException(String.format(message, "lastName"));
        if(account.password() != null && account.password().length() <= 8) throw new AccountInvalidValueException(String.format(message, "password"));
        if(account.pixKey() != null && account.pixKey().length() <= 10) throw new AccountInvalidValueException(String.format(message, "pixKey"));

        if(account.brideGroom() != null) this.brideGroom = account.brideGroom() != null ? account.brideGroom() : brideGroom;
        if(account.weddingDate() != null) this.weddingDate = account.weddingDate() != null ? account.weddingDate() : weddingDate;
        if(account.firstName() != null) this.firstName = account.firstName() != null ? account.firstName() : firstName;
        if(account.lastName() != null) this.lastName = account.lastName() != null ? account.lastName() : lastName;
        if(account.password() != null) this.password = account.password() != null ? account.password() : password;
        if(account.pixKey() != null) this.pixKey = account.pixKey() != null ? account.pixKey() : pixKey;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getPassword(){
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return LimitDateForAccount.deadlineHasNotPassed(weddingDate);
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
