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
package controladorQytetet;

import java.util.ArrayList;
import modeloqytetet.EstadoJuego;
import modeloqytetet.MetodoSalirCarcel;
/**
 *
 * @author diego
 */
public class ControladorQytetet {
    
    private ArrayList<String> nombreJugadores;
    private static final ControladorQytetet controlador = new ControladorQytetet();
    private modeloqytetet.Qytetet modelo = modeloqytetet.Qytetet.getQytetet();
    
    private ControladorQytetet(){
        
    }
    public static ControladorQytetet getControlador(){
        return controlador;
    }
    public void setNombreJugadores(ArrayList<String> nombreJugadores){
            this.nombreJugadores = nombreJugadores;
    }
    public ArrayList<Integer> obtenerOperacionesJuegoValidas(){
        ArrayList<Integer> dev = new ArrayList<>();
        if(modelo.getJugadores().size() == 0){
            dev.add(OpcionMenu.INICIARJUEGO.ordinal());
        }else{
            switch(modelo.getEstado()){
                case JA_PREPARADO:
                    dev.add(OpcionMenu.JUGAR.ordinal());
                    break;
                    
                case JA_CONSORPRESA:
                    dev.add(OpcionMenu.APLICARSORPRESA.ordinal());
                    break;
                    
                case ALGUNJUGADORENBANCARROTA:
                    dev.add(OpcionMenu.OBTENERRANKING.ordinal());
                    break;
                    
                case JA_PUEDECOMPRAROGESTIONAR:
                    dev.add(OpcionMenu.PASARTURNO.ordinal());
                    dev.add(OpcionMenu.HIPOTECARPROPIEDAD.ordinal());
                    dev.add(OpcionMenu.CANCELARHIPOTECA.ordinal());
                    dev.add(OpcionMenu.VENDERPROPIEDAD.ordinal());
                    dev.add(OpcionMenu.COMPRARTITULOPROPIEDAD.ordinal());
                    dev.add(OpcionMenu.EDIFICARCASA.ordinal());
                    dev.add(OpcionMenu.EDIFICARHOTEL.ordinal());
                    break;
                    
                case JA_PUEDEGESTIONAR:
                    dev.add(OpcionMenu.PASARTURNO.ordinal());
                    dev.add(OpcionMenu.HIPOTECARPROPIEDAD.ordinal());
                    dev.add(OpcionMenu.CANCELARHIPOTECA.ordinal());
                    dev.add(OpcionMenu.VENDERPROPIEDAD.ordinal());
                    dev.add(OpcionMenu.EDIFICARCASA.ordinal());
                    dev.add(OpcionMenu.EDIFICARHOTEL.ordinal());
                    break;
                    
                case JA_ENCARCELADO:
                    dev.add(OpcionMenu.PASARTURNO.ordinal());                    
                    break;
                    
                case JA_ENCARCELADOCONOPCIONDELIBERTAD:
                    dev.add(OpcionMenu.INTENTARSALIRCARCELCONDADO.ordinal());
                    dev.add(OpcionMenu.INTENTARSALIRCARCELPAGANDOLIBERTAD.ordinal());
                    break;
            }
            dev.add(OpcionMenu.TERMINARJUEGO.ordinal());
            dev.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
            dev.add(OpcionMenu.MOSTRARTABLERO.ordinal());
            dev.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
        }
        return dev;
    }
    public boolean necesitaElegirCasilla(int opcionMenu){
        return (opcionMenu > 5 && opcionMenu < 11);
    }
    public ArrayList<Integer> obtenerCasillas(int opcionMenu){
        ArrayList<Integer> casillas = new ArrayList<>();
        switch (opcionMenu) {
            case 6:
                casillas = modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(false);
                break;
            case 7:
                casillas = modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(true);
                break;
            case 8:
            case 9:
            case 10:
                casillas = modelo.obtenerPropiedadesJugador();
                break;
        }
        return casillas;
    }
    public String realizarOperacion(int opcionElegida, int casillaElegida){
        String mensaje = "";
        OpcionMenu opcion = OpcionMenu.values()[opcionElegida];
        switch(opcion){
            case INICIARJUEGO:
                modelo.inicializarJuego(nombreJugadores);
                mensaje = "Juego Inicializado \n";
                break;
                
            case JUGAR:
                int num = modelo.jugar();
                mensaje = "Dado: " + num + "\n" + modelo.getJugadorActual().toString();
                break;
                
            case APLICARSORPRESA:
                mensaje = modelo.getCartaActual().toString();
                modelo.aplicarSorpresa();
                break;
                
            case INTENTARSALIRCARCELPAGANDOLIBERTAD:
                if(modelo.intentarSalirCarcel(MetodoSalirCarcel.PAGANDOLIBERTAD))
                    mensaje = "Has logrado salir de la cárcel \n";
                else
                    mensaje = "No tienes suficiente dinero para salir de la cárcel \n";
                break;
                
            case INTENTARSALIRCARCELCONDADO:
                if(modelo.intentarSalirCarcel(MetodoSalirCarcel.TRANDODADO))
                    mensaje = "Has logrado salir de la cárcel \n";
                else
                    mensaje = "No has podido salir de la cárcel \n";
                break;
                
            case COMPRARTITULOPROPIEDAD:
                if(modelo.comprarTituloPropiedad())
                    mensaje = "Has comprado esta calle \n" + modelo.getJugadorActual().toString();
                else
                    mensaje = "No has podido comprar esta calle \n";
                break;
                
            case HIPOTECARPROPIEDAD:
                modelo.hipotecarPropiedad(casillaElegida);
                mensaje = "Has hipotecado una de tus propiedades\nSaldo = " + modelo.getJugadorActual().getSaldo() + "\n";
                break;
                
            case CANCELARHIPOTECA:
                if(modelo.cancelarHipoteca(casillaElegida))
                    mensaje = "Has cancelado tu hipoteca\nSaldo = " + modelo.getJugadorActual().getSaldo() + "\n";
                else
                    mensaje = "No has podido cancelar la hipoteca\nSaldo = " + modelo.getJugadorActual().getSaldo() + "\n";
                break;
                
            case EDIFICARCASA:
                if(modelo.edificarCasa(casillaElegida))
                    mensaje = "Has contruido una casa\nSaldo = " + modelo.getJugadorActual().getSaldo() + "\n";
                else
                    mensaje = "No has podido construir una casa\nSaldo = " + modelo.getJugadorActual().getSaldo() + "\n";
                break;
                
            case EDIFICARHOTEL:
                if(modelo.edificarHotel(casillaElegida))
                    mensaje = "Has contruido un hotel\nSaldo = " + modelo.getJugadorActual().getSaldo() + "\n";
                else
                    mensaje = "No has podido construir un hotel\nSaldo = " + modelo.getJugadorActual().getSaldo() + "\n";
                break;
                
            case VENDERPROPIEDAD:
                modelo.venderPropiedad(casillaElegida);
                mensaje = "Has vendido tu propiedad\nSaldo = " + modelo.getJugadorActual().getSaldo() + "\n";
                break;
                
            case PASARTURNO:
                modelo.siguienteJugador();
                mensaje = "Es el turno del jugador:\n " + modelo.getJugadorActual().toString();
                break;
                
            case OBTENERRANKING:
                modelo.obtenerRanking();
                mensaje = "Ranking:\n";
                for(int i = 0; i<modelo.getJugadores().size(); ++i){
                    mensaje += modelo.getJugadores().get(i).toString() + "\n";
                }
                break;
                
            case TERMINARJUEGO:
                System.out.println("Juego Terminado");
                System.exit(0);
                break;
                
            case MOSTRARJUGADORACTUAL:
                mensaje = modelo.getJugadorActual().toString();
                break;
                
            case MOSTRARJUGADORES:
                mensaje = modelo.getJugadores().toString();
                break;
                
            case MOSTRARTABLERO:
                mensaje = modelo.getTablero().toString();
                break;
        }
        return mensaje;
    }
}
