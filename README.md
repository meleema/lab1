# Кастомная реализация контейнера для хранения объектов на Java без использования встроенных коллекций.

## Особенности:

- Динамическое расширение при заполнении
- Безопасность типов (работа с Object)
- JavaDoc документация
- Полное покрытие unit тестами
- Maven проект

## Требования

- Java 11+
- Maven 3.6+

## Сборка и тестирование

```bash
# Сборка проекта
mvn clean compile

# Запуск тестов
mvn test

# Генерация javadoc
mvn javadoc:javadoc

# Запуск демонстрации
mvn exec:java -Dexec.mainClass="com.container.Main"

# Сборка JAR
mvn package

# Генерация документации
# Проект включает полную JavaDoc документацию для всех классов и методов. Для генерации документации выполните:

# Генерация JavaDoc в target/site/apidocs
mvn javadoc:javadoc

# Генерация JavaDoc с отчетом о проверке
mvn javadoc:jar