package tpe;

import java.util.Objects;

public class Tarea {
	private String id;
	private String nombre;
	private Integer tiempo;
	private boolean critica;
	private Integer prioridad;

	public Tarea(String id, String nombre, Integer tiempo, boolean critica, Integer prioridad) {

	this.id = id;
	this.nombre = nombre;
	this.tiempo = tiempo;
	this.critica = critica;
	this.prioridad = prioridad;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getTiempo() {
		return tiempo;
	}
	public void setTiempo(Integer tiempo) {
		this.tiempo = tiempo;
	}
	public boolean isCritica() {
		return critica;
	}
	public void setCritica(boolean critica) {
		this.critica = critica;
	}
	public Integer getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(Integer prioridad) {
		this.prioridad = prioridad;
	}
	public Tarea copiaTarea() {
		return new Tarea(this.id, this.nombre, this.tiempo, this.critica, this.prioridad);
	}


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        Tarea tarea = (Tarea) obj;
        return critica == tarea.critica &&
               Objects.equals(id, tarea.id) &&
               Objects.equals(nombre, tarea.nombre) &&
               Objects.equals(tiempo, tarea.tiempo) &&
               Objects.equals(prioridad, tarea.prioridad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, tiempo, critica, prioridad);
    }
	@Override
	public String toString() {
	return "Tarea: " + id + ", tiempo: " + tiempo + ", critica: " + critica ;
	}
}
