package tela.login.login.securaty;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tela.login.login.Util.JwtUtil;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
    
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
    
            try {
                Claims claims = jwtUtil.extractClaims(token);
    
                if (claims == null) {
                    throw new Exception("Token inválido ou expirado");
                }
    
                String nome = claims.get("nome", String.class);  // Alterado para "nome"
                String role = claims.get("role", String.class);
    
                if (nome == null || role == null) {
                    throw new Exception("Token sem informações suficientes");
                }
    
                List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(nome, null, authorities);
    
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Usuário autenticado: " + nome + " - Role: " + role);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Erro ao processar o token: " + e.getMessage());
                return;
            }
        }
    
        chain.doFilter(request, response);
    }
}
