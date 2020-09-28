package dominio.licitacion;

import java.util.ArrayList;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.licitacion.criterioSeleccion.CriterioSeleccionDeProveedor;
import dominio.notificador_suscriptores.NotificadorSuscriptores;
import dominio.operaciones.OperacionEgreso;

public class Licitacion {
	private final OperacionEgreso compra;
	private ArrayList<Presupuesto> presupuestos;
	private boolean finalizada;
	private boolean resultadoCantPresupCargada;
	private boolean resultadoSeleccionDeProveedor;
	private boolean resultadoPresupCorresp;
	private CriterioSeleccionDeProveedor criterioSeleccionDeProveedor;
	private final NotificadorSuscriptores notificadorSuscriptores;
	private ArrayList<CuentaUsuario> suscriptores;
	public String identificadorLicitacion;

	public Licitacion(OperacionEgreso compra, NotificadorSuscriptores notificadorSuscriptores){
		this.compra = compra;
		this.presupuestos = new ArrayList<>();
		this.suscriptores = new ArrayList<>();
		this.finalizada = false;
		this.notificadorSuscriptores = notificadorSuscriptores;
	}

	public ArrayList<Presupuesto> getPresupuestos() {
		return presupuestos;
	}

	public void agregarPresupuesto(Presupuesto presup) {
		if(presup.esValido(compra)) {
			this.presupuestos.add(presup);
		}
	}

	public void sacarPresupuesto(Presupuesto presup) {
		this.presupuestos.remove(presup);
	}

	public void agregarCriterioSeleccionDeProveedor(CriterioSeleccionDeProveedor criterioSeleccionDeProveedor){
		this.criterioSeleccionDeProveedor = criterioSeleccionDeProveedor;
	}

	private boolean cumpleCriterioCantidadPresupuestos(OperacionEgreso operacion) {
		resultadoCantPresupCargada = operacion.getPresupuestosNecesarios() == this.presupuestos.size();
		return resultadoCantPresupCargada;
	}

	public boolean cantidadItemsValida(OperacionEgreso operacion) {
		return this.getPresupuestos().stream().allMatch(p->p.esValido(operacion));
	}

	private boolean cumpleCriterioCorrespondeYSeleccionDeProveedor(OperacionEgreso operacion) {
		Presupuesto presupuestoCorrespondiente = presupuestos.stream().filter(p->p.esCorrespondiente(operacion))
				.findFirst()
				.get();

		resultadoPresupCorresp = presupuestoCorrespondiente!=null;
		resultadoSeleccionDeProveedor = criterioSeleccionDeProveedor.presupuestoElegido(presupuestos) == presupuestoCorrespondiente;
		return resultadoPresupCorresp && resultadoSeleccionDeProveedor;
	}

	public String descripcionCantPresupuestos() {
		return resultadoCantPresupCargada?"Criterio de cantidad de presupuestos: Valido":"Criterio de cantidad de presupuestos: Invalido";
	}

	public String descripcionPresupCorresp() {
		return resultadoPresupCorresp?"Criterio presupuesto correspondiente: Valido":"Criterio presupuesto correspondiente: Invalido";
	}

	public String descripcionSeleccionDeProveedor() {
		return resultadoSeleccionDeProveedor?"Criterio de seleccion de proveedor: Valido":"Criterio de seleccion de proveedor: Invalido";
	}

	public String mensajeTexto() {
		return this.descripcionCantPresupuestos() + "\n" + this.descripcionSeleccionDeProveedor() + "\n" + this.descripcionPresupCorresp();
	}

	public void licitar () {
		this.cumpleCriterioCorrespondeYSeleccionDeProveedor(compra);
		this.cumpleCriterioCantidadPresupuestos(compra);
		this.finalizada = true;
		notificadorSuscriptores.notificar(this.mensajeTexto(),this.suscriptores);
	}

	public boolean puedeLicitar() {
		return this.cumpleCriterioCantidadPresupuestos(compra) &&
				this.cumpleCriterioCorrespondeYSeleccionDeProveedor(compra);
	}

	public void suscribir(CuentaUsuario cuenta) throws Exception {
		if(this.validarSuscripcion()) {
			suscriptores.add(cuenta);
		}
		else {
			throw new Exception("Licitacion cerrada, no puede suscribir.");
		}
	}

	private boolean validarSuscripcion(){
		return !this.finalizada;
	}

	public void setIdentificador(String identificadorLicitacion){
		if(this.identificadorLicitacion == null) {
			this.identificadorLicitacion = identificadorLicitacion;
		}
	}

	public String getIdentificador() {
		return identificadorLicitacion;
	}

	public OperacionEgreso getOperacionEgreso(){
		return compra;
	}

	public boolean estaFinalizada(){
		return finalizada;
	}
}
