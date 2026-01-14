package br.com.brunolutterbach.ticketmasterapi.security;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class Roles {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

}
