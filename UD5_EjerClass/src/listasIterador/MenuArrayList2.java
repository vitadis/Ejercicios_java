package listasIterador;

import trataExcepciones.*;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * MenuAppendOptions
 *
 * Menú que: - Pide un nombre y muestra los usuarios "debajo" de ese nombre (los
 * que aparecen después). - Permite elegir entre operar por "nombre" o por
 * "grupo de caracteres". - Grupo de caracteres: pide grupoA (buscar) y grupoB
 * (añadir). Se añadirá grupoB1, grupoB2... a cada elemento que contenga grupoA
 * (para evitar duplicados). - Nombre: pide un nombre origen; si existe, pide
 * segundo nombre y lo concatena al origen.
 *
 * Usa Util (trataExcepciones.Util) para todas las entradas. Todas las
 * iteraciones sobre la lista usan ListIterator.
 */
public class MenuArrayList2 {

	public static void main(String[] args) {
		ArrayList<String> usuarios = new ArrayList<>();
		// Ejemplos iniciales
		usuarios.add("ALICE");
		usuarios.add("BOB");
		usuarios.add("CARLOS");
		usuarios.add("DAVID");
		usuarios.add("CARLA");
		usuarios.add("ALEX");

		boolean salir = false;
		while (!salir) {
			System.out.println("\n--- MENÚ PRINCIPAL ---");
			System.out.println("1. Pedir un nombre y mostrar usuarios 'debajo' de ese nombre");
			System.out.println("2. Menu de añadir por NOMBRE o por GRUPO DE CARACTERES");
			System.out.println("3. Imprimir todos los usuarios");
			System.out.println("4. Salir");
			int op = Util.leerInt("Elige una opción (1-4): ");

			switch (op) {
			case 1:
				mostrarDebajo(usuarios);
				break;
			case 2:
				menuNombreOGrupo(usuarios);
				break;
			case 3:
				imprimirTodos(usuarios);
				break;
			case 4:
				salir = true;
				System.out.println("Saliendo...");
				break;
			default:
				System.out.println("Opción inválida. Intenta de nuevo.");
			}
		}
	}

	/**
	 * Devuelve un ListIterator para la lista dada.
	 */
	public static ListIterator<String> crearListIterator(ArrayList<String> lista) {
		return lista.listIterator();
	}

	/**
	 * Opción 1: Pide un nombre y muestra los usuarios que aparecen después de ese
	 * nombre en la lista. Si no existe el nombre, muestra mensaje y vuelve al menú.
	 *
	 * Recorre con ListIterator: cuando encuentra el nombre, continúa el iterador
	 * para imprimir los elementos restantes.
	 */
	public static void mostrarDebajo(ArrayList<String> usuarios) {
		if (usuarios.isEmpty()) {
			System.out.println("La lista está vacía.");
			return;
		}
		String nombre = Util.introducirCadena("Introduce el nombre a buscar: ").trim().toUpperCase();
		if (nombre.isEmpty()) {
			System.out.println("Nombre vacío. Operación cancelada.");
			return;
		}

		ListIterator<String> it = crearListIterator(usuarios);
		boolean encontrado = false;
		while (it.hasNext()) {
			String actual = it.next();
			if (actual.equals(nombre)) {
				encontrado = true;
				break; // it queda justo después del elemento encontrado
			}
		}

		if (!encontrado) {
			System.out.println("El nombre \"" + nombre + "\" no existe en la lista.");
			return;
		}

		// Mostrar los elementos "debajo" (los que siguen)
		boolean haySiguientes = false;
		while (it.hasNext()) {
			System.out.println(it.next());
			haySiguientes = true;
		}
		if (!haySiguientes) {
			System.out.println("No hay usuarios debajo de \"" + nombre + "\" (es el último elemento).");
		}
	}

	/**
	 * Opción 2: Menu que pregunta si se va a operar por NOMBRE o por GRUPO DE
	 * CARACTERES.
	 */
	public static void menuNombreOGrupo(ArrayList<String> usuarios) {
		System.out.println("\n--- MENU: Nombre o Grupo de caracteres ---");
		System.out.println("1. Operar por NOMBRE (concatenar un segundo nombre a uno existente)");
		System.out.println("2. Operar por GRUPO DE CARACTERES (buscar grupoA y añadir grupoB1, groupB2...)");
		System.out.println("3. Volver al menú principal");
		int op = Util.leerInt("Elige opción (1-3): ");
		switch (op) {
		case 1:
			operarPorNombre(usuarios);
			break;
		case 2:
			operarPorGrupo(usuarios);
			break;
		case 3:
			System.out.println("Volviendo al menú principal...");
			break;
		default:
			System.out.println("Opción inválida. Volviendo al menú principal.");
		}
	}

