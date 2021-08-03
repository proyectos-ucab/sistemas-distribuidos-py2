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
        
        try {
            Registry registry = LocateRegistry.getRegistry("192.168.1.1", puertoRMI);
            ServidorInterfaz stub = (ServidorInterfaz) registry.lookup("servidor1");
            
            System.out.println("Lookup for servidor1 completed ");
            System.out.println("***Buen viaje, " + " agente 007");
            
            Vector listaNodos = new Vector();
            listaNodos.addElement("servidor1");
            listaNodos.addElement("servidor2");
            // listaNodos.addElement("servidor3");
            Agente a = new Agente("007", listaNodos, puertoRMI);
            stub.recibe(a);
            System.out.println("*** Buen trabajo, 007");
        } catch (NotBoundException | RemoteException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}
