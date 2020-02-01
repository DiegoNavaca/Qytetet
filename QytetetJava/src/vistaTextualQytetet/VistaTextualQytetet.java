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
package vistaTextualQytetet;

import java.util.ArrayList;
import controladorQytetet.OpcionMenu;
import java.util.Scanner;

/**
 *
 * @author diego
 */
public class VistaTextualQytetet {
    
    private static final Scanner in = new Scanner(System.in);
    
    controladorQytetet.ControladorQytetet controlador = controladorQytetet.ControladorQytetet.getControlador();
    
    public ArrayList<String> obtenerNombreJugadores(){
        ArrayList<String> nombres = new ArrayList<>();
        String cadena;
        int numJug;
        do{
            System.out.print("Introduzca el numero de jugadores (2-4)\n");
            cadena = in.nextLine();
            numJug = Integer.parseInt(cadena);
        }while(numJug < 2 || numJug > 4);
        for(int i = 0; i<numJug; i++){
            System.out.print("Introduzca el nombre del jugador "+i+"\n");
            cadena = in.nextLine();
            nombres.add(cadena);
        }
        return nombres;
    }
    public int elegirCasilla(int opcionMenu){
        ArrayList<Integer> lista = controlador.obtenerCasillas(opcionMenu);
        if(lista.size() == 0)
            return -1;
        else{
            ArrayList<String> list = new ArrayList<>();
            for(int i = 0; i<lista.size(); ++i){
                list.add(lista.get(i).toString());
            }
            return Integer.parseInt(this.leerValorCorrecto(list));
        }
    }
    public String leerValorCorrecto(ArrayList<String> valoresCorrectos){
        boolean correcto = false;
        String valor;
        do{
            System.out.print("Introduzca uno de los siguientes valores:\n");
            for(int i = 0; i<valoresCorrectos.size(); ++i){
                System.out.print(valoresCorrectos.get(i) + "  ");
            }
            System.out.print("\n");
            valor = in.nextLine();
            for(int i = 0; i<valoresCorrectos.size() && !correcto; ++i){
                if( valor.equals(valoresCorrectos.get(i)))
                    correcto = true;
            }
        }while(!correcto);
        return valor;
    }
    public int elegirOperacion(){
        ArrayList<Integer> opciones = controlador.obtenerOperacionesJuegoValidas();
        ArrayList<String> lista = new ArrayList<>();
        for(int i = 0; i<opciones.size(); ++i){
            lista.add(opciones.get(i).toString());
            System.out.print(opciones.get(i).toString() + " = " + OpcionMenu.values()[opciones.get(i)].toString() + "\n");
        }
        return Integer.parseInt(this.leerValorCorrecto(lista));
    }
    public static void main(String [] args){
        VistaTextualQytetet ui = new VistaTextualQytetet();
        ui.controlador.setNombreJugadores(ui.obtenerNombreJugadores());
        int operacionElegida, casillaElegida = 0;
        boolean necesitaElegirCasilla;
        do {
        operacionElegida = ui.elegirOperacion();
        necesitaElegirCasilla = ui.controlador.necesitaElegirCasilla(operacionElegida);
        if (necesitaElegirCasilla)
            casillaElegida = ui.elegirCasilla(operacionElegida);
        if (!necesitaElegirCasilla || casillaElegida >= 0)
            System.out.println(ui.controlador.realizarOperacion(operacionElegida,casillaElegida));
        } while (true);
    }
}
