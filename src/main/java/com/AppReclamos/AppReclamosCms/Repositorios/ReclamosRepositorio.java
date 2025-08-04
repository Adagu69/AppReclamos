package com.AppReclamos.AppReclamosCms.Repositorios;

import com.AppReclamos.AppReclamosCms.Modelos.Reclamos;
import com.AppReclamos.AppReclamosCms.Modelos.Enums.EstadoReclamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReclamosRepositorio extends JpaRepository<Reclamos, Integer> {
    /**
     * Busca reclamos con filtros mejorados para la tabla principal.
     */
    @Query("SELECT DISTINCT r FROM Reclamos r " +
            "LEFT JOIN FETCH r.presentante pres " +
            "LEFT JOIN FETCH r.afectado afec " +
            "LEFT JOIN FETCH r.detalles d " +
            "LEFT JOIN FETCH r.gestion g " +
            "LEFT JOIN FETCH r.resultados res " +
            "LEFT JOIN FETCH r.medidas med " +
            "WHERE (:estadoEnum IS NULL OR r.estadoReclamo = :estadoEnum) " +
            "AND (:periodo IS NULL OR r.periodoDeclaracion = :periodo) " +
            "AND (" +
            "     :buscarPor = 'TODO' OR " +
            "     :query IS NULL OR :query = '' OR " +
            "     (:buscarPor = 'CODIGO' AND (" +
            "         LOWER(r.codigoReclamo) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "         LOWER(r.codigoDeclarante) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "         LOWER(r.codigoUgipress) LIKE LOWER(CONCAT('%', :query, '%'))" +
            "     )) OR " +
            "     (:buscarPor = 'NOMBRE' AND (" +
            "         LOWER(pres.nombres) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "         LOWER(pres.apellidoPaterno) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "         LOWER(pres.apellidoMaterno) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "         LOWER(pres.razonSocial) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "         LOWER(afec.nombres) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "         LOWER(afec.apellidoPaterno) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "         LOWER(afec.apellidoMaterno) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "         LOWER(afec.razonSocial) LIKE LOWER(CONCAT('%', :query, '%'))" +
            "     )) OR " +
            "     (:buscarPor = 'DOCUMENTO' AND (" +
            "         pres.numeroDocumento LIKE CONCAT('%', :query, '%') OR " +
            "         afec.numeroDocumento LIKE CONCAT('%', :query, '%')" +
            "     )) OR " +
            "     (:buscarPor = 'DESCRIPCION' AND (" +
            "         LOWER(d.descripcion) LIKE LOWER(CONCAT('%', :query, '%'))" +
            "     ))" +
            ") " +
            "ORDER BY r.idReclamo DESC")
    List<Reclamos> findReclamosByFilters(
            @Param("estadoEnum") EstadoReclamo estadoEnum,
            @Param("buscarPor") String buscarPor,
            @Param("query") String query,
            @Param("periodo") String periodo
    );
}
