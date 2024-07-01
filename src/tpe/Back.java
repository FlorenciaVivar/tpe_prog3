package tpe;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Back {

    private HashMap<String, Procesador> mejorAsignacion;
    private HashMap<String, Procesador> asignacionActual;
    private int mejorTiempoTotal;
    private int cont_estado;

    private LinkedList<Tarea> listaTareasCONTADOR;

    public Back() {
        this.mejorTiempoTotal = Integer.MAX_VALUE;
        this.mejorAsignacion = new HashMap<>();
        this.asignacionActual = new HashMap<>();
        this.cont_estado = 0;
    }

    public ResultadoBack asignacionTareas(int tiempo, Servicios servicio) {
        LinkedList<Tarea> list = servicio.getListServicio();
        this.listaTareasCONTADOR = new LinkedList<>(list);
        backAsignacionTareas(tiempo, list, servicio);
        if(this.mejorAsignacion.isEmpty()){
            return null;
        }
        else{
            ResultadoBack res = new ResultadoBack( this.mejorAsignacion , this.cont_estado , this.mejorTiempoTotal );
            return res;
        }
    }

    /*La estrategia utilizada por el grupo en nuestro backtracking fue asignar cada tarea a todos
   los procesadores posibles y por cada procesador preguntar si cumple las conidiciones requeridas
   para la entrega y en el momento de que la linkedList de tareas este vacia preguntamos si es solucion*/
    private void backAsignacionTareas(int tiempo, LinkedList<Tarea> listTareas, Servicios servicio) {
        this.incrementarEstado();
        if (listTareas.isEmpty()) {
            if (this.esLaMejorSolucion()) {
                this.actualizarSolucion(this.getAsignacionTareas());
            }
        } else {
            Tarea tarea = listTareas.removeFirst();
            Iterator<Procesador> itProcesador = servicio.obtProcesadores();
            boolean tareaAsignada = false;
            while (itProcesador.hasNext()) {
                Procesador proc = itProcesador.next();
                if (proc.cumpleCondicion(tarea, tiempo)) {
                    this.addTarea(tarea, proc);
                        backAsignacionTareas(tiempo, listTareas, servicio);
                    this.removeTarea(tarea, proc);
                }
            }
            listTareas.addFirst(tarea);
        }
    }


    public void incrementarEstado() {
        this.cont_estado++;
    }

    public void addTarea(Tarea tarea, Procesador proc) {
        String id = proc.getId_procesador();
        if (asignacionActual.containsKey(id)) {
            this.asignacionActual.get(id).addTarea(tarea);
        } else {
            proc.addTarea(tarea.copiaTarea());
            this.asignacionActual.put(id, proc);
        }
    }

    public void removeTarea(Tarea tarea, Procesador proc) {
        String id = proc.getId_procesador();
        this.asignacionActual.get(id).removeTarea(tarea);
    }

    public HashMap<String, Procesador> getMejorAsignacion() {
        return new HashMap<>(this.mejorAsignacion);
    }

    public void actualizarSolucion(HashMap<String, Procesador> asignacion_tareas) {
        this.mejorAsignacion.clear();
        for (Procesador procesador : asignacion_tareas.values()) {
            mejorAsignacion.put(procesador.getId_procesador(), procesador.copiaProc());
        }
        this.mejorTiempoTotal = calcularTiempoMaximo(mejorAsignacion);
    }

    protected int calcularTiempoMaximo(HashMap<String, Procesador> asignacion) {
        int tiempoTotal = 0;
        for (Procesador procesador : asignacion.values()) {
            if (procesador.getTiempoTotal() > tiempoTotal)
                tiempoTotal = procesador.getTiempoTotal();
        }
        return tiempoTotal;
    }

    public int calcularTiempo() {
        int tiempoTotal = 0;

        for (Procesador procesador : this.getMejorAsignacion().values()) {
            if (procesador.getTiempoTotal() > tiempoTotal)
                tiempoTotal = procesador.getTiempoTotal();
        }
        return tiempoTotal;
    }

    public HashMap<String, Procesador> getAsignacionTareas() {
        return new HashMap<>(this.asignacionActual);
    }

    public int getMejorTiempoTotal() {
        return mejorTiempoTotal;
    }

    public boolean esLaMejorSolucion() {
        int tiempoActual = calcularTiempoMaximo(this.asignacionActual);
        return tiempoActual <= mejorTiempoTotal && this.cantidadTareasAsignadas() == this.listaTareasCONTADOR.size() ;
    }

    private int cantidadTareasAsignadas(){
    int resultado =0;
        for ( Procesador p : this.asignacionActual.values()){
            resultado+= p.getLinkedListCopia().size();
        }
        return resultado;
    }

}