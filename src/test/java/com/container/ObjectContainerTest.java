package com.container;

import com.container.exceptions.ContainerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit тесты для класса {@link ObjectContainer}.
 * Проверяет корректность работы всех методов контейнера в различных сценариях.
 * Тесты охватывают основные операции: добавление, удаление, поиск, обход элементов,
 * а также обработку граничных случаев и исключительных ситуаций.
 * 
 * @author Yarovaya Maria 
 * @version 1.0.0
 * @see ObjectContainer
 * @see ContainerException
 */
class ObjectContainerTest {

    private ObjectContainer container;

    /**
     * Инициализация нового контейнера перед каждым тестом.
     * Гарантирует изолированность тестовых сценариев.
     */
    @BeforeEach
    void setUp() {
        container = new ObjectContainer();
    }

    @Test
    @DisplayName("Создание контейнера со стандартной емкостью")
    void testDefaultConstructor() {
        assertNotNull(container);
        assertEquals(0, container.size());
        assertTrue(container.isEmpty());
    }

    @Test
    @DisplayName("Создание контейнера с заданной емкостью")
    void testConstructorWithCapacity() {
        ObjectContainer customContainer = new ObjectContainer(20);
        assertEquals(0, customContainer.size());
        assertTrue(customContainer.isEmpty());
    }

    @Test
    @DisplayName("Создание контейнера с некорректной емкостью должно бросать исключение")
    void testConstructorWithInvalidCapacity() {
        assertThrows(ContainerException.class, () -> new ObjectContainer(0));
        assertThrows(ContainerException.class, () -> new ObjectContainer(-5));
    }

    @Test
    @DisplayName("Добавление элементов в контейнер")
    void testAddElements() {
        assertTrue(container.add("First"));
        assertTrue(container.add(42));
        assertTrue(container.add(3.14));

        assertEquals(3, container.size());
        assertFalse(container.isEmpty());
    }

    @Test
    @DisplayName("Добавление null элементов")
    void testAddNullElements() {
        assertTrue(container.add(null));
        assertTrue(container.add("NotNull"));
        assertTrue(container.add(null));

        assertEquals(3, container.size());
        assertTrue(container.contains(null));
    }

    @Test
    @DisplayName("Вставка элемента по индексу")
    void testAddAtIndex() {
        container.add("A");
        container.add("C");

        container.add(1, "B");

        assertEquals(3, container.size());
        assertEquals("A", container.get(0));
        assertEquals("B", container.get(1));
        assertEquals("C", container.get(2));
    }

    @Test
    @DisplayName("Вставка по невалидному индексу должна бросать исключение")
    void testAddAtInvalidIndex() {
        assertThrows(ContainerException.class, () -> container.add(-1, "Element"));
        assertThrows(ContainerException.class, () -> container.add(1, "Element"));
    }

    @Test
    @DisplayName("Вставка в начало и конец контейнера")
    void testAddAtFirstAndLastPosition() {
        container.add("Middle");
        
        container.add(0, "First");
        container.add(2, "Last");

        assertEquals(3, container.size());
        assertEquals("First", container.get(0));
        assertEquals("Middle", container.get(1));
        assertEquals("Last", container.get(2));
    }

    @Test
    @DisplayName("Получение элемента по индексу")
    void testGetElement() {
        container.add("Test");
        container.add(123);

        assertEquals("Test", container.get(0));
        assertEquals(123, container.get(1));
    }

    @Test
    @DisplayName("Получение элемента по невалидному индексу должно бросать исключение")
    void testGetWithInvalidIndex() {
        container.add("Test");
        
        assertThrows(ContainerException.class, () -> container.get(-1));
        assertThrows(ContainerException.class, () -> container.get(1));
        assertThrows(ContainerException.class, () -> container.get(100));
    }

    @Test
    @DisplayName("Удаление элемента по индексу")
    void testRemoveByIndex() {
        container.add("A");
        container.add("B");
        container.add("C");

        Object removed = container.remove(1);

        assertEquals("B", removed);
        assertEquals(2, container.size());
        assertEquals("A", container.get(0));
        assertEquals("C", container.get(1));
    }

    @Test
    @DisplayName("Удаление по невалидному индексу должно бросать исключение")
    void testRemoveWithInvalidIndex() {
        container.add("A");
        
        assertThrows(ContainerException.class, () -> container.remove(-1));
        assertThrows(ContainerException.class, () -> container.remove(1));
    }

    @Test
    @DisplayName("Удаление первого и последнего элемента")
    void testRemoveFirstAndLast() {
        container.add("First");
        container.add("Middle");
        container.add("Last");

        Object firstRemoved = container.remove(0);
        Object lastRemoved = container.remove(1); // Теперь размер 2, индекс 1 - последний

        assertEquals("First", firstRemoved);
        assertEquals("Last", lastRemoved);
        assertEquals(1, container.size());
        assertEquals("Middle", container.get(0));
    }

