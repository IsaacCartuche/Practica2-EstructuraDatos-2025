package com.unl.music.base.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.unl.music.base.controller.dao.dao_models.DaoCuenta;
import com.unl.music.base.controller.dao.dao_models.DaoPersona;
import com.unl.music.base.models.Cuenta;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@Transactional(propagation = Propagation.REQUIRES_NEW)
@AnonymousAllowed

public class PersonaCuentaService {
    public List<HashMap> listAll() throws Exception {
        List<HashMap> lista = new ArrayList<>();
        DaoCuenta dc = new DaoCuenta();
        if (!dc.listAll().isEmpty()) {
            Cuenta[] arreglo = dc.listAll().toArray();

            DaoPersona dp = new DaoPersona();

            for (int i = 0; i < dc.listAll().getLength(); i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("id", arreglo[i].getId().toString());
                aux.put("usuario", dp.listAll().get(arreglo[i].getId_persona() - 1).getUsuario());
                aux.put("edad", dp.listAll().get(arreglo[i].getId_persona() - 1).getEdad().toString());
                aux.put("email", arreglo[i].getEmail());
                aux.put("clave", arreglo[i].getClave());
                aux.put("estado", arreglo[i].getEstado().toString());
                lista.add(aux);
            }
        }

        return lista;
    }
}
