package ferramentas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ferramentas.config.security.TokenConfig;
import ferramentas.dto.request.LoginRequest;
import ferramentas.dto.request.RegisterUserRequest;
import ferramentas.dto.response.LoginResponse;
import ferramentas.dto.response.RegisterUserResponse;
import ferramentas.loggin.logger.AuditLogger;
import ferramentas.model.authentication.Role;
import ferramentas.model.authentication.User;
import ferramentas.repository.UserRepository;

import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;

    @Autowired
    AuditLogger auditLogger;


    public AuthService( UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenConfig tokenConfig) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
    }

    public LoginResponse Login (LoginRequest request ){

        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = authenticationManager.authenticate(userAndPass);

        User user = (User) authentication.getPrincipal();
        String token = tokenConfig.generateToken(user);

        auditLogger.logLogin(user.getId(), user.getEmail(),user.getRoles().toString());
        return new LoginResponse(token);

    }

    public RegisterUserResponse Register(RegisterUserRequest request) {


        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setEmail(request.email());
        newUser.setName(request.name());
        newUser.setRoles(request.role() != null? Set.of(request.role()) : Set.of(Role.ROLE_USER) );
        userRepository.save(newUser);

        return new RegisterUserResponse(newUser.getName(), newUser.getEmail());

    }
}