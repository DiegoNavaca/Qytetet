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
public class Calle extends Casilla{
    private TituloPropiedad titulo;
    Calle(int numeroCasilla, TituloPropiedad titulo){
        super(numeroCasilla, titulo.getPrecioCom(), TipoCasilla.CALLE);
        this.titulo = titulo;
    }
    TituloPropiedad asignarPropietario(Jugador jugador){
        titulo.setPropietario(jugador);
        return titulo;
    }
    protected TituloPropiedad getTitulo() {
        return titulo;
    }
    private boolean setTitulo(TituloPropiedad tit){
        boolean todoOK = true;
        titulo = tit;
        return todoOK;
    }
    boolean tengoPropietario(){
        if(titulo == null){     //Sin título de propiedad no puede tener propietario
            return false;
        }else{
            return titulo.tengoPropietario();   //Comprobar el propietario del título de propiedad
        }
    }
    boolean propietarioEncarcelado(){
        if(titulo == null){
            return false;
        }else{
            return titulo.propietarioEncarcelado();
        }    
    }
    int pagarAlquiler(){
        return titulo.pagarAlquiler();
    }
    @Override
    public String toString(){
       return super.toString() + "\nTitulo: " + titulo.toString() + "\n";
    }
}
