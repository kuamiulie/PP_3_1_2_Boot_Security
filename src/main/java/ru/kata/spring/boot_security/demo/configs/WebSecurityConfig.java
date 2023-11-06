package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserDetailService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailService userDetailService;

    private SuccessUserHandler successUserHandler;


    @Autowired
    public void setSuccessUserHandler(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }

    @Autowired
    public void setUserDetailService(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers( "/auth/registration").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .failureUrl("/auth/login?error")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }



    // аутентификация inMemory
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("Amir")
//                        .password("1234")
//                        .roles("ADMIN")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
//}

//
//    @Bean
//    public JdbcUserDetailsManager users (DataSource datasource) {
//        UserDetails user = User.builder()
//                .username ("user")
//                .password("{bcrypt}$2a$12$bB0TKJu8Fz7Kf09pf82csewC124GjcIckXGUho7RzHljfH5uJo7TG")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username ("admin")
//                .password("{bcrypt$2a$12$cAykk4OaZn/Dz563KKfnjO.Oov0nI1YQX2X7GuBshBcA3nl5UnX3q")
//                .roles ("ADMIN", "USER")
//                .build();
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager (datasource);
//
//        if (users.userExists(user.getUsername())) {
//            users.deleteUser(user. getUsername());
//        }
//        if (users.userExists(admin.getUsername())) {
//            users.deleteUser(admin.getUsername());
//        }
//        users.createUser(user);
//        users.createUser(admin);
//        return users;
//    }

//    @PostConstruct
//    public void postConstruct() {
//        Role role1 = new Role("ROLE_ADMIN");
//        Role role2 = new Role("ROLE_USER");
//        roleService.addRole(role1);
//        roleService.addRole(role2);
//        Set<Role> roles_admin = new HashSet<>();
//        roles_admin.add(roleService.getRoleByName("ROLE_ADMIN"));
//        ru.kata.spring.boot_security.demo.model.User admin = new ru.kata.spring.boot_security.demo.model.User("Amir","12345", roles_admin);
//        userDetailService.addUser(admin);
//        Set<Role> roles_user = new HashSet<>();
//        roles_user.add(roleService.getRoleByName("ROLE_USER"));
//        ru.kata.spring.boot_security.demo.model.User user = new ru.kata.spring.boot_security.demo.model.User("user","1234", roles_user);
//        userDetailService.addUser(user);
//    }
}