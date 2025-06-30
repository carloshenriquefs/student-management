package com.students.mapper;

import com.students.dto.AlunoRequest;
import com.students.dto.AlunoResponse;
import com.students.dto.MatriculaDTO;
import com.students.entity.Aluno;
import com.students.entity.Matricula;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AlunoMapper {

    public Aluno toEntity(AlunoRequest request) {
        Aluno aluno = new Aluno();
        aluno.setNome(request.nome());
        aluno.setDataNascimento(request.dataNascimento());
        aluno.setTelefone(request.telefone());
        List<Matricula> matriculas = request.matriculas().stream().map(m -> {

            Matricula matricula = new Matricula();
            matricula.setCodigoMatricula(m.codigoMatricula());
            matricula.setDataInicio(m.dataInicio());
            matricula.setNomeCurso(m.nomeCurso());
            matricula.setAluno(aluno);

            return matricula;

        }).toList();
        aluno.setMatriculas(matriculas);

        return aluno;
    }

    public AlunoResponse toResponse(Aluno aluno) {
        List<MatriculaDTO> matriculaDTOS = aluno.getMatriculas().stream().map(m ->
                new MatriculaDTO(m.getCodigoMatricula(), m.getNomeCurso(), m.getDataInicio())).toList();

        return new AlunoResponse(aluno.getId(), aluno.getNome(), aluno.getTelefone(), aluno.getDataNascimento(), matriculaDTOS);
    }

}
