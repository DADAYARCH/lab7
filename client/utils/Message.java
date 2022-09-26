package client.utils;

import java.util.Hashtable;
import java.util.Set;

public class Message {
    protected static Hashtable<String, String> messages;
    protected static Set<String> availableLanguages;
    protected static String currentLanguage = "ru";
    static {
        Hashtable<String, String> hashtable1 = new Hashtable();
        hashtable1.put("console_prefix", ">>> ");
        hashtable1.put("available_command", "%s - недоступная команда. \n Введите \"help\" для справки.\n");
        hashtable1.put("helpCommand_text", "help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции.\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "insert null {element} : добавить новый элемент с заданным ключом\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_id null : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "exit : завершить программу \n" +
                "remove_greater {salary} : удалить из коллекции все элементы, превышающие заданный параметр salary\n" +
                "remove_lower {salary} : удалить из коллекции все элементы, меньшие, чем заданный параметр salary\n" +
                "replace_if_lower id {element} : заменить значение по id, если новое значение меньше старого\n" +
                "filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку\n"+
                "login: авторизация\n"+
                "register: регистрация\n");
        hashtable1.put("infoCommand_format", "=== Информация о колекции ===\nПоследнее сохранение: %s;\nКоличество элементов: %d;\nРабочий файл: %s\n");
        hashtable1.put("showCommand_emptyCollection", "Колекция пуста.\n");
        hashtable1.put("shell_incorrectVarName", "Некорректное имя переменной.\nВведите новое имя: ");
        hashtable1.put("insertCommand_invalidData", "Некорректные данные\n");
        hashtable1.put("identifier_does_not_exist", "Такого идентификатора не существует.\n");
        hashtable1.put("executeCommand_Fail", "Не удалось найти файл со скриптом.\n");
        hashtable1.put("countByStartDate", "Количество дат равных заданному равно:");
        hashtable1.put("countByStartDate_fail", "По заданному времени не было найдено ни одного совпадения");
        hashtable1.put("shell_invalidFileFormat", "Неверная структура файла\n");
        hashtable1.put("insertCommand_addedTicket","Билет добавлен.\n");
        hashtable1.put("removeCommand", "Рабочий удален\n");

        hashtable1.put("workerForm_nameField", "Введите имя рабочего: ");
        hashtable1.put("coordinatesForm_XField", "Введите координату x: ");
        hashtable1.put("coordinatesForm_YField", "Введите координату y: ");
        hashtable1.put("workerForm_salaryField", "Введите зарплату рабочего: ");

        hashtable1.put("workerForm_startDateField", "Введите дату, когда рабочего устроили в формате ГГ-ММ-ДД : ");
        hashtable1.put("workerForm_startDateField_year", "Введите год, когда рабочего устроили:");

        hashtable1.put("workerForm_positionField", "Введите должность рабочего: ");
        hashtable1.put("workerForm_statusField", "Введите статус рабочего: ");
        hashtable1.put("workerForm_organizationCountField", "Введите количество сотрудников организации: ");
        hashtable1.put("workerForm_organizationTypeField", "Введите тип организации: ");
        hashtable1.put("workerForm_organizationAddressField", "Введите адрес организации: ");
        hashtable1.put("workerForm_addressStreetField", "Введите название улицы: ");
        hashtable1.put("workerForm_addressTownField", "Введите название города: ");

        hashtable1.put("collection_is_clear", "Коллекция очищена.\n");
        hashtable1.put("success", "Операция выполнена\n");
        hashtable1.put("updateCommand_wasUpdate","Данные обновлены\n");

        hashtable1.put("workerForm_invalidName", "Имя не должно быть пустым или NULL.\n");
        hashtable1.put("workerForm_invalidX", "Неверное значение координаты x.\n");
        hashtable1.put("workerForm_invalidSalary", "Зарплата должна быть больше нуля.\n");
        hashtable1.put("workerForm_invalidPosition", "Неверный тип. Тип может быть null. Доступные типы: MANAGER, LABORER, HEAD_OF_DIVISION, HEAD_OF_DEPARTMENT.\n");
        hashtable1.put( "workerForm_invalidStatus", "Неверный тип. Тип не может быть null. Доступные типы:FIRED, HIRED, RECOMMENDED_FOR_PROMOTION, REGULAR, PROBATION.\n");
        hashtable1.put("organization_invalidCount", "Неверное количество, оно должно быть больше нуля.\n");
        hashtable1.put("organization_invalidType", "Неверный тип. Тип не может быть null. Доступные типы: COMMERCIAL, PUBLIC, GOVERNMENT, TRUST, PRIVATE_LIMITED_COMPANY.\n");
        hashtable1.put("organization_invalidAddress", "The street and the city couldn't be equal to NULL at the same time.\n");
        hashtable1.put("workerForm_invalidStartDate" , "Введен неправильный формат времени!\n");
        hashtable1.put("incorrectAttr","Неверный параметр команды.\n");

        hashtable1.put("login", "Введите имя пользователя: ");
        hashtable1.put("helpText_login", "Логин должен быть не пустой и не превышать 50 символов.\n");
        hashtable1.put("password", "Введите пароль: ");
        hashtable1.put("helpText_password", "Пароль должен быть не пустой и не превышать 50 символов.\n");

        hashtable1.put("workerForm_isInvalid", "Неверно введены данные \n");
        hashtable1.put("incorrectId","Билета с таким идентификаторм не существует.\n");
        hashtable1.put("crushedServer","Сервер находится в упадке, милорд!\n Попробуйте его взбодрить или повторите попытку позже.\n");

        messages = hashtable1;
    }

    public static String getMessage(String messageCode) {
        return messages.get(messageCode);
    }

    static void changeLanguage(String language) {
        if (availableLanguages.contains(language)) {
            currentLanguage = language;
        }
    }
}
