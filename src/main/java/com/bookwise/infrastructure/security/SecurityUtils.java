package com.bookwise.infrastructure.security;

import com.bookwise.domain.exception.NaoAuthenticadoException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static String getEmailUsuarioAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new NaoAuthenticadoException(auth.getName());
        }
        return auth.getName();
    }
}


//Exemplo:
//String email = SecurityUtils.getEmailUsuarioAutenticado();

