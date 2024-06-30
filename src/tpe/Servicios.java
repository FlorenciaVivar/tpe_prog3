package tpe;


import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import tpe.utils.CSVReader;

/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {

	private HashMap<String, Tarea> mapaTareas;
	private HashMap<String, Procesador> mapProcesadores;
	private LinkedList<Tarea> ListaCritica ;
	private LinkedList<Tarea> ListaNoCritica;
	private LinkedList<Tarea> listaTareas;
	private LinkedList<Procesador> listaProcesadores;

	/* Complejidad del constructor: O(n+m)  */
	public Servicios(String pathProcesadores, String pathTareas){
		CSVReader reader = new CSVReader();
		reader.readProcessors(pathProcesadores);
		reader.readTasks(pathTareas);

		this.mapaTareas = new HashMap<>();
		this.mapProcesadores = new HashMap<>();
		this.ListaCritica = new LinkedList<>();
		this.ListaNoCritica = new LinkedList<>();

		this.listaTareas = new LinkedList<>();
		this.listaTareas = reader.getTasks();
		for (Tarea tarea : listaTareas) {// lleno el mapa con las tareas
			this.addTarea(tarea.getId(), tarea.getNombre(), tarea.getTiempo(), tarea.isCritica(), tarea.getPrioridad());
		}	

		this.listaProcesadores =new LinkedList<>();
		this.listaProcesadores =reader.getProcesadores();
		for(Procesador procesador: listaProcesadores){ // lleno el mapa con los procesdpres
			this.addProcesador(procesador.getId_procesador(),procesador.getCodigo_procesador(),procesador.isEsta_refrigerado(),procesador.getAnio_procesamiento());
		}
	}
	
	
	public void addTarea(String ID,String nombre, Integer tiempo,boolean critica,Integer prioridad) {
		if(!contieneTarea(ID)) {
			Tarea t = new Tarea(ID,nombre, tiempo, critica, prioridad);
			mapaTareas.put(ID, t);
			addTareaCritica(t);
		}
	}
	
	private boolean contieneTarea(String id) {
		return this.mapaTareas.containsKey(id);
	}
	
	/*
	 * O(1) donde 1 es cada Tarea
	 */

	public Tarea servicio1(String ID) {
		return mapaTareas.get(ID);
	}
    
    /*
     * O(1) donde n se retorna la lista de criticas y no criticas
     */
	
	public LinkedList<Tarea> servicio2(boolean esCritica) {
		if(esCritica){
			return ListaCritica;
		}
		return ListaNoCritica;
	}

	private void addTareaCritica(Tarea t) {
		if(t.isCritica()) {
			ListaCritica.add(t); 
		   }
		   else{
			   ListaNoCritica.add(t);
		   }
	}	
	
    /*
     *El siguiente metodo es O(n), aunque entendemos que lo podriamos realizar con arbol binario de busqueda y en el peor de los casos su complejidad seria O(n).
     */
	public LinkedList<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
		LinkedList<Tarea> Resultado= new LinkedList<>();
		for(Tarea t: mapaTareas.values()) {
			if(t.getPrioridad()<=prioridadSuperior && t.getPrioridad()>= prioridadInferior) {
				Resultado.add(t);
			}
		}
		return Resultado;
	}

	public void addProcesador(String id_procesador, String codigo_procesador, boolean esta_refrigerado, int anio_procesamiento){
		Procesador procesador = new Procesador(id_procesador, codigo_procesador, esta_refrigerado, anio_procesamiento);
		this.mapProcesadores.put(id_procesador, procesador);
	}

	public Iterator<Procesador> obtProcesadores(){
		return this.mapProcesadores.values().iterator();
	}
	public LinkedList<Tarea> getListServicio(){
		return new LinkedList<>(this.listaTareas);
	}

	public void imprimirTodasLasTareas() {
		for (Tarea t : mapaTareas.values()) {
			System.out.println(t);
		}
	}

	public void imprimirTodosLosProcesadores() {
		for (Procesador p : mapProcesadores.values()) {
			System.out.println(p);
		}
	}

}