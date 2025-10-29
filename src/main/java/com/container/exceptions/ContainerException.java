package com.container.exceptions;

/**
 * Базовое исключение для контейнерной системы.
 * Используется для обработки ошибок, связанных с работой контейнера зависимостей.
 * Все специфичные исключения контейнера должны наследоваться от этого класса.
 * 
 * @author Yarovaya Maria
 * @version 1.0
 * @since 1.0
 */
public class ContainerException extends RuntimeException {
    
    /**
     * Создает новое исключение контейнера с указанным сообщением об ошибке.
     * Сообщение должно содержать детальное описание возникшей проблемы.
     *
     * @param message детальное сообщение об ошибке, не должно быть {@code null}
     * @throws IllegalArgumentException если {@code message} равен {@code null}
     * 
     * <p><b>Пример использования:</b>
     * <pre>{@code
     * throw new ContainerException("Не удалось создать экземпляр класса: " + className);
     * }</pre>
     */
    public ContainerException(String message) {
        super(message);
    }
    
    /**
     * Создает новое исключение контейнера с указанным сообщением и причиной.
     * Используется для создания цепочки исключений (exception chaining).
     *
     * @param message детальное сообщение об ошибке, не должно быть {@code null}
     * @param cause исходное исключение, которое вызвало данную ошибку, 
     *              может быть {@code null} если причина неизвестна
     * @throws IllegalArgumentException если {@code message} равен {@code null}
     * 
     * <p><b>Пример использования:</b>
     * <pre>{@code
     * try {
     *     // код, который может вызвать исключение
     * } catch (ReflectiveOperationException e) {
     *     throw new ContainerException("Ошибка рефлексии при создании компонента", e);
     * }
     * }</pre>
     * 
     * @see #getCause()
     */
    public ContainerException(String message, Throwable cause) {
        super(message, cause);
    }
}