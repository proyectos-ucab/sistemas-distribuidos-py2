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
        System.out.println("Aqui el agente 007");
        actual = (String) listaNodos.elementAt(indiceNodo);
        indiceNodo++;
        if (indiceNodo < listaNodos.size()) {
            // si hay mas computadoras que visitar
            siguiente = (String) listaNodos.elementAt(indiceNodo);
            dormir(2); //pausa para poder visualizarlo
            try{
                //Localiza el registro RMI en el siguiente nodo
                Registry registro = LocateRegistry.getRegistry("localhost", puertoRMI);
                ServidorInterfaz h = (ServidorInterfaz) registro.lookup(siguiente);
                System.out.println("Buscando " + siguiente +" en " + actual + " completado ");
                dormir(2); //pausa para poder visualizarlo
                //Pide al servidor del siguiente nodo que reciba a este agente
                h.recibe(this);
            } //fin try
            catch(Exception e) {
                System.out.println("Excepcion en el ejecuta del Agente: " + e);
                
            }
        } //fin if
        else{ //si se han hecho todas las paradas
            dormir(2); //pausa para poder visualizarlo
            System.out.println("El Agente 007 ha regresado a casa");
            dormir(2); //pausa para poder visualizarlo
        } 
    }
}
