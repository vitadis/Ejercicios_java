package listasIterador;


import java.util.ArrayList;
import java.util.ListIterator;

public class Ejer2 {

	public static void main(String[] args) {
		// meter elementos despues del que salga la M
		ArrayList<String> alumnos = new ArrayList<>();
		alumnos.add("Ana");
		alumnos.add("Luis");
		alumnos.add("Marta");
		alumnos.add("Juan");
		alumnos.add("Pedro");
		
		
		// lo que hace es recorrer la lista bidireccional
		ListIterator<String> listIterator = alumnos.listIterator();

		// recorre la lista de hacia delante ->
		while (listIterator.hasNext()) {
			String nombre = listIterator.next();
			if (nombre.startsWith("P")) {
				listIterator.set(nombre.toUpperCase());
			} else if (nombre.startsWith("M")) {
				listIterator.add("Marcos");
			}
		}

		System.out.println("Lista después de modificaciones:");
		for (String nombre : alumnos) {
			System.out.println(nombre);
		}

		System.out.println("Lista en orden inverso:");
		// recorre la lista hacia atras <-
		while (listIterator.hasPrevious()) {
			System.out.println(listIterator.previous());
		}
		
		/*Metodos de inserción
		 *agregar = add() , eliminar = set() remove() 
		 * 
		 * 
		 * */
		
		
	}
}