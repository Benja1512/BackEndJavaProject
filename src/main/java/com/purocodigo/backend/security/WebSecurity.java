package com.purocodigo.backend.security;

import com.purocodigo.backend.services.UserServiceInterface;
import jakarta.servlet.DispatcherType;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.security.authorization.AuthenticatedAuthorizationManager.authenticated;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserServiceInterface userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    public WebSecurity(UserServiceInterface userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST,"/users").permitAll()
                .anyRequest().authenticated();
                .authenticated().and().addFilter(getAuthenticationFilter()
                .addFilter(new AuthorizationFilter(authenticationManager()))
                .sessionManagement();
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private void sessionCreationPolicy(SessionCreationPolicy sessionCreationPolicy) {

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, "/users").permitAll()
                        .antMatchers(HttpMethod.GET,"/posts/last").permitAll().antMatchers(HttpMethod.GET,"/posts/{id}")
                        .permitAll()
                        .anyRequest().Authenticaded().and().addFilter(getAuthenticationFilter())
                        .addFilter( new AuthenticationFilter(authenticationManager())).sessionManager()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private AuthenticationManager authenticationManager() {
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    public AuthenticationFilter getAuthenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());

        filter.setFilterProcessesUrl("/users/login");

        return filter;

    }

}

