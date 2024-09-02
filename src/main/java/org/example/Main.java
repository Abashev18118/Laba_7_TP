package org.example; // объявление пакета org.example

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main // объявление класса Main
{
    //public static Lock outLock = new ReentrantLock(); // создание переменной outLock типа Lock и инициализация объекта ReentrantLock

    public static final int CARS_COUNT = 8; // объявление константы CARS_COUNT со значением 6

    public static void main(String[] args) // объявление метода main
    {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!"); // вывод строки на консоль

        Race race = new Race(new Road(60), new Tunnel(3), new Road(40)); // создание объекта Race с указанными параметрами
        Car[] cars = new Car[CARS_COUNT]; // создание массива объектов Car

        CyclicBarrier startBarrier = new CyclicBarrier(CARS_COUNT, () -> System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!")); // создание объекта CyclicBarrier с указанными параметрами и действием при достижении барьера
        CountDownLatch endLatch = new CountDownLatch(CARS_COUNT); // создание объекта CountDownLatch с указанным параметром

        Car.setStartBarrier(startBarrier); // вызов статического метода setStartBarrier класса Car
        Car.setEndLatch(endLatch); // вызов статического метода setEndLatch класса Car

        for (int i = 0; i < cars.length; i++) // цикл для создания объектов Car и добавления их в массив
        {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 1)); // создание объекта Car с указанными параметрами
        }

        for (Car car : cars) // цикл для запуска потоков для каждого объекта Car
        {
            new Thread(car).start(); // запуск нового потока для объекта Car
        }

        try // блок обработки исключения
        {
            endLatch.await(); // ожидание завершения всех потоков
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!"); // вывод строки на консоль
        }
        catch (InterruptedException e) // обработка исключения
        {
            throw new RuntimeException(e); // выброс исключения RuntimeException
        }
        Car.showWinners(); // вызов статического метода showWinners класса Car
    }
}