package dominio.operaciones.medioDePago;

import dominio.operaciones.medioDePago.MedioDePago;

public class TarjetaDeCredito implements MedioDePago {
    int cuotas;
    String nombre;
    String numero;

    public TarjetaDeCredito(int cuotas,String nombre,String numero){
        this.cuotas = cuotas;
        this.nombre = nombre;
        this.numero = numero;
    }



    public void informacionARegistrar() {

    }

    public int getCuotas() {
        return cuotas;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public double getMonto() {
        return 0;
    }

    @Override
    public String getPuntoDePago() {
        return null;
    }

    public String getNumero() {
        return numero;
    }

    @Override
    public boolean getEsDineroEnCuenta() {
        return false;
    }

    @Override
    public boolean getEsEfectivo() {
        return false;
    }

    @Override
    public boolean getEsTarjetaDeCredito() {
        return true;
    }

    @Override
    public boolean getEsTarjetaDeDebito() {
        return false;
    }
}
