package com.example;

public class SynchronizedExample {
    private static int counter = 0;

    // Metodo increment() que incrementa un contador de manera sincronizada
    public static synchronized void increment() {
        counter++;
    }

    public static void main(String[] args) {
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                increment();
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        try {
            // Aseguran que el programa principal espere a que ambos hilos terminen antes de continuar
            thread1.join();// Espera a que thread1 termine
            thread2.join(); // Espera a que thread2 termine
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Counter: " + counter);
    }
}
