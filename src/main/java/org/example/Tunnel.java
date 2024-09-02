package org.example;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
//Семафор- механизм, который позволяет ограничить количество потоков, которые могут одновременно использовать этот ресурс.
//Замок (Lock) — это механизм синхронизации, который используется для управления доступом к общему ресурсу, обеспечивая,
// что только один поток может использовать этот ресурс в определенный момент времени.
public class Tunnel extends Stage // Объявление класса Tunnel, который наследуется от абстрактного класса Stage
{
    private static final int length = 80; // Объявление и инициализация константы длины тоннеля (80 метров)
    private final Semaphore semaphore; // Объявление переменной semaphore типа Semaphore
    //private final Lock lock; // Объявление переменной lock типа Lock

    public Tunnel(int limit) // Конструктор класса Tunnel, принимающий параметр limit
    {
        super(length, "Тоннель " + length + " метров"); // Вызов конструктора родительского класса Stage с параметрами длины и описания

        semaphore = new Semaphore(limit); // Инициализация семафора с указанным лимитом (количество машин, которые могут одновременно находиться в тоннеле)
       // lock = new ReentrantLock(); // Инициализация реентерабельного замка
    }

    @Override
    public void go(Car car) // Переопределение абстрактного метода go из класса Stage, принимающего объект Car
    {
        try // Блок обработки исключений
        {
            //Main.outLock.lock(); // Блокировка вывода
            System.out.println(car.getName() + " готовится к этапу(ждет): " + description); // Вывод сообщения о том, что машина готовится к этапу
            //Main.outLock.unlock(); // Разблокировка вывода

            //lock.lock(); // Блокировка для обеспечения потокобезопасности

            semaphore.acquire(); // Захват разрешения семафора (ожидание, если лимит достигнут)

            //Main.outLock.lock(); // Блокировка вывода
            System.out.println(car.getName() + " начал этап: " + description); // Вывод сообщения о начале этапа
            //Main.outLock.unlock(); // Разблокировка вывода

            //lock.unlock(); // Разблокировка

            Thread.sleep(length / car.getSpeed() * 1000L); // Задержка для имитации времени прохождения этапа (длина делится на скорость машины и умножается на 1000 для перевода в миллисекунды)
        }
        catch (InterruptedException e) // Обработка исключения прерывания потока
        {
            e.printStackTrace(); // Вывод стека вызовов исключения
        }
        finally // Блок, который выполняется в любом случае (после try или catch)
        {
            //Main.outLock.lock(); // Блокировка вывода
            System.out.println(car.getName() + " закончил этап: " + description); // Вывод сообщения о завершении этапа
            semaphore.release(); // Освобождение разрешения семафора
        }
    }
}