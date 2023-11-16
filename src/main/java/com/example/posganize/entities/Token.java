package com.example.posganize.entities;


import com.example.posganize.enums.TokenTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String token;
    @Enumerated(EnumType.STRING)
    private
    TokenTypeEnum tokenTypeEnum;
    private boolean expired;
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

}
