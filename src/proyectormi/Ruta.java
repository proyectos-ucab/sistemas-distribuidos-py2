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
public class Ruta {
    public int posicion;
    public Cofre cofre;

    public Ruta(int posicion, Cofre cofre) {
        this.posicion = posicion;
        this.cofre = cofre;
    }

    public int getPosicion() {
        return posicion;
    }

    public Cofre getCofre() {
        return cofre;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public void setCofre(Cofre cofre) {
        this.cofre = cofre;
    }
    
    
    
}
