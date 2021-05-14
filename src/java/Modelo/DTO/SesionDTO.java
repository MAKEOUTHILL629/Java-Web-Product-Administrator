/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.DTO;

import java.io.Serializable;

/**
 *
* @author Angelica Barros Espitia
 * @author Luis Felipe Hernandez chica
 */
public class SesionDTO implements Serializable{
    private int id;
    private String clave;
    private String correo;

    public SesionDTO() {
    }

    public SesionDTO(int id, String clave, String correo) {
        this.id = id;
        this.clave = clave;
        this.correo = correo;
    }

    public SesionDTO(String clave, String correo) {
        this.clave = clave;
        this.correo = correo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    
    
}
