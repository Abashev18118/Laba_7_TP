package org.example;

// Класс, представляющий дорогу как один из этапов гонки
public class Road extends Stage
{
    // Конструктор для создания дороги заданной длины
    public Road(int length)
    {
        // Вызов конструктора родительского класса для инициализации длины и описания дороги
        super(length, "Дорога " + length + " метров");
    }

    // Переопределенный метод для выполнения этапа гонки (проезда по дороге)
    @Override
    public void go(Car car)
    {
        try
        {
            //Main.outLock.lock(); // Блокировка вывода
            System.out.println(car.getName() + " начал этап: " + description); // Вывод начала этапа
            //Main.outLock.unlock(); // Разблокировка вывода
            Thread.sleep(length / car.getSpeed() * 1000L); // Задержка для имитации проезда

            //Main.outLock.lock(); // Блокировка вывода
            System.out.println(car.getName() + " закончил этап: " + description); // Вывод завершения этапа
        }
        catch (InterruptedException e)
        {
            e.printStackTrace(); // Обработка исключения прерывания потока
        }
    }
}