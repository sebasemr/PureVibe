package com.upc.purevibeb.repositories;

import com.upc.purevibeb.entities.FactoresEmision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FactoresEmisionRepository extends JpaRepository<FactoresEmision, Long> {

    Optional<FactoresEmision> findFirstByCategoriaIgnoreCaseAndSubcategoriaIgnoreCaseAndUnidadBaseIgnoreCaseAndVigenteTrue(
            String categoria, String subcategoria, String unidadBase
    );
}