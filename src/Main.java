import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final String PARTICIPANTES_FILE = "../data/participantes.txt";
    private static final String GANADOR_FILE = "../output/ganador.txt";

    // Cargar la lista de participantes desde el archivo
    public static List<String> cargarParticipantes() {
        List<String> participantes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PARTICIPANTES_FILE))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                participantes.add(linea);
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo de participantes.");
        }
        return participantes;
    }

    // Guardar los participantes en el archivo
    public static void guardarParticipantes(List<String> participantes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PARTICIPANTES_FILE))) {
            for (String participante : participantes) {
                bw.write(participante);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("No se pudo guardar la lista de participantes.");
        }
    }

    // Mostrar el menú de opciones
    public static void mostrarMenu() {
        System.out.println("\nTÓMBOLA DE OPORTUNIDADES");
        System.out.println("1. Ver lista de participantes");
        System.out.println("2. Agregar nuevo participante");
        System.out.println("3. Elegir al ganador");
        System.out.println("4. Salir");
    }

    // Elegir un ganador aleatoriamente
    public static boolean elegirGanador(List<String> participantes) {
        if (participantes.isEmpty()) {
            System.out.println("No hay participantes para elegir.");
            return false;
        }

        Random random = new Random();
        int ganadorIndex = random.nextInt(participantes.size());
        String ganador = participantes.get(ganadorIndex);
        System.out.println("\n¡Felicidades " + ganador + ", has ganado la bicicleta!");

        // Guardar el nombre del ganador en un archivo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(GANADOR_FILE))) {
            bw.write("Ganador: " + ganador + "\n¡Felicidades!");
        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo del ganador.");
        }
        return true;
    }

    // Función principal del programa
    public static void main(String[] args) {
        List<String> participantes = cargarParticipantes();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            mostrarMenu();
            System.out.print("\nSelecciona una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    if (participantes.isEmpty()) {
                        System.out.println("\nNo hay participantes registrados.");
                    } else {
                        System.out.println("\nLista de participantes:");
                        for (String participante : participantes) {
                            System.out.println("- " + participante);
                        }
                    }
                    break;

                case "2":
                    System.out.print("Ingresa el nombre del nuevo participante: ");
                    String nuevoParticipante = scanner.nextLine();
                    if (!nuevoParticipante.isEmpty()) {
                        participantes.add(nuevoParticipante);
                        System.out.println(nuevoParticipante + " ha sido agregado a la lista.");
                    } else {
                        System.out.println("El nombre no puede estar vacío.");
                    }
                    break;

                case "3":
                    if (elegirGanador(participantes)) {
                        guardarParticipantes(participantes);
                        return; // Termina el programa después de elegir al ganador
                    }
                    break;

                case "4":
                    guardarParticipantes(participantes);
                    System.out.println("El programa ha terminado.");
                    return;

                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
                    break;
            }
        }
    }
}
