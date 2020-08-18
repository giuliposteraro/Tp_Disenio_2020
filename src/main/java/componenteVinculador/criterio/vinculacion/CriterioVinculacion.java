package componenteVinculador.criterio.vinculacion;

import componenteVinculador.criterio.ResultadoVinculado.ResultadoVinculado;
import componenteVinculador.criterio.orden.CriterioOrden;
import componenteVinculador.vinculable.OperacionVinculable;
import java.util.ArrayList;

public class CriterioVinculacion {
    private ArrayList<ResultadoVinculado> resultados;

    public CriterioVinculacion() {
        resultados = new ArrayList<>();
    }

    public void ejecutar(ArrayList<OperacionVinculable> ingresos,ArrayList<OperacionVinculable> egresos, int rangoDias) {
        ordenar(ingresos,egresos, getCriterioOrden());
        vincular(ingresos,egresos, rangoDias);
    }

    protected void vincular(ArrayList<OperacionVinculable> ingresos, ArrayList<OperacionVinculable> egresos, int rangoDias) {
        for (OperacionVinculable egreso : egresos) {
            for (OperacionVinculable ingreso : ingresos) {
                ResultadoVinculado resultado = this.buscaOCreaResultadoNuevo(ingreso);

                if(resultado.sePuedeVincularEgreso(egreso, rangoDias)){
                    resultado.vincularNuevoEgreso(egreso);

                    agregarResultadoSiEsNecesario(resultado);
                    break;
                }
            }
        }
    }

    void ordenar(ArrayList<OperacionVinculable> ingresos, ArrayList<OperacionVinculable> egresos, CriterioOrden criterioOrden) {
        ingresos.sort(criterioOrden);
        egresos.sort(criterioOrden);
    }

    public ArrayList<ResultadoVinculado> getResultadosVinculados(){
        return resultados;
    }

    private ResultadoVinculado buscaOCreaResultadoNuevo(OperacionVinculable ingreso) {
        for (ResultadoVinculado resultado:resultados) {
            if (resultado.contieneAlIngreso(ingreso)) {
                return resultado;
            }
        }
        return new ResultadoVinculado(ingreso);
    }

    protected CriterioOrden getCriterioOrden() {
       return null;
    }

    private void agregarResultadoSiEsNecesario(ResultadoVinculado resultadoVinculado) {
        if(resultadoVinculado.getEgresos().size() == 1){
            resultados.add(resultadoVinculado);
        }
    }

    public ETipoCriterioVinculacion getTipoCriterio() {
        return null;
    }
}
