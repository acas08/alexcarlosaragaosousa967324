package com.seplag.acervo.repository;

import com.seplag.acervo.domain.Artista;
import com.seplag.acervo.enumeradores.ModalidadeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class ArtistaRepositoryTest {

    @Autowired
    private ArtistaRepository artistaRepository;

    @Test
    void findByNomeContaining() {
        Artista a1 = new Artista();
        a1.setNome("John Lennon");
        a1.setModalidade(ModalidadeEnum.CANTOR);

        Artista a2 = new Artista();
        a2.setNome("Paul McCartney");
        a2.setModalidade(ModalidadeEnum.CANTOR);

        artistaRepository.save(a1);
        artistaRepository.save(a2);

        var page = artistaRepository.findByNomeContaining("John", PageRequest.of(0, 10));

        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getContent().get(0).getNome()).isEqualTo("John Lennon");
    }
}