package empleadosyjefes;

import trataExcepciones.Util;

public class MainEmpresa {

	public static void main(String[] args) {
		final int TAMAÑO_FIJO = 125;
		Empleado[] personas = new Empleado[TAMAÑO_FIJO];
		int contador = 0;
		boolean buclee = true;

		while (buclee) {
			int opcion = Util.leerInt(menu());
			switch (opcion) {
			case 1 -> contador = menu1(personas, TAMAÑO_FIJO, contador); // actualizamos contador
			case 2 -> menu2(personas, contador);
			case 3 -> menu3(personas, contador);
			case 4 -> menu4(personas, contador);
			case 5 -> menu5(personas, contador);
			case 6 -> menu6(personas, contador);
			case 7 -> menu7(personas, contador);
			case 8 -> menu8(personas, contador);
			case 9 -> menu9(personas, contador);
			case 10 -> menu10(personas, contador);
			case 11 -> buclee = false;
			default -> System.out.println("Agrega opciones correctas");
			}
		}
	}

	public static String menu() {
		String menusino = "1. Introducir datos de empleado/s y/o jefe/s\n"
				+ "2. Visualizar todos los datos (con sueldo final)\n" + "3. Visualizar los jefes\n"
				+ "4. Visualizar los jefe/s de un departamento concreto\n" + "5. Visualizar mas a detalle\r\n"
				+ "6. Usuarios con mayor sueldo que el introducido\n"
				+ "7. Introducir un nº de años y mostrar los jefes que lleven en la empresa esos o más años.\r\n"
				+ "8. Ordenar los empleados\n" + "9. Dar de baja a un empleade/jefe a partir de su DNI.\n"
				+ "10. Top 20 departamentos\n" + "11. Salir.\r\n" + "ELIJA UNA OPCIÓN:\r\n";
		return menusino;
	}

	public static int menu1(Empleado[] personas, int TAMAÑO_FIJO, int contador) {
		if (contador < TAMAÑO_FIJO) {

			// validar que el dni no este pillado
			String dni = "";
			boolean bueltinha = true;
			while (bueltinha || dni.length() != 9) {
				dni = Util.introducirCadena("DNI unico (Obligatorio): ");
				bueltinha = false;
				for (int i = 0; i < contador; i++) {
					if (personas[i] == null)
						;
					else if (personas[i].getDni().equalsIgnoreCase(dni)) {
						bueltinha = true;
					}
				}
			}

			// para que tenga nombre
			String nombre = "";
			while (nombre.length() == 0) {
				nombre = Util.introducirCadena("Nombre (Obligatorio): ");
			}

			int mesEntrada = Util.leerInt("Mes: ");
			int anoEntrada = Util.leerInt("Año: ");
			double porcentajeIncremento = Util.leerDouble("Porcentaje Incremento: ");
			String esJefe = Util.introducirCadena("Es jefe o empleado normal: ");

			if (esJefe.equalsIgnoreCase("jefe")) {
				String dpto = Util.introducirCadena("Departamento: ");
				personas[contador] = new Jefe(dni, nombre, mesEntrada, anoEntrada, porcentajeIncremento, dpto);
			} else {
				personas[contador] = new Empleado(dni, nombre, mesEntrada, anoEntrada, porcentajeIncremento);
			}
			contador++;
		} else {
			System.out.println("YA NO HAY ESPACIO EN EL ARRAY");
		}
		return contador;
	}

	public static void menu2(Empleado[] personas, int contador) {
		for (int i = 0; i < contador; i++) {
			if (personas[i] != null)
				System.out.println(personas[i].infoCompletaConSueldo() + "\n");
		}
		if (contador == 0)
			System.out.println("No tienes ningun empleado");
	}

	public static void menu3(Empleado[] personas, int contador) {
		boolean existenJefes = false;

		for (int i = 0; i < contador; i++) {
			if (personas[i] instanceof Jefe) {
				System.out.println(personas[i].infoCompletaConSueldo() + "\n");
				existenJefes = true;
			}
		}
		if (!existenJefes)
			System.out.println("No existen jefes");

	}

