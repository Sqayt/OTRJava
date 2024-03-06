//package ru.ivanovds.tasks.demo.config;
//
//import io.swagger.models.HttpMethod;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
//
//@Configuration
//@EnableWebSecurity
//@Profile("!https")
//public class SecSecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
//                .authorizeRequests(request -> request.requestMatchers("/task/admin/**")
//                        .hasRole("ADMIN")
//                        .requestMatchers("/task/anonymous*")
//                        .anonymous()
//                        .requestMatchers(HttpMethod.GET, "/index*", "/static/**", "/*.js", "/*.json", "/*.ico", "/rest")
//                        .permitAll()
//                        .anyRequest()
//                        .authenticated())
//                .formLogin(form -> form.loginPage("/index.html")
//                        .loginProcessingUrl("/perform_login")
//                        .defaultSuccessUrl("/homepage.html", true)
//                        .failureUrl("/index.html?error=true"))
//                .logout(logout -> logout.logoutUrl("/perform_logout")
//                        .deleteCookies("JSESSIONID"))
//                .build();
//    }
//}
