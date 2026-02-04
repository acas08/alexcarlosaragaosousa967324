package com.seplag.acervo.service;

import com.seplag.acervo.domain.Artista;
import com.seplag.acervo.dto.ArtistaCompletoDto;
import com.seplag.acervo.dto.ArtistaDto;
import com.seplag.acervo.repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArtistaService {

    private final ArtistaRepository repository;

    public ArtistaService(ArtistaRepository repository) {
        this.repository = repository;
    }

    public Page<ArtistaCompletoDto> findAll(Pageable pageable) {

        return repository.findAll(pageable)
                .map(ArtistaCompletoDto::toDto);
    }

    public Page<ArtistaCompletoDto> findByNomeContaining(String nome, Pageable pageable) {
        return repository.findByNomeContaining(nome, pageable)
                .map(ArtistaCompletoDto::toDto);
    }

    @Transactional
    public Artista save(Artista artista) {
        return repository.save(artista);
    }

    @Transactional
    public Artista atualizar(Long id, ArtistaDto artistaDto) {
        Artista artista = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Artista não encontrado: " + id));

        artista.setNome(artistaDto.getNome());
        return repository.save(artista);
    }

}
