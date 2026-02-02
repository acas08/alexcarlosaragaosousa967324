package com.seplag.acervo.service;

import com.seplag.acervo.domain.Artista;
import com.seplag.acervo.repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArtistaService {

    @Autowired
    private ArtistaRepository repository;

    public List<Artista> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Artista save(Artista artista) {
        return repository.save(artista);
    }
}
