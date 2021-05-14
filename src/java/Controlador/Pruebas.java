/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.DTO.ProductoDTO;
import java.util.List;

/**
 *
* @author Angelica Barros Espitia
 * @author Luis Felipe Hernandez chica
 */
public class Pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Facade obj = new Facade();
        char sexo =  'M';
        if(sexo== 'M'){
            System.out.println("Masculino"); 
        }
        List<ProductoDTO> lst = null; 
        lst = obj.listarProductos();
        if(lst != null){
            for (ProductoDTO producto : lst){
                System.out.println(producto);
            }
        }else{
            System.out.println("No hay productos");
        }
        // TODO code application logic here
    }
    
}
