package com.example;

import java.util.concurrent.*;

public class CallableExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<Integer> task = () -> {
            int sum = 0;
            for (int i = 0; i < 5; i++) {
                sum += i;
                try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
            }
            return sum;
        };

        Future<Integer> future = executor.submit(task); // Envía la tarea y recibe un Future que representará el resultado de la tarea

        try {
            Integer result = future.get(); // Obtiene el resultado de la tarea
            System.out.println("Sum: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }
}
