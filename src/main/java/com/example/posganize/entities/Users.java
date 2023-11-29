package com.example.posganize.entities;


import com.example.posganize.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long user_id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean trailTraining;
    private String phoneNumber;
    private LocalDateTime registrationDate;
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
    @OneToMany(mappedBy = "users")
    private List<Token> tokens;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Membership> memberships;
    @JsonManagedReference
    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Payments payments;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
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
