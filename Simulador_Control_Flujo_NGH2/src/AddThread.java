// Simulador algoritmo de control de flujo de referencia (NGHTTP2)
// Simula el comportamiento del control de flujo en una transmisión

import java.util.Queue;
import java.util.Random;


public class AddThread extends Thread 
{

	private Queue<Byte> queue;
    byte num;
    public int bytesATransmitir; //Total de bytes que se van a transmitir
    public int ventanaControl; //tamaño de la ventana en bytes
    private int tamanoPaquete; //tamaño paquete Default: 1500 B.
    public long tiempoInicio;

    // Constructor para almacenar nuestro nombre
    // y el retardo
    public AddThread(Queue<Byte> q, int bytes, int ventana) {
        queue = q;
        num = 1;
        bytesATransmitir = bytes;
        ventanaControl = ventana;
        tamanoPaquete = 512; //Valor por defecto 1500
        tiempoInicio = System.currentTimeMillis();
        }

    public void run() 
    {
    	int contadorPaquetes=1;
    	int vent = ventanaControl;
    	int contWU=1; //Contador de paquetes WU
    	
    	
    	while(bytesATransmitir>0) //SI LLEGA A CERO SE SALE DEL WHILE
    	{
    	
    		//Transmisión de paquetes
        try 
    	{	
        	
        	if(bytesATransmitir>tamanoPaquete && vent>tamanoPaquete && vent>0) //Si tengo bytes a transmitir y si la ventana disponible es mayor al tamaño del paquete
        	{
        		for(int i=0;i<tamanoPaquete;i++)
        		{
        			queue.add(num); //Agrega un byte a la cola
        			bytesATransmitir--;
        			vent--; //Reduce la ventana disponible en 1 B.
        		}
        		System.out.println("paquete, "+ contadorPaquetes + ", tamaño, "+ tamanoPaquete);
        		contadorPaquetes++;
        		sleep(41); //Tiempo por pack (Ttrans) [Simula el tiempo de procesamiento en el transmisor] 8,41,409 s (según corresponda)
        		
        	}
        	else if(vent!=0 && vent>0)
        	{
        		int temp=Math.min(bytesATransmitir,vent); //El paquete va a ser del mínimo entre bytes a transmitir y el tamaño de la ventana disponible
        		for(int i=0;i<temp;i++)
        		{
        			queue.add(num);
        			bytesATransmitir--;
        			vent--;
        		}
        		System.out.println("paquete, "+ contadorPaquetes + ", tamaño, "+ temp);
        		contadorPaquetes++;
        		sleep(41); //TIEMPO por pack Ttrans (Tiempo de procesamiento en el transmisor (simulado)) 8,41,409
        	}
        	else
        	{
        		sleep(20);
        	}
        	
    		if(vent<= ventanaControl/2 && vent!=0) //Si la ventana disponible está por debajo de la mitad de su tamaño máximo
            	{
            		
    				sleep(10); //Pausa para simular una lectura
    				
    				
            		int ventTemp=ventanaControl-queue.size(); //Nuevo tamaño de la ventana (Cantidad de Bytes que el buffer tiene capacidad de recibir en ese momento)
            		if(ventTemp<0)
            		{
            			sleep(200); //Tiempo para volver a leer si la ventana está vacía.
            			ventTemp=ventanaControl-queue.size();
            		}
            		long tiempoTemp=System.currentTimeMillis();
            		long tiempoFinal=tiempoTemp+200; 
            		
            		while(System.currentTimeMillis()<=tiempoFinal && vent>0)
            		{
            			if(bytesATransmitir>tamanoPaquete && vent>tamanoPaquete && vent>0) //Si hay bytes a transmitir y si la ventana disponible es mayor al tamaño del paquete
                    	{
                    		for(int i=0;i<tamanoPaquete;i++)
                    		{
                    			queue.add(num); //Agrega un byte a la cola
                    			bytesATransmitir--;
                    			vent--; //Reduce la ventana disponible en 1.
                    		}
                    		System.out.println("paquete, "+ contadorPaquetes + ", tamaño, "+ tamanoPaquete);
                    		contadorPaquetes++;
                    		sleep(41); //Tiempo por pack (Ttrans) [Simula el tiempo de procesamiento en el transmisor] 8,41,409 (401)
                    		
                    	}
                    	else if(vent!=0 && vent>0)
                    	{
                    		int temp=Math.min(bytesATransmitir,vent); //El paquete va a ser del mínimo entre bytes a transmitir y el tamaño de la ventana disponible
                    		for(int i=0;i<temp;i++)
                    		{
                    			queue.add(num);
                    			bytesATransmitir--;
                    			vent--;
                    		}
                    		System.out.println("paquete, "+ contadorPaquetes + ", tamaño, "+ temp);
                    		contadorPaquetes++;
                    		sleep(41); //TIEMPO por pack Ttrans (Tiempo de procesamiento en el transmisor (simulado)) 8,41,409
                    	}
                    	else
                    	{
                    		sleep(20);
                    	}

            		}
            		
            		vent=ventTemp;
            		System.out.println("VENTANA, "+vent +", numero, " +contWU);
            		contWU++;
            		
        	
            	}
    		if(vent==0)
    		{
    			sleep(200);
    			vent=ventanaControl-queue.size(); //Nuevo tamaño de la ventana (Cantidad de Bytes que el buffer tiene capacidad de recibir en ese momento)
        		System.out.println("ventana, "+vent +", numero, " +contWU);
        		contWU++;
    		}
    	}
    	catch( InterruptedException e ) 
    	{
            System.out.println("Error al agregar");
        }
    	}

    }
    
    
}

