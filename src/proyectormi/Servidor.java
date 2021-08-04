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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static proyectormi.Agente.loot;

/**
 *
 * @author felix
 */
public class Servidor extends Canvas implements ServidorInterfaz{
    
    private static Scanner leer = new Scanner(System.in);
    
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
    
    public Servidor(int x, int y, String foto) throws IOException{
        double prob = this.randDouble(1, 100);
        
        mapa = new ImageIcon(this.getClass().getResource(foto));
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
    
    public static double randDouble(double bound1, double bound2) {
        //make sure bound2> bound1
        double min = Math.min(bound1, bound2);
        double max = Math.max(bound1, bound2);
        //math.random gives random number from 0 to 1
        return min + (Math.random() * (max - min));
    }
    
    public static void crearRuta(){
        
        //System.out.println("EL NUMERO ES "+x);
        
        for(int i=1;i<6;i++){
            double prob = randDouble(1, 100);
            
            if (prob>20){
                ruta.add(new Ruta(i,null));
            }else{
                double a = randDouble(0, 2);
                int a1 = (int) a;
                double b = randDouble(0, 2);
                int b1 = (int) b;
                double c = randDouble(0, 3);
                int c1 = (int) c;
                double d = randDouble(0, 2);
                int d1 = (int) d;
                double e = randDouble(0, 3);
                int e1 = (int) e;
                double f = randDouble(0, 2);
                int f1 = (int) f;
                double g = randDouble(0, 1);
                int g1 = (int) g;
                double h = randDouble(1, 60);
                //System.out.println(h);
                int h1 = (int) h;
                
                ruta.add(new Ruta(i,new Cofre(a1,b1,c1,d1,e1,f1,g1,h1)));
                
                //System.out.println(i+" "+a1+b1+c1+d1+e1+f1+g1+h1);
            }
        }
        
        //ruta.add(new Ruta(1,null));
        //ruta.add(new Ruta(5,new Cofre(1,1,1,1,1,1,1,1)));
    }
    
    public static void main(String args[]) throws IOException {
           
        try {         
            Servidor servidor = new Servidor(1000,1000,"/mapas/Playa.jpg");
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
        
        while (posicion<6){
            int condicion = 3;
            
            ventana.setVisible(false);
            Servidor servidor = new Servidor(150,posicion,"/mapas/Playa.jpg");
            //servidor.imprimirBarco(0,posicion);
            System.out.println("El barco se encuentra en la posicion: " +posicion);
                 
                if(ruta.get(posicion-1).getCofre() != null){
                    while((condicion!=0) && (condicion!=1)){
                        System.out.println("Has encontrado un cofre en la posicion "+posicion);
                        System.out.println("");
                        if(ruta.get(posicion-1).getCofre().getMapa()!=0);
                            System.out.println("Mapas: "+ruta.get(posicion-1).getCofre().getMapa());
                        if(ruta.get(posicion-1).getCofre().getBarraOro()!=0);
                            System.out.println("Barras de Oro: "+ruta.get(posicion-1).getCofre().getBarraOro());
                        if(ruta.get(posicion-1).getCofre().getBarraPlata()!=0);
                            System.out.println("Barras de Plata: "+ruta.get(posicion-1).getCofre().getBarraPlata());
                        if(ruta.get(posicion-1).getCofre().getBolsaPerla()!=0);
                            System.out.println("Bolsas de Perla: "+ruta.get(posicion-1).getCofre().getBolsaPerla());
                        if(ruta.get(posicion-1).getCofre().getBolsaMoneda()!=0);
                            System.out.println("Bolsas de Monedas: "+ruta.get(posicion-1).getCofre().getBolsaMoneda());
                        if(ruta.get(posicion-1).getCofre().getCofreJoya()!=0);
                            System.out.println("Cofre con Joyas: "+ruta.get(posicion-1).getCofre().getCofreJoya());
                        if(ruta.get(posicion-1).getCofre().getCofrePiedras()!=0);
                            System.out.println("Cofre con Piedras: "+ruta.get(posicion-1).getCofre().getCofrePiedras());
                            
                        System.out.println("");
                        System.out.println(ruta.get(posicion-1).getCofre().getProbCorazonPrincesa()+"% Probabilidad de Encontrar El Corazon de La Princesa");
                        System.out.println("");
                        System.out.println("Deseas lutear? [1]Si / [0]No");
                        condicion = leer.nextInt();
                    }
                    if (condicion == 1){
                        loot.add(ruta.get(posicion-1).getCofre());
                        
                        if(loot.size()!=0){
                            if (loot.get(loot.size()-1).getCorazonPrincesa()== 1){
                            servidor = new Servidor(150,posicion,"/mapas/Playa2.jpg");
                            return loot;
                            }
                        }else{
                            if (loot.get(loot.size()).getCorazonPrincesa()== 1){
                            return loot;
                            }
                        }
                    }
                }
                    
            try {
                Thread.sleep((long) (5*1000.0));
            } catch (InterruptedException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            posicion++;
            clrscr();
        }
        return loot;
    }
    
    public static void clrscr(){
    //Clears Screen in java
    try {
        if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            Runtime.getRuntime().exec("clear");
    } catch (IOException | InterruptedException ex) {}
    }
}
