package com.bkrwin.ufast.infra.security;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig
        extends WebSecurityConfigurerAdapter {
    public void configure(WebSecurity web)
            throws Exception {
        web.ignoring().antMatchers(new String[]{"/static/**"});
    }

    protected void configure(HttpSecurity http)
            throws Exception {
        ((HttpSecurity) ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)
                ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)
                        ((HttpSecurity) http.formLogin().loginPage("/login").and())
                                .authorizeRequests()
                                .antMatchers(new String[]{"/login", "/loginSuccess", "/oauth/authLogin", "/oauth/authorize", "/oauth/token", "/oauth/user-me", "/oauth/remove_token", "/oauth2/logout"}))
                        .permitAll().anyRequest()).authenticated().and()).csrf().disable();
    }
}
