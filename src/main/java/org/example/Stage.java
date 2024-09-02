package org.example;

// Абстрактный класс, представляющий этап гонки
public abstract class Stage
{
    // Защищенные поля для длины и описания этапа
    protected final int length;
    protected final String description;

    // Конструктор для создания экземпляра этапа с заданной длиной и описанием
    public Stage(int length, String description)
    {
        this.length = length; // Инициализация длины этапа
        this.description = description; // Инициализация описания этапа
    }

    // Абстрактный метод, представляющий выполнение этапа гонки
    public abstract void go(Car car);
}