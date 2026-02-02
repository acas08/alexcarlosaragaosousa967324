package com.seplag.acervo.service;

import com.seplag.acervo.domain.Album;
import com.seplag.acervo.dto.AlbumCompletoDto;
import com.seplag.acervo.enumeradores.ModalidadeEnum;
import com.seplag.acervo.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository repository;

    public Page<AlbumCompletoDto> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(AlbumCompletoDto::toDto);
    }

    @Transactional
    public Album save(Album album) {
        return repository.save(album);
    }

    public Page<AlbumCompletoDto> buscarPorModalidadeArtista(ModalidadeEnum modalidade, Pageable pageable) {
        return repository.findByModalidadeArtista(modalidade, pageable)
                .map(AlbumCompletoDto::toDto);
    }
}
