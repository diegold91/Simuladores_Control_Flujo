//SIMULADOR NGHTTP2

import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;

public class RemoveThread extends Thread {

	private Queue<Byte> queue;
    public int ventanaControl;
    public int tamanoPaquete;
    public int TAMANO_VENTANA;
    public long tiempoInicio;
    

    public RemoveThread(Queue<Byte> q, int ventana) 
    {
        queue = q;
        ventanaControl = ventana;
        TAMANO_VENTANA = ventana;
        tiempoInicio = System.currentTimeMillis();
    }

    public void run() 
    {
    	int index=0;
    	int contadorDePaquetes=1;
    	int vent = ventanaControl;
        while(true)
        {
        	try 
        	{
        		if(queue.isEmpty())
        		{
        			System.out.println("buffer vacío");
        			sleep(400); //TIME OUT
        		}
        		while(!queue.isEmpty())
        		{
        			try
        			{
        			queue.remove(); //Lectura de bytes en el buffer. (Los saco de la cola)
        			index++;
        			}
        			catch( NoSuchElementException e ) 
                	{
                		System.out.println("Cola vacía");
                		sleep(400); 
                	}
        			if(index>=130)
        			{
        				//System.out.print("tiempo lectura de 130B: ");
        				long t=System.currentTimeMillis()-tiempoInicio;
        				System.out.println("tiempo, "+t);
        				//System.out.println("Tamaño buffer: "+ queue.size());
        				index=0;
        				sleep(104); //simula un tiempo de lectura por cada 100 B. (Lo hice así por facilidad). //82x100B, 10,2, 104x130B
        			}
        			
        		}
        	} 
        	
        	catch( InterruptedException e ) 
        	{
        		System.out.println("Error al desencolar");
        	}
        }
    }

    
    //Esto no se utiliza por ahora
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
    // Esto no se actualiza por ahora
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
