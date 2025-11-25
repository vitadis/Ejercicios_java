package empleadosyjefes;

import java.time.LocalDate;
import java.time.Period;

public class Empleado {
	public static final String NOMBRE_EMPRESA = "Eléctrica S.A.";
	public static final double SUELDO_BASE = 1000.0;

	private String dni;
	private String nombre;
	private int mesEntrada;
	private int anoEntrada;
	private double porcentajeIncremento;

	public Empleado(String dni, String nombre, int mesEntrada, int anoEntrada, double porcentajeIncremento) {
		this.dni = dni;
		this.nombre = nombre;
		this.mesEntrada = Math.max(1, Math.min(12, mesEntrada));
		this.anoEntrada = anoEntrada;
		this.porcentajeIncremento = porcentajeIncremento;
	}

	public String getDni() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}

	public int getMesEntrada() {
		return mesEntrada;
	}

	public int getAnoEntrada() {
		return anoEntrada;
	}

	public double getPorcentajeIncremento() {
		return porcentajeIncremento;
	}

	/** Años completos en la empresa (tiene en cuenta el mes). */
	public int getAniosEnEmpresa() {
		LocalDate hoy = LocalDate.now();
		LocalDate entrada;
		try {
			entrada = LocalDate.of(anoEntrada, mesEntrada, 1);
		} catch (Exception e) {
			entrada = LocalDate.of(anoEntrada, 1, 1);
		}
		Period p = Period.between(entrada, hoy);
		return p.getYears();
	}

	/**
	 * Sueldo final = sueldo base + porcentaje sobre base + 100€ si lleva >=6 años
	 */
	public double getSueldoFinal() {
		double sueldo = SUELDO_BASE + (SUELDO_BASE * porcentajeIncremento);
		if (getAniosEnEmpresa() >= 6)
			sueldo += 100.0;
		return sueldo;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setMesEntrada(int mesEntrada) {
		this.mesEntrada = mesEntrada;
	}

	public void setAnoEntrada(int anoEntrada) {
		this.anoEntrada = anoEntrada;
	}

	public void setPorcentajeIncremento(double porcentajeIncremento) {
		this.porcentajeIncremento = porcentajeIncremento;
	}

	/** Texto con información básica y sueldo final */
	public String infoCompletaConSueldo() {

		StringBuilder sb = new StringBuilder();

		sb.append("Empresa: ").append(NOMBRE_EMPRESA).append("\n");
		sb.append("DNI: ").append(dni).append("\n");
		sb.append("Nombre: ").append(nombre).append("\n");
		sb.append("Fecha de entrada: ").append(String.format("%02d", mesEntrada)).append("/").append(anoEntrada)
				.append("\n");
		sb.append("Porcentaje incremento: ").append(porcentajeIncremento).append("\n");
		sb.append(String.format("Sueldo final: %.2f€", getSueldoFinal()));
		return sb.toString();
	}
}
