/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectormi;

import java.io.IOException;
import java.util.Vector;
import static proyectormi.Servidor.dormir;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static proyectormi.Servidor.crearRuta;
import static proyectormi.Servidor.mover;

/**
 *
 * @author felix
 */
public class Agente implements AgenteInterfaz{
    int indiceNodo; //siguiente computadora a visitar
    String nombre;
    Vector listaNodos; //itinerario
    int puertoRMI = 12345;
    
    public static ArrayList<Cofre> loot = new ArrayList();
    
    //atributos
    private int libras;
    private int mapa;
    private int barraOro;
    private int barraPlata;
    private int bolsaPerla;
    private int bolsaMoneda;
    private int cofreJoya;
    private int cofrePiedras;
    private int CorazonPrincesa;
    
    
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
        
        libras=0;
        mapa=0;
        barraOro=0;
        barraPlata=0;
        bolsaPerla=0;
        bolsaMoneda=0;
        cofreJoya=0;
        cofrePiedras=0;
        CorazonPrincesa=0;
    }

    @Override
    public void ejecuta() {
        String actual, siguiente; 
        dormir(2); //pausa para poder visualizarlo
        
        double prob = this.randDouble(1, 100);
        
        
        //if ((prob > 30) && 
        if (CorazonPrincesa == 0){
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
                dormir(2);
                crearRuta();
                loot = mover();
                //System.out.println(loot.toString());
                
                for(int i=0;i<loot.size();i++){
                    libras = libras + loot.get(i).getLibras();
                    mapa = mapa + loot.get(i).getMapa();
                    barraOro = barraOro + loot.get(i).getBarraOro();
                    barraPlata = barraPlata + loot.get(i).getBarraPlata();
                    bolsaPerla = bolsaPerla + loot.get(i).getBolsaPerla();
                    bolsaMoneda = bolsaMoneda + loot.get(i).getBolsaMoneda();
                    cofreJoya = cofreJoya + loot.get(i).getCofreJoya();
                    cofrePiedras = cofrePiedras + loot.get(i).getCofrePiedras();
                    CorazonPrincesa = loot.get(i).getCorazonPrincesa();
                    
                    if (CorazonPrincesa==1){
                        System.out.println("El Pirata Peter ha encontrado la gema, wujuu :D ");
                        return;
                    }
                }
                
                System.out.println("El pirata Peter posee en su inventario: ");
                System.out.println("Libras = " +libras);
                System.out.println("Mapas = " +mapa);
                System.out.println("Barra de Oro = " +barraOro);
                System.out.println("Barra de Plata = " +barraPlata);
                System.out.println("Bolsa Perla = " +bolsaPerla);
                System.out.println("Bolsa de Monedas  = " +bolsaMoneda);
                System.out.println("Cofre de Joyas = " +cofreJoya);
                System.out.println("Cofre de Piedras = " +cofrePiedras);
                System.out.println("");
                System.out.println("");
                System.out.println("Navegando a la " + siguiente +" desde la " + actual + ", tierra a la vista!");
                try {
                    Thread.sleep((long) (4*1000.0));
                    //clrscr();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                h.recibe(this);
            } //fin try
            catch(Exception e) {
                System.out.println("Excepcion en el ejecuta del Agente: " + e);
                
            }
        }else{
            dormir(2);
            System.out.println("El Pirata Peter ha encontrado la gema! epaa ojo");
            dormir(2);
        }
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
