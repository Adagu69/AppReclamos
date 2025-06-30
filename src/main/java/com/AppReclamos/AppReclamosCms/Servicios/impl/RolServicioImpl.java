package com.AppReclamos.AppReclamosCms.Servicios.impl;

import com.AppReclamos.AppReclamosCms.Modelos.Roles;
import com.AppReclamos.AppReclamosCms.Repositorios.RolRepositorio;
import com.AppReclamos.AppReclamosCms.Servicios.interfaces.IRolServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServicioImpl implements IRolServicio {


    @Autowired
    private RolRepositorio rolRepositorios;


    @Override
    public List<Roles> listarRoles() {
        return rolRepositorios.findAll();
    }
}
