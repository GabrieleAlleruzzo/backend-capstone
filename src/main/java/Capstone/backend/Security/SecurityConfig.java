package Capstone.backend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration       // Indica che questa è una classe di configurazione Spring
@EnableWebSecurity   // Abilita la configurazione della sicurezza web di Spring
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable());

        httpSecurity.sessionManagement(http->http.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.cors(Customizer.withDefaults());
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/portfolio/all").permitAll());
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/portfolio/ID/**").permitAll());
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/auth/**").permitAll());
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers(HttpMethod.POST, "/commissioni/addCommissione").permitAll());
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers(HttpMethod.DELETE).authenticated());
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/commissioni/**").authenticated());




        httpSecurity.authorizeHttpRequests(http->http.requestMatchers(HttpMethod.POST, "/portfolio/upload-with-data").authenticated());

        httpSecurity.authorizeHttpRequests(http->http.requestMatchers(HttpMethod.PUT).authenticated());


        httpSecurity.authorizeHttpRequests(http->http.anyRequest().denyAll());
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();// Costruisce e restituisce la configurazione della catena di filtri
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
}
