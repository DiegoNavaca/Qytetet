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
import java.util.Collections;

/**
 *
 * @author diegonavaca
 */

public class Qytetet {
    //Atributos Básicos
    public static final int MAX_JUGADORES = 4;
    static final int NUM_SORPRESAS = 10;
    public static final int NUM_CASILLAS = 20;
    static final int PRECIO_LIBERTAD = 200;
    static final int SALDO_SALIDA = 1000;
    //Atributos de Referencia
    private static final Qytetet qytetet = new Qytetet();
    private ArrayList<Sorpresa> mazo ;
    private Tablero tablero;
    static final Dado dado = Dado.getDado();
    private Sorpresa cartaActual;
    private Jugador jugadorActual;
    private ArrayList<Jugador> jugadores;
    private EstadoJuego estado;

    private Qytetet() {
        mazo = new ArrayList<>();
        cartaActual = null;
        jugadorActual = null;
        jugadores = new ArrayList<>();
    }
    
    void actuarSiEnCasillaEdificable(){
        boolean deboPagar = jugadorActual.deboPagarAlquiler();
        if(deboPagar){
            jugadorActual.pagarAlquiler();
            if(jugadorActual.getSaldo() <= 0){
                setEstadoJuego();   //Bancarrota
            }
        }
        Casilla casilla = this.obtenerCasillaJugadorActual();
        boolean tengoPropietario = ((Calle)casilla).tengoPropietario();
        if(estado != EstadoJuego.ALGUNJUGADORENBANCARROTA){
            if(tengoPropietario){
                setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
            }else{
                setEstadoJuego(EstadoJuego.JA_PUEDECOMPRAROGESTIONAR);
            }
        }
    }
    void actuarSiEnCasillaNoEdificable(){
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        Casilla casillaActual = jugadorActual.getCasillaActual();
        if(casillaActual.getTipo() == TipoCasilla.IMPUESTO){
            jugadorActual.pagarImpuesto();
        }else if(casillaActual.getTipo() == TipoCasilla.JUEZ){
            this.encarcelarJugador();
        }else if(casillaActual.getTipo() == TipoCasilla.SORPRESA){
            cartaActual = mazo.remove(0);
            setEstadoJuego(EstadoJuego.JA_CONSORPRESA);
            mazo.add(cartaActual);
        }
    }
    public void aplicarSorpresa(){
        int aux;
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        if(cartaActual.getTipo() == TipoSorpresa.SALIRCARCEL){
            jugadorActual.setCartaLibertad(cartaActual);
        }else{
            if(cartaActual.getTipo() == TipoSorpresa.PAGARCOBRAR){
                jugadorActual.modificarSaldo(cartaActual.getValor());
                if(jugadorActual.getSaldo() < 0){
                    setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
                }
            }
            if(cartaActual.getTipo() == TipoSorpresa.IRACASILLA){
                int valor = cartaActual.getValor();
                boolean casillaCarcel = tablero.esCasillaCarcel(valor);
                if(casillaCarcel){
                    encarcelarJugador();
                }else{
                    aux = jugadorActual.getSaldo(); //Suponemos que esta carta no te da el bonus de la salida
                    mover(20 + valor - this.getJugadorActual().getCasillaActual().getNumeroCasilla());
                    if(aux < jugadorActual.getSaldo())
                        jugadorActual.modificarSaldo(-SALDO_SALIDA);
                }
            }
            if(cartaActual.getTipo() == TipoSorpresa.PORCASAHOTEL){
                int cantidad = cartaActual.getValor();
                int numeroTotal = jugadorActual.cuantasCasasHotelesTengo();
                jugadorActual.modificarSaldo(cantidad * numeroTotal);
                if(jugadorActual.getSaldo() < 0){
                    setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
                }
            }
            if(cartaActual.getTipo() == TipoSorpresa.PORJUGADOR){
                Jugador jugador;
                for(int i = 0; i<jugadores.size(); i++){
                    jugador = jugadores.get(i);
                    if(jugador != jugadorActual){
                        jugador.modificarSaldo(cartaActual.getValor());
                        if(jugador.getSaldo() < 0){
                            setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
                        }
                        jugadorActual.modificarSaldo(cartaActual.getValor());
                        if(jugadorActual.getSaldo() < 0){
                            setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
                        }   
                    }
                }
            }
            if(cartaActual.getTipo() == TipoSorpresa.CONVERTIRME){
                int i = 0;
                for(int j = 0; j<jugadores.size(); j++){
                    if(jugadorActual == jugadores.get(j)){
                        i = j;
                    }
                }
                jugadorActual = jugadorActual.convertirme(cartaActual.getValor());
                jugadores.set(i, jugadorActual);
            }
        }
    }
    public void barajarSorpresas(){
        int valor; Sorpresa sorpresa;
        for(int i = 0; i<20; i++){
        valor = (int)(Math.random()*(mazo.size()));
        sorpresa = mazo.remove(valor);
        mazo.add(sorpresa);
        }
    }
    public boolean cancelarHipoteca(int numeroCasilla){
        TituloPropiedad titulo = ((Calle)tablero.obtenerCasillaNumero(numeroCasilla)).getTitulo();
        boolean cancelada = jugadorActual.cancelarHipoteca(titulo);
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        return cancelada;
    }
    public boolean comprarTituloPropiedad(){
        boolean comprado = jugadorActual.comprarTituloPropiedad();
        if(comprado){
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        }
        return comprado;
    }
    public boolean edificarCasa(int numeroCasilla){
        boolean edificada = false;
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        if(casilla.getTipo() == TipoCasilla.CALLE){
            TituloPropiedad titulo = ((Calle)casilla).getTitulo();
            edificada = jugadorActual.edificarCasa(titulo);
            if(edificada){
                setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
            }
        }
        return edificada;
    }
    public boolean edificarHotel(int numeroCasilla){
        boolean edificada = false;
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        if(casilla.getTipo() == TipoCasilla.CALLE){
            if(((Calle)casilla).getTitulo().getNumCasas() == 4){
                TituloPropiedad titulo = ((Calle)casilla).getTitulo();
                edificada = jugadorActual.edificarHotel(titulo);
                if(edificada){
                    setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
                }
            }
        }
        return edificada;
    }
    private void encarcelarJugador(){
        if(jugadorActual.deboIrCarcel()){
            Casilla casillaCarcel = tablero.getCarcel();
            jugadorActual.irACarcel(casillaCarcel);
            setEstadoJuego(EstadoJuego.JA_ENCARCELADO);
        }else{
            Sorpresa carta = jugadorActual.devolverCartaLibertad();
            mazo.add(carta);
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        }
    }
    public Sorpresa getCartaActual(){
        return cartaActual;
    }
    Dado getDado(){
        return dado;
    }
    public Jugador getJugadorActual(){
        return jugadorActual;
    }
    public ArrayList<Jugador> getJugadores(){
        return jugadores;
    }
    ArrayList<Sorpresa> getMazo(){
        return mazo;
    }
    public void hipotecarPropiedad(int numeroCasilla){
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = ((Calle)casilla).getTitulo();
        jugadorActual.hipotecarPropiedad(titulo);
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
    }
    public int getValorDado(){
        return dado.getValor();
    }
    void inicializarCartasSorpresa(){
        mazo.add(new Sorpresa ("Te conviertes en un especulador",5000,TipoSorpresa.CONVERTIRME));
        mazo.add(new Sorpresa ("Te conviertes en un especulador",3000,TipoSorpresa.CONVERTIRME));
        mazo.add(new Sorpresa ("Vas a casa de tu abuela y te da un poco de dinero",1000,TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa ("Te demandan por ser demasiado guapo",-1000,TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa ("Te han pillado robando un donut, vas a la carcel",tablero.getCarcel().getNumeroCasilla(),TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa ("Te has tropezado con una piedra y has ido rodando hasta la casilla 4",4,TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa ("Te vas de fiesta toda la noche y no sabes muy bien cómo pero acabaste en un parking",10,TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa ("Hay una plaga de cucarachas en la ciudad y tienes que exterminarlas",-150,TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa ("Es verano y tienes todas tus propiedades llenas",150,TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa ("Haces una campaña solidaria donde donan todos los jugadores pero te quedas el dinero",500,TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa ("Se te cae la cartera por la calle y casualmente el resto de jugadores pasaban por alli y se reparten el dinero",-500,TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa ("Han descubierto que eres primo decimocuarto de una infanta, es la excusa perfecta para no ir a la carcel",9,TipoSorpresa.SALIRCARCEL));
    }
    public void inicializarJuego(ArrayList<String> nombres){
        inicializarTablero();
        inicializarJugadores(nombres);
        inicializarCartasSorpresa();
        salidaJugadores();
        this.barajarSorpresas();
    }
    void inicializarJugadores(ArrayList<String> nombres){
        Casilla salida = tablero.getCasillas().get(0);
        for(int i = 0; i<nombres.size(); i++){
            jugadores.add(new Jugador (nombres.get(i), salida));
        }
    }
    private void inicializarTablero(){
        tablero = new Tablero();
    } 
    public boolean intentarSalirCarcel(MetodoSalirCarcel metodo){
        if(metodo == MetodoSalirCarcel.TRANDODADO){
            int resultado = dado.tirar();
            if(resultado >= 5){
                jugadorActual.setEncarcelado(false);
            }
        }else if(metodo == MetodoSalirCarcel.PAGANDOLIBERTAD){
            jugadorActual.pagarLibertad(PRECIO_LIBERTAD);
        }
        boolean encarcelado = jugadorActual.getEncarcelado();
        if(encarcelado){
            setEstadoJuego(EstadoJuego.JA_ENCARCELADO);
        }else{
            setEstadoJuego(EstadoJuego.JA_PREPARADO);
        }
        return !encarcelado;
    }
    public int jugar(){
        int mov = dado.tirar();
        mover(mov);
        return mov;
    }
    void mover(int numCasillaDestino){ //Mover mueve un numero determinado de casillas
        Casilla casillaInicial = jugadorActual.getCasillaActual();
        Casilla casillaFinal = tablero.obtenerCasillaFinal(casillaInicial, numCasillaDestino);
        jugadorActual.setCasillaActual(casillaFinal);
        if(casillaInicial.getNumeroCasilla() >= casillaFinal.getNumeroCasilla()){
            jugadorActual.modificarSaldo(SALDO_SALIDA);
        }if(casillaFinal.soyEdificable()){
            actuarSiEnCasillaEdificable();
        }else{
            actuarSiEnCasillaNoEdificable();
        }
    }
    public Casilla obtenerCasillaJugadorActual(){
        return jugadorActual.getCasillaActual();
    }
    public ArrayList<Casilla> obtenerCasillasTablero(int NUM_CASILLAS){
        return tablero.getCasillas();
    }
    public ArrayList<Integer> obtenerPropiedadesJugador(){
        ArrayList<Integer> aDevolver = new ArrayList<>();
        ArrayList<Casilla> casillas = tablero.getCasillas();
        for(int j = 0; j<NUM_CASILLAS; j++){
            if(casillas.get(j).getTipo() == TipoCasilla.CALLE)
                if(jugadorActual.esDeMiPropiedad(((Calle)casillas.get(j)).getTitulo())){
                    aDevolver.add(casillas.get(j).getNumeroCasilla());
                }
        }
        return aDevolver;
    }
    public ArrayList<Integer> obtenerPropiedadesJugadorSegunEstadoHipoteca(boolean estadoHipoteca){
        ArrayList<Integer> aDevolver = new ArrayList<>();
        ArrayList<Casilla> casillas = tablero.getCasillas();
        for(int j = 0; j<NUM_CASILLAS; j++){
            if(casillas.get(j).getTipo() == TipoCasilla.CALLE)
              if(jugadorActual.esDeMiPropiedad(((Calle)casillas.get(j)).getTitulo())){
                if(((Calle)casillas.get(j)).getTitulo().getHipotecada() == estadoHipoteca)
                    aDevolver.add(casillas.get(j).getNumeroCasilla());
              }
        }
        return aDevolver;    
    }
    public void obtenerRanking(){
        Collections.sort(jugadores);
    }
    private void salidaJugadores(){
        Casilla casilla = tablero.obtenerCasillaNumero(0);
        int ja = (int)(Math.random()*(jugadores.size()));
        for(int i = 0; i<jugadores.size(); i++){
            jugadores.get(i).setCasillaActual(casilla);
        }
        jugadorActual = jugadores.get(ja);
        setEstadoJuego(EstadoJuego.JA_PREPARADO);
    }
    private void setCartaActual(Sorpresa CartaActual){
        cartaActual = CartaActual;
    }
    public void setEstadoJuego(){
        estado = EstadoJuego.ALGUNJUGADORENBANCARROTA;
    }
    public void setEstadoJuego(EstadoJuego estadojuego){
        estado = estadojuego;
    }
    public void siguienteJugador(){
        int indice = -1;
        for(int i = 0; i<jugadores.size(); i++){
            if(jugadorActual == jugadores.get(i)){
                indice = i;
            }
        }
        if(indice == jugadores.size() - 1){
            jugadorActual = jugadores.get(0);
        }else{
            jugadorActual = jugadores.get(indice +1);
        }
        if(jugadorActual.getEncarcelado()){
            setEstadoJuego(EstadoJuego.JA_ENCARCELADOCONOPCIONDELIBERTAD);
        }else{
            setEstadoJuego(EstadoJuego.JA_PREPARADO);
        }
    }
    int TirarDado(){
        return dado.tirar();
    }
    public void venderPropiedad(int numeroCasilla){
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        jugadorActual.venderPropiedad(((Calle)casilla));
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
    }
    
    public static Qytetet getQytetet(){
        return qytetet;
    }
    public Tablero getTablero() {
        return tablero;
    }
    public EstadoJuego getEstado(){
        return estado;
    }
        
   public String toString(){
       String aDevolver = "";
       aDevolver += "Mazo: \n";
       aDevolver += mazo.toString();
       aDevolver += tablero.toString();
       aDevolver += "Dado = " + dado.getValor() + "\n";
       aDevolver += jugadores.toString();
       return aDevolver;
   }
     
    
}
