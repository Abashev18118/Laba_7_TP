package org.example;
import java.util.ArrayList;
import java.util.Arrays;

// Класс, представляющий гонку
public class Race
{
    private final ArrayList<Stage> stages; // Список этапов гонки

    // Конструктор для создания экземпляра гонки с заданными этапами
    public Race(Stage... stages)
    {
        this.stages = new ArrayList<>(Arrays.asList(stages)); // Инициализация списка этапов
    }

    // Метод для получения копии списка этапов гонки
    public ArrayList<Stage> getStages()
    {
        return new ArrayList<>(stages); // Возвращаем копию списка этапов, чтобы избежать изменений извне
    }
}