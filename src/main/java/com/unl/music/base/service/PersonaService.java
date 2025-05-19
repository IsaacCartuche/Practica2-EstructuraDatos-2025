package com.unl.music.base.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.unl.music.base.controller.dao.dao_models.DaoPersona;
import com.unl.music.base.models.Persona;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@Transactional(propagation = Propagation.REQUIRES_NEW)
@AnonymousAllowed

public class PersonaService {
    private DaoPersona da;
    public PersonaService() {
        da = new DaoPersona();
    }

    public void createPersona(@NotEmpty String usuario,Integer edad) throws Exception{
        if(usuario.trim().length() > 0 && edad.toString().length() > 0){

            da.getObj().setId(da.listAll().getLength()+1);
            da.getObj().setUsuario(usuario);
            da.getObj().setEdad(edad);

            if(!da.save()){
                throw new Exception("No se pudo guardar los datos de la Persona");
            }
        }
    }
    

    public void updatePersona(Integer id, @NotEmpty String usuario,Integer edad) throws Exception{
        //System.out.println("El id que está ingresando eeeesssss:  " + id);
        if(id != null && id > 0 && usuario.trim().length() > 0 && edad.toString().length() > 0){
            da.setObj(da.listAll().get(id - 1));
            da.getObj().setUsuario(usuario);
            da.getObj().setEdad(edad);

            if(!da.update(id - 1)){
                throw new Exception("No se pudo modificar los datos de la Persona");
            }
        }
    }

    public List<Persona> listAllPersona(){
        return Arrays.asList(da.listAll().toArray());
    }

    public String getLength(){
        DaoPersona dp = new DaoPersona();
        return dp.listAll().getLength().toString();
    }
}
