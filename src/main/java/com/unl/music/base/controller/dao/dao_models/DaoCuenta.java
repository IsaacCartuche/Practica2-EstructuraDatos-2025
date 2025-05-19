package com.unl.music.base.controller.dao.dao_models;

import com.unl.music.base.models.Cuenta;

import com.unl.music.base.controller.dao.AdapterDao;

public class DaoCuenta extends AdapterDao<Cuenta> {

    private Cuenta obj;

    public DaoCuenta() {
        super(Cuenta.class);
        // TODO Auto-generated constructor stub
    }

    public Cuenta getObj() {
        if (obj == null)
            this.obj = new Cuenta();
        return this.obj;
    }

    public void setObj(Cuenta obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            this.persist(obj);
            return true;
        } catch (Exception e) {
            // LOG de error

            // TODO: handle exception
            return false;
        }
    }


    public Boolean update(Integer pos) {
        try {
            this.update(obj,pos);
            return true;
        } catch (Exception e) {
            // LOG de error

            // TODO: handle exception
            return false;
        }
    }

}