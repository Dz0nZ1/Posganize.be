package com.example.posganize.entities;


import com.example.posganize.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Column(name = "user_id")
    private Long user_id;
    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    @Email(message = "Email address must be in valid format")
    @Column(name = "email", unique = true, nullable = false)
    @NotBlank(message = "Email is mandatory")
    private String email;
    @Column(name = "trail_training")
    private boolean trailTraining;
    @Column(name = "phone_number", nullable = false)
    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;
    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
    @Column(name = "image", length = 5000000)
    @Lob
    private byte[] image;
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Token> tokens;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Membership> memberships;
    @JsonManagedReference
    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ClubNews> clubNews;

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
