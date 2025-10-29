package com.container;

import com.container.exceptions.ContainerException;
import java.util.Arrays;
import java.util.Objects;

/**
 * Кастомная реализация контейнера для хранения объектов различных типов.
 * Контейнер реализован на основе массива с автоматическим расширением при заполнении.
 * Не использует стандартные коллекции Java, предоставляя собственную реализацию.
 * Поддерживает основные операции: добавление, удаление, поиск и обход элементов.
 * 
 * @author Yarovaya Maria
 * @version 1.0.0
 * @see ContainerException
 */
public class ObjectContainer {
    
    /**
     * Внутренний массив для хранения элементов контейнера.
     * Емкость массива может превышать текущее количество элементов.
     */
    private Object[] elements;
    
    /**
     * Текущее количество элементов в контейнере.
     * Всегда удовлетворяет условию: 0 <= size <= elements.length
     */
    private int size;
    
    /**
     * Стандартная начальная емкость контейнера по умолчанию.
     */
    private static final int DEFAULT_CAPACITY = 10;
    
    /**
     * Коэффициент увеличения размера массива при необходимости расширения.
     */
    private static final double GROW_FACTOR = 1.5;
    
    /**
     * Создает новый контейнер со стандартной начальной емкостью.
     */
    public ObjectContainer() {
        this(DEFAULT_CAPACITY);
    }
    
    /**
     * Создает новый контейнер с указанной начальной емкостью.
     * 
     * @param initialCapacity начальная емкость контейнера
     * @throws ContainerException если начальная емкость отрицательна или равна нулю
     * @see #DEFAULT_CAPACITY
     */
    public ObjectContainer(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new ContainerException(
                "Емкость контейнера должна быть положительным числом. Получено: " + initialCapacity
            );
        }
        this.elements = new Object[initialCapacity];
        this.size = 0;
    }
    
    /**
     * Добавляет новый элемент в конец контейнера.
     * 
     * @param element элемент для добавления в контейнер
     * @return {@code true} если элемент успешно добавлен
     * @see #add(int, Object)
     */
    public boolean add(Object element) {
        ensureCapacity(size + 1);
        elements[size++] = element;
        return true;
    }
    
    /**
     * Вставляет элемент в указанную позицию контейнера.
     * Сдвигает элемент, находящийся в данной позиции, и все последующие элементы вправо.
     * 
     * @param index позиция для вставки элемента
     * @param element элемент для вставки в контейнер
     * @throws ContainerException если индекс выходит за допустимые границы
     * @see #add(Object)
     */
    public void add(int index, Object element) {
        validateIndexForAdding(index);
        ensureCapacity(size + 1);
        
        // Сдвиг элементов вправо для освобождения места
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }
    
    /**
     * Возвращает элемент по указанному индексу.
     * 
     * @param index индекс запрашиваемого элемента
     * @return элемент находящийся по указанному индексу
     * @throws ContainerException если индекс выходит за границы контейнера
     * @see #size()
     */
    public Object get(int index) {
        validateIndex(index);
        return elements[index];
    }
    
    /**
     * Удаляет элемент по указанному индексу и возвращает его.
     * Сдвигает все последующие элементы влево.
     * 
     * @param index индекс элемента для удаления
     * @return удаленный элемент
     * @throws ContainerException если индекс выходит за границы контейнера
     * @see #remove(Object)
     */
    public Object remove(int index) {
        validateIndex(index);
        
        Object removedElement = elements[index];
        int numMoved = size - index - 1;
        
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        
        elements[--size] = null; // Освобождаем ссылку для сборщика мусора
        return removedElement;
    }
    
    /**
     * Удаляет первое вхождение указанного элемента из контейнера.
     * 
     * @param element элемент который необходимо удалить
     * @return {@code true} если элемент был найден и удален, {@code false} если элемент не найден
     * @see #remove(int)
     * @see #indexOf(Object)
     */
    public boolean remove(Object element) {
        int index = indexOf(element);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }
    
    /**
     * Проверяет наличие элемента в контейнере.
     * 
     * @param element искомый элемент
     * @return {@code true} если элемент присутствует в контейнере, {@code false} в противном случае
     * @see #indexOf(Object)
     */
    public boolean contains(Object element) {
        return indexOf(element) != -1;
    }
    
    /**
     * Возвращает индекс первого вхождения указанного элемента.
     * Для сравнения элементов используется метод {@link Objects#equals(Object, Object)}.
     * 
     * @param element элемент для поиска
     * @return индекс первого вхождения элемента или -1 если элемент не найден
     * @see #contains(Object)
     */
    public int indexOf(Object element) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(element, elements[i])) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Возвращает текущее количество элементов в контейнере.
     * 
     * @return количество элементов в контейнере
     * @see #isEmpty()
     */
    public int size() {
        return size;
    }
    
    /**
     * Проверяет, является ли контейнер пустым.
     * 
     * @return {@code true} если контейнер не содержит элементов, {@code false} в противном случае
     * @see #size()
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Очищает контейнер, удаляя все содержащиеся в нем элементы.
     * Емкость внутреннего массива при этом не изменяется.
     */
    public void clear() {
        // Оптимизированная очистка - обнуляем только используемые элементы
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }
    
    /**
     * Преобразует контейнер в массив объектов.
     * 
     * @return новый массив содержащий все элементы контейнера в правильном порядке
     * @see Arrays#copyOf(Object[], int)
     */
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }
    
    /**
     * Гарантирует, что контейнер имеет достаточную емкость для размещения новых элементов.
     * При необходимости увеличивает размер внутреннего массива с использованием {@link #GROW_FACTOR}.
     * 
     * @param minCapacity минимальная требуемая емкость
     */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity = (int) Math.max(elements.length * GROW_FACTOR, minCapacity);
            elements = Arrays.copyOf(elements, newCapacity);
        }
    }
    
    /**
     * Проверяет корректность индекса для операций получения и удаления элементов.
     * 
     * @param index проверяемый индекс
     * @throws ContainerException если индекс выходит за допустимые границы
     */
    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ContainerException(
                String.format("Индекс %d выходит за границы контейнера. Допустимый диапазон: [0, %d)", 
                            index, size)
            );
        }
    }
    
    /**
     * Проверяет корректность индекса для операций вставки элементов.
     * 
     * @param index проверяемый индекс
     * @throws ContainerException если индекс выходит за допустимые границы
     */
    private void validateIndexForAdding(int index) {
        if (index < 0 || index > size) {
            throw new ContainerException(
                String.format("Индекс %d выходит за границы для вставки. Допустимый диапазон: [0, %d]", 
                            index, size)
            );
        }
    }
    
    /**
     * Возвращает строковое представление контейнера в формате [элемент1, элемент2, ...].
     * 
     * @return строковое представление контейнера
     */
    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < size; i++) {
            stringBuilder.append(String.valueOf(elements[i]));
            if (i < size - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}