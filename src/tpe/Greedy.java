package tpe;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class Greedy {

    private int cont_estado;
    private LinkedList<Procesador> solucion;

    public Greedy() { 
        this.cont_estado = 0;
        this.solucion = new LinkedList<>();
    }

    /*
     * Greedy asignara las tareas ordenadas de menor a mayor tiempo a cada
     * procesador chequeando antes de agregarlas que no tenga mas de 2 tareas
     * criticas y su procesador refrigerado no supere el tiempo X
     */

    public LinkedList<Procesador> greedy(int xParaNorRefrigerados, Servicios servicio) {
        LinkedList<Tarea> candidatos = new LinkedList<>(servicio.getListServicio());

        ordenarList(candidatos);
        System.out.println("lista candidatos tareas ordenadas " + candidatos);

        while (!candidatos.isEmpty()) {
            Tarea tarea = candidatos.getFirst(); // selecciono la primera de la lista (con menor tiempo)
            candidatos.remove(tarea);
            Procesador mejorHastaAhora = null;
            Integer menorTiempoHastaAhora = Integer.MAX_VALUE;
            this.incrementarEstadoGreddy();

            if (tarea.isCritica()) { // si es critica, tengo que tener el cuenta solo a los que tienen menos de 2
                                     // criticas
                LinkedList<Procesador> procesadoresPosibles = obtenerProcesadoresConMenosDe2Criticas(servicio);
                if(procesadoresPosibles.isEmpty()){
                    System.out.println("no hay procesadores con menos de 2 criticas y se quiso agregar la tarea: " + tarea );
                }
                for (Procesador actual : procesadoresPosibles) {
                    if (mejorHastaAhora == null && actual.cumpleCondicion(tarea, xParaNorRefrigerados)) {
                        mejorHastaAhora = actual;
                        menorTiempoHastaAhora = actual.getTiempoTotal();
                    } else if (actual.getTiempoTotal() < menorTiempoHastaAhora
                            && actual.cumpleCondicion(tarea, xParaNorRefrigerados)) {
                        menorTiempoHastaAhora = actual.getTiempoTotal();
                        mejorHastaAhora = actual;
                    } 
                    else if(!actual.cumpleCondicion(tarea, xParaNorRefrigerados)){
                        System.out.println("hay por lo menos 1 tarea que no puede ser asignada  " + tarea);
                        break;
                    }
                }
                if(mejorHastaAhora!=null){
                    mejorHastaAhora.addTarea(tarea);
                    if (!solucion.contains(mejorHastaAhora)) {
                        solucion.add(mejorHastaAhora);
                    }
                }
            } else { // si no es critica, no agarro todos los procesadores
                Iterator<Procesador> procesadoresPosibles = servicio.obtProcesadores();
                while (procesadoresPosibles.hasNext()) {
                    Procesador actual = procesadoresPosibles.next();
                    if (mejorHastaAhora == null && actual.cumpleCondicion(tarea, xParaNorRefrigerados)) {
                        mejorHastaAhora = actual;
                        menorTiempoHastaAhora = actual.getTiempoTotal();
                    } else if (actual.getTiempoTotal() < menorTiempoHastaAhora
                            && actual.cumpleCondicion(tarea, xParaNorRefrigerados)) {
                        menorTiempoHastaAhora = actual.getTiempoTotal();
                        mejorHastaAhora = actual;
                    } 
                    else if(!actual.cumpleCondicion(tarea, xParaNorRefrigerados)){
                        System.out.println("hay por lo menos 1 tarea que no puede ser asignada  " + tarea);
                        break;
                    }
                }
                if (mejorHastaAhora != null) {
                    mejorHastaAhora.addTarea(tarea);
                    if (!solucion.contains(mejorHastaAhora)) {
                        solucion.add(mejorHastaAhora);
                    }
                } else {
                    System.out.println("no se pudo asignar la tarea : " + tarea);
                }
            }
        }
        if (candidatos.isEmpty()) {
           return solucion;
        } else {
            return null;
        }

    }

    // Todos los procesadores que tienen menos de 2 tareas criticas
    private LinkedList<Procesador> obtenerProcesadoresConMenosDe2Criticas(Servicios servicio) {

        Iterator<Procesador> todos = servicio.obtProcesadores();
        LinkedList<Procesador> tienenMenosDe2Criticas = new LinkedList<>();
        while (todos.hasNext()) {
            Procesador p = todos.next();
            if (p.cantidadDeTareasCriticas() < 2) {
                tienenMenosDe2Criticas.add(p);
            }
        }
        return tienenMenosDe2Criticas;
    }
/* 
    public void ordenarList(LinkedList<Tarea> list) {
        Collections.sort(list, new Comparator<Tarea>() {
            @Override
            public int compare(Tarea t1, Tarea t2) {
                return Integer.compare(t1.getTiempo(), t2.getTiempo());
            }
        });
    }
*/
    public void ordenarList(LinkedList<Tarea> list) {
        Collections.sort(list, new Comparator<Tarea>() {
            @Override
            public int compare(Tarea t1, Tarea t2) {
                return Integer.compare(t2.getTiempo(), t1.getTiempo());
            }
        });
    }
    public void incrementarEstadoGreddy() {
        this.cont_estado++;
    }
    @Override
    public String toString() {
        String result = "Asignacion greedy :\n";
        for (Procesador p : this.solucion) {
            result += "    " + p + "\n";	
        }
        result += "Contador de estado: " + cont_estado;
        return result;
    }
}
