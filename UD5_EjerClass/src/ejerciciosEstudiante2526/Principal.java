package ejerciciosEstudiante2526;

import java.time.LocalDate;

import java.util.ArrayList;

import java.util.ListIterator;

import trataExcepciones.Util;

public class Principal {

	public static void main(String[] args) {

		ArrayList<Alumno> alumnos = new ArrayList<>();
		boolean validador = true;

		while (validador) {
			int opcion = menu();

			switch (opcion) {
			case 1 -> menu1(alumnos);
			case 2 -> menu2(alumnos);
			case 3 -> menu3(alumnos);
			case 4 -> menu4(alumnos);
			case 5 -> menu5(alumnos);
			case 6 -> menu6(alumnos);
			case 7 -> menu7(alumnos);
			case 8 -> validador = false;
			default -> mensajeDefault();
			}
		}

	}

	// mensaje en caso de que el usuario sea tonto
	public static void mensajeDefault() {
		System.out.println("Agrega una opción válida.");
	}

	// - menu del principio
	public static int menu() {
		String menu = "1. Matricular alumnos\n" + "2. Listado de alumnos\n" + "3. Información de un alumno por NIF\n"
				+ "4. Modificar alumno\n" + "5. Cambiar repetidor DAW por edad\n" + "6. Dar de baja\n"
				+ "7. Eliminar todos los alumnos que se dieron de baja\n" + "8. Salir\n" + "Elige una opcion: ";

		return Util.leerInt(menu);
	}

	// - menu 1, donde valido que los usuarios no se repitan en un mismo ciclo, el
	// tamaño del nif y otras cosillas mas
	public static ArrayList<Alumno> menu1(ArrayList<Alumno> lista) {

		// le pregunto de nuevo si quiere continuar
		String pregunta1 = Util.introducirCadena("¿Quieres matricular? (s/n): ");
		if (!"s".equalsIgnoreCase(pregunta1)) {
			System.out.println("Volviendo al menú...");
			return lista;
		}

		// bucle donde la condicion es null, para que a menos que agregue un campo
		// correcto seguira...
		Ciclo ciclodefi = null;
		String ciclo;
		do {
			ciclo = Util.introducirCadena("DAW o DAM: ").toUpperCase();
			if (ciclo.equals("DAW"))
				ciclodefi = Ciclo.DAW;
			else if (ciclo.equals("DAM"))
				ciclodefi = Ciclo.DAM;
			else
				System.out.println("Ciclo no válido.");
		} while (ciclodefi == null);

		// Campo para tener un dni unico, pero si puede estar en mas de dos ciclos, pero
		// no se puede repetir en el mismo ciclo
		String nif1;
		while (true) {
			nif1 = Util.introducirCadena("Ingresa tu NIF (9 caracteres): ").replace(" ", ""); // lo uso en caso de que
																								// el usuario me
																								// entregue espacios en
																								// blancos

			if (nif1.length() != 9) {
				System.out.println("El NIF debe tener 9 caracteres.");
				continue; // con esta sentencia le digo que continue
			}

			if (nifExisteEnCiclo(lista, nif1, ciclodefi, 0)) {
				System.out.println("Este NIF ya está registrado en el ciclo " + ciclodefi + ". No puedes registrarlo.");
				continue; // lo mismo que el de arriba
			}

			// terminara si no se ejecuta el continue, osea cuando no se cumpla ninguno de
			// los if
			break;
		}

		// Nombre obligatorio
		String nombre;
		do {
			nombre = Util.introducirCadena("Nombre del alumno: ");
			if (nombre.length() == 0)
				System.out.println("El nombre no puede quedar vacío.");
		} while (nombre.length() == 0);

		// edad sin mas lo guardo para modificarlo segun su fecha de nacimiento
		int edad = 0;

		// fecha de nacimiento
		LocalDate fechaNac = null;

		boolean fechaIncorrecta = true;
		while (fechaIncorrecta) {
			try {
				int dia = Util.leerInt("Introduce el día de nacimiento: ");
				int mes = Util.leerInt("Introduce el mes de nacimiento: ");
				int anio = Util.leerInt("Introduce el año de nacimiento: ");
				// si el formato no es valido me dara una excepcion
				fechaNac = LocalDate.of(anio, mes, dia);
				fechaIncorrecta = false;
				if (LocalDate.now().getYear() - anio <= 60 && LocalDate.now().getYear() - anio >= 16)
					edad = LocalDate.now().getYear() - anio;
				else {
					fechaIncorrecta = true;
					System.out.println("No agregues jubilados o niños");
				}
			} catch (Exception e) {
				System.out.println("Formato de fecha incorrecta");
			}
		}

		// la fecha de matriculacion es la actual
		LocalDate fechaInscrip = LocalDate.now();

		// Es repetidor
		String esRepe = Util.introducirCadena("Se dio de baja s/n: ").toUpperCase();
		boolean esRepeBool = esRepe.equals("S");

		// agrego todo a la lista alumnos
		lista.add(new Alumno(ciclodefi, nif1, nombre, edad, fechaNac, fechaInscrip, false, esRepeBool));
		System.out.println("Alumno matriculado correctamente en " + ciclodefi);

		return lista;
	}

