package com.seplag.acervo.repository;

import com.seplag.acervo.domain.Album;
import com.seplag.acervo.domain.Artista;
import com.seplag.acervo.enumeradores.ModalidadeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
class AlbumRepositoryTest {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    @Test
    void findByModalidadeArtista() {
        Artista cantor = new Artista();
        cantor.setNome("Leoanardo");
        cantor.setModalidade(ModalidadeEnum.CANTOR);
        cantor = artistaRepository.save(cantor);

        Artista banda = new Artista();
        banda.setNome("Legiao Urbana");
        banda.setModalidade(ModalidadeEnum.BANDA);
        banda = artistaRepository.save(banda);

        Album albumCantor = new Album();
        albumCantor.setDescricao("Tempo");
        albumCantor.setArtistas(Set.of(cantor));
        albumRepository.save(albumCantor);

        Album albumBanda = new Album();
        albumBanda.setDescricao("Dois");
        albumBanda.setArtistas(Set.of(banda));
        albumRepository.save(albumBanda);

        var page = albumRepository.findByModalidadeArtista(ModalidadeEnum.CANTOR, PageRequest.of(0, 10));

        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getContent().get(0).getDescricao()).isEqualTo("Tempo");

    }
}