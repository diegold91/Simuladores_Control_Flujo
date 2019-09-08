package control;

import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;

public class RemoveThread extends Thread {

	private Queue<Byte> queue;
    //private int num;
    public int ventanaControl;
    public int tamanoPaquete;
    public int TAMANO_VENTANA;
    public long tiempoInicio;
    

    // Constructor para almacenar nuestro nombre
    // y el retardo
    public RemoveThread(Queue<Byte> q, int ventana) {
        queue = q;
        //num = 0;
        ventanaControl = ventana;
        TAMANO_VENTANA = ventana;
        //tamanoPaquete = 1500;
        tiempoInicio = System.currentTimeMillis();
    }

    // El metodo run() es similar al main(), pero para
    // threads. Cuando run() termina el thread muere
    int index=0;
    public void run() {
        // Retasamos la ejecuciÃ³n el tiempo especificado
    	int contadorDePaquetes=1;
    	int vent = ventanaControl;
        while(true)
        {
        	try 
        	{
        		if(queue.isEmpty())
        		{
        			System.out.println("buffer vacío");
        			sleep(100); //TIME OUT 
        		}
        		while(!queue.isEmpty())
        		{
        		
        			try
        			{
        			queue.remove();
        			index++;
        			}
        			catch( NoSuchElementException e ) 
                	{
                		//System.out.println("Cola vacía");
                		sleep(100);
                	}

        			if(index>=130)
        			{
        				//System.out.print("tiempo lectura de 130B: "); ****
        				long t=System.currentTimeMillis()-tiempoInicio;
        				System.out.println("tiempo, "+t);
        				index=0;
        				sleep(10); // 80x100B , 10*130B, 2*130B (104*130)
        			}
        			
        		}

        	} 
        	
        	catch( InterruptedException e ) 
        	{
        		System.out.println("Error al desencolar");
        	}
        }
    }
    
    public void actualizarVentana()
    {
    	if(queue.size()==0)
    	{
    		ventanaControl = TAMANO_VENTANA;
    	}
    	else
    	{
    		ventanaControl = TAMANO_VENTANA-queue.size();
    	}
    }
    
    // Con algoritmo NGHTTP2
    
    public void actualizarVentanang()
    
    {
    	if(queue.size()==0)
    	{
    		ventanaControl = TAMANO_VENTANA;
    	}
    	else
    	{
    		ventanaControl = TAMANO_VENTANA-queue.size();
    	}
    }
}
