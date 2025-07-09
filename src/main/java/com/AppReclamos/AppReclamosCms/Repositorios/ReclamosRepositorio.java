package com.AppReclamos.AppReclamosCms.Repositorios;

import com.AppReclamos.AppReclamosCms.Modelos.Reclamos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReclamosRepositorio extends JpaRepository<Reclamos, Integer> {
    /**
     * Busca reclamos de forma dinámica con la nueva estructura de presentante y afectado.
     */
    @Query("SELECT DISTINCT r FROM Reclamos r " +
            // ¡CAMBIO! Hacemos JOIN con los nuevos campos.
            "LEFT JOIN FETCH r.presentante pres " +
            "LEFT JOIN FETCH r.afectado afec " +
            // El resto de los JOINs se mantienen.
            "LEFT JOIN FETCH r.detalles d " +
            "LEFT JOIN FETCH r.gestion g " +
            "LEFT JOIN FETCH r.resultados res " +
            "LEFT JOIN FETCH r.medidas med " +
            "WHERE (:estado IS NULL OR r.estadoReclamo = :estado) " +
            "AND (:periodo IS NULL OR r.periodoDeclaracion = :periodo) " +
            "AND (" +
            "     :buscarPor = 'TODO' OR " +
            "     :query IS NULL OR " +
            "     (:buscarPor = 'CODIGO' AND r.codigoReclamo LIKE CONCAT('%', :query, '%')) OR " +
            // ¡CAMBIO! Ahora busca por el nombre del presentante O del afectado.
            "     (:buscarPor = 'NOMBRE' AND (LOWER(pres.nombres) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(afec.nombres) LIKE LOWER(CONCAT('%', :query, '%'))))" +
            ")")
    List<Reclamos> findReclamosByFilters(
            @Param("estado") String estado,
            @Param("buscarPor") String buscarPor,
            @Param("query") String query,
            @Param("periodo") String periodo
    );
}
