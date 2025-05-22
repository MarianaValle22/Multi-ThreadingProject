package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TiendaVirtualThread {
    // Variable compartida entre los hilos
    private static int stock = 0;

    // Metodo que agregar productos al stock de manera sincronizada
    public static synchronized void agregarStock(int cantidad) {
        stock += cantidad;
        System.out.println(Thread.currentThread().getName() + " agregó " + cantidad + " productos. Stock actual: " + stock);
    }

    // Metodo que disminuye la cantidad de productos en stock de manera sincronizada
    public static synchronized int procesarPedido(int cantidad) {
        // Verifica que exista stock suficiente para cumplir el pedido
        if (stock >= cantidad) {
            stock -= cantidad;
            System.out.println(Thread.currentThread().getName() + " procesó un pedido de " + cantidad + ". Stock restante: " + stock);
            return cantidad;
        } else {
            System.out.println(Thread.currentThread().getName() + " no pudo procesar pedido por falta de stock.");
            return 0;
        }
    }

    public static void main(String[] args) {
        // Crear y arrancar dos hilos para agregar productos al stock
        // El hilo 1 agrega 10 productos al stock en 5 ocasiones
        Thread proveedor1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                agregarStock(10);
                try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); } // Pausa el hilo por 1 segundo
            }
        }, "Proveedor 1");

        // El hilo 2 agrega 5 productos al stock en 5 ocasiones
        Thread proveedor2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                agregarStock(5);
                try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); } // Pausa el hilo por 1 segundo
            }
        }, "Proveedor 2");

        // Inicia los dos hilos
        proveedor1.start();
        proveedor2.start();

        // Esperar a que los proveedores terminen antes de procesar pedidos
        try {
            proveedor1.join();
            proveedor2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Crear un pool de hilos para procesar pedidos con Callable
        ExecutorService executor = Executors.newFixedThreadPool(4);

        List<Callable<Integer>> pedidos = new ArrayList<>();

        // Cada cliente intenta procesar un pedido de cierto tamaño
        pedidos.add(() -> procesarPedido(15));
        pedidos.add(() -> procesarPedido(20));
        pedidos.add(() -> procesarPedido(10));
        pedidos.add(() -> procesarPedido(40));

        try {
            List<Future<Integer>> resultados = executor.invokeAll(pedidos);
            int totalProcesado = 0;
            for (Future<Integer> result : resultados) {
                totalProcesado += result.get(); // Suma la cantidad procesada por cada pedido
            }
            System.out.println("Total de productos procesados: " + totalProcesado);
            System.out.println("Stock final disponible: " + stock);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown(); // Cierra el pool de hilos
        }
    }
}