	/**
	 * Caso "NOMBRE": Pide un nombre origen; si no existe, informa y vuelve. Si
	 * existe, pide un segundo nombre y lo concatena al final del nombre origen.
	 *
	 * Usa ListIterator para localizar y set() para actualizar.
	 */
	public static void operarPorNombre(ArrayList<String> usuarios) {
		if (usuarios.isEmpty()) {
			System.out.println("La lista está vacía.");
			return;
		}
		String origen = Util.introducirCadena("Introduce el nombre origen: ").trim().toUpperCase();
		if (origen.isEmpty()) {
			System.out.println("Nombre vacío. Operación cancelada.");
			return;
		}

		// Buscar con ListIterator
		ListIterator<String> it = crearListIterator(usuarios);
		boolean encontrado = false;
		while (it.hasNext()) {
			String actual = it.next();
			if (actual.equals(origen)) {
				encontrado = true;
				// pedimos segundo nombre y lo concatenamos
				String segundo = Util.introducirCadena("Introduce el segundo nombre a añadir: ").trim();
				// No forzamos upper/lower: se concatenará tal cual el usuario escriba
				String nuevo = actual + segundo;
				it.set(nuevo);
				System.out.println("Usuario actualizado: " + nuevo);
				break;
			}
		}

		if (!encontrado) {
			System.out.println("El nombre \"" + origen + "\" no existe. Volviendo al menú.");
		}
	}

	/**
	 * Caso "GRUPO DE CARACTERES": - Pide grupoA (a buscar) y grupoB (a añadir). - A
	 * cada elemento que contenga grupoA le añade grupoBX (X = 1,2,3...) para
	 * diferenciar. - Usa ListIterator para iterar y set() para actualizar.
	 *
	 * Ejemplo: lista: [ALICE, BOB, ALEX, ALICIA] grupoA = "ALI", grupoB = "_X"
	 * Resultado: [ALICE_X1, BOB, ALEX, ALICIA_X2] (se añade _X1 y _X2 a los que
	 * contienen "ALI")
	 */
	public static void operarPorGrupo(ArrayList<String> usuarios) {
		if (usuarios.isEmpty()) {
			System.out.println("La lista está vacía.");
			return;
		}
		String grupoA = Util.introducirCadena("Introduce el grupo de caracteres a buscar (grupoA): ");
		if (grupoA == null || grupoA.isEmpty()) {
			System.out.println("GrupoA vacío. Operación cancelada.");
			return;
		}
		String grupoB = Util.introducirCadena("Introduce el grupo de caracteres a añadir (grupoB): ");
		if (grupoB == null)
			grupoB = "";

		// Contador para diferenciar cada append y evitar duplicados
		int contador = 0;

		ListIterator<String> it = crearListIterator(usuarios);
		boolean huboCambio = false;
		while (it.hasNext()) {
			String actual = it.next();
			if (actual.contains(grupoA)) {
				contador++;
				// Añadimos grupoB + contador para diferenciar: por ejemplo grupoB1, grupoB2...
				String añadido = grupoB + contador;
				String nuevo = actual + añadido;
				it.set(nuevo);
				huboCambio = true;
			}
		}

		if (huboCambio) {
			System.out
					.println("Operación completada. Se añadieron sufijos del tipo '" + grupoB + "X' en coincidencias.");
		} else {
			System.out.println("No se encontró ninguna coincidencia con \"" + grupoA + "\".");
		}
	}

	/**
	 * Imprime todos los usuarios (con índice) usando ListIterator.
	 */
	public static void imprimirTodos(ArrayList<String> usuarios) {
		if (usuarios.isEmpty()) {
			System.out.println("La lista está vacía.");
			return;
		}

		ListIterator<String> it = crearListIterator(usuarios);
		System.out.println("Usuarios actuales:");
		while (it.hasNext()) {
			int idx = it.nextIndex();
			String actual = it.next();
			System.out.printf("%d: %s%n", idx, actual);
		}
	}
}
