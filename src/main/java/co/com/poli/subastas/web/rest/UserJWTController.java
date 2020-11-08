package co.com.poli.subastas.web.rest;

import co.com.poli.subastas.domain.Cliente;
import co.com.poli.subastas.repository.ClienteRepository;
import co.com.poli.subastas.security.jwt.JWTFilter;
import co.com.poli.subastas.security.jwt.TokenProvider;
import co.com.poli.subastas.web.rest.vm.LoginVM;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    
    private final ClienteRepository clienteRepository;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, ClienteRepository clienteRepository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.clienteRepository = clienteRepository;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }
    
    @PostMapping("/authenticate_mobile")
    public ResponseEntity<JWTToken> authorizeMobile(@Valid @RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());
        String clientStatus = "anonymousClientNotBidder";
        List<Cliente> listClient = clienteRepository.findByIdusuario(loginVM.getUsername());
        if(!listClient.isEmpty()){
            Cliente client = clienteRepository.findByIdusuario(loginVM.getUsername()).get(0);
            clientStatus = client.getEstadocliente().getCode();
        }
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt,clientStatus), httpHeaders, HttpStatus.OK);
    }
    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;
        private String clientStatus;
        
        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        JWTToken(String idToken, String clientStatus) {
            this.idToken = idToken;
            this.clientStatus = clientStatus;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
        
        @JsonProperty("client_status")
        public String getClientStatus() {
            return clientStatus;
        }

        public void setClientStatus(String clientStatus) {
            this.clientStatus = clientStatus;
        }
        
        
    }
}
