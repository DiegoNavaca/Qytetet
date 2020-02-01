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

import java.util.ArrayList;

/**
 *
 * @author diegonavaca
 */
public class Tablero {
   private ArrayList<Casilla> casillas;
   private Casilla carcel;

    ArrayList<Casilla> getCasillas() {
        return casillas;
    }

    Casilla getCarcel() {
        return carcel;
    }
    
    boolean esCasillaCarcel(int casilla){
        return carcel.getNumeroCasilla() == casilla;
    }
    
    Casilla obtenerCasillaFinal(Casilla casilla, int desplazamiento){
        int num = (casilla.getNumeroCasilla()+desplazamiento) % casillas.size();
        return casillas.get(num);
    }
    Casilla obtenerCasillaNumero(int numero){
        return casillas.get(numero);
    }

    Tablero(ArrayList<Casilla> casillas, Casilla carcel) {
        this.casillas = casillas;
        this.carcel = carcel;
    }

    Tablero() {
        inicializar();
    }

    public String toString() {
        return "Tablero: \n" + "Casillas= " + casillas + "\n, Carcel= " + carcel + "\n";
    }
    
    private void inicializar(){
        casillas = new ArrayList<>();
        casillas.add(new OtraCasilla(0,0,TipoCasilla.SALIDA));
        casillas.add(new Calle(1,new TituloPropiedad("Calle 1",500+(int)(Math.random()*(1000 - 499)),
                50,-0.1f,150+(int)(Math.random()*(1000 - 149)),250+(int)(Math.random()*(750 - 249)))));
        casillas.add(new Calle(2,new TituloPropiedad("Calle 2",500+(int)(Math.random()*(1000 - 499)),
                60,0.1f,150+(int)(Math.random()*(1000 - 149)),250+(int)(Math.random()*(750 - 249)))));
        casillas.add(new OtraCasilla(3,500,TipoCasilla.IMPUESTO));
        casillas.add(new Calle(4,new TituloPropiedad("Calle 3",500+(int)(Math.random()*(1000 - 499)),
                70,-0.1f,150+(int)(Math.random()*(1000 - 149)),250+(int)(Math.random()*(750 - 249)))));
        casillas.add(new OtraCasilla(5,0,TipoCasilla.JUEZ));
        casillas.add(new Calle(6,new TituloPropiedad("Calle 4",500+(int)(Math.random()*(1000 - 499)),
                80,0.1f,150+(int)(Math.random()*(1000 - 149)),250+(int)(Math.random()*(750 - 249)))));
        casillas.add(new Calle(7,new TituloPropiedad("Calle 5",500+(int)(Math.random()*(1000 - 499)),
                90,-0.1f,150+(int)(Math.random()*(1000 - 149)),250+(int)(Math.random()*(750 - 249)))));
        casillas.add(new Calle(8,new TituloPropiedad("Calle 6",500+(int)(Math.random()*(1000 - 499)),
                90,0.1f,150+(int)(Math.random()*(1000 - 149)),250+(int)(Math.random()*(750 - 249)))));
        casillas.add(new OtraCasilla(9,0,TipoCasilla.SORPRESA));
        casillas.add(new OtraCasilla(10,0,TipoCasilla.PARKING));
        casillas.add(new Calle(11,new TituloPropiedad("Calle 7",500+(int)(Math.random()*(1000 - 499)),
                80,-0.1f,150+(int)(Math.random()*(1000 - 149)),250+(int)(Math.random()*(750 - 249)))));
        casillas.add(new Calle(12,new TituloPropiedad("Calle 8",500+(int)(Math.random()*(1000 - 499)),
                70,0.1f,150+(int)(Math.random()*(1000 - 149)),250+(int)(Math.random()*(750 - 249)))));
        casillas.add(new OtraCasilla(13,0,TipoCasilla.SORPRESA));
        casillas.add(new Calle(14,new TituloPropiedad("Calle 9",500+(int)(Math.random()*(1000 - 499)),
                60,-0.1f,150+(int)(Math.random()*(1000 - 149)),250+(int)(Math.random()*(750 - 249)))));
        carcel = new OtraCasilla(15,0,TipoCasilla.CARCEL);
        casillas.add(carcel);
        casillas.add(new Calle(16,new TituloPropiedad("Calle 10",500+(int)(Math.random()*(1000 - 499)),
                50,0.1f,150+(int)(Math.random()*(1000 - 149)),250+(int)(Math.random()*(750 - 249)))));
        casillas.add(new Calle(17,new TituloPropiedad("Calle 11",500+(int)(Math.random()*(1000 - 499)),
                60,-0.1f,150+(int)(Math.random()*(1000 - 149)),250+(int)(Math.random()*(750 - 249)))));
        casillas.add(new Calle(18,new TituloPropiedad("Calle 12",500+(int)(Math.random()*(1000 - 499)),
                70,0.1f,150+(int)(Math.random()*(1000 - 149)),250+(int)(Math.random()*(750 - 249)))));
        casillas.add(new OtraCasilla(19,0,TipoCasilla.SORPRESA));
        
    }
   
}
