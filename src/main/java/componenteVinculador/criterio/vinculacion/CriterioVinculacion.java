package componenteVinculador.criterio.vinculacion;

import componenteVinculador.criterio.ResultadoVinculado.Vinculacion;
import componenteVinculador.criterio.orden.CriterioOrden;
import componenteVinculador.vinculable.OperacionVinculable;
import componenteVinculador.vinculable.utils.FechaUtils;

import java.util.ArrayList;
import java.util.Date;

public class CriterioVinculacion {
    private ArrayList<Vinculacion> resultados;
    private Object parametro;

    public CriterioVinculacion(Object parametroCondicion) {

        resultados = new ArrayList<>();
        parametro = parametroCondicion;
    }

    public void ejecutar(ArrayList<OperacionVinculable> ingresos,ArrayList<OperacionVinculable> egresos) {
        ordenar(ingresos,egresos, getCriterioOrden());
        vincular(ingresos,egresos);
    }

    protected void vincular(ArrayList<OperacionVinculable> ingresos, ArrayList<OperacionVinculable> egresos) {
        for (OperacionVinculable egreso : egresos) {
            for (OperacionVinculable ingreso : ingresos) {
                Vinculacion resultado = this.buscaOCreaResultadoNuevo(ingreso);

                if(resultado.sePuedeVincularEgreso(egreso) && cumpleCondicion(ingreso.getFecha(), egreso.getFecha()) ){
                    resultado.vincularNuevoEgreso(egreso);

                    agregarResultadoSiEsNecesario(resultado);
                    break;
                }
            }
        }
    }

    private boolean cumpleCondicion(Object parametro1, Object parametro2) {
        return  FechaUtils.estaDentroDelRango((Date) parametro1,(Date) parametro2,(int)parametro);
    }

    void ordenar(ArrayList<OperacionVinculable> ingresos, ArrayList<OperacionVinculable> egresos, CriterioOrden criterioOrden) {
        ingresos.sort(criterioOrden);
        egresos.sort(criterioOrden);
    }

    public ArrayList<Vinculacion> getResultadosVinculados(){
        return resultados;
    }

    private Vinculacion buscaOCreaResultadoNuevo(OperacionVinculable ingreso) {
        for (Vinculacion resultado:resultados) {
            if (resultado.contieneAlIngreso(ingreso)) {
                return resultado;
            }
        }
        return new Vinculacion(ingreso);
    }

    protected CriterioOrden getCriterioOrden() {
       return null;
    }

    private void agregarResultadoSiEsNecesario(Vinculacion vinculacion) {
        if(vinculacion.getEgresos().size() == 1){
            resultados.add(vinculacion);
        }
    }

    public ETipoCriterioVinculacion getTipoCriterio() {
        return null;
    }
}
