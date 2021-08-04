/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectormi;

/**
 *
 * @author Nestor
 */
public class Cofre {
    private int libras;
    private int mapa;
    private int barraOro;
    private int barraPlata;
    private int bolsaPerla;
    private int bolsaMoneda;
    private int cofreJoya;
    private int cofrePiedras;
    private int probCorazonPrincesa;
    private int CorazonPrincesa;
    
    public Cofre(int mapa, int barraOro, int barraPlata, int bolsaPerla, int bolsaMoneda, int cofreJoya, int cofrePiedras, int probCorazonPrincesa){
        
        this.mapa = mapa;
        this.barraOro = barraOro;
        this.barraPlata = barraPlata;
        this.bolsaPerla = bolsaPerla;
        this.bolsaMoneda = bolsaMoneda;
        this.cofreJoya = cofreJoya;
        this.cofrePiedras = cofrePiedras; 
        this.probCorazonPrincesa = probCorazonPrincesa;
        this.CorazonPrincesa = 0;
        
        double prob = randDouble(1, 100);
        if(prob<probCorazonPrincesa){
            CorazonPrincesa = 1;
            System.out.println("SE GUARDO UN CORAZON");
        }else{
            CorazonPrincesa = 0;
            System.out.println("NO SE GUARDO UN CORAZON");
        }
        
        this.libras = ((mapa*5)+(barraOro*25)+(barraPlata*50)+(bolsaPerla*10)+(bolsaMoneda*15)+(cofreJoya*15)+(cofrePiedras*10));
    
    }
    
    public static double randDouble(double bound1, double bound2) {
        //make sure bound2> bound1
        double min = Math.min(bound1, bound2);
        double max = Math.max(bound1, bound2);
        //math.random gives random number from 0 to 1
        return min + (Math.random() * (max - min));
    }

    @Override
    public String toString() {
        return "Cofre{" + "libras=" + libras + ", mapa=" + mapa + ", barraOro=" + barraOro + ", barraPlata=" + barraPlata + ", bolsaPerla=" + bolsaPerla + ", bolsaMoneda=" + bolsaMoneda + ", cofreJoya=" + cofreJoya + ", cofrePiedras=" + cofrePiedras + ", probCorazonPrincesa=" + probCorazonPrincesa + '}';
    }

    public int getLibras() {
        return libras;
    }

    public int getMapa() {
        return mapa;
    }

    public int getBarraOro() {
        return barraOro;
    }

    public int getBarraPlata() {
        return barraPlata;
    }

    public int getBolsaPerla() {
        return bolsaPerla;
    }

    public int getBolsaMoneda() {
        return bolsaMoneda;
    }

    public int getCofreJoya() {
        return cofreJoya;
    }

    public int getCofrePiedras() {
        return cofrePiedras;
    }

    public int getProbCorazonPrincesa() {
        return probCorazonPrincesa;
    }

    public int getCorazonPrincesa() {
        return CorazonPrincesa;
    }
    
    
    
    
}
