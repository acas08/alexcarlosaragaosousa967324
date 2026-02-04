package com.seplag.acervo.repository;

import com.seplag.acervo.domain.Regional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
class RegionalRepositoryTest {

    @Autowired
    private RegionalRepository regionalRepository;

    @Test
    void findByAtivoTrue() {
        Regional regional = new Regional();
        regional.setCodigo(10);
        regional.setNome("Regional 1");
        regional.setAtivo(true);

        regionalRepository.save(regional);

        var result = regionalRepository.findByAtivoTrue();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCodigo()).isEqualTo(10);
        assertThat(result.get(0).getAtivo()).isTrue();
    }

    @Test
    void findFirstByCodigoAndAtivoTrue() {
        Regional regional = new Regional();
        regional.setCodigo(10);
        regional.setNome("Regional Cuiaba");
        regional.setAtivo(true);
        regionalRepository.save(regional);

        var result = regionalRepository.findFirstByCodigoAndAtivoTrue(10);

        assertThat(result).isPresent();
        assertThat(result.get().getAtivo()).isTrue();
        assertThat(result.get().getNome()).isEqualTo("Regional Cuiaba");
    }

    @Test
    void findAllByOrderByCodigoAsc() {
        Regional regional = new Regional();
        regional.setCodigo(10);
        regional.setNome("Regional Cuiaba");
        regional.setAtivo(true);

        regionalRepository.save(regional);

        var listaRegional = regionalRepository.findAllByOrderByCodigoAsc();

        assertThat(listaRegional).hasSize(1);
        assertThat(listaRegional.get(0).getCodigo()).isEqualTo(10);
    }
}