package com.AppReclamos.AppReclamosCms.Repositorios;

import aj.org.objectweb.asm.commons.Remapper;
import com.AppReclamos.AppReclamosCms.Modelos.MedidasAdoptadas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedidaAdoptadaRepositorio extends JpaRepository<MedidasAdoptadas, Integer> {

    // Método para buscar todas las medidas de un reclamo específico
    List<MedidasAdoptadas> findByReclamo_IdReclamo(Integer idReclamo);

    // Método para contar las medidas de un reclamo específico
    @Query("SELECT COUNT(m) FROM MedidasAdoptadas m WHERE m.reclamo.idReclamo = :idReclamo")
    Long countByReclamoId(@Param("idReclamo") Integer idReclamo);

}
