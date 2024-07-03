package tpe;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Greedy {

    private int cont_estado;
    private LinkedList<Procesador> solucion;
    private int mejorTiempoTotal;
    private int cantidadTareasAsignadas;

    public Greedy() {
        this.cont_estado = 0;
        this.solucion = new LinkedList<>();
        this.mejorTiempoTotal = 0;
        this.cantidadTareasAsignadas = 0;
    }

    /*
     * Se tiene un conjunto de procesadores y una lista de tareas. La estrategia
     * greedy asignara las tareas ordenadas de mayor a menor tiempo a cada
     * procesador chequeando antes de agregarlas que no tenga mas de 2 tareas
     * criticas y su procesador refrigerado no supere el tiempo X
     */

    public ResultadoGreedy greedy(int xParaNorRefrigerados, Servicios servicio) {
        LinkedList<Tarea> candidatos = new LinkedList<>(servicio.getListServicio());

        ordenarList(candidatos);

        while (!candidatos.isEmpty()) {
            Tarea tarea = seleccionar(candidatos); // selecciona la primera de la lista (con mayor tiempo)
            candidatos.remove(tarea);
            this.incrementarEstadoGreddy();
            LinkedList<Procesador> procesadoresPosibles = obtenerProcesadoresPosibles(tarea, servicio);

            if (procesadoresPosibles.isEmpty() && tarea.isCritica()) {
                System.out.println("No hay procesadores con menos de 2 críticas para la tarea: " + tarea);
                return null;
            }
            Procesador mejorHastaAhora = seleccionarMejorProcesador(procesadoresPosibles, tarea, xParaNorRefrigerados);
            if (mejorHastaAhora==null) {
                System.out.println("No hay procesadores para la tarea: " + tarea);
                return null;              
            }
            asignarTareaAProcesador(mejorHastaAhora, tarea);
        }
        return verificarResultado(candidatos, servicio);
    }

    private Tarea seleccionar(List<Tarea> candidatos) {
        return candidatos.get(0);
    }

    private LinkedList<Procesador> obtenerProcesadoresPosibles(Tarea tarea, Servicios servicio) {
        if (tarea.isCritica()) {
            return obtenerProcesadoresConMenosDe2Criticas(servicio);
        } else {
            LinkedList<Procesador> procesadoresPosibles = new LinkedList<>();
            Iterator<Procesador> iterador = servicio.obtProcesadores();
            while (iterador.hasNext()) {
                procesadoresPosibles.add(iterador.next());
            }
            return procesadoresPosibles;
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

    private Procesador seleccionarMejorProcesador(LinkedList<Procesador> procesadores, Tarea tarea,
            int xParaNorRefrigerados) {
        Procesador mejorHastaAhora = null;
        int menorTiempoHastaAhora = Integer.MAX_VALUE;

        for (Procesador actual : procesadores) {
            if (!actual.cumpleCondicion(tarea, xParaNorRefrigerados))
                break;

            if (actual.getTiempoTotal() < menorTiempoHastaAhora) {
                menorTiempoHastaAhora = actual.getTiempoTotal();
                mejorHastaAhora = actual;
            }
        }

        return mejorHastaAhora;
    }

    private void asignarTareaAProcesador(Procesador procesador, Tarea tarea) {
        if (procesador != null) {
            procesador.addTarea(tarea);
            mejorTiempoTotal += tarea.getTiempo();
            if (!solucion.contains(procesador)) {
                solucion.add(procesador);
            }
        } else {
            System.out.println("No se pudo asignar la tarea: " + tarea);
        }
    }

    private ResultadoGreedy verificarResultado(LinkedList<Tarea> candidatos, Servicios servicio) {
        if (candidatos.isEmpty() && cantidadTareasAsignadas() == servicio.getListServicio().size()) {
            return new ResultadoGreedy(this.solucion, this.cont_estado, this.mejorTiempoTotal);
        } else {
            return null;
        }
    }

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

    private int cantidadTareasAsignadas() {
        for (Procesador p : this.solucion) {
            cantidadTareasAsignadas += p.getLinkedListCopia().size();
        }
        return cantidadTareasAsignadas;
    }
}