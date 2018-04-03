package org.paulushc.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.paulushc.model.AccountCredentials;
import org.paulushc.service.TokenAuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	public JWTLoginFilter(String url, AuthenticationManager authManager) {
            super(new AntPathRequestMatcher(url));
            setAuthenticationManager(authManager);
        }

        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
            AccountCredentials credentials = new ObjectMapper().readValue(request.getInputStream(), AccountCredentials.class);
            return getAuthenticationManager().authenticate( new UsernamePasswordAuthenticationToken(credentials.getUsername(),credentials.getPassword(),Collections.emptyList()) );
        }

        @Override
        protected void successfulAuthentication(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain,Authentication auth) {
            TokenAuthenticationService.addAuthentication(response, auth.getName());
            log.info("Succes on Loging In With User {} -> Generating Authorization: {}",auth.getName(),response.getHeader("Authorization"));

            try {
                AccountCredentials accountCredentials = new AccountCredentials(auth.getName(),"",response.getHeader("Authorization"));
                response.setContentType("application/json");
                PrintWriter pr = response.getWriter();
                new ObjectMapper().writeValue(pr,accountCredentials);
            } catch (IOException e) { log.error("Error while parsing the data to the body",e); }

        }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
	    log.error("Fail to log in");
        super.unsuccessfulAuthentication(request, response, failed);
    }


}
