package control;

import java.util.Queue;
import java.util.Random;


public class AddThread extends Thread {

	private Queue<Byte> queue;
    byte num;
    public int bytesATransmitir; //Total de bytes que se van a transmitir
    public int ventanaControl; //tamaño de la ventana en bytes
    private int tamanoPaquete; //tamaño paquete Default: 1500
    public long tiempoInicio;

    // Constructor
    public AddThread(Queue<Byte> q, int bytes, int ventana) {
        queue = q;
        num = 1;
        bytesATransmitir = bytes;
        ventanaControl = ventana;
        tamanoPaquete = 512; //Valor por defecto 1500
        tiempoInicio = System.currentTimeMillis();
        }


    public void run() {

    	int contadorPaquetes=1;
		int tamanoPaq=tamanoPaquete;
    	int vent = ventanaControl;
    	int contWU=1;
    	
    	
    	while(bytesATransmitir>0)
    	{
    	
    		//Transmisión de paquetes
        try 
    	{	
        	
        	if(bytesATransmitir>tamanoPaquete && vent>tamanoPaquete)
        	{
        		for(int i=0;i<tamanoPaquete;i++)
        		{
        			queue.add(num);
        			bytesATransmitir--;
        			vent--;
        		}
        		System.out.println("Paquete, "+ contadorPaquetes + ", tamaño, "+ tamanoPaq);
        		contadorPaquetes++;
        		sleep(8); //TIEMPO por pack (Ttrans), 8, 41, 401*
        		
        		
        	}
        	else
        	{
        		int temp=Math.min(bytesATransmitir,vent);
        		for(int i=0;i<temp;i++)
        		{
        			queue.add(num);
        			bytesATransmitir--;
        			vent--;
        		}
        		System.out.println("Paquete, "+ contadorPaquetes + ", tamaño, "+ temp);
        		contadorPaquetes++;
        		sleep(8); //TIEMPO por pack Ttrans
        	}
        	
        	
        	if(vent==0)
        	{
        		sleep(100); //Ttrans-rec (Ttrans-rec - Ttrans*#pack)
            	
        		vent=ventanaControl-queue.size();
        		System.out.println("Ventana, "+vent +", Numero, " +contWU);
        		sleep(200); //TIEMPO entre ráfagas (Trec-trans) ****
        		
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
