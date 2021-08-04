/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectormi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.Vector;

/**
 *
 * @author felix
 */
public class Cliente {
    
    static int puertoRMI = 12345;
    
    public static void main(String[] args) {
        
        //String host = !"".equals(args[0]) ? args[0] : "localhost" ;
   
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", puertoRMI);
            ServidorInterfaz stub = (ServidorInterfaz) registry.lookup("isla1");
            
            System.out.println("Listos para zapar a aguas desconocidas, Pirata Peter! Ahoy!");
            System.out.println("Buen viaje, " + "Pirata Peter");
            
            Vector listaNodos = new Vector();
            listaNodos.addElement("isla1");
            listaNodos.addElement("isla2");
            listaNodos.addElement("isla3");
            listaNodos.addElement("isla4");
            Agente a = new Agente("Pirata Peter", listaNodos, puertoRMI);
            stub.recibe(a);
            System.out.println("Felicidades por encontrar la gema, Pirata Peter!");
        } catch (NotBoundException | RemoteException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}
