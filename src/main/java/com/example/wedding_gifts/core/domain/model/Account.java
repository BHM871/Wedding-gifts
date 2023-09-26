package com.example.wedding_gifts.core.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.wedding_gifts.commun.LimitDateForAccount;
import com.example.wedding_gifts.core.domain.dtos.account.CreateAccountDTO;
import com.example.wedding_gifts.core.domain.dtos.account.UpdateAccountDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @OneToMany(cascade = {CascadeType.REMOVE})
    private List<Gift> gifts;

    public Account(CreateAccountDTO account){
        this.brideGroom = account.brideGroom();
        this.weddingDate = account.weddingDate();
        this.firstName = account.firstName();
        this.lastName = account.lastName();
        this.email = account.email();
        this.password = account.password();
        this.pixKey = account.pixKey();
        this.gifts = new ArrayList<Gift>();
    }

    public Account update(UpdateAccountDTO account) throws Exception {
        String message = "Some value is invalid";

        if(account.brideGroom() != null && account.brideGroom().length() > 3) this.brideGroom = account.brideGroom();
        else if(account.brideGroom() != null) throw new Exception(message);

        if(account.weddingDate() != null && account.weddingDate().getTime() > new Date().getTime()) this.weddingDate = account.weddingDate();
        else if(account.weddingDate() != null) throw new Exception(message);

        if(account.firstName() != null && account.firstName().length() > 3) this.firstName = account.firstName();
        else if (account.firstName() != null) throw new Exception(message);

        if(account.lastName() != null && account.lastName().length() > 3) this.lastName = account.lastName();
        else if(account.lastName() != null) throw new Exception(message);

        if(account.password() != null && account.password().length() > 8) this.password = account.password();
        else if(account.password() != null) throw new Exception(message);

        if(account.pixKey() != null && account.pixKey().length() > 10) this.pixKey = account.pixKey();
        else if(account.pixKey() != null) throw new Exception(message);

        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
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
