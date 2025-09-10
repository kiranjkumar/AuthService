package com.java.authService.services;

import com.java.authService.excetions.UserAlreadyExistsException;
import com.java.authService.excetions.WrongPasswordException;
import com.java.authService.excetions.newInvalidUsernameException;
import com.java.authService.models.Role;
import com.java.authService.models.Session;
import com.java.authService.models.User;
import com.java.authService.repositories.RoleRepository;
import com.java.authService.repositories.SessionRepository;
import com.java.authService.repositories.UserRespository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${jwt.secret}")
    private String key;

    @Autowired
    private RoleRepository roleRespository;

    public boolean signup(String email, String password) throws UserAlreadyExistsException {

        if (userRespository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists.");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        Role defaultRole = roleRespository.findByValue("USER_ROLE").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setValue("USER_ROLE");
            return roleRespository.save(newRole);
        });
        user.setRoles(Collections.singletonList(defaultRole));
        userRespository.save(user);
        return true;
    }

    public String login(String username, String password) throws newInvalidUsernameException, WrongPasswordException {

        Optional<User> userOptional = userRespository.findByEmail(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
                String token = generateToken(user.getId(), user.getEmail(), user.getRoles());
                Session s = new Session();
                s.setToken(token);
                s.setUser(user);
                s.setExpirationDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)); // Token valid for 10 hours
                sessionRepository.save(s);

                return token;
            } else {
                throw new WrongPasswordException("Invalid password for user: " + username);
            }
        } else {
            throw new newInvalidUsernameException("User with email " + username + " does not exist.");
        }

        // return "User signed up successfully: " + username;
    }

    private String generateToken(long id, String email, List<Role> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("email", email);
        claims.put("roles", roles);
        // Here you would typically use a JWT library to create a token with the claims
        // For example, using jjwt:
        io.jsonwebtoken.JwtBuilder builder = Jwts.builder();
        builder.claims(claims);
        builder.issuedAt(new Date());
        builder.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10));
        builder.signWith(Keys.hmacShaKeyFor(key.getBytes()));
        return builder.compact(); // Token valid for 10 hours

        //generate jwt token    }
    }
}
