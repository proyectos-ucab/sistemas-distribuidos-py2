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
import java.util.ArrayList;
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
    
    public static ArrayList<Cofre> cofres = new ArrayList();
    public static ArrayList<Ruta> ruta = new ArrayList();
    
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
        myLabel2.setLocation(180*y,x);
        
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
    
    public static void crearRuta(String x){
        
        //System.out.println("EL NUMERO ES "+x);
        
        ruta.add(new Ruta(1,null));
        ruta.add(new Ruta(2,null));
        ruta.add(new Ruta(3,new Cofre(1,1,1,1,1,1,1,0)));
        ruta.add(new Ruta(4,null));
        ruta.add(new Ruta(5,new Cofre(1,1,1,1,1,1,1,1)));
        
    }
    
    public static void main(String args[]) throws IOException {
           
        
        try {
            
            Servidor servidor = new Servidor(1000,1000);
            ServidorInterfaz stub = (ServidorInterfaz) UnicastRemoteObject.exportObject(servidor, 0);
            String nombre = "isla" + args[0];
            crearRuta(args[0]);

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
    
   
    static void dormir(double time){
        try{
            Thread.sleep((long) (time*1000.0));
        }
        catch(InterruptedException e){
            System.out.println("excepcion en dormir");
        }
    }
    
    static ArrayList<Cofre> mover() throws IOException{
        
        ArrayList<Cofre> loot = new ArrayList();
        int posicion = 1;
        System.out.println(posicion);
        
        
        while (posicion<6){
            ventana.setVisible(false);
            Servidor servidor = new Servidor(150,posicion);
            //servidor.imprimirBarco(0,posicion);
            System.out.println("El barco se encuentra en la posicion: " +posicion);
                
                if(ruta.get(posicion-1).getCofre() != null){
                    System.out.println("Has encontrado un cofre en la posicion "+posicion+". Deseas lutear?(S/N) ");
                    loot.add(ruta.get(posicion-1).getCofre());
                }
                    
            try {
                Thread.sleep((long) (4*1000.0));
            } catch (InterruptedException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            posicion++;
        }
        
        return loot;
    }
}
