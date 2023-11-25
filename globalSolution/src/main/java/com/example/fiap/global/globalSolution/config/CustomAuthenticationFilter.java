package com.example.fiap.global.globalSolution.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Mapear as credenciais do JSON da requisição para um objeto
            CustomAuthenticationRequest authenticationRequest =
                    new ObjectMapper().readValue(request.getInputStream(), CustomAuthenticationRequest.class);

            // Criar um token de autenticação
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    );

            // Autenticar o usuário
            return authenticationManager.authenticate(authenticationToken);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        // Se a autenticação for bem-sucedida, gerar e enviar o token JWT (ou outro mecanismo de autenticação)

        // Exemplo: Gerar um token JWT (você pode usar uma biblioteca como jjwt para isso)
        String token = "seu_token_jwt_aqui";

        // Adicionar o token ao cabeçalho da resposta
        response.addHeader("Authorization", "Bearer " + token);

        // Adicionar informações adicionais à resposta, se necessário
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("username", authResult.getName());
        responseBody.put("roles", authResult.getAuthorities());

        // Converter o corpo da resposta para JSON
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
        response.getWriter().flush();
        response.getWriter().close();
    }

    // Adicione outros métodos ou personalizações conforme necessário
}