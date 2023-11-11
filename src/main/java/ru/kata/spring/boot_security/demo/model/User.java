package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "name cannot be empty")
    @Pattern(regexp = "[a-zA-Z]+", message = "Name can consist of only letters")
    @Column
    private String username;

    @NotEmpty(message = "Name of your country cannot be empty")
    @Pattern(regexp = "[a-zA-Z]+", message = "Country name can consist of only letters")
    private String countryOfBirth;

    @Column(name = "password")
    private String password;

    @Column
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Set<Role> roles;

    public User() {

    }

    public User(String name, String countryOfBirth, String password, Set<Role> roles) {
        this.username = name;
        this.countryOfBirth = countryOfBirth;
        this.password = password;
        this.roles = roles;
    }

    public long getId() {
            return id;
        }

    public void setId(long id) {
            this.id = id;
        }

    public String getName() {
            return username;
        }

    public void setName(String name) {
            this.username = name;
        }

    public String getCountryOfBirth() {
            return countryOfBirth;
        }

    public void setCountryOfBirth(String countryOfBirth) {
            this.countryOfBirth = countryOfBirth;
        }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
            return password;
        }

    @Override
    public String getUsername() {
        return username;
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

    public void setPassword(String password) {
            this.password = password;
        }

    public Set<Role> getRoles() {
            return roles;
        }

    public void setRoles(Set<Role> roles) {
            this.roles = roles;
        }

    public String getStringRoles() {
        StringBuilder s = new StringBuilder();
        for (Role role : roles) {
            s.append(" ").append(role.getName().substring(5));
        }
        return String.valueOf(s);
    }
}
