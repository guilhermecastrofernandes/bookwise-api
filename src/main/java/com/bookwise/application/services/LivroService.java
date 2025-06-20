package com.bookwise.application.services;

import com.bookwise.adapters.in.rest.exception.AccessDeniedException;
import com.bookwise.adapters.in.rest.exception.NotFoundException;
import com.bookwise.domain.model.Livro;
import com.bookwise.domain.ports.in.LivroUseCase;
import com.bookwise.domain.ports.out.LivroRepositoryPort;
import com.bookwise.infrastructure.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LivroService implements LivroUseCase {

    private final LivroRepositoryPort livroRepository;

    public LivroService(LivroRepositoryPort livroRepository) {
        this.livroRepository = livroRepository;
    }


    @Override
    public Livro cadastrarLivro(Livro livro) {

        String email = SecurityUtils.getEmailUsuarioAutenticado();
        livro.setUsuario(email);
        return livroRepository.salvar(livro);
    }

    @Override
    public List<Livro> buscarTodos() {
        return livroRepository.buscarTodos();
    }





    @Override
    public List<Livro> buscarPorFiltros(String titulo, String autor, String genero, LocalDate dataInicio, LocalDate dataFim) {
        return livroRepository.buscarPorFiltro(titulo,autor, genero, dataInicio, dataFim);
    }



    @Override
    public List<Livro> buscarGenerosFavoritosPorUsuario(String emailUsuario) {
        List<Livro> todosLivros = livroRepository.buscarTodos();
        List<Livro> livrosDoUsuario = livroRepository.buscarLivrosPorUsuario(emailUsuario);


        Set<String> generosFavoritos = livrosDoUsuario.stream()
                .filter(l -> l.isLido() && l.getNota() != null && l.getNota() >= 4)
                .flatMap(l -> l.getGeneros().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        if (generosFavoritos.isEmpty()) return List.of();


        Set<Long> idsLidos = livrosDoUsuario.stream()
                .map(Livro::getId)
                .collect(Collectors.toSet());


        return todosLivros.stream()
                .filter(l -> !idsLidos.contains(l.getId()))
                .filter(l -> l.getGeneros().stream().anyMatch(generosFavoritos::contains))
                .limit(10)
                .toList();
    }

    @Override
    public void deletarLivro(Long id) {
        Livro livro = livroRepository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException("Livro não encontrado"));

        String email = SecurityUtils.getEmailUsuarioAutenticado();
        if (!livro.getUsuario().equals(email)) {
            throw new AccessDeniedException("Usuário não autorizado a deletar este livro");
        }

        livroRepository.deletar(id);
    }

    @Override
    public Livro atualizarLivro(Long id, Livro domain) {
        Livro existente = livroRepository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException("Livro não encontrado"));

        String email = SecurityUtils.getEmailUsuarioAutenticado();
        if (!existente.getUsuario().equals(email)) {
            throw new AccessDeniedException("Usuário não autorizado a atualizar este livro");
        }


        existente.setTitulo(domain.getTitulo());
        existente.setAutor(domain.getAutor());
        existente.setGeneros(domain.getGeneros());
        existente.setLido(domain.isLido());
        existente.setDataLeitura(domain.getDataLeitura());
        existente.setNota(domain.getNota());

        return livroRepository.salvar(existente);

    }


}
