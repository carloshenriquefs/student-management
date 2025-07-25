package com.students.dto;

import java.time.LocalDate;
import java.util.List;

public record AlunoRequest(
        String nome,
        String telefone,
        LocalDate dataNascimento,
        String codigoMatricula,
        List<MatriculaDTO> matriculas
) {
}
