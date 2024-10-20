import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.Collections;

public class AlgoritmosPlanificacion {
    private List<Proceso> procesos = new ArrayList<Proceso>();
    private JTextField tiempoCPUTxt;
    private JTextField tiempoLlegadaTxt;
    private JTextField prioridadTxt;
    private JButton añadirProcesoButton;
    private JButton FIFOButton;
    private JButton SJFButton;
    private JButton prioridadButton;
    private JTextField nombreTxt;
    private JTextArea Salida;
    private JButton roundRobinButton;
    private JPanel pGeneral;
    int procesosTotales = 0;

    public AlgoritmosPlanificacion() {
        añadirProcesoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int tiempoCPU = Integer.parseInt(tiempoCPUTxt.getText());
                int tiempoLlegada = Integer.parseInt(tiempoLlegadaTxt.getText());
                int prioridad = Integer.parseInt(prioridadTxt.getText());
                String nombre = nombreTxt.getText();
                Proceso proceso = new Proceso(tiempoCPU, tiempoLlegada, prioridad, nombre);
                procesos.add(proceso);
                Salida.append("\n Proceso Agregado");
                tiempoLlegadaTxt.setText("");
                tiempoCPUTxt.setText("");
                prioridadTxt.setText("");
                nombreTxt.setText("");
                procesosTotales++;
            }
        });
        FIFOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Salida.setText("");
                Collections.sort(procesos);
                for (Proceso proceso:procesos){
                    Salida.append(proceso.toString());
                }
            }
        });
        SJFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Salida.setText("");
                int tiempoActual = 0;

                while (true) {
                    List<Proceso> procesosListos = new ArrayList<>();
                    for (Proceso proceso : procesos) {
                        if (proceso.tiempoLlegada <= tiempoActual && !proceso.completado) {
                            procesosListos.add(proceso);
                        }
                    }
                    if (procesosListos.isEmpty()) {
                        tiempoActual++;
                        continue;
                    }
                    Proceso procesoActual = procesosListos.get(0);
                    for (Proceso proceso : procesosListos) {
                        if (proceso.rafagaCPU < procesoActual.rafagaCPU) {
                            procesoActual = proceso;
                        }
                    }
                    Salida.append(procesoActual.toString());
                    tiempoActual += procesoActual.rafagaCPU;
                    procesoActual.completado = true;
                    boolean todosCompletados = true;
                    for (Proceso proceso : procesos) {
                        if (!proceso.completado) {
                            todosCompletados = false;
                            break;
                        }
                    }
                    if (todosCompletados) {
                        break;
                    }
                }
                for (Proceso proceso:procesos){
                    proceso.completado = false;
                }
            }
        });
        prioridadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Salida.setText("");
                int tiempoActual = 0;
                Proceso procesoActual = procesos.get(0);
                Collections.sort(procesos);
                boolean completado = false;
                while (!completado){
                    for (Proceso proceso:procesos){
                        if (proceso.prioridad<procesoActual.prioridad&&
                                !proceso.completado&&proceso.tiempoLlegada<=tiempoActual||
                                !proceso.completado&&procesoActual.completado){
                            procesoActual = proceso;
                        }
                        else{
                            tiempoActual++;
                        }
                    }
                    tiempoActual = tiempoActual + procesoActual.rafagaCPU;
                    procesoActual.completado = true;
                    Salida.append(procesoActual.toString());
                    for (Proceso procesoCompletado:procesos){
                        if (procesoCompletado.completado){
                            completado=true;
                        }
                        else{
                            completado=false;
                        }
                    }
                }

            }
        });
        roundRobinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Salida.setText("");

                int quantum = 3;
                int tiempoActual = 0;
                Queue<Proceso> cola = new LinkedList<>(procesos);

                while (!cola.isEmpty()) {
                    Proceso procesoActual = cola.poll();
                    if (procesoActual.tiempoLlegada <= tiempoActual) {
                        int tiempoEjecutado = Math.min(quantum, procesoActual.rafagaCPU);
                        if (procesoActual.rafagaCPU > quantum) {
                            tiempoActual += quantum;
                            procesoActual.rafagaCPU -= quantum;
                            cola.add(procesoActual);
                        } else {
                            tiempoActual += procesoActual.rafagaCPU;
                            procesoActual.completado = true;
                            Salida.append(procesoActual.toString());
                        }
                    } else {
                        cola.add(procesoActual);
                        tiempoActual++;
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("AlgoritmosPlanificacion");
        frame.setContentPane(new AlgoritmosPlanificacion().pGeneral);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}