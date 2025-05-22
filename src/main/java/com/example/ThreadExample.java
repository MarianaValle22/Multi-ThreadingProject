package com.example;

public class ThreadExample {
    public static void main(String[] args) {
        // Se crea un nuevo hilo utilizando la clase Thread
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread: " + i);
                try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        });
        // thread.start() inicia la ejecución del hilo
        thread.start();
    }
}
