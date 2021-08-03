/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectormi;

import java.util.Vector;
import static proyectormi.Servidor.dormir;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author felix
 */
public class Agente implements AgenteInterfaz{
    int indiceNodo; //siguiente computadora a visitar
    String nombre;
    Vector listaNodos; //itinerario
    int puertoRMI = 12345;
    
    public double randDouble(double bound1, double bound2) {
        //make sure bound2> bound1
        double min = Math.min(bound1, bound2);
        double max = Math.max(bound1, bound2);
        //math.random gives random number from 0 to 1
        return min + (Math.random() * (max - min));
    }
    
    public Agente(String miNombre, Vector laListaComputadoras, int elPuertoRMI) {
        nombre = miNombre;
        listaNodos = laListaComputadoras;
        indiceNodo = 0;
        puertoRMI = elPuertoRMI;
    }

    @Override
    public void ejecuta() {
        String actual, siguiente; 
        dormir(2); //pausa para poder visualizarlo
        
        double prob = this.randDouble(1, 100);
        
        
        if (prob > 30){
            actual = (String) listaNodos.elementAt(indiceNodo);
            
            
            if (indiceNodo >= listaNodos.size() - 1){
                indiceNodo--;
            }
            else{
               indiceNodo++;
            }
            
            siguiente = (String) listaNodos.elementAt(indiceNodo);
           try{
                
                Registry registro = LocateRegistry.getRegistry("localhost", puertoRMI);
                ServidorInterfaz h = (ServidorInterfaz) registro.lookup(siguiente);
                System.out.println("Navegando a la " + siguiente +" desde la " + actual + ", tierra a la vista!");
                dormir(2);
                h.recibe(this);
            } //fin try
            catch(Exception e) {
                System.out.println("Excepcion en el ejecuta del Agente: " + e);
                
            }
        }else{
            dormir(2);
            System.out.println("El Pirata Peter ha encontrado la gema!");
            dormir(2);
        }
    }
}
