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
public class Especulador extends Jugador {
    int fianza;
    
    Especulador(String nom, Casilla casillaInicial, int f){
        super(nom,casillaInicial);
        fianza = f;
    }
    Especulador(Jugador j, int f){
        super(j);
        fianza = f;
    }
    @Override
    protected void pagarImpuesto(){
        saldo -= casillaActual.getCoste()/2;
    }
    @Override
    protected boolean deboIrCarcel(){
        if(super.deboIrCarcel()){
            if(! pagarFianza()){
                return true;
            }
        }
        return false;
    }
    @Override
    protected Especulador convertirme(int fianza){
        this.fianza = fianza;
        return this;
    }
    private boolean pagarFianza(){
        if(this.tengoSaldo(fianza)){
            saldo -= fianza;
            return true;
        }else{
            return false;
        }
    }
    @Override
    protected boolean puedoEdificarCasa(TituloPropiedad titulo){
        boolean tengoSaldo = false;
        boolean hayEspacio = (titulo.getNumCasas() < 8);
        if(hayEspacio){
            int costeEdificarCasa = titulo.getPrecioEdif();
            tengoSaldo = tengoSaldo(costeEdificarCasa);
        }  
        return tengoSaldo;
    }
    @Override
    protected boolean puedoEdificarHotel(TituloPropiedad titulo){
        boolean tengoSaldo = false;
        boolean hayEspacio = (titulo.getNumHoteles() < 8);
        if(hayEspacio){
            int costeEdificarHotel = titulo.getPrecioEdif();
            tengoSaldo = tengoSaldo(costeEdificarHotel);
        }  
        return tengoSaldo;
    }
    @Override
    public String toString(){
        return (super.toString() + " Fianza = " + fianza + "\n");
    }
}
