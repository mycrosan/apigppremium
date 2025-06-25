package br.compneusgppremium.api.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioLogadoUtil {

    public Long getUsuarioIdLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof String) {
            return Long.parseLong((String) auth.getPrincipal());
        }
        throw new RuntimeException("Usuário não autenticado");
    }
}
