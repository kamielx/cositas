/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package examentransversal;

/**
 *
 * @author Ninao
 */

import java.util.Scanner;
import java.util.ArrayList;

public class Examentransversal {

    private static ArrayList<String> asientosReservados = new ArrayList<>();
    private static ArrayList<String> asientosDisponibles = new ArrayList<>();
    private static ArrayList<String> ventas = new ArrayList<>();

    private static void completarasientos() {
        for (int i = 1; i < 34; i++) {
            asientosDisponibles.add("A" + i);
            asientosDisponibles.add("B" + i);
            asientosDisponibles.add("C" + i);
            asientosDisponibles.add("D" + i);
            asientosDisponibles.add("E" + i);
            asientosDisponibles.add("F" + i);
        }
    }

    private static void mostrardisponibilidad() {
        System.out.println("... Asientos Disponibles ...");

        if (asientosDisponibles.isEmpty()) {
            System.out.println("No hay asientos disponibles.");
        } else {
            for (int i = 1; i <= 33; i++) {
                String fila = "";
                for (char letra = 'A'; letra <= 'F'; letra++) {
                    String asiento = letra + String.valueOf(i);
                    if (asientosDisponibles.contains(asiento)) {
                        fila += (i < 10 ? "0" + asiento : asiento) + " - ";
                    } else {
                        fila += "XXX - ";
                    }
                }
                System.out.println(fila.substring(0, fila.length() - 3));
            }
        }
    }

    private static void reservarAsientos(Scanner sc) {
         System.out.println("..::: Reservar Asientos :::..");
    mostrardisponibilidad();

    System.out.print("¿Cuántos asientos desea reservar?: ");
    int cantidad;
    try {
        cantidad = Integer.parseInt(sc.nextLine());
    } catch (NumberFormatException e) {
        System.out.println("Número inválido. Operación cancelada.");
        return;
    }

    ArrayList<String> reservadosAhora = new ArrayList<>();

    for (int i = 1; i <= cantidad; i++) {
        System.out.print("Ingrese asiento #" + i + " (ej: A1, B5, etc.): ");
        String asiento = sc.nextLine().toUpperCase();

        if (asientosDisponibles.contains(asiento)) {
            asientosReservados.add(asiento);
            asientosDisponibles.remove(asiento);
            reservadosAhora.add(asiento);
        } else {
            System.out.println("Asiento " + asiento + " no disponible o inexistente. Intente otro.");
            i--; // Repite esta iteración
        }
    }

    System.out.println("Reserva completada. Asientos reservados: " + reservadosAhora);
}

    private static String obtenerUbicacionAsiento(String asiento) {
        char fila = asiento.charAt(0);
        switch (fila) {
            case 'A': return "VIP";
            case 'B': return "Palco";
            case 'C': return "Platea Baja";
            case 'D': return "Platea Alta";
            case 'E':
            case 'F': return "Galería";
            default: return "Desconocida";
        }
    }

    private static double calcularDescuento(int edad, boolean esEstudiante, String sexo) {
        double descuento = 0;
        if (edad <= 12) descuento = Math.max(descuento, 0.10);      // Niños
        if (sexo.equalsIgnoreCase("F")) descuento = Math.max(descuento, 0.20); // Mujeres
        if (edad >= 60) descuento = Math.max(descuento, 0.25);      // Tercera edad
        if (esEstudiante) descuento = Math.max(descuento, 0.15);    // Estudiante
        return descuento;
    }

    private static void pagarReservas(Scanner sc) {
        System.out.println("..::: Pagar Reservas :::..");
        System.out.print("¿Cuántas entradas desea pagar?: ");
        int cantidad;

        try {
        cantidad = Integer.parseInt(sc.nextLine());
    } catch (NumberFormatException e) {
        System.out.println("Cantidad inválida. Operación cancelada.");
        return;
        }
        
         ArrayList<String> asientosPagados = new ArrayList<>();
         double totalPagado = 0;
         
         for (int i = 1; i <= cantidad; i++) {
        System.out.println("\nEntrada #" + i);

        System.out.print("Ingrese el asiento reservado a pagar: ");
        String asiento = sc.nextLine().toUpperCase();

        if (!asientosReservados.contains(asiento)) {
            System.out.println("El asiento no está reservado. Intente con otro.");
            i--; // repetir este ciclo
            continue;
        }

        System.out.print("Ingrese su nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Ingrese su sexo (M/F): ");
        String sexo = sc.nextLine();

        System.out.print("¿Es estudiante? (s/n): ");
        boolean esEstudiante = sc.nextLine().equalsIgnoreCase("s");

        System.out.print("Ingrese su edad: ");
        int edad;
        try {
            edad = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Edad inválida. Operación cancelada.");
            return;
        }

        double precioBase = 10000;
        double descuento = calcularDescuento(edad, esEstudiante, sexo);
        double precioFinal = precioBase * (1 - descuento);
        String ubicacion = obtenerUbicacionAsiento(asiento);

        String boleta = "\n--- BOLETA ---\n" +
                        "Cliente: " + nombre + "\n" +
                        "Sexo: " + sexo + "\n" +
                        "Edad: " + edad + " años\n" +
                        "Asiento: " + asiento + " (" + ubicacion + ")\n" +
                        "Descuento aplicado: " + (int)(descuento * 100) + "%\n" +
                        "Total pagado: $" + String.format("%.0f", precioFinal) + "\n";

        ventas.add(boleta);
        asientosPagados.add(asiento);
        System.out.println("Boleta generada para el asiento " + asiento + ":");
        System.out.println(boleta);
         }
         
          asientosReservados.removeAll(asientosPagados);
          System.out.println("Pago completo por " + cantidad + " entradas. Total: $" + String.format("%.0f", totalPagado));
    }

    private static void mostrarBoletas() {
        System.out.println("..::: Boletas Generadas :::..");
        if (ventas.isEmpty()) {
            System.out.println("No se han generado boletas aún.");
        } else {
            for (String boleta : ventas) {
                System.out.println(boleta);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        completarasientos();

        do {
            System.out.println("\n...:: Teatro Moro ::..");
            System.out.println("1.- Ver Asientos Disponibles");
            System.out.println("2.- Reservar Asientos");
            System.out.println("3.- Pagar Reserva");
            System.out.println("4.- Ver Boletas Generadas");
            System.out.println("5.- Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    mostrardisponibilidad();
                    break;
                case 2:
                    reservarAsientos(sc);
                    break;
                case 3:
                    pagarReservas(sc);
                    break;
                case 4:
                    mostrarBoletas();
                    break;
                case 5:
                    System.out.println("Gracias por usar el sistema Teatro Moro.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 5);

        sc.close();
    }
}
