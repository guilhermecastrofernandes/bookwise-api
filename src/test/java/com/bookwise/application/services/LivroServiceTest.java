package com.bookwise.application.services;

import com.bookwise.domain.model.Livro;
import com.bookwise.domain.ports.out.LivroRepositoryPort;
import com.bookwise.infrastructure.security.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @Mock
    private LivroRepositoryPort livroRepository;
    private LivroService livroService;

    @BeforeEach
    void setup() {
        livroService = new LivroService(livroRepository);
    }

    @Test
    void deveCadastrarLivroComUsuario() {
        Livro livro = new Livro();
        livro.setTitulo("Clean Code");
        livro.setAutor("Robert C. Martin");



        Livro livroComUsuario = new Livro();
        livroComUsuario.setTitulo("Clean Code");
        livroComUsuario.setAutor("Robert C. Martin");
        livroComUsuario.setUsuario("user@example.com");

        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getEmailUsuarioAutenticado)
                    .thenReturn("user@example.com");

            when(livroRepository.salvar(any())).thenReturn(livroComUsuario);

            Livro resultado = livroService.cadastrarLivro(livro);

            assertNotNull(resultado);
            assertEquals("user@example.com", resultado.getUsuario());
        }
    }

    @Test
    void deveBuscarTodosLivros() {
        Livro livro = new Livro();
        livro.setTitulo("Livro A");
        when(livroRepository.buscarTodos()).thenReturn(List.of(livro));

        List<Livro> livros = livroService.buscarTodos();
        assertEquals(1, livros.size());
        assertEquals("Livro A", livros.get(0).getTitulo());
    }

    @Test
    void deveBuscarPorFiltros() {
        LocalDate inicio = LocalDate.of(2023, 1, 1);
        LocalDate fim = LocalDate.of(2023, 12, 31);
        Livro livro = new Livro();
        livro.setTitulo("Livro Filtrado");

        when(livroRepository.buscarPorFiltro("Livro Filtrado", null, null, inicio, fim))
                .thenReturn(List.of(livro));

        List<Livro> resultado = livroService.buscarPorFiltros("Livro Filtrado", null, null, inicio, fim);
        assertEquals(1, resultado.size());
        assertEquals("Livro Filtrado", resultado.get(0).getTitulo());
    }

    @Test
    void deveRecomendarLivrosBaseadoNosGenerosFavoritos() {
        Livro livroLido = new Livro()
                .setId(1L)
                .setTitulo("Livro 1")
                .setAutor("Autor 1")
                .setUsuario("user@example.com")
                .setLido(true)
                .setNota(5)
                .setGeneros(Set.of("Fantasia"));

        Livro livroNaoLido = new Livro()
                .setId(2L)
                .setTitulo("Livro 2")
                .setAutor("Autor 2")
                .setUsuario("user@example.com")
                .setLido(true)
                .setNota(5)
                .setGeneros(Set.of("Fantasia"));

        Livro recomendacao = new Livro()
                .setId(3L)
                .setTitulo("Livro 3")
                .setAutor("Autor 3")
                .setUsuario("outro@example.com")
                .setLido(false)   // Não lido pelo usuário
                .setGeneros(Set.of("Fantasia"));

        when(livroRepository.buscarTodos()).thenReturn(List.of(livroLido, livroNaoLido, recomendacao));
        when(livroRepository.buscarLivrosPorUsuario("user@example.com")).thenReturn(List.of(livroLido, livroNaoLido));

        List<Livro> recomendados = livroService.buscarGenerosFavoritosPorUsuario("user@example.com");

        assertEquals(1, recomendados.size());
        assertEquals("Livro 3", recomendados.get(0).getTitulo());
    }


    @Test
    void naoDeveRecomendarLivrosSeNaoHouverGenerosFavoritos() {
        Livro livroSemNota = new Livro();

        when(livroRepository.buscarTodos()).thenReturn(List.of(livroSemNota));
        when(livroRepository.buscarLivrosPorUsuario("user@example.com")).thenReturn(List.of(livroSemNota));

        List<Livro> recomendados = livroService.buscarGenerosFavoritosPorUsuario("user@example.com");

        assertTrue(recomendados.isEmpty());
    }
}

