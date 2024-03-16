/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1;

import java.util.LinkedList;
import java.util.Queue;

public class NewClass3 {
    public static void main(String[] args) {
        WorkerThreadQueue queue = new WorkerThreadQueue();

        // Додаємо завдання до черги
        queue.execute(() -> System.out.println("Виконання завдання 1"));
        queue.execute(() -> System.out.println("Виконання завдання 2"));
        queue.execute(() -> System.out.println("Виконання завдання 3"));

        // Зупиняємо чергу
        queue.shutdown();
    }
}

class WorkerThreadQueue {
    private final Queue<Runnable> tasks = new LinkedList<>();
    private final Thread worker;

    public WorkerThreadQueue() {
        worker = new Thread(() -> {
            while (true) {
                Runnable task;
                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        try {
                            tasks.wait(); // Чекаємо на нові завдання
                        } catch (InterruptedException e) {
                            return; // Вихід з потоку при прериванні
                        }
                    }
                    task = tasks.poll(); // Отримуємо завдання з черги
                }
                try {
                    task.run(); // Виконуємо завдання
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        });
        worker.start(); // Запускаємо робочий потік
    }

    public void execute(Runnable task) {
        synchronized (tasks) {
            tasks.add(task); // Додаємо завдання до черги
            tasks.notify(); // Повідомляємо робочому потоку про нове завдання
        }
    }

    public void shutdown() {
        worker.interrupt(); // Прериваємо робочий потік
    }
}

