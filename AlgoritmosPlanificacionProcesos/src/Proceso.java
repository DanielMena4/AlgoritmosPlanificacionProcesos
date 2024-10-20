import java.util.ArrayList;

public class Proceso implements Comparable<Proceso>{

    public int rafagaCPU;
    public int tiempoLlegada;
    public int prioridad;
    public String nombre;
    public boolean completado;

    public Proceso(int rafagaCPU, int tiempoLlegada, int prioridad, String nombre) {
        this.rafagaCPU = rafagaCPU;
        this.tiempoLlegada = tiempoLlegada;
        this.prioridad = prioridad;
        this.nombre = nombre;
        completado = false;
    }

    @Override
    public String toString() {
        return "\nProceso: "+nombre+" Tiempo llegada: "+tiempoLlegada+" Tiempo CPU: "+rafagaCPU+" Prioridad: "+ prioridad
                ;
    }
    @Override
    public int compareTo(Proceso proceso) {
        Proceso otro = proceso;
        return Integer.compare(this.tiempoLlegada, otro.tiempoLlegada);
    }

}
