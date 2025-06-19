package com.bookwise.adapters.in.rest.dto;

import java.time.LocalDate;

public record UsuarioResponse(Long id, String nome, String email, LocalDate dataNascimento) {}
