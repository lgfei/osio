package com.lgfei.osio.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.test.context.ActiveProfiles;

import javax.naming.directory.DirContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("local")
public class LdapAuthenticationTest {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private LdapContextSource ldapContextSource;

    @Test
    public void testLdapConnection() {
        try {
            DirContext ctx = ldapContextSource.getContext("cn=admin,dc=lgfei,dc=com", "admin");
            assertNotNull(ctx);
            System.out.println("LDAP connection successful");
            ctx.close();
        } catch (Exception e) {
            fail("LDAP connection failed: " + e.getMessage());
        }
    }
    
    @Test
    public void testDirectLdapBind() {
        try {
            // Test direct bind with user credentials
            DirContext ctx = ldapContextSource.getContext("uid=alice,ou=users,dc=lgfei,dc=com", "abc123");
            assertNotNull(ctx);
            System.out.println("Direct LDAP bind successful for alice");
            ctx.close();
        } catch (Exception e) {
            fail("Direct LDAP bind failed for alice: " + e.getMessage());
        }
    }

    @Test
    public void testAliceAuthentication() {
        try {
            System.out.println("Testing alice authentication...");
            UsernamePasswordAuthenticationToken token = 
                new UsernamePasswordAuthenticationToken("alice", "abc123");
            Authentication auth = authenticationManager.authenticate(token);
            
            assertNotNull(auth);
            assertTrue(auth.isAuthenticated());
            assertEquals("alice", auth.getName());
            System.out.println("Alice authentication successful: " + auth.getName());
            System.out.println("Authorities: " + auth.getAuthorities());
            System.out.println("Principal type: " + auth.getPrincipal().getClass().getName());
        } catch (AuthenticationException e) {
            System.err.println("Alice authentication failed with exception: " + e.getClass().getSimpleName());
            System.err.println("Error message: " + e.getMessage());
            e.printStackTrace();
            fail("Alice authentication failed: " + e.getMessage());
        }
    }

    @Test
    public void testBobAuthentication() {
        try {
            UsernamePasswordAuthenticationToken token = 
                new UsernamePasswordAuthenticationToken("bob", "abc123");
            Authentication auth = authenticationManager.authenticate(token);
            
            assertNotNull(auth);
            assertTrue(auth.isAuthenticated());
            assertEquals("bob", auth.getName());
            System.out.println("Bob authentication successful: " + auth.getName());
        } catch (AuthenticationException e) {
            fail("Bob authentication failed: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidPasswordAuthentication() {
        assertThrows(AuthenticationException.class, () -> {
            UsernamePasswordAuthenticationToken token = 
                new UsernamePasswordAuthenticationToken("alice", "wrongpassword");
            authenticationManager.authenticate(token);
        });
    }

    @Test
    public void testInvalidUserAuthentication() {
        assertThrows(AuthenticationException.class, () -> {
            UsernamePasswordAuthenticationToken token = 
                new UsernamePasswordAuthenticationToken("nonexistent", "abc123");
            authenticationManager.authenticate(token);
        });
    }
}