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
public class TituloPropiedad {
    //Atributos Base
    private String nombre;
    private boolean hipotecada;
    private int precioCompra;
    private int alquilerBase;
    private float factorRevalorizacion;
    private int hipotecaBase;
    private int precioEdificar;
    private int numHoteles;
    private int numCasas;
    //Atributos De Relacion
    private Jugador propietario;
    
    
    TituloPropiedad(String nom, int precio, int alquiler, float factor, int hip, int edif){
        numHoteles = 0;
        numCasas = 0;
        hipotecada = false;
        nombre = nom;
        precioCompra = precio;
        alquilerBase = alquiler;
        factorRevalorizacion = factor;
        hipotecaBase = hip;
        precioEdificar = edif;
        propietario = null;
    }
    
    int calcularCosteCancelar(){
        return (int)(1.1 * calcularCosteHipotecar());
    }
    int calcularCosteHipotecar(){
        int costeHipoteca = hipotecaBase + numCasas * hipotecaBase / 2
                            + numHoteles * hipotecaBase;
        return costeHipoteca;
    }
    int calcularImporteAlquiler(){
        return (alquilerBase + (int)(numCasas * 0.5 + numHoteles * 2));
    }
    int calcularPrecioVenta(){
        int precio = (int)(precioCompra +(numCasas + numHoteles) * precioEdificar * factorRevalorizacion);
        return precio;
    }
    void cancelarHipoteca(){
        hipotecada = false;
    }
    void edificarCasa(){
        numCasas = numCasas + 1;
    }
    void edificarHotel(){
        numHoteles = numHoteles + 1;
        numCasas -= 4;
    }
    public String getNombre(){
        return nombre;
    }
    public boolean getHipotecada(){
        return hipotecada;
    }
    public int getAlquilerBase(){
        return alquilerBase;
    }
    public int getPrecioCom(){
        return precioCompra;
    }
    public int getHipotecaBase(){
        return hipotecaBase;
    }
    public int getPrecioEdif(){
        return precioEdificar;
    }
    public float getFactorRev(){
        return factorRevalorizacion;
    }
    public int getNumCasas(){
        return numCasas;
    }
    public int getNumHoteles(){
        return numHoteles;
    }
    public int hipotecar(){
        int costeHipoteca = calcularCosteHipotecar();
        hipotecada = true;
        return costeHipoteca;
    }
    int pagarAlquiler(){
        int costeAlquiler = calcularImporteAlquiler();
        propietario.modificarSaldo(costeAlquiler);
        return costeAlquiler;
    }
    boolean propietarioEncarcelado(){
        return propietario.getEncarcelado();
    }
    void setHipotecada(boolean hipotecada){
        this.hipotecada = hipotecada;
    }
    void setPropietario(Jugador propietario){
        this.propietario = propietario;
    }
    boolean tengoPropietario(){
        return propietario != null;
    }
    public String toString() {
        return "TituloPropiedad: " + "nombre= " + nombre + ", hipotecada= " + hipotecada + ", precioCompra= " + precioCompra + ", alquilerBase= " + alquilerBase + ", factorRevalorizacion= " + factorRevalorizacion + ", hipotecaBase= " + hipotecaBase + ", precioEdificar= " + precioEdificar + ", numHoteles= " + numHoteles + ", numCasas= " + numCasas;
    }
    
}
