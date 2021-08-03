/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectormi;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author felix
 */
public class Servidor implements ServidorInterfaz{
    
    static int puertoRMI = 12345;
    
    @Override
    public void recibe(Agente h) throws RemoteException {
        dormir(3); //pausa para poder visualizarlo
        System.out.println("****El Agente " +/* h.nombre +*/ " ha llegado");
        h.ejecuta();
    }
    
    
    public static void main(String args[]) {
        try {
            Servidor servidor = new Servidor();
            ServidorInterfaz stub = (ServidorInterfaz) UnicastRemoteObject.exportObject(servidor, 0);
            String nombre = "servidor" + args[0];

            Registry registry = LocateRegistry.createRegistry(puertoRMI);
            registry.rebind(nombre, stub);

            System.out.println("Servidor "  + nombre + " Listo" );
        } catch (RemoteException e) {
            System.err.println("Servidor exception: " + e.toString());
        }
    }
    
   
    static void dormir(double time){
        try{
            Thread.sleep((long) (time*1000.0));
        }
        catch(InterruptedException e){
            System.out.println("excepcion en dormir");
        }
    }

   
    
}
