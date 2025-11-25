package empleadosyjefes;

public class Jefe extends Empleado {

	private static final int PLUS_JEFE = 250;
	private String departamento;
	public Jefe(String dni, String nombre, int mesEntrada, int anoEntrada, double porcentajeIncremento,String departamento) {
		super(dni, nombre, mesEntrada, anoEntrada, porcentajeIncremento);
		this.setDepartamento(departamento);
	}
	public static int getPlusJefe() {
		return PLUS_JEFE;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	@Override
	public double getSueldoFinal() {
		double precioPlus = super.getSueldoFinal()+PLUS_JEFE;
		return precioPlus;
	}
	
	@Override
	public String infoCompletaConSueldo() {
		String nuevoMesa=super.infoCompletaConSueldo()+"\nDepartamento: "+getDepartamento();
		return nuevoMesa;
	}

}
