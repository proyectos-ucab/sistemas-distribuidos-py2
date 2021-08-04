/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectormi;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author felix
 */
public class Servidor extends Canvas implements ServidorInterfaz{
    
    static int puertoRMI = 12345;
    
    private static final int ANCHO=400;
    private static final int ALTO=800;
    
    private static String NOMBRE="Mapa";
    
    private static JFrame ventana;
    private ImageIcon mapa,barco;
    private JLabel myLabel,myLabel2;
    
    @Override
    public void recibe(Agente h) throws RemoteException {
        String isla =  h.listaNodos.elementAt(h.indiceNodo).toString();
        dormir(3); //pausa para poder visualizarlo
        System.out.println(h.nombre +  " ha llegado a la " + isla );
        h.ejecuta();
    }
    
    public Servidor(int x, int y) throws IOException{
        
        mapa = new ImageIcon(this.getClass().getResource("/mapas/Playa.jpg"));
        myLabel = new JLabel(mapa);
        myLabel.setSize(1200, 800);
        myLabel.setLocation(0, 0);
        
        barco = new ImageIcon(this.getClass().getResource("/mapas/barco.png"));
        myLabel2 = new JLabel(barco);
        myLabel2.setSize(100, 100);
        myLabel2.setLocation(100*y,x);
        
        setPreferredSize(new Dimension(ALTO,ANCHO));
        ventana = new JFrame(NOMBRE);
        //ventana.add(myLabel);
      
        ventana.add(myLabel2);
        ventana.add(myLabel);
        
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setLayout(new BorderLayout());
        ventana.add(this, BorderLayout.CENTER);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
       
        
    }
    
    public static void main(String args[]) throws IOException {
        
        try {
            Servidor servidor = new Servidor(1000,1000);
            ServidorInterfaz stub = (ServidorInterfaz) UnicastRemoteObject.exportObject(servidor, 0);
            String nombre = "isla" + args[0];

           // inicializacion de registro RMI
            try {
                Registry registro = LocateRegistry.getRegistry(puertoRMI);
                registro.rebind(nombre, servidor);
            } catch (RemoteException e) {
                Registry registro = LocateRegistry.createRegistry(puertoRMI);
                registro.rebind(nombre, servidor);
            }
            
            System.out.println("Servidor "  + nombre + " Listo" );
        } catch (RemoteException e) {
            System.err.println("Servidor exception: " + e.toString());
        }
    }
    
   
    static int dormir(double time){
        try{
            Thread.sleep((long) (time*1000.0));
        }
        catch(InterruptedException e){
            System.out.println("excepcion en dormir");
        }
        
        return 90;
    }
    
    static void mover() throws IOException{
        int posicion = 1;
        System.out.println(posicion);
        
        
        while (posicion<11){
            ventana.setVisible(false);
            Servidor servidor = new Servidor(150,posicion);
            //servidor.imprimirBarco(0,posicion);
            System.out.println("El barco se encuentra en la posicion: " +posicion);
                
                if(posicion == 4)
                    System.out.println("Has encontrado un cofre. Deseas lutear? ");
                    
            try {
                Thread.sleep((long) (2*1000.0));
            } catch (InterruptedException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            posicion++;
        }
    }
    
    void imprimirBarco(int x, int y){
        
        barco = new ImageIcon(this.getClass().getResource("/mapas/barco.png"));
        myLabel2 = new JLabel(barco);
        myLabel2.setSize(100, 100);
        myLabel2.setLocation(100*y,350);
        
        ventana.add(myLabel2);
        ventana.add(myLabel);
        
    }


    
}
