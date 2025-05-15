package com.AppReclamos.AppReclamosCms.Repositorios;

import com.AppReclamos.AppReclamosCms.Modelos.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuarios, Integer> {
    @Query("SELECT u FROM Usuarios u WHERE LOWER(u.email) = LOWER(:email)")
    Optional<Usuarios> findByEmail(@Param("email") String email);
    List<Usuarios> findByActivoTrue();
}
