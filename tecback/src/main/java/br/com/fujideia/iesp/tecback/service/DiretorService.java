package br.com.fujideia.iesp.tecback.service;

import br.com.fujideia.iesp.tecback.model.Ator;
import br.com.fujideia.iesp.tecback.model.Diretor;
import br.com.fujideia.iesp.tecback.model.Filme;
import br.com.fujideia.iesp.tecback.model.Genero;
import br.com.fujideia.iesp.tecback.model.dto.AtorDTO;
import br.com.fujideia.iesp.tecback.model.dto.DiretorDTO;
import br.com.fujideia.iesp.tecback.model.dto.FilmeDTO;
import br.com.fujideia.iesp.tecback.model.dto.GeneroDTO;
import br.com.fujideia.iesp.tecback.repository.AtorRepository;
import br.com.fujideia.iesp.tecback.repository.DiretorRepository;
import br.com.fujideia.iesp.tecback.repository.FilmeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiretorService {

    private final DiretorRepository diretorRepository;

    public List<DiretorDTO> listarTodos() {
        return diretorRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<DiretorDTO> buscarPorId(Long id) {
        return diretorRepository.findById(id)
                .map(this::convertToDTO);
    }

    public DiretorDTO criarDiretor(DiretorDTO diretorDTO) {
        Diretor diretor = convertToEntity(diretorDTO);
        return convertToDTO(diretorRepository.save(diretor));
    }

    public Optional<DiretorDTO> atualizarDiretor(Long id, DiretorDTO diretorDTO) {
        return diretorRepository.findById(id).map(diretor -> {
            diretor.setNome(diretorDTO.getNome());
            diretor.setId(diretorDTO.getId());

            return convertToDTO(diretorRepository.save(diretor));
        });
    }

    public boolean deletarDiretor(Long id) {
        if (diretorRepository.existsById(id)) {
            diretorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private DiretorDTO convertToDTO(Diretor diretor) {
        return new DiretorDTO(
                diretor.getId(),
                diretor.getNome());

    }


    private Filme convertToEntity(FilmeDTO filmeDTO) {
        Filme filme = new Filme();
        filme.setTitulo(filmeDTO.getTitulo());
        filme.setAnoLancamento(filmeDTO.getAnoLancamento());
        filme.setDiretor(convertToEntity(filmeDTO.getDiretor()));
        filme.setAtores(filmeDTO.getAtores()
                .stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList()));
        filme.setGeneros(filmeDTO.getGeneros()
                .stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList()));
        return filme;
    }

    private Diretor convertToEntity(DiretorDTO diretorDTO) {
        if (diretorDTO == null) {
            return null; // Retorne null caso não haja Diretor
        }
        Diretor diretor = new Diretor();
        diretor.setId(diretorDTO.getId());
        diretor.setNome(diretorDTO.getNome());
        return diretor;
    }

    private Ator convertToEntity(AtorDTO atorDTO) {
        Ator ator = new Ator();
        ator.setId(atorDTO.getId());
        ator.setNome(atorDTO.getNome());
        return ator;
    }

    private Genero convertToEntity(GeneroDTO generoDTO) {
        Genero genero = new Genero();
        genero.setId(generoDTO.getId());
        genero.setNome(generoDTO.getDescricao());
        return genero;
    }
}
