package tech.hellsoft.trading.util;

import tech.hellsoft.trading.EstadoCliente;

import java.io.*;
import tech.hellsoft.trading.exception.configuracion.SnapshotCorruptoException;

public class SnapshotManager {
    // Guarda el estado del cliente en un archivo binario.
    public static void guardarEstado(EstadoCliente estado, String ruta) {
        ruta = "data/" + ruta + ".bin";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ruta))) {
            out.writeObject(estado);
        } catch (IOException e) {
            System.out.println("❌ Error al guardar el estado: " + e.getMessage());
        }
    }
    //  Carga el estado del cliente desde un archivo binario.
    public static EstadoCliente cargarEstado(String ruta) {
        ruta = "data/" + ruta + ".bin";
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ruta))) {
            EstadoCliente estado = (EstadoCliente) in.readObject();
            return estado;
        } catch (IOException | ClassNotFoundException e) {
            throw new SnapshotCorruptoException("Error al cargar el estado", e);
        }
    }
    public static void listarSnapshots(){
        File carpeta = new File("data/");
        File[] archivos = carpeta.listFiles();
        if (archivos != null) {
            for (File archivo : archivos) {
                System.out.println(archivo.getName());
            }
            return;
        }
        System.out.println("No se encontraron snapshots.");
    }
}
