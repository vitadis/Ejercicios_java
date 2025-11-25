package listasIterador;

import trataExcepciones.*;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Menú para manipular una lista de usuarios usando ListIterator en las iteraciones.
 * Reemplaza el uso de Scanner por la clase Util proporcionada.
 *
 * - No se usa Collections.
 * - No hay variables globales.
 * - Crear ListIterator: crearListIterator(lista)
 *
 * Opciones:
 * 1. Agregar usuario (convierte a MAYÚSCULAS). Si ya existe, informa y vuelve al menú.
 * 2. Ordenar alfabéticamente (se usa ListIterator para iterar y set()).
 * 3. Cambiar caracteres (target -> replacement) en todos los elementos (ListIterator).
 * 4. Buscar usuarios que contengan substring (ListIterator).
 * 5. Eliminar usuarios que contengan substring (ListIterator.remove()).
 * 6. Imprimir todos (ListIterator).
 * 7. Cambiar de posición: busca índices con for y luego intercambia usando ListIterator.
 * 8. Salir
 */
public class MenuArrayList {

    public static void main(String[] args) {
        // Lista local (no global)
        ArrayList<String> usuarios = new ArrayList<>();
        // Datos iniciales de ejemplo (mayúsculas para mantener consistencia)
        usuarios.add("ALICE");
        usuarios.add("BOB");
        usuarios.add("CARLOS");

        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENÚ USUARIOS ---");
            System.out.println("1. Agregar usuario (se convierte a MAYÚSCULAS).");
            System.out.println("2. Ordenar usuarios alfabéticamente (usa ListIterator).");
            System.out.println("3. Cambiar caracteres en todos los usuarios.");
            System.out.println("4. Buscar usuarios que contengan un grupo de caracteres.");
            System.out.println("5. Eliminar usuarios que contengan un grupo de caracteres.");
            System.out.println("6. Imprimir todos los usuarios.");
            System.out.println("7. Cambiar de posición (buscar índices con for y luego intercambiar).");
            System.out.println("8. Salir.");
            int opcion = Util.leerInt("Elige una opción (1-8): ");

            switch (opcion) {
                case 1:
                    agregarUsuario(usuarios);
                    break;
                case 2:
                    ordenarAlfabeticamente(usuarios);
                    break;
                case 3:
                    cambiarCaracteres(usuarios);
                    break;
                case 4:
                    buscarPorSubstring(usuarios);
                    break;
                case 5:
                    eliminarPorSubstring(usuarios);
                    break;
                case 6:
                    imprimirTodos(usuarios);
                    break;
                case 7:
                    cambiarPosiciones(usuarios);
                    break;
                case 8:
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
     * Según tu petición, solo recibe la referencia a la ArrayList.
     */
    public static ListIterator<String> crearListIterator(ArrayList<String> lista) {
        return lista.listIterator();
    }

    /**
     * Opción 1:
     * Agrega un usuario convertido a mayúscula. Si ya existe (comparación exacta),
     * muestra mensaje y vuelve al menú.
     * La comprobación de existencia se hace con ListIterator.
     */
    public static void agregarUsuario(ArrayList<String> usuarios) {
        String nombre = Util.introducirCadena("Introduce el nombre del usuario a agregar: ").trim().toUpperCase();
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
                break;
            }
        }

        if (encontrado) {
            System.out.println("El usuario \"" + nombre + "\" ya existe. Volviendo al menú.");
            return;
        }

        usuarios.add(nombre);
        System.out.println("Usuario \"" + nombre + "\" agregado correctamente.");
    }

    /**
     * Opción 2:
     * Ordena alfabéticamente sin Collections. Implementación: Selection Sort.
     * - Bucle externo con for (permitido para "resto").
     * - Búsqueda del mínimo en cada paso con ListIterator.
     * - Intercambio usando ListIterator.set().
     */
    public static void ordenarAlfabeticamente(ArrayList<String> usuarios) {
        int n = usuarios.size();
        if (n < 2) {
            System.out.println("Lista con menos de 2 elementos: nada que ordenar.");
            return;
        }

        // Selection sort: para cada posición i, buscamos el mínimo en [i..n-1]
        for (int i = 0; i < n - 1; i++) {
            // Encontrar el índice del mínimo en [i..n-1] usando ListIterator
            int minIndex = i;
            String minValue = null;

            ListIterator<String> it = usuarios.listIterator(i);
            if (it.hasNext()) {
                minValue = it.next(); // corresponde a índice i
            }
            int currentIndex = i;
            while (it.hasNext()) {
                String siguiente = it.next();
                currentIndex++;
                if (siguiente.compareTo(minValue) < 0) {
                    minValue = siguiente;
                    minIndex = currentIndex;
                }
            }

            // Si minIndex != i, intercambiar elemento en i con minIndex usando ListIterator
            if (minIndex != i) {
                // obtener valor en i mediante ListIterator posicionado en i
                ListIterator<String> itI = usuarios.listIterator(i);
                String valueI = null;
                if (itI.hasNext()) valueI = itI.next(); // value en i

                // establecer en i el minValue
                itI.set(minValue);

                // ahora ir al minIndex y establecer el antiguo valueI
                ListIterator<String> itMin = usuarios.listIterator(minIndex);
                String valueAtMin = null;
                if (itMin.hasNext()) valueAtMin = itMin.next(); // este era minValue original
                // seteamos al valor que antes estaba en i
                itMin.set(valueI);
            }
            // continúa siguiente i
        }

        System.out.println("Lista ordenada alfabéticamente.");
    }

