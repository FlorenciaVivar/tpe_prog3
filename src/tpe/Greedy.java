package tpe;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Greedy {

    private int tiempo = 0;

    private HashMap<String, Procesador> mejorAsignacion;
    private HashMap<String, Procesador> asignacionActual;
    private int mejorTiempoTotal;
    private int cont_estado;
    private LinkedList <Procesador>solucion;

    public Greedy() { // TODO: Revisar que es lo que usamos y que no.. sacar lo que no .
        this.mejorTiempoTotal = Integer.MAX_VALUE;
        this.mejorAsignacion = new HashMap<>();
        this.asignacionActual = new HashMap<>();
        this.cont_estado = 0;
        this.solucion = new LinkedList<>() ;
    }

    public LinkedList<Procesador> greedy(int xParaNorRefrigerados, Servicios servicio) {
        LinkedList<Tarea> candidatos = new LinkedList<>(servicio.getListServicio());
        this.solucion = new LinkedList<>();

        ordenarList(candidatos);

        // ya los tengo de menor a mayor
        // pregunto: la tarea es critica?
        // busco los procesadores que esten habilitados para recibir una tarea critica
        // si no es critica, se la asigno a cualquiera

        // ahora, dentro de los posible candidatos de procesadores, busco el que tenga
        // menor tiempo
        // a ese, le asigno

        // pregunto: cual es el procesador con menos tiempo

        while (!candidatos.isEmpty()) {
            Tarea tarea = candidatos.getFirst(); // selecciono la primera de la lista (menor tiempo)
            candidatos.remove(tarea);

            if (tarea.isCritica()) { // si es critica, tengo que tener el cuenta solo a los que tienen menos de 2
                                     // criticas
                LinkedList<Procesador> procesadoresPosibles = obtenerProcesadoresConMenosDe2Criticas(servicio);
                Integer menorTiempoHastaAhora = null;
                Procesador mejorHastaAhora = null;
                for (Procesador actual : procesadoresPosibles) {
                    if (mejorHastaAhora == null && actual.cumpleCondicion(tarea, xParaNorRefrigerados)) {
                        mejorHastaAhora = actual;
                        menorTiempoHastaAhora = actual.getTiempoTotal();
                    } else if (actual.getTiempoTotal() < menorTiempoHastaAhora
                            && actual.cumpleCondicion(tarea, xParaNorRefrigerados)) {
                        menorTiempoHastaAhora = actual.getTiempoTotal();
                        mejorHastaAhora = actual;
                    } else {
                        System.out.println("hay por lo menos 1 tarea que no puede ser asignada");
                        System.out.println(this);
                        break;
                    }
                }
                solucion.add(mejorHastaAhora);
            } else { // si no es critica, no agarro todos los procesadores
                Iterator<Procesador> procesadoresPosibles = servicio.obtProcesadores();
                Integer menorTiempoHastaAhora = null;
                Procesador mejorHastaAhora = null;
                while (procesadoresPosibles.hasNext()) {
                    Procesador actual = procesadoresPosibles.next();
                    if (mejorHastaAhora == null && actual.cumpleCondicion(tarea, xParaNorRefrigerados)) {
                        mejorHastaAhora = actual;
                        menorTiempoHastaAhora = actual.getTiempoTotal();
                    } else if (actual.getTiempoTotal() < mejorHastaAhora.getTiempoTotal()
                            && actual.cumpleCondicion(tarea, xParaNorRefrigerados)) {
                        menorTiempoHastaAhora = actual.getTiempoTotal();
                        mejorHastaAhora = actual;
                    } else {
                        System.out.println("hay por lo menos 1 tarea que no puede ser asignada");
                        System.out.println(this);
                        break;
                    }
                }
                mejorHastaAhora.addTarea(tarea);
                solucion.add(mejorHastaAhora);
            }
        }

        if (candidatos.isEmpty()) {
            System.out.println(this);
            return solucion;
        }
        else{
            System.out.println(this);
            return null;
        }

    }

    // esSolucion(solucion)) {
    // asignarTareas(solucion);
    // return back.getMejorAsignacion();
    //} else {
    // System.out.println("No hay solución completa.");
    // return new HashMap<>();
    // }
    // }

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

    // public boolean esSolucion(LinkedList<Tarea> solucion) {

    // return solucion.isEmpty();
    // }

    // private Tarea seleccionar(LinkedList<Tarea>candidatos){
    // return candidatos.getFirst();
    // }

    // private boolean factible(LinkedList<Tarea> solucion, Tarea tarea) {
    // Iterator<Procesador> itProcesador =
    // back.getAsignacionTareas().values().iterator();
    // while (itProcesador.hasNext()) {
    // Procesador proc = itProcesador.next();
    // if (proc.cumpleCondicion(tarea, tiempo)) {
    // return true;
    // }
    // }
    // return false;
    // }

    // private void asignarTareas(LinkedList<Tarea> solucion) {
    // for (Tarea tarea : solucion) {
    // Iterator<Procesador> itProcesador =
    // back.getAsignacionTareas().values().iterator();
    // while (itProcesador.hasNext()) {
    // Procesador proc = itProcesador.next();
    // if (proc.cumpleCondicion(tarea, tiempo)) {
    // back.addTarea(tarea, proc);
    // break;
    // }
    // }
    // }
    // }

    // public void asignacionTareasGreedy(int tiempo, Servicios servicio) {
    // LinkedList<Tarea> list = servicio.getListServicio();
    // ordenarList(list);//ordenamos las tareas de mayor a menor tiempo

    // while (!list.isEmpty()) {
    // Tarea tarea = list.removeFirst();
    // boolean asignada = false;
    // // int mejorTiempo = Integer.MAX_VALUE;

    // Iterator<Procesador> itProcesador = servicio.obtProcesadores();
    // while (itProcesador.hasNext()) {
    // Procesador proc = itProcesador.next();

    // if (proc.cumpleCondicion(tarea,tiempo)) {
    // back.addTarea(tarea, proc);
    // asignada = true;
    // break;
    // }

    // }
    // if (!asignada) {
    // // System.out.println("No se pudo asignar la tarea: " + tarea);
    // list.addFirst(tarea);
    // break;
    // } else {
    // // System.out.println("Tarea asignada correctamente: " + tarea);

    // }
    // if(back.esLaMejorSolucion()){
    // back.actualizarSolucion(getMejorAsignacion());
    // }
    // // System.out.println(this.toString());
    // }
    // while(itProcesador.hasNext()){
    // esFactible(itProcesador, tarea)){
    // itProcesador.asignarTarea(tarea);
    // }
    // if(hay solcucion){
    // return solucion
    // }
    // } else {
    // System.out.println("No hay solucion");
    // }

    // list.addFirst(tarea);
    // System.out.println(this.toString());

    // }
    // }

    // private boolean esFactible(Iterator<Procesador> itProcesador, Tarea tarea) {
    // while (itProcesador.hasNext()) {
    // Procesador proc = itProcesador.next();
    // if (proc.puedeAsignar(tarea, tiempo)) {
    // return true;
    // }
    // }
    // return false;
    // }

    public void ordenarList(LinkedList<Tarea> list) {
        Collections.sort(list, new Comparator<Tarea>() {
            @Override
            public int compare(Tarea t1, Tarea t2) {
                return Integer.compare(t1.getTiempo(), t2.getTiempo());
            }
        });
    }

    @Override
    public String toString() {
        String result = "Mejor asignación:\n";
        for (Procesador p : this.solucion) {
            result += "    " + p.getCodigo_procesador() + "\n";
        }
        return result;
    }
}
