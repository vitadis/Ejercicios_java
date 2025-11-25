package listasIterador;

import java.util.*;


public class MasCasos1 {

	/**Array list.- es una lista que su tamaño puede cambiar.
	 * ListIterrator.- es un Iterador bidireccional <-->
	 * */
	public static void main(String[] args) {
		
		// Casos de como crear una lista
        ArrayList<Integer> listaActivada = new ArrayList<>(List.of(1,7,2,1,1,1)); // llamo a la clase list, donde dentro agrego los elementos de mi lista
        // metodos array size (tamañolista), 
        ListIterator<Integer> eta = listaActivada.listIterator();
        
        // ->
        while (eta.hasNext()) {
            int n = eta.next();
            // aqui puedo meter cualquier codigo para iterar con mi lista
            // se puede usar los metodos con iterrador.- remove, set
            if (n==1) {
                eta.add(2); // lo mismo que el de abajo 
            }
            if (n==2)
            	eta.set(222); // solo se actualiza en la lista actual, no en las nuevas modificaciones
            
            if (n==7)
            	eta.remove();
        }
        // <-
        while (eta.hasPrevious()) {
        	System.out.println(eta.previous());
        }
        System.out.println(listaActivada);
		// no hay manera de reiniciar el iterador, tengo que crear uno nuevo
        
        
        // list iterator desde un indice
        // empezar desde la mitad
        int tamaño = listaActivada.size();
        ListIterator<Integer> itInicio = listaActivada.listIterator(tamaño/2);
        
        while (itInicio.hasNext()) {
         
            System.out.print(itInicio.next()+" ");
        } // recorre hasta el final
        System.out.println();
        // por ende este recorrera todo al reves 
        while (itInicio.hasPrevious()) {
        	System.out.print(itInicio.previous()+" ");
        }
        
        // (1. recorre de la mitad al final - ya que ya llego al final - recorre desde ahí hasta el 1er indice)
        
	}

}
