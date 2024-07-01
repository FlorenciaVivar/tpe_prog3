package tpe;

import java.util.LinkedList;

public class ResultadoGreedy {

    private LinkedList<Procesador> resultado;
    private int cont_estado;
    private int mejorTiempoTotal;

    public ResultadoGreedy(LinkedList<Procesador> resultado , int cont_estado , int mejorTiempoTotal ){
        this.resultado = resultado;
        this.cont_estado = cont_estado;
        this.mejorTiempoTotal = mejorTiempoTotal;

    }

    public int getCont_estado() {
        return cont_estado;
    }

    public LinkedList<Procesador> getResultado() {
        return new LinkedList<Procesador>(resultado);
    }

    public int getMejorTiempoTotal() {
        return mejorTiempoTotal;
    }
}
