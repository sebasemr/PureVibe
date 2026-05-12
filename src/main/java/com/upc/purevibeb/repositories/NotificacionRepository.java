package com.upc.purevibeb.repositories;

import com.upc.purevibeb.entities.Notificacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    Page<Notificacion> findByUsuarioIdOrderByFechaCreacionDesc(Long usuarioId, Pageable pageable);

    long countByUsuarioIdAndLeidoFalse(Long usuarioId);

    @Query("SELECT n FROM Notificacion n WHERE n.usuario.id = :usuarioId AND n.id IN :ids")
    List<Notificacion> findByIdsAndUsuarioId(@Param("ids") List<Long> ids, @Param("usuarioId") Long usuarioId);
}