package com.restaurante.restaurante.repository;

import com.restaurante.restaurante.model.RecuperacaoToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecuperacaoTokenRepository extends JpaRepository<RecuperacaoToken, Long> {
    Optional<RecuperacaoToken> findByToken(String token);
}
