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
public class Sorpresa {
    private String texto;
    private TipoSorpresa tipo;
    private int valor;
    
    Sorpresa(String texto, int valor, TipoSorpresa tipo){
        this.texto = texto;
        this.tipo = tipo;
        this.valor = valor;
    }  
    
    String getDescripcion(){
        return texto;
    }
    TipoSorpresa getTipo(){
        return tipo;
    }
    int getValor(){
        return valor;
    }
    
    public String toString() {
        return "Sorpresa: " + "texto= " + texto + ", valor= " + 
        Integer.toString(valor) + ", tipo= " + tipo + "\n";
    }
    
}
