package com.AppReclamos.AppReclamosCms.Repositorios;

import com.AppReclamos.AppReclamosCms.Modelos.Reclamos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReclamosRepositorio extends JpaRepository<Reclamos, Integer> {
    /**
     * Busca reclamos de forma dinámica y eficiente, aplicando los filtros directamente
     * en la base de datos y cargando las relaciones necesarias para evitar N+1 selects.
     */
    @Query("SELECT DISTINCT r FROM Reclamos r " +
            "LEFT JOIN FETCH r.personas p " +
            "LEFT JOIN FETCH r.detalles d " +
            "LEFT JOIN FETCH r.gestion g " +
            "LEFT JOIN FETCH r.resultados res " +
            "LEFT JOIN FETCH r.medidas med " +
            "WHERE (:estado IS NULL OR r.estadoReclamo = :estado) " +
            "AND (:anio IS NULL OR YEAR(r.fechaReclamo) = :anio) " +
            "AND (:mes IS NULL OR MONTH(r.fechaReclamo) = :mes) " +
            "AND (" +
            "     :buscarPor = 'TODO' OR " +
            "     :query IS NULL OR " +
            "     (:buscarPor = 'CODIGO' AND r.codigoReclamo LIKE :query) OR " +
            "     (:buscarPor = 'NOMBRE' AND LOWER(p.nombres) LIKE LOWER(:query) AND p.tipoPersona = com.AppReclamos.AppReclamosCms.Modelos.Enums.TipoPersona.USUARIO)" +
            ")")
    List<Reclamos> findReclamosByFilters(
            @Param("estado") String estado,
            @Param("buscarPor") String buscarPor,
            @Param("query") String query,
            @Param("anio") Integer anio,
            @Param("mes") Integer mes
    );
}
