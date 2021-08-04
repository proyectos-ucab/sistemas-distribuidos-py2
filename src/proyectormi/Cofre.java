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
    private boolean CorazonPrincesa;
    
    public Cofre(int mapa, int barraOro, int barraPlata, int bolsaPerla, int bolsaMoneda, int cofreJoya, int cofrePiedras, int CorazonPrincesa){
        
        this.mapa = mapa;
        this.barraOro = barraOro;
        this.barraPlata = barraPlata;
        this.bolsaPerla = bolsaPerla;
        this.bolsaMoneda = bolsaMoneda;
        this.cofreJoya = cofreJoya;
        this.cofrePiedras = cofrePiedras; 
        this.probCorazonPrincesa = CorazonPrincesa;
        
        this.libras = ((mapa*5)+(barraOro*25)+(barraPlata*50)+(bolsaPerla*10)+(bolsaMoneda*15)+(cofreJoya*15)+(cofrePiedras*10));
    
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
    
    
    
    
}
