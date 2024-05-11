package org.example.bank_application.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.example.bank_application.enums.Role;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
    @Table(name = "account_user")
    public class AccountUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "first_name")
    @NotBlank(message = "First name must not be blank")
    @NotNull()
    @Length(min = 3, max = 20)
    private String firstName;
    @Column(name = "last_name")
    @NotNull
    @NotBlank
    @Length(min = 4, max = 20)
    private String lastName;
    private String middleName;
    @Column(name = "username", unique = true)
    @Email
    @Length(min = 5, max = 50)
    private String username;
//   @NotBlank
//    @NotNull
//    @Length(min = 5, max = 20, message = "Your password must be more than 5 characters and less than 20")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,20}$",
//            message = "Password must be 8-20 characters long and include at least one lowercase letter, one uppercase letter, one digit, and one special character.")
    private String password;

    @NotBlank
    @NotNull
    @Pattern(regexp = "[0-9]{11}", message = "Phone number must be 11 digits")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }


}
