
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

        System.out.println("\nTareas Criticas: ");
        System.out.println(servicios.servicio2(true));

        System.out.println("\nTareas NO Criticas:  ");
        System.out.println(servicios.servicio2(false));

        System.out.println("\nTareas segun prioridad: ");
        System.out.println(servicios.servicio3(50, 99));

        System.out.println("\n---------------SEGUNDA PARTE--------------");
        System.out.println("-------Back------");
        back.asignacionTareas(100, servicios);
        /* 
        System.out.println("-------Greedy------");
        Greedy greedy = new Greedy();
        greedy.greedy(200,servicios);
        System.out.println(greedy);
        */
    }

}
