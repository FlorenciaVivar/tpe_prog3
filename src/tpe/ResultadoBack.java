package tpe;

import java.util.HashMap;

public class ResultadoBack {

    private int cont_estado;
    private HashMap<String, Procesador> resultado;
    private int mejorTiempoTotal;

    public ResultadoBack ( HashMap<String, Procesador> resultado , int cont_estado , int mejorTiempoTotal ){
        this.resultado = resultado;
        this.mejorTiempoTotal = mejorTiempoTotal;
        this.cont_estado = cont_estado;
    }

    public int getCont_estado() {
        return cont_estado;
    }

    public HashMap<String, Procesador> getResultado() {
        return new HashMap<String, Procesador>(resultado);
    }

    public int getMejorTiempoTotal() {
        return mejorTiempoTotal;
    }
}
