package com.seplag.acervo.service;

import com.seplag.acervo.domain.Album;
import com.seplag.acervo.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository repository;

    public List<Album> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Album save(Album album) {
        return repository.save(album);
    }
}
