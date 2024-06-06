

import tpe.Back;
import tpe.Servicios;

public class Main {

	public static void main(String[] args) {
     Servicios servicios = new Servicios("./src/tpe/datasets/Procesadores.csv", "./src/tpe/datasets/Tareas.csv");

    Back back = new Back();

             System.out.println("\n----------------------TODAS LAS TAREAS-----------------\n");
             servicios.imprimirTodasLasTareas();
             System.out.println("\n----------------------TODAS LOS PROCESADORES-----------------\n");
             servicios.imprimirTodosLosProcesadores();
            
            System.out.println("\n---------------PRIMERA PARTE--------------");
           System.out.println("\nTarea segun id: ");
            System.out.println(servicios.servicio1("T1"));
        
            System.out.println("\n Tareas Criticas: " );
            System.out.println(servicios.servicio2(true));

            System.out.println("\n Tareas NO Criticas:  " );
            System.out.println(servicios.servicio2(false));
         
            System.out.println("\nTareas segun prioridad: " );
            System.out.println(servicios.servicio3(0, 99));
            
            System.out.println("\n---------------SEGUNDA PARTE--------------");

            
            back.asignacionTareas(200,servicios); 
        }
	
}
