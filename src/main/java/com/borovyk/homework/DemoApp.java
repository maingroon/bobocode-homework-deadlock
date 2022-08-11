package com.borovyk.homework;

public class DemoApp {

    private static final String MONITOR_1 = "MONITOR_1";
    private static final String MONITOR_2 = "MONITOR_2";

    public static void main(String[] args) {
        final Thread thread1 = new Thread(new FirstRunnable());
        final Thread thread2 = new Thread(new SecondRunnable());

        System.out.println(thread1.getName() + ": " + thread1.getState());
        System.out.println(thread2.getName() + ": " + thread2.getState());

        thread1.start();
        thread2.start();

        for (int i = 0; i < 50; i++) {
            System.out.println(thread1.getName() + ": " + thread1.getState());
            System.out.println(thread2.getName() + ": " + thread2.getState());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class FirstRunnable implements Runnable {

        @Override
        public void run() {
            final String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " before Monitor 1");
            synchronized (MONITOR_1) {
                System.out.println(threadName + " took Monitor 1");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(threadName + " before Monitor 2");
                synchronized (MONITOR_2) {
                    System.out.println(threadName + " took Monitor 2");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(threadName + " after Monitor 2");
            }
            System.out.println(threadName + " after Monitor 1");
        }
    }

    static class SecondRunnable implements Runnable {

        @Override
        public void run() {
            final String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " before Monitor 2");
            synchronized (MONITOR_2) {
                System.out.println(threadName + " took Monitor 2");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(threadName + " before Monitor 1");
                synchronized (MONITOR_1) {
                    System.out.println(threadName + " took Monitor 1");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(threadName + " after Monitor 1");
            }
            System.out.println(threadName + " after Monitor 2");
        }
    }
}
