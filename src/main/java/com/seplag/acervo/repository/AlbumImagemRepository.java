package com.seplag.acervo.repository;

import com.seplag.acervo.domain.AlbumImagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumImagemRepository extends JpaRepository<AlbumImagem, Long> {
}
