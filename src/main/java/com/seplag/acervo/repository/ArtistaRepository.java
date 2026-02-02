package com.seplag.acervo.repository;

import com.seplag.acervo.dto.ArtistaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.seplag.acervo.domain.Artista;

import java.util.List;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {

    public Page<Artista> findByNomeContaining(String nome, Pageable pageable);

}