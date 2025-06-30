package com.students.service;

import com.students.dto.AlunoRequest;
import com.students.dto.AlunoResponse;
import com.students.dto.MatriculaDTO;
import com.students.entity.Aluno;
import com.students.entity.Matricula;
import com.students.mapper.AlunoMapper;
import com.students.repository.AlunoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlunoService {

    private final AlunoMapper alunoMapper;
    private final AlunoRepository alunoRepository;
    private LocalDate dataNascimento;

    public AlunoService(AlunoMapper alunoMapper, AlunoRepository alunoRepository) {
        this.alunoMapper = alunoMapper;
        this.alunoRepository = alunoRepository;
    }

    public AlunoResponse salvar(AlunoRequest request) {
        Aluno aluno = alunoMapper.toEntity(request);
        alunoRepository.save(aluno);
        return alunoMapper.toResponse(aluno);
    }

    public List<AlunoResponse> listar() {
        return alunoRepository.findAll().stream().map(alunoMapper::toResponse).toList();
    }

    public List<MatriculaDTO> listarMatriculas(Long id) {
        Aluno aluno = alunoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
        return aluno.getMatriculas().stream().map(m -> new MatriculaDTO(m.getCodigoMatricula(), m.getNomeCurso(), m.getDataInicio())).toList();
    }

    public AlunoResponse atualizar(Long id, AlunoRequest request) {
        Aluno aluno = alunoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
        aluno.setNome(request.nome());
        aluno.setTelefone(request.telefone());
        aluno.setDataNascimento(dataNascimento);

        for (MatriculaDTO m : request.matriculas()) {
            Matricula matricula = new Matricula();
            matricula.setCodigoMatricula(m.codigoMatricula());
            matricula.setDataInicio(m.dataInicio());
            matricula.setNomeCurso(m.nomeCurso());
            aluno.getMatriculas().add(matricula);
        }

        return alunoMapper.toResponse(alunoRepository.save(aluno));
    }

    public void remover(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new EntityNotFoundException("ID do aluno não encontrado");
        }

        alunoRepository.deleteById(id);
    }
}
