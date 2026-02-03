package com.seplag.acervo.repository;

import com.seplag.acervo.domain.Album;
import com.seplag.acervo.enumeradores.ModalidadeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query("""
           select distinct album
           from Album album
           left join album.artistas artista
           where artista.modalidade = :modalidade
           """)
    Page<Album> findByModalidadeArtista(ModalidadeEnum modalidade, Pageable pageable);
}
