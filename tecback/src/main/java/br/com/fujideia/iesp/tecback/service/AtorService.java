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
import br.com.fujideia.iesp.tecback.repository.FilmeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AtorService {

    private final AtorRepository atorRepository;

    public List<AtorDTO> listarTodos() {
        return atorRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<AtorDTO> buscarPorId(Long id) {
        return atorRepository.findById(id)
                .map(this::convertToDTO);
    }

    public AtorDTO criarAtor(AtorDTO atorDTO) {
        Ator ator = convertToEntity(atorDTO);
        return convertToDTO(atorRepository.save(ator));
    }

    public Optional<AtorDTO> atualizarAtor(Long id, AtorDTO atorDTO) {
        return atorRepository.findById(id).map(ator -> {
            ator.setNome(atorDTO.getNome());
            ator.setId(atorDTO.getId());

            return convertToDTO(atorRepository.save(ator));
        });
    }

    public boolean deletarAtor(Long id) {
        if (atorRepository.existsById(id)) {
            atorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private AtorDTO convertToDTO(Ator ator) {
        return new AtorDTO(
                ator.getId(),
                ator.getNome());

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
