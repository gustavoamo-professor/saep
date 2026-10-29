package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SistemaSeguranca {
    private final List<Usuario> usuariosCadastrados = new ArrayList<>();
    private final Map<String, Set<String>> rotasPrivadas = new HashMap<>();

    public SistemaSeguranca() {
        rotasPrivadas.put("gestao-usuarios", Set.of("ADMINISTRADOR"));
        rotasPrivadas.put("consultas", Set.of("ADMINISTRADOR", "OPERADOR"));
        rotasPrivadas.put("cadastro-dados", Set.of("ADMINISTRADOR", "OPERADOR"));
    }

    public void cadastrarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário inválido");
        }

        boolean jaExiste = usuariosCadastrados.stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(usuario.getUsername()));

        if (jaExiste) {
            throw new IllegalArgumentException("Usuário já cadastrado");
        }

        usuariosCadastrados.add(usuario);
    }

    public Usuario autenticar(String username, String senha) {
        for (Usuario usuario : usuariosCadastrados) {
            if (usuario.autenticar(username, senha)) {
                return usuario;
            }
        }
        return null;
    }

    public boolean acessarRota(Usuario usuario, String rota) {
        if (usuario == null) {
            return false;
        }

        Set<String> tiposPermitidos = rotasPrivadas.get(rota);
        if (tiposPermitidos == null) {
            return true;
        }

        return tiposPermitidos.contains(usuario.getTipoUsuario());
    }
}
