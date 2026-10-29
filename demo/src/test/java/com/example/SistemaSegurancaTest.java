package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SistemaSegurancaTest {

    @Test
    void devePermitirAcessoAdministradorAareaPrivada() {
        SistemaSeguranca sistema = new SistemaSeguranca();
        Administrador admin = new Administrador("Maria", "maria", "1234");

        sistema.cadastrarUsuario(admin);

        Usuario usuarioAutenticado = sistema.autenticar("maria", "1234");

        assertNotNull(usuarioAutenticado);
        assertTrue(sistema.acessarRota(usuarioAutenticado, "gestao-usuarios"));
    }

    @Test
    void deveBloquearOperadorDeRotaRestrita() {
        SistemaSeguranca sistema = new SistemaSeguranca();
        Operador operador = new Operador("Joao", "joao", "abcd");

        sistema.cadastrarUsuario(operador);

        Usuario usuarioAutenticado = sistema.autenticar("joao", "abcd");

        assertNotNull(usuarioAutenticado);
        assertFalse(sistema.acessarRota(usuarioAutenticado, "gestao-usuarios"));
    }
}
