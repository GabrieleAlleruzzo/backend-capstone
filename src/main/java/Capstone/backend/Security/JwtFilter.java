package Capstone.backend.Security;


import Capstone.backend.exception.NotFoundException;
import Capstone.backend.model.Admin;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;


@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTool jwtTool;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");


        if (authorization == null || !authorization.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token non presente o non valido. Non sei autorizzato ad usare il servizio richiesto.");
            return; // Importante: termina l'esecuzione del filtro qui
        }


        String token = authorization.substring(7);

        try {
            jwtTool.validateToken(token);
            Admin admin = jwtTool.getUserFromToken(token);
            Authentication authentication = new UsernamePasswordAuthenticationToken(admin, null, admin.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);

        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT scaduto. Accedi nuovamente.");
        } catch (io.jsonwebtoken.MalformedJwtException | io.jsonwebtoken.security.SignatureException e) {

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT non valido o firma errata.");
        } catch (NotFoundException e) {

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Utente collegato al token non trovato.");
        } catch (Exception e) {

            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore interno durante l'autenticazione.");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String[] excludedEndpoints = new String[]{
                "/auth/**",
                "/commissioni/addCommissione", // Aggiunto per le richieste POST
                "/portfolio/all",
                "/portfolio/ID/**"
        };

        String requestPath = request.getServletPath();
        return Arrays.stream(excludedEndpoints)
                .anyMatch(e -> new AntPathMatcher().match(e, requestPath));
    }
}