package com.example.wedding_gifts_api.core.domain.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.wedding_gifts_api.common.LimitDateForAccount;
import com.example.wedding_gifts_api.common.Validation;
import com.example.wedding_gifts_api.core.domain.exceptions.account.AccountInvalidValueException;
import com.example.wedding_gifts_api.infra.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts_api.infra.dtos.account.UpdateAccountDTO;

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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String brideGroom;

    private Date weddingDate;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String pixKey;

    public Account(CreateAccountDTO account){
        this.brideGroom = account.brideGroom().trim();
        this.weddingDate = account.weddingDate();
        this.firstName = account.firstName().trim();
        this.lastName = account.lastName().trim();
        this.email = account.email().trim();
        this.password = account.password().trim();
        this.pixKey = account.pixKey().trim().length() == 14 && account.pixKey().trim().charAt(11) == '-' 
                        ? account.pixKey().trim().replace(".", "").replace("-", "") 
                        : account.pixKey().trim();
    }

    public void update(UpdateAccountDTO account) throws Exception {
        String message = "Error in update. %s is invalid. ";

        if(account.brideGroom() != null && !Validation.brideGroom(account.brideGroom())) throw new AccountInvalidValueException(String.format(message, "brideGroom"));
        if(account.weddingDate() != null && !Validation.date(account.weddingDate())) throw new AccountInvalidValueException(String.format(message, "weddingDate"));
        if((account.firstName() != null || account.lastName() != null) && !Validation.name(account.firstName(), account.lastName())) throw new AccountInvalidValueException(String.format(message, "firstName"));
        if(account.password() != null && !Validation.password(account.password())) throw new AccountInvalidValueException(String.format(message, "password"));
        if(account.pixKey() != null && !Validation.pixKey(account.pixKey())) throw new AccountInvalidValueException(String.format(message, "pixKey"));

        if(account.brideGroom() != null) this.brideGroom = account.brideGroom().trim();
        if(account.weddingDate() != null) this.weddingDate = account.weddingDate();
        if(account.firstName() != null) this.firstName = account.firstName().trim();
        if(account.lastName() != null) this.lastName = account.lastName().trim();
        if(account.password() != null) this.password = account.password().trim();
        if(account.pixKey() != null) this.pixKey = account.pixKey().trim().length() == 14 && account.pixKey().trim().charAt(11) == '-' 
                                                        ? account.pixKey().trim().replace(".", "").replace("-", "") 
                                                        : account.pixKey().trim();

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
