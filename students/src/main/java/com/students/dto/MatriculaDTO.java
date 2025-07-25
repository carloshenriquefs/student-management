package com.students.dto;

import java.time.LocalDate;

public record MatriculaDTO(
        String codigoMatricula,
        String nomeCurso,
        LocalDate dataInicio
) {
}
