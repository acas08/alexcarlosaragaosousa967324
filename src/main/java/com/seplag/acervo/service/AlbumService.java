package com.seplag.acervo.service;

import com.seplag.acervo.domain.Album;
import com.seplag.acervo.dto.AlbumCompletoDto;
import com.seplag.acervo.enumeradores.ModalidadeEnum;
import com.seplag.acervo.repository.AlbumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Service
public class AlbumService {

    private static final Logger log = LoggerFactory.getLogger(AlbumService.class);

    @Autowired
    private AlbumRepository repository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public Page<AlbumCompletoDto> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(AlbumCompletoDto::toDto);
    }

    @Transactional
    public Album save(Album album) {
        Album albumSalvo = repository.save(album);

        log.info("Enviando WS para destino={} albumId={} descricao={}",
                "/topic/albuns", albumSalvo.getId(), albumSalvo.getDescricao());

        messagingTemplate.convertAndSend("/topic/albuns", AlbumCompletoDto.toDto(albumSalvo));

        log.info("WS publicado (convertAndSend retornou) destino={} albumId={}",
                "/topic/albuns", albumSalvo.getId());
        return albumSalvo;
    }

    public Page<AlbumCompletoDto> buscarPorModalidadeArtista(ModalidadeEnum modalidade, Pageable pageable) {
        return repository.findByModalidadeArtista(modalidade, pageable)
                .map(AlbumCompletoDto::toDto);
    }
}
