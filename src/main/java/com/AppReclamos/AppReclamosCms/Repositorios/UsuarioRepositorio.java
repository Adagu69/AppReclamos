package com.AppReclamos.AppReclamosCms.Repositorios;

import com.AppReclamos.AppReclamosCms.Modelos.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuarios, Integer> {
    Optional<Usuarios> findByEmail(String email);
}
