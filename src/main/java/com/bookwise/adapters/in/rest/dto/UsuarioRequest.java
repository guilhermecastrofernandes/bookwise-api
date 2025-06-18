package com.bookwise.adapters.in.rest.dto;

import java.time.LocalDate;

public record UsuarioRequest(String nome, String email, String senha, LocalDate dataNascimento) {}


