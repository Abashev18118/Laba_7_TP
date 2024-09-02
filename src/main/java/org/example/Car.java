package org.example;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// Класс, представляющий автомобиль
public class Car implements Runnable
{
    private static final AtomicInteger atomicWinnersCount; // Атомарная переменная для подсчета победителей
    private static final List<String> winnersLists; // Список для хранения победителей
    private static final Lock lock; // Замок для обеспечения потокобезопасности
    private static CyclicBarrier startBarrier; // Барьер для старта гонки
    private static CountDownLatch endLatch; // Защелка для завершения гонки
    private static int carsCount; // Счетчик автомобилей

    // Инициализация статических переменных
    static
    {
        carsCount = 0;
        lock = new ReentrantLock(); // Создание реентерабельного замка
        atomicWinnersCount = new AtomicInteger(0); // Создание атомарной переменной с начальным значением 0
        winnersLists = new CopyOnWriteArrayList<>(); // Создание потокобезопасного списка
    }

    private final String name; // Имя автомобиля
    private final Race race; // Гонка, в которой участвует автомобиль
    private final int speed; // Скорость автомобиля

    // Конструктор для создания экземпляра автомобиля
    public Car(Race race, int speed)
    {
        this.race = race;
        this.speed = speed;
        this.name = "Участник #" + ++carsCount; // Генерация имени автомобиля
    }

    // Установка барьера старта
    public static void setStartBarrier(CyclicBarrier startBarrier)
    {
        Car.startBarrier = startBarrier;
    }

    // Установка защелки завершения гонки
    public static void setEndLatch(CountDownLatch endLatch)
    {
        Car.endLatch = endLatch;
    }

    // Вывод списка победителей
    public static void showWinners()
    {
        for (int i = 0; i < winnersLists.size() && i < 3; i++)
        {
            System.out.println(winnersLists.get(i) + " победил. Место - " + (i+1));
        }
    }

    // Получение имени автомобиля
    public String getName()
    {
        return name;
    }

    // Получение скорости автомобиля
    public int getSpeed()
    {
        return speed;
    }

    // Метод выполнения потока
    @Override
    public void run()
    {
        try // Блок обработки исключений
        {
            System.out.println(this.name + " готовится"); // Вывод сообщения о том, что машина готовится к старту
            Thread.sleep(500 + (int) (Math.random() * 800)); // Задержка для имитации времени подготовки (от 500 до 1300 миллисекунд)
            System.out.println(this.name + " готов"); // Вывод сообщения о том, что машина готова
            startBarrier.await(); // Ожидание старта гонки (все машины должны быть готовы)
        }
        catch (InterruptedException | BrokenBarrierException e) // Обработка исключений InterruptedException и BrokenBarrierException
        {
            throw new RuntimeException(e); // Выброс исключения RuntimeException с причиной e
        }

        for (int i = 0; i < race.getStages().size(); i++) // Цикл для прохождения всех этапов гонки
        {
            race.getStages().get(i).go(this); // Машина проходит текущий этап гонки
            if (i < race.getStages().size() - 1) // Если это не последний этап
            {
                //Main.outLock.unlock(); // Освобождение вывода
            }
        }

        //lock.lock(); // Блокировка для обновления списка победителей

        int winnerNumber = atomicWinnersCount.incrementAndGet(); // Увеличение счетчика победителей и получение номера победителя
        if (winnerNumber <= 3) // Если победитель в топ-3
        {
            winnersLists.add(this.name); // Добавление победителя в списокSystem.out.println(this.name + " победил. Место - " + winnerNumber);
    }

       // lock.unlock(); // Разблокировка

        endLatch.countDown(); // Уменьшение счетчика защелки завершения
        //Main.outLock.unlock(); // Освобождение вывода
    }
}