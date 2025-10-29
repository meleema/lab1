package com.container;

/**
 * Демонстрационный класс для тестирования функциональности контейнера.
 * Показывает основные операции работы с контейнером объектов, включая
 * добавление, удаление, поиск и обход элементов.
 * 
 * @author Yarovaya Maria
 * @version 1.0
 * @see ObjectContainer
 */
public class Main {
    
    /**
     * Основной метод демонстрации работы контейнера.
     * Выполняет последовательность операций для展示 возможностей контейнера:
     * <ol>
     *   <li>Инициализация контейнера</li>
     *   <li>Добавление элементов разных типов</li>
     *   <li>Вставка по индексу</li>
     *   <li>Извлечение и приведение типов</li>
     *   <li>Поиск элементов</li>
     *   <li>Обход всех элементов</li>
     *   <li>Удаление элементов</li>
     *   <li>Дополнительные операции</li>
     * </ol>
     * 
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        System.out.println("=== ДЕМОНСТРАЦИЯ РАБОТЫ КОНТЕЙНЕРА ===");
        
        // Инициализация контейнера
        ObjectContainer container = new ObjectContainer();

        // Добавление разнотипных элементов в контейнер
        container.add("Hello");
        container.add(11);
        container.add(3.554);

        // Вставка элемента по конкретному индексу
        container.add(1, "World");

        // Извлечение элементов с приведением типов
        String text = (String) container.get(0);
        Integer number = (Integer) container.get(2);
        
        System.out.println("Элемент с индексом 0: " + text);
        System.out.println("Элемент с индексом 2: " + number);

        // Проверка наличия элемента в контейнере
        boolean hasElement = container.contains(11);
        System.out.println("Содержит число 11: " + hasElement);

        // Обход всех элементов контейнера
        System.out.println("\nВсе элементы в контейнере:");
        for (int i = 0; i < container.size(); i++) {
            Object element = container.get(i);
            System.out.println("Индекс " + i + ": " + element + " (тип: " + element.getClass().getSimpleName() + ")");
        }

        // Удаление элементов различными способами
        Object removedByIndex = container.remove(0);        // По индексу
        boolean removedByValue = container.remove("World"); // По значению
        
        System.out.println("\nУдален элемент по индексу: " + removedByIndex);
        System.out.println("Элемент 'World' удален: " + removedByValue);

        System.out.println("\nСостояние контейнера после удаления:");
        for (int i = 0; i < container.size(); i++) {
            Object element = container.get(i);
            System.out.println("Индекс " + i + ": " + element);
        }

        // Дополнительные операции для демонстрации
        System.out.println("\nДОПОЛНИТЕЛЬНЫЕ ОПЕРАЦИИ");
        System.out.println("Размер контейнера: " + container.size());
        System.out.println("Контейнер пустой: " + container.isEmpty());
        System.out.println("Строковое представление: " + container.toString());
        
        // Демонстрация очистки контейнера
        container.clear();
        System.out.println("\nПосле очистки - размер: " + container.size());
        System.out.println("Контейнер пустой: " + container.isEmpty());
    }
}