package com.socgen.kata.bank_account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "client_id")
    private Integer clientId;

    @NotEmpty
    @Column(name = "firstname")
    private String firstname;

    @NotEmpty
    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

}
