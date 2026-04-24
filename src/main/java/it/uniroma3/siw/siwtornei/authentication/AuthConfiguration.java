package it.uniroma3.siw.siwtornei.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Importiamo la tua classe handler creata in precedenza
import it.uniroma3.siw.siwtornei.controller.UrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

    // HO RIMOSSO L'AUTOWIRED CHE CAUSAVA IL CRASH!

    @Bean
    protected SecurityFilterChain configure(final HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // 1. Pagine pubbliche accessibili a tutti
                .requestMatchers(HttpMethod.GET, "/", "/index", "/tornei", "/torneo/**","/squadre", "/squadra/**", "/partite", "/partita/**","/arbitri","/arbitro/**","/giocatore/**", "/classifica/**", "/registrazione", "/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/registrazione", "/login").permitAll()
                
                // 2. Risorse statiche e PAGINA DI ERRORE
                .requestMatchers("/css/**", "/images/**", "/error").permitAll()
                
                // 3. Solo l'ADMIN può accedere alle funzionalità di gestione
                .requestMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority("ADMIN")
                
                // 4. Tutti gli altri devono essere autenticati
                .anyRequest().authenticated()
            )
            // Configurazione del Login
            .formLogin(form -> form
                .loginPage("/login")
                // CREIAMO L'OGGETTO DIRETTAMENTE QUI: Problema risolto!
                .successHandler(new UrlAuthenticationSuccessHandler())
                .failureUrl("/login?error=true")
                .permitAll()
            )
            // Configurazione del Logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
            );

        return http.build();
    }

    // Bean per criptare le password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}