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
class Dado {
    private static final Dado dado = new Dado();
    private int valor;
    
    private Dado(){
        tirar();
    }
    public static Dado getDado(){
        return dado;
    }
    public int getValor(){
        return valor;
    }
    int tirar(){
        valor = 1+(int)(Math.random()*(6));
        return valor;
    }
    
}
