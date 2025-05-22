package com.example;

public class RunnableExample {
    public static void main(String[] args) {
        // Se define una tarea Runnable
        Runnable task = () -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Runnable: " + i);
                try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        };
        // Se crea un nuevo hilo utilizando el Runnable como argumento
        Thread thread = new Thread(task);
        thread.start();
    }
}