	public static void menu4(Empleado[] personas, int contador) {
		String seccionJefe = Util.introducirCadena("Ingresa el nombre del departamento: ");

		boolean existenJefes = false;

		for (int i = 0; i < contador; i++) {
			if (personas[i] instanceof Jefe) {
				if (((Jefe) personas[i]).getDepartamento().equalsIgnoreCase(seccionJefe)) {
					System.out.println(personas[i].infoCompletaConSueldo() + "\n");
					existenJefes = true;
				}
			}
		}

		if (!existenJefes)
			System.out.println("No existen jefes en esa seccion");

	}

	public static void menu5(Empleado[] personas, int contador) {
		String nomUser = Util.introducirCadena("Ingresa el nombre de el/los trabajoderes: ");

		boolean existenJefes = false;

		for (int i = 0; i < contador; i++) {
			if (personas[i].getNombre().equalsIgnoreCase(nomUser)) {
				System.out.println(personas[i].infoCompletaConSueldo() + "\n");
				existenJefes = true;
			}
		}
		if (!existenJefes)
			System.out.println("No tienes trabajadores");
	}

	public static void menu6(Empleado[] personas, int contador) {
		int salarioComparar = Util.leerInt("Ingresa el salario a comparar: ");

		boolean existem6 = false;

		for (int i = 0; i < contador; i++) {
			if (personas[i].getSueldoFinal() >= salarioComparar) {
				System.out.println(personas[i].infoCompletaConSueldo() + "\n");
				existem6 = true;
			}
		}
		if (!existem6)
			System.out.println("No tienes trabajadores que ganen esa cantidad");
	}

	public static void menu7(Empleado[] personas, int contador) {
		int anioComp = Util.leerInt("Años a comparar: ");

		boolean existem7 = false;

		for (int i = 0; i < contador; i++) {
			if (personas[i].getAniosEnEmpresa() >= anioComp) {
				System.out.println(personas[i].infoCompletaConSueldo() + "\n");
				existem7 = true;
			}
		}
		if (!existem7)
			System.out.println("No hay trabajadores con esa experiencia");
	}

	public static int menu8(Empleado[] personas, int contador) {
		boolean existem7 = false;

		for (int i = 0; i < contador - 1; i++) {
			for (int x = 0; x < contador - 1 - i; x++) {

				if (personas[x] == null) {
					Empleado tempo = personas[x];
					personas[x] = personas[x + 1];
					personas[x + 1] = tempo;
				} else {
					if (personas[x + 1] != null) {
						if (personas[x].getSueldoFinal() < personas[x + 1].getSueldoFinal()) {
							Empleado tempo = personas[x];

							personas[x] = personas[x + 1];
							personas[x + 1] = tempo;
						}
					}
				}

			}
			existem7 = true;
		}
		if (!existem7)
			System.out.println("No hay trabajadores");
		return contador;
	}

	public static int menu9(Empleado[] personas, int contador) {

		boolean existem9 = false;
		// pedir el dni y validar que solo tenga 9 digitos
		String dni = "";
		while (dni.length() != 9) {
			dni = Util.introducirCadena("DNI unico (Obligatorio): ");
		}

		for (int i = 0; i < contador; i++) {
			if (personas[i].getDni().equalsIgnoreCase(dni)) {
				String confirmacion = Util.introducirCadena("Si deseas confimar escribe 'Si deseo eliminar': ");
				if ("Si deseo eliminar".equalsIgnoreCase(confirmacion)) {
					personas[i] = null;
				} else {
					System.out.println("Se cancelo la operacion");
					break;
				}
				existem9 = true;
			}
		}

		if (!existem9)
			System.out.println("No existe el empleado");

		return contador;
	}

	public static void menu10(Empleado[] personas, int contador) {

		// indicar el departamento y numero de departamento
		String[] departamentos = { "1a", "2a", "3a", "4a", "5a" };
		int[] contadores = new int[departamentos.length];

		for (int i = 0; i < departamentos.length; i++) {
			for (int x = 0; x < contador; x++) {
				if (departamentos[i].equalsIgnoreCase(((Jefe) personas[x]).getDepartamento()))
					contadores[i]++;
			}
			System.out.println(departamentos[i]+": "+contadores[i]);
		}
		

	}

}