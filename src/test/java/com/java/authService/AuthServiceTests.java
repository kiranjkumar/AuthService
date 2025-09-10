package com.java.authService;

import com.java.authService.security.repositories.ClientRepository;
import com.java.authService.security.repositories.JpaRegisteredClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.UUID;

@SpringBootTest
public class AuthServiceTests {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder ;

    @Autowired
    private JpaRegisteredClientRepository clientRepository;

    //@Test
    public void addClient(){



        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("oidc-client")
                .clientSecret(bCryptPasswordEncoder.encode("secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("https://oauth.pstmn.io/v1/callback")
                .postLogoutRedirectUri("http://127.0.0.1:8080/")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope("Email")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        clientRepository.save(oidcClient);
    }
}
