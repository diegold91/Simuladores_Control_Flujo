/*Simulador de algoritmo de control de flujo propuesto*/

package control;

import java.util.LinkedList;
import java.util.Queue;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Queue<Byte> cola = crearCola();
		imprimirCola(cola);
		int numeroBytes=500000;
		int tamanoVentana=4096; // 2048, 4096, 8192, 16384, 32768, 65536
		RemoveThread desencolar = new RemoveThread(cola, tamanoVentana);
		AddThread agregar = new AddThread(cola,numeroBytes,tamanoVentana);
		agregar.start();
		desencolar.start();
    }
	
	public static Queue<Byte> crearCola() {
        /*Creamos la Cola Indicando el tipo de dato*/
        Queue<Byte> cola=new LinkedList();
        return cola;
	}
	
	public static void agregarDato(Queue<Byte> cola, byte obj) {
		cola.add(obj);
	}
	
	public static void imprimirCola(Queue<Byte> cola) {
		/*Impresion de la Cola llena con los datos*/
        System.out.println("Cola con: " + cola);
	}
	
	public static void desencolarTodo(Queue<Byte> cola) {
		/*Estructura repetitiva para desencolar*/
        while(cola.poll()!=null){//Desencolamos y el valor se compara con null
            System.out.println(cola.peek());//Muestra el nuevo Frente
        }
        /*Muestra null debido a que la cola ya esta vacia*/
        System.out.println(cola.peek());   
	}
}