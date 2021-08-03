/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectormi;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 *
 * @author felix
 */
public interface ServidorInterfaz extends Remote {
    public void recibe(Agente h)
    throws RemoteException;
}