    /**
     * Opción 3:
     * Cambia en todos los elementos un substring por otro; recorre con ListIterator y usa set().
     */
    public static void cambiarCaracteres(ArrayList<String> usuarios) {
        if (usuarios.isEmpty()) {
            System.out.println("La lista está vacía.");
            return;
        }
        String target = Util.introducirCadena("Introduce el caracter o grupo de caracteres a buscar (target): ");
        if (target == null || target.isEmpty()) {
            System.out.println("Target vacío. Operación cancelada.");
            return;
        }
        String replacement = Util.introducirCadena("Introduce el nuevo caracter(es) (replacement): ");

        ListIterator<String> it = crearListIterator(usuarios);
        boolean huboCambio = false;
        while (it.hasNext()) {
            String actual = it.next();
            String modificado = actual.replace(target, replacement);
            if (!modificado.equals(actual)) {
                it.set(modificado);
                huboCambio = true;
            }
        }

        if (huboCambio) System.out.println("Reemplazos aplicados correctamente.");
        else System.out.println("No se encontró el grupo de caracteres en ningún usuario.");
    }

    /**
     * Opción 4:
     * Pide un substring y muestra los usuarios que lo contienen. Recorrido con ListIterator.
     */
    public static void buscarPorSubstring(ArrayList<String> usuarios) {
        if (usuarios.isEmpty()) {
            System.out.println("La lista está vacía.");
            return;
        }
        String buscado = Util.introducirCadena("Introduce el caracter o grupo de caracteres a buscar: ");
        if (buscado == null || buscado.isEmpty()) {
            System.out.println("Búsqueda vacía, operación cancelada.");
            return;
        }

        ListIterator<String> it = crearListIterator(usuarios);
        boolean encontradoAlgo = false;
        while (it.hasNext()) {
            String actual = it.next();
            if (actual.contains(buscado)) {
                System.out.println(actual);
                encontradoAlgo = true;
            }
        }

        if (!encontradoAlgo) System.out.println("No se encontró ningún usuario que contenga \"" + buscado + "\".");
    }

    /**
     * Opción 5:
     * Elimina usuarios que contienen un substring. Uso de ListIterator.remove() para eliminar de forma segura.
     */
    public static void eliminarPorSubstring(ArrayList<String> usuarios) {
        if (usuarios.isEmpty()) {
            System.out.println("La lista está vacía.");
            return;
        }
        String buscado = Util.introducirCadena("Introduce el caracter o grupo de caracteres que eliminará al usuario: ");
        if (buscado == null || buscado.isEmpty()) {
            System.out.println("Input vacío, operación cancelada.");
            return;
        }

        ListIterator<String> it = crearListIterator(usuarios);
        boolean borradoAlgo = false;
        while (it.hasNext()) {
            String actual = it.next();
            if (actual.contains(buscado)) {
                it.remove();
                borradoAlgo = true;
            }
        }

        if (borradoAlgo) System.out.println("Eliminación completada.");
        else System.out.println("No se encontraron usuarios a eliminar con \"" + buscado + "\".");
    }

    /**
     * Opción 6:
     * Imprime todos los usuarios con índice usando ListIterator.
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

    /**
     * Opción 7:
     * Cambia de posición (intercambia) dos usuarios dados por nombre.
     * - Busca índices con un for (tal como pediste).
     * - Si ambos existen, intercambia usando ListIterator.set().
     */
    public static void cambiarPosiciones(ArrayList<String> usuarios) {
        if (usuarios.isEmpty()) {
            System.out.println("La lista está vacía.");
            return;
        }

        String u1 = Util.introducirCadena("Introduce el nombre del primer usuario: ").trim().toUpperCase();
        String u2 = Util.introducirCadena("Introduce el nombre del segundo usuario: ").trim().toUpperCase();

        if (u1.isEmpty() || u2.isEmpty()) {
            System.out.println("Nombre(s) vacíos, operación cancelada.");
            return;
        }

        // Buscar índices con for (no ListIterator) tal y como pediste explícitamente
        int idx1 = -1;
        int idx2 = -1;
        for (int i = 0; i < usuarios.size(); i++) {
            String val = usuarios.get(i);
            if (val.equals(u1) && idx1 == -1) idx1 = i;
            if (val.equals(u2) && idx2 == -1) idx2 = i;
            if (idx1 != -1 && idx2 != -1) break;
        }

        if (idx1 == -1) {
            System.out.println("Usuario \"" + u1 + "\" no encontrado. Volviendo al menú.");
            return;
        }
        if (idx2 == -1) {
            System.out.println("Usuario \"" + u2 + "\" no encontrado. Volviendo al menú.");
            return;
        }

        // Intercambiar usando ListIterator (no Collections.swap)
        ListIterator<String> it1 = usuarios.listIterator(idx1);
        String valor1 = null;
        if (it1.hasNext()) valor1 = it1.next(); // valor en idx1
        it1.set(usuarios.get(idx2)); // poner valor del otro en idx1

        ListIterator<String> it2 = usuarios.listIterator(idx2);
        String valor2 = null;
        if (it2.hasNext()) valor2 = it2.next(); // valor en idx2 (original)
        it2.set(valor1); // poner el antiguo valor1 en idx2

        System.out.println("Intercambiados \"" + u1 + "\" y \"" + u2 + "\" correctamente.");
    }
}