	// - menu 2, para imprimir
	public static ArrayList<Alumno> menu2(ArrayList<Alumno> lista) {

		boolean existe = false;
		System.out.println("Listado de alumnos:");
		for (Alumno a : lista) {
			System.out.println(a);
			existe = true;
		}
		if (!existe)
			System.out.println("No existen estudiantes registrados");
		return lista;
	}

	// mostrar informacion del usuario, segun su nif
	public static ArrayList<Alumno> menu3(ArrayList<Alumno> lista) {

		String nif = Util.introducirCadena("Introduce el NIF: ").replace(" ", "");
		boolean encontrado = false;

		for (Alumno a : lista) {
			if (a.getNif().equalsIgnoreCase(nif)) {

				String estudiante = String.format(
						"Ciclo: %s\nNif: %s\nNombre: %s\nEdad: %d\nFecha: %s\nEsta de baja: %s", a.getCiclo(),
						a.getNif(), a.getNombre(), a.getEdad(), a.getFechaInscripcion(), a.isBaja() ? "Sí" : "No");

				System.out.println(estudiante);
				encontrado = true;
				break;
			}
		}

		if (!encontrado)
			System.out.println("No existe un alumno con ese NIF.");

		return lista;
	}

	// - menu 4, modifico todo con el nif del alumno
	public static ArrayList<Alumno> menu4(ArrayList<Alumno> lista) {

		try {
			String nif = Util.introducirCadena("Introduce el NIF: ").replace(" ", "");
			String ciclo = Util.introducirCadena("Introduce el ciclo: ").toUpperCase();
			Ciclo cicloReal = null;
			if ("DAM".equals(ciclo))
				cicloReal = Ciclo.DAM;
			else if ("DAW".equals(ciclo))
				cicloReal = Ciclo.DAW;

			int indice = 0;
			if (nifExisteEnCiclo(lista, nif, cicloReal, indice)) {
				System.out.println("Solo podras modificar los campos modificables: ");

				String nombre;
				do {
					nombre = Util.introducirCadena("Introduce el Nombre: ");
				} while (nombre.length() <= 0);

				String esRepe = Util.introducirCadena("Es Repetidor s/n: ").toUpperCase();
				boolean esRepeBool = esRepe.equals("S");

				String seDioBaja = Util.introducirCadena("Se dio de baja s/n: ").toUpperCase();
				boolean seDioBajaBool = seDioBaja.equals("S");

				Alumno alumnoCambiar = lista.get(indice);
				alumnoCambiar.setNombre(nombre);
				alumnoCambiar.setBaja(seDioBajaBool);
				alumnoCambiar.setRepetidor(esRepeBool);

				lista.set(indice, alumnoCambiar);
			} else
				System.out.println("No existe el alumno en este ciclo");

		} catch (Exception e) {
			System.out.println("Selecciona un ciclo valido, volviendo al menu...");
		}
		return lista;
	}

