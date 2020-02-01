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
 * author diegonavaca
 */

public class Jugador implements Comparable {
    //Atributos Basicos
    private boolean encarcelado;
    private String nombre;
    protected int saldo;
    //Atributos De Refrencia
    protected Casilla casillaActual;
    private Sorpresa cartaLibertad;
    private ArrayList<TituloPropiedad> propiedades;
    
    protected Jugador(String nom, Casilla casillaInicial){
        encarcelado = false;
        nombre = nom;
        saldo = 7500;
        casillaActual = casillaInicial;
        cartaLibertad = null;
        propiedades = new ArrayList<>();
    }
    protected Jugador(Jugador j){
        encarcelado = j.encarcelado;
        nombre = j.nombre;
        saldo = j.saldo;
        casillaActual = j.casillaActual;
        cartaLibertad = j.cartaLibertad;
        propiedades = j.propiedades;
    }
    
    @Override
    public int compareTo(Object otroJugador) {
        int otroCapital = ((Jugador) otroJugador).obtenerCapital();
        return otroCapital - obtenerCapital();
    }
    
    boolean cancelarHipoteca(TituloPropiedad titulo){
        boolean cancelada = true;
        int cantidad = titulo.calcularCosteCancelar();
        if(tengoSaldo(cantidad)){
            modificarSaldo(-cantidad);
            titulo.cancelarHipoteca();
        }else cancelada = false;
        return cancelada;
    }
    protected Especulador convertirme(int fianza){
        Especulador aDevolver = new Especulador(this, fianza);
        
        return aDevolver;
    }
    boolean comprarTituloPropiedad(){
        boolean comprado = false;
        if(casillaActual.getTipo() == TipoCasilla.CALLE){
            int costeCompra = casillaActual.getCoste();
            if(costeCompra < saldo){
                TituloPropiedad titulo = ((Calle)casillaActual).asignarPropietario(this);
                propiedades.add(titulo);
                this.modificarSaldo(-costeCompra);
                comprado = true;
            }
        }
        return comprado;
    }
    int cuantasCasasHotelesTengo(){
        int num = 0;
        for(int i = 0; i<propiedades.size(); i++){
            num += propiedades.get(i).getNumCasas(); 
            num += propiedades.get(i).getNumHoteles();
        }
        return num;
    }
    protected boolean deboIrCarcel(){
        return cartaLibertad == null;
    }
    boolean deboPagarAlquiler(){
        boolean deboPagar = false;
        TituloPropiedad titulo = ((Calle)casillaActual).getTitulo();
        boolean esDeMiPropiedad = esDeMiPropiedad(titulo);
        if(!esDeMiPropiedad){
            boolean tienePropietario = titulo.tengoPropietario();
            if(tienePropietario){
                boolean encarcelado = titulo.propietarioEncarcelado();
                if(!encarcelado){
                    boolean estaHipotecada = titulo.getHipotecada();
                    if(!estaHipotecada){
                        deboPagar = true;
                    }
                }
            }
        }
        return deboPagar;
    }
    Sorpresa devolverCartaLibertad(){
        Sorpresa aux = cartaLibertad;
        cartaLibertad = null;
        return aux;
    }
    boolean edificarCasa(TituloPropiedad titulo){
        boolean puedo = this.puedoEdificarCasa(titulo);
        if(puedo){
            int costeEdificarCasa = titulo.getPrecioEdif();
            titulo.edificarCasa();
            modificarSaldo(-costeEdificarCasa);
        }
        return puedo;
    }
    boolean edificarHotel(TituloPropiedad titulo){
        boolean puedo = this.puedoEdificarHotel(titulo);
        if(puedo){
            int costeEdificarHotel = titulo.getPrecioEdif();
            titulo.edificarHotel();
            modificarSaldo(-costeEdificarHotel);
        }
        return puedo;
    }
    void eliminarDeMisPropiedades(TituloPropiedad titulo){
        propiedades.remove(titulo);
    }
    boolean esDeMiPropiedad(TituloPropiedad titulo){
        boolean aDevolver = false;
        for(int i = 0; i<propiedades.size() && !aDevolver; i++){
            if(propiedades.get(i) == titulo){
                aDevolver = true;
            }
        }
        return aDevolver;
    }
    boolean estoyEnCalleLibre(){
        throw new UnsupportedOperationException("Sin implementar");
    }
    Sorpresa getCartaLibertad(){
        return cartaLibertad;
    }
    String getNombre() {
        return nombre;
    }
    public int getSaldo() {
        return saldo;
    }
    Casilla getCasillaActual(){
        return casillaActual;
    }
    boolean getEncarcelado() {
        return encarcelado;
    }
    ArrayList<TituloPropiedad> getPropiedades(){
        return propiedades;
    }
    void hipotecarPropiedad(TituloPropiedad titulo){
        int costeHipoteca =  titulo.hipotecar();
        modificarSaldo(costeHipoteca);
    }
    void irACarcel(Casilla casilla){
        setCasillaActual(casilla);
        setEncarcelado(true);
    }
    int modificarSaldo(int cantidad){
        saldo += cantidad;
        return saldo;
    }
    int obtenerCapital(){
        int capital = saldo;
        for(int i = 0; i<propiedades.size(); i++){
            capital += propiedades.get(i).getPrecioCom();
            capital += ( propiedades.get(i).getNumCasas() + 4*propiedades.get(i).getNumHoteles() ) * 
                    propiedades.get(i).getPrecioEdif();
            if(propiedades.get(i).getHipotecada()){
                capital -= propiedades.get(i).getHipotecaBase();
            }
        }
        return capital;
    }
    ArrayList<TituloPropiedad> obtenerPropiedades(boolean hipotecada){
        ArrayList<TituloPropiedad> prop = new ArrayList<>();
        for(int i = 0; i<propiedades.size(); i++){
            if(propiedades.get(i).getHipotecada() == hipotecada){
                prop.add(propiedades.get(i));
            }
        }
        return prop;
    }
    void pagarAlquiler(){
        int costeAlquiler = ((Calle)casillaActual).pagarAlquiler();
        modificarSaldo(-costeAlquiler);
    }
    void pagarImpuesto(){
        saldo -= casillaActual.getCoste();
    }
    void pagarLibertad(int cantidad){
        boolean tengoSaldo = tengoSaldo(cantidad);
        if(tengoSaldo){
            setEncarcelado(false);
            modificarSaldo(-cantidad);
        }
    }
    protected boolean puedoEdificarCasa(TituloPropiedad titulo){
        boolean tengoSaldo = false;
        boolean hayEspacio = (titulo.getNumCasas() < 4);
        if(hayEspacio){
            int costeEdificarCasa = titulo.getPrecioEdif();
            tengoSaldo = tengoSaldo(costeEdificarCasa);
        }  
        return tengoSaldo;
    }
    protected boolean puedoEdificarHotel(TituloPropiedad titulo){
        boolean tengoSaldo = false;
        boolean hayEspacio = (titulo.getNumHoteles() < 4);
        if(hayEspacio){
            int costeEdificarHotel = titulo.getPrecioEdif();
            tengoSaldo = tengoSaldo(costeEdificarHotel);
        }  
        return tengoSaldo;
    }
    void setCartaLibertad(Sorpresa carta){
        cartaLibertad = carta;
    }
    void setCasillaActual(Casilla casilla){
        casillaActual = casilla;
    }
    void setEncarcelado(boolean encarcelado){
        this.encarcelado = encarcelado;
    }
    boolean tengoCartaLibertad(){
        return cartaLibertad != null;
    }
    protected boolean tengoSaldo(int cantidad){
        return saldo > cantidad;
    }
    void venderPropiedad(Calle casilla){
        TituloPropiedad titulo = casilla.getTitulo();
        eliminarDeMisPropiedades(titulo);
        titulo.setPropietario(null);
        int precioVenta = titulo.calcularPrecioVenta();
        modificarSaldo(precioVenta);
    }

    @Override
    public String toString() {
        String string, encar;
        if(cartaLibertad == null){
            string = "Ninguna";
        }else{
            string = cartaLibertad.toString();
        }
        if(encarcelado){
            encar = ", Esta encarcelado";
        }else{
            encar = ", No esta encarcelado";
        }
    string = "Jugador:" + " Nombre = " + nombre +  encar +
      ", Saldo = " + saldo + ", Capital = " + obtenerCapital() + ", \nCasillaActual = " +
      casillaActual.toString() + "CartaLibertad = " + string + "\nPropiedades: \n";
    for (int i = 0; i<propiedades.size(); i++){
      string = string + propiedades.get(i).toString() + "\n";
    }
    return string;
    }
    
    
}
