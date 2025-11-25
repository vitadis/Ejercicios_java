package ejerciciosEstudiante2526;

import java.time.LocalDate;

public class Alumno {
	private Ciclo ciclo;
	private String nif;
	private String nombre;
	private int edad;
	private LocalDate fechaNac, fechaInscripcion;
	private boolean repetidor;
	private boolean baja;

	public Alumno(Ciclo ciclo, String nif, String nombre, int edad, LocalDate fechaNac, LocalDate fechaInscripcion,
			boolean repetidor, boolean baja) {
		super();
		this.ciclo = ciclo;
		this.nif = nif;
		this.nombre = nombre;
		this.edad = edad;
		this.fechaNac = fechaNac;
		this.fechaInscripcion = fechaInscripcion;
		this.repetidor = repetidor;
		this.baja = baja;
	}

	public Ciclo getCiclo() {
		return ciclo;
	}

	public void setCiclo(Ciclo ciclo) {
		this.ciclo = ciclo;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public LocalDate getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}

	public LocalDate getFechaInscripcion() {
		return fechaInscripcion;
	}

	public void setFechaInscripcion(LocalDate fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

	public boolean isRepetidor() {
		return repetidor;
	}

	public void setRepetidor(boolean repetidor) {
		this.repetidor = repetidor;
	}

	public boolean isBaja() {
		return baja;
	}

	public void setBaja(boolean baja) {
		this.baja = baja;
	}

	@Override
	public String toString() {
		return "Alumno [ciclo=" + ciclo + ", nif=" + nif + ", nombre=" + nombre + ", edad=" + edad + ", fechaNac="
				+ fechaNac + ", fechaInscripcion=" + fechaInscripcion + ", repetidor=" + repetidor + ", baja=" + baja
				+ "]";
	}

}