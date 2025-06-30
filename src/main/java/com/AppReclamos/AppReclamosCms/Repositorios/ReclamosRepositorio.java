package com.AppReclamos.AppReclamosCms.Repositorios;

import com.AppReclamos.AppReclamosCms.Modelos.Reclamos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReclamosRepositorio extends JpaRepository<Reclamos, Integer> {
}
