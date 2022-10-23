package com.struninproject.onlinestore.model;

import com.struninproject.onlinestore.model.enums.Gender;
import com.struninproject.onlinestore.model.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

/**
 * The {@code User} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotBlank
    @Email()
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = """
                    Not Valid Email, example:
                    username@domain.com
                    user.name@domain.com
                    user-name@domain.com
                    username@domain.co.in
                    user_name@domain.com""")
    @Column(unique=true)
    private String email;
    @Pattern(regexp = "^\\S+$", message = "Your password must not contain spaces")
    @Pattern(regexp = "^.*[!@#$%&*_?]+.*$", message = "Your password must contain special characters \"!@#$%&*_?\"")
    @Pattern(regexp = "^.*[A-Z]+.*$", message = "Your password must contain upper letters")
    @Pattern(regexp = "^.*[0-9]+.*$", message = "Your password must contain number")
    @Pattern(regexp = "^.*[a-z]+.*$", message = "Your password must contain lower letter")
    @Size(min = 8, max = 20, message = "Your password must be 8-20 characters long")
    private String password;
    @NotBlank
    @Size(min = 3, max = 50)
    private String firstName;
    @NotBlank
    @Size(min = 3, max = 50)
    private String lastName;
    @Min(value = 7)
    @Max(value = 100)
    private int age;
    private boolean active;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
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
        return active;
    }
}