	// -menu 5 modificar el campo de los repetidores con una edad determinada
	public static ArrayList<Alumno> menu5(ArrayList<Alumno> lista) {
		ListIterator<Alumno> iterador = lista.listIterator();

		int edad = Util.leerInt("Añademe una edad");
		int contador = 0;
		while (iterador.hasNext()) {
			contador++;
			Alumno junior = iterador.next();
			if (junior.isRepetidor() && edad == junior.getEdad()) {
				System.out.println("Solo modificaremos los campos modificables: ");
				System.out.println(
						"Repetidor nº" + contador + " NIf: " + junior.getNif() + " Nombre: " + junior.getNombre());

				String nombre = Util.introducirCadena("Dime tu nombre: ");

				String esRepe = Util.introducirCadena("Es Repetidor s/n: ").toUpperCase();
				boolean esRepeBool = esRepe.equals("S");

				String seDioBaja = Util.introducirCadena("Se dio de baja s/n: ").toUpperCase();
				boolean seDioBajaBool = seDioBaja.equals("S");

				String preguntaModi = Util.introducirCadena("En serio quieres modificar s/n: ").toUpperCase();
				boolean preguntaModiBool = preguntaModi.equals("S");

				if (preguntaModiBool) {
					junior.setNombre(nombre);
					junior.setRepetidor(esRepeBool);
					junior.setBaja(seDioBajaBool);
					iterador.set(junior);
				} else
					System.out.println("No pasa nada en otra modificamos");
			}
		}
		return lista;
	}

	// - menu 6 dar de baja a un alumno
	public static ArrayList<Alumno> menu6(ArrayList<Alumno> lista) {

		String nif = Util.introducirCadena("Introduce el NIF: ").replace(" ", "");
		Ciclo cicloReal = null;
		if (estaEnDosCiclos(lista, nif)) {
			String ciclo;
			do {
				ciclo = Util.introducirCadena("Introduce el ciclo: ").toUpperCase();
				if ("DAM".equals(ciclo))
					cicloReal = Ciclo.DAM;
				else if ("DAW".equals(ciclo))
					cicloReal = Ciclo.DAW;

			} while (ciclo.equals("DAM") || ciclo.equals("DAW"));
		}

		int indice = 0;
		if (nifExisteEnCiclo(lista, nif, cicloReal, indice)) {
			Alumno alumnoCambiar = lista.get(indice);
			alumnoCambiar.setBaja(true);

			lista.set(indice, alumnoCambiar);
		} else
			System.out.println("No existe el alumno en ningun grado");
		return lista;
	}

	// menu 7 eliminar a los alumnos que se dieron de baja
	public static ArrayList<Alumno> menu7(ArrayList<Alumno> lista) {
		ListIterator<Alumno> iterador = lista.listIterator();
		while(iterador.hasNext()) {
			Alumno junior = iterador.next();
			if (junior.isBaja()) {
				iterador.remove();
			}
		}
		return lista;
	
	
	
	}

	// Metodos secundarios

	// metodo para verificar la existencia de un nif con su ciclo
	private static boolean nifExisteEnCiclo(ArrayList<Alumno> lista, String nif, Ciclo ciclo, int indice) {
		indice = 0;
		for (Alumno a : lista) {
			if (a.getCiclo() == ciclo && a.getNif().equalsIgnoreCase(nif)) {
				return true;
			}
			indice++;
		}
		return false;
	}

	// verificar que un alumno esta en dos ciclos
	private static boolean estaEnDosCiclos(ArrayList<Alumno> lista, String nif) {
		int indice = 0;
		for (Alumno a : lista) {
			if (a.getNif().equalsIgnoreCase(nif))
				indice++;
		}
		boolean estaEnMasCiclos = indice == 2;
		return estaEnMasCiclos;

	}

}
