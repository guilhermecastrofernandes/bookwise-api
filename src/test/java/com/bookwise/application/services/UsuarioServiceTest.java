package com.bookwise.application.services;

import com.bookwise.domain.model.Usuario;
import com.bookwise.domain.ports.out.UsuarioRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepo;

    @Mock
    private PasswordEncoder encoder;

    private UsuarioService usuarioService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        usuarioService = new UsuarioService(usuarioRepo, encoder);
    }

    @Test
    void deveCadastrarUsuarioComSenhaCriptografada() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@example.com");
        usuario.setSenha("senha123");

        when(encoder.encode("senha123")).thenReturn("senhaCriptografada");
        when(usuarioRepo.salvar(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario usuarioSalvo = usuarioService.cadastrar(usuario);

        assertNotNull(usuarioSalvo);
        assertEquals("senhaCriptografada", usuarioSalvo.getSenha());
        verify(encoder).encode("senha123");
        verify(usuarioRepo).salvar(usuarioSalvo);
    }

    @Test
    void deveBuscarUsuarioPorEmailExistente() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@example.com");

        when(usuarioRepo.buscarPorEmail("teste@example.com")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.buscarPorEmail("teste@example.com");

        assertTrue(resultado.isPresent());
        assertEquals("teste@example.com", resultado.get().getEmail());
        verify(usuarioRepo).buscarPorEmail("teste@example.com");
    }

    @Test
    void deveRetornarVazioSeUsuarioNaoEncontrado() {
        when(usuarioRepo.buscarPorEmail("naoexiste@example.com")).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioService.buscarPorEmail("naoexiste@example.com");

        assertTrue(resultado.isEmpty());
        verify(usuarioRepo).buscarPorEmail("naoexiste@example.com");
    }
}
