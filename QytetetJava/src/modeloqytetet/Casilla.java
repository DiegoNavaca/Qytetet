/* 
 * Copyright (C) 2020 diego
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package modeloqytetet;

/**
 *
 * @author diegonavaca
 */
public abstract class Casilla {
    private int numeroCasilla;
    private int coste;
    private TipoCasilla tipo;

    Casilla(int numeroCasilla, int coste, TipoCasilla tipo) {
        this.numeroCasilla = numeroCasilla;
        this.coste = coste;
        this.tipo = tipo;
    }
    
    public int getNumeroCasilla() {
        return numeroCasilla;
    }
    public int getCoste() {
        return coste;
    }
    protected TipoCasilla getTipo() {
        return tipo;
    }
    public void setCoste(int coste){
        coste = coste;
    }
    protected boolean soyEdificable(){
        return tipo == TipoCasilla.CALLE;
    }
    public String toString() {
        String aDevolver;
        aDevolver = "Casilla: " + "numeroCasilla= " + numeroCasilla + ", coste= " + coste + ", tipo= " + tipo;
        return aDevolver;
    }
    
    
}

