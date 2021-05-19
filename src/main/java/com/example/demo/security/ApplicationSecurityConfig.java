package com.example.demo.security;

import com.example.demo.services.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.concurrent.TimeUnit;

import static com.example.demo.security.UserRole.*;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // для можливості викликів @PreAuthorized на енд-поінтах
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;

    @Autowired
    ApplicationSecurityConfig(PasswordEncoder passwordEncoder, /*@Qualifier("applicationUserService")*//* UserDetailsService userDetailsService*/
                              ApplicationUserService applicationUserService){
        this.passwordEncoder = passwordEncoder;
//        this.userDetailsService = userDetailsService;
        this.applicationUserService = applicationUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //csrf.disable enables post, put, delete mapping
                //cross site request forgery - need to be used in web-browser apps, when program will be working not using web-browser, then csrf using is unnecessary
//                .csrf().disable()
                .csrf().csrfTokenRepository( // logout is post, so it must be handled like post method when try to log out
                        // this disables that cookie will be unaccessable from the client-side script (JavaScript)
                        CookieCsrfTokenRepository.withHttpOnlyFalse())// spring security doing this by default
                .and()
                .authorizeRequests()
              /*  .antMatchers(HttpMethod.DELETE, "/management/students/**").hasAuthority(UserPermission.COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.POST, "/management/students/**").hasAuthority(UserPermission.COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/management/students/**").hasAuthority(UserPermission.COURSE_WRITE.getPermission())
                .antMatchers( HttpMethod.GET, "/management/students/**").hasAnyRole(ADMIN.name(),ADMINTRAINEE.name())*/
                .antMatchers("/api/**").permitAll()
                .antMatchers("/registration/**").permitAll()
//                .antMatchers("/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/courses")//default end-point after login
//                    .passwordParameter("password")//сопоставимо с <input name="password"> in the login.html
//                    .usernameParameter("username")
                .and()
                .rememberMe()//Session time is extended  to 2 weeks instead of 30 minutes
                    .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
                    .key("verySecuredKeyXD")
                    .rememberMeParameter("remember-me")
                .and()
                .logout()
                    .logoutUrl("/logout") ///logout is default; if using csrf() enabled than /logout must be POST method
                //.logoutRequestMathcer(new AntPathRequestMatcher("logout", "GET")) // use when csrf is disabled otherwise dont use
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID" , "remember-me" )
                    .logoutSuccessUrl("/login");
//                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
   /* @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails me = User.builder()
                .username("muriel")
                .password(passwordEncoder.encode("muriel74"))
//                .roles(STUDENT.name())
                .authorities(STUDENT.getGrantedPermissions())
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
//                .roles(ADMIN.name())
                .authorities(ADMIN.getGrantedPermissions())
                .build();
        UserDetails tom = User.builder()
                .username("adminTom")
                .password(passwordEncoder.encode("admin123"))
//                .roles(ADMINTRAINEE.name())
                .authorities(ADMINTRAINEE.getGrantedPermissions())
                .build();
        return new InMemoryUserDetailsManager(me,admin,tom);
    }*/
}
