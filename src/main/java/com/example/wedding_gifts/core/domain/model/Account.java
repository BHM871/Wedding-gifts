package com.example.wedding_gifts.core.domain.model;

import java.util.List;

import org.springframework.lang.NonNull;

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
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @NonNull
    @Column(unique = true)
    private String brideAndGroom;

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
    private List<Gifts> gifts;

    public Account(CreateAccountDTO account){
        this.brideAndGroom = account.brideAndGroom();
        this.firstName = account.firstName();
        this.lastName = account.lastName();
        this.email = account.email();
        this.password = account.password();
        this.pixKey = account.pixKey();
    }

    public Account update(UpdateAccountDTO account) throws Exception {
        String message = "Some value is invalid";

        if(account.brideAndGroom().length() > 3) this.brideAndGroom = account.brideAndGroom();
        else if(account.brideAndGroom() != null) throw new Exception(message);

        if(account.firstName().length() > 3) this.firstName = account.firstName();
        else if (account.firstName() != null) throw new Exception(message);

        if(account.lastName().length() > 3) this.lastName = account.lastName();
        else if(account.lastName() != null) throw new Exception(message);

        if(account.password().length() > 8) this.password = account.password();
        else if(account.password() != null) throw new Exception(message);

        if(account.pixKey().length() > 10) this.pixKey = account.pixKey();
        else if(account.pixKey() != null) throw new Exception(message);

        return this;
    }
    
}
