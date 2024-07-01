
import tpe.*;

public class Main {

    public static void main(String[] args) {
        Servicios servicios = new Servicios("./src/tpe/datasets/Procesadores.csv", "./src/tpe/datasets/Tareas.csv");

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
       
        Back back = new Back();
        ResultadoBack resultadoBack = back.asignacionTareas(200, servicios);
        if (resultadoBack != null) {
            String result = "-------------------ASIGNACION BACKTRACKING--------------------- \n";
            for (String id : resultadoBack.getResultado().keySet()) {
                result += "    " + resultadoBack.getResultado().get(id) + "\n";
            }
            result += " \n Contador de estado: " + resultadoBack.getCont_estado() + " tiempo total = "
                    + resultadoBack.getMejorTiempoTotal()+ "\n";

            System.out.println(result);
        } else {
            System.out.println("no hay solucion en el Backtracking");
        }

        Greedy greedy = new Greedy();
        ResultadoGreedy resultadoGreedy = greedy.greedy(200, servicios);

        if (resultadoGreedy != null) {
            String resultGreedyString = "--------------ASIGNACION GREEDY------------- \n";
            for (Procesador p : resultadoGreedy.getResultado()) {
                resultGreedyString += "    " + p + "\n";
            }
            resultGreedyString += "\n Contador de estado Greedy: " + resultadoGreedy.getCont_estado() + " Tiempo total Greedy: "+ resultadoGreedy.getMejorTiempoTotal();

            System.out.println(resultGreedyString);
        } else {
            System.out.println(" no hay solucion en el Greedy");
        }

    }

}
