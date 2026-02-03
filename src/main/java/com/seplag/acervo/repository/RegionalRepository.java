package com.seplag.acervo.repository;

import com.seplag.acervo.domain.Regional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegionalRepository extends JpaRepository<Regional, Long> {

    List<Regional> findByAtivoTrue();

    Optional<Regional> findFirstByCodigoAndAtivoTrue(Integer codigo);

    List<Regional> findAllByOrderByCodigoAsc();
}