    @Test
    @DisplayName("Удаление элемента по значению")
    void testRemoveByValue() {
        container.add("A");
        container.add("B");
        container.add("C");

        boolean removed = container.remove("B");

        assertTrue(removed);
        assertEquals(2, container.size());
        assertFalse(container.contains("B"));
    }

    @Test
    @DisplayName("Удаление несуществующего элемента")
    void testRemoveNonExistent() {
        container.add("A");
        container.add("B");

        boolean removed = container.remove("C");

        assertFalse(removed);
        assertEquals(2, container.size());
    }

    @Test
    @DisplayName("Удаление null элемента")
    void testRemoveNull() {
        container.add("A");
        container.add(null);
        container.add("B");

        boolean removed = container.remove(null);

        assertTrue(removed);
        assertEquals(2, container.size());
        assertFalse(container.contains(null));
    }

    @Test
    @DisplayName("Проверка наличия элемента")
    void testContains() {
        container.add("Existing");
        container.add(null);

        assertTrue(container.contains("Existing"));
        assertTrue(container.contains(null));
        assertFalse(container.contains("NonExisting"));
    }

    @Test
    @DisplayName("Поиск индекса элемента")
    void testIndexOf() {
        container.add("A");
        container.add("B");
        container.add("A");
        container.add(null);

        assertEquals(0, container.indexOf("A"));
        assertEquals(1, container.indexOf("B"));
        assertEquals(3, container.indexOf(null));
        assertEquals(-1, container.indexOf("C"));
    }

    @Test
    @DisplayName("Очистка контейнера")
    void testClear() {
        container.add("A");
        container.add("B");
        container.add("C");

        assertEquals(3, container.size());
        container.clear();

        assertEquals(0, container.size());
        assertTrue(container.isEmpty());
    }

    @Test
    @DisplayName("Преобразование в массив")
    void testToArray() {
        container.add("A");
        container.add(42);
        container.add(null);

        Object[] array = container.toArray();

        assertNotNull(array);
        assertEquals(3, array.length);
        assertEquals("A", array[0]);
        assertEquals(42, array[1]);
        assertNull(array[2]);
    }

    @Test
    @DisplayName("Динамическое расширение контейнера")
    void testDynamicExpansion() {
        int initialCapacity = 5;
        ObjectContainer smallContainer = new ObjectContainer(initialCapacity);

        // Добавляем больше элементов чем начальная емкость
        for (int i = 0; i < initialCapacity * 2; i++) {
            smallContainer.add("Element " + i);
        }

        assertEquals(initialCapacity * 2, smallContainer.size());
        
        // Проверяем, что все элементы на месте
        for (int i = 0; i < initialCapacity * 2; i++) {
            assertEquals("Element " + i, smallContainer.get(i));
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 100})
    @DisplayName("Параметризованный тест добавления элементов")
    void testParameterizedAdd(int count) {
        for (int i = 0; i < count; i++) {
            container.add(i);
        }

        assertEquals(count, container.size());
        for (int i = 0; i < count; i++) {
            assertEquals(i, container.get(i));
        }
    }

    @Test
    @DisplayName("Строковое представление контейнера")
    void testToString() {
        assertEquals("[]", container.toString());

        container.add("A");
        container.add(42);

        String result = container.toString();
        assertTrue(result.contains("A"));
        assertTrue(result.contains("42"));
        assertTrue(result.startsWith("["));
        assertTrue(result.endsWith("]"));
    }

    @Test
    @DisplayName("Комплексный тест всех операций")
    void testComplexScenario() {
        // Добавление
        container.add("Start");
        container.add(100);
        container.add(2, "Middle"); // вставка по индексу
        
        // Проверка состояния
        assertEquals(3, container.size());
        assertTrue(container.contains("Middle"));
        
        // Удаление
        container.remove(Integer.valueOf(100));
        assertEquals(2, container.size());
        
        // Очистка и повторное заполнение
        container.clear();
        assertTrue(container.isEmpty());
        
        for (int i = 0; i < 10; i++) {
            container.add(i);
        }
        
        assertEquals(10, container.size());
        assertEquals(5, container.get(5));
    }

    @Test
    @DisplayName("Тест целостности данных после множественных операций")
    void testDataIntegrity() {
        // Множественные добавления и удаления
        for (int i = 0; i < 100; i++) {
            container.add(i);
            if (i % 3 == 0) {
                container.remove(Integer.valueOf(i));
            }
        }
        
        // Проверка что нет дубликатов и все операции выполнены корректно
        assertTrue(container.size() > 0);
        assertFalse(container.contains(0)); // Должен быть удален
        assertTrue(container.contains(1));  // Должен остаться
    }
}