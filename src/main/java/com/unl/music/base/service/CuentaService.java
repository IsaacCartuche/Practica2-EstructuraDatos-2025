package com.unl.music.base.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.unl.music.base.controller.dao.dao_models.DaoCuenta;
import com.unl.music.base.models.Cuenta;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@Transactional(propagation = Propagation.REQUIRES_NEW)
@AnonymousAllowed

public class CuentaService {
    private DaoCuenta da;

    public CuentaService() {
        da = new DaoCuenta();
    }

    public boolean validateCuenta(@NotEmpty String email){

        if(!da.listAll().isEmpty()){
            Cuenta[] aux = da.listAll().toArray();
            for(int i = 0; i < aux.length ;i++){
                System.out.println("email a comparar: "+aux[i].getEmail());
                System.out.println("email comparado: "+email);
                if(aux[i].getEmail().equals(email)){
                    return true;
                }
            }
        }
        
        return false;
    }

    public void createCuenta(@NotEmpty String email, @NotEmpty String clave, Boolean estado, Integer idPersona)throws Exception {
        if (email.trim().length() > 0 && clave.trim().length() > 0 && estado.toString().length() > 0
                && idPersona.toString().length() > 0) {
            da.getObj().setId(da.listAll().getLength() + 1);
            da.getObj().setEmail(email);
            da.getObj().setClave(clave);
            da.getObj().setEstado(estado);
            da.getObj().setId_persona(idPersona);
            if (!da.save()) {
                throw new Exception("No se pudo guardar los datos de la Cuenta");
            }
        }
    }

    public void updateCuenta(Integer id, @NotEmpty String email, @NotEmpty String clave, Boolean estado,
            Integer idPersona) throws Exception {
        System.out.println("El estado que ingresa es:  " + estado);
        if (id != null && id > 0 && email.trim().length() > 0 && clave.trim().length() > 0
                && estado.toString().length() > 0 && idPersona.toString().length() > 0) {
            da.setObj(da.listAll().get(id - 1));
            da.getObj().setEmail(email);
            da.getObj().setClave(clave);
            da.getObj().setEstado(estado);
            da.getObj().setId_persona(idPersona);

            if (!da.update(id - 1)) {
                throw new Exception("No se pudo modificar los datos de la Cuenta");
            }
        }
    }

    public List<Cuenta> listAllCuenta() {
        return Arrays.asList(da.listAll().toArray());
    }
}
