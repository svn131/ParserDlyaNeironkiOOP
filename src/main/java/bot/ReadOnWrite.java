package bot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class ReadOnWrite {


    // Проверка есть ли такой юзер с таким чат ид или нет в файле
    public static boolean checkChatIdExists(String filePath, Long chatId) {
        try (FileReader reader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            boolean chatIdExists = false;

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith(chatId.toString())) {
                    chatIdExists = true;
                    break;
                }
            }

            return chatIdExists;

        } catch (IOException e) {
            e.printStackTrace();
            return false; // или выбросить исключение для обработки выше
        }
    }

    // Запись в мапу при инициализации из файла
    public static void loadBaseUsers(String filePath, Map<Long, String> baseUsers) {
        try (FileReader reader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ", 2);
                if (parts.length != 2) {
                    // Неверный формат строки, пропускаем ее
                    continue;
                }
                try {
                    Long chatId = Long.parseLong(parts[0].trim());
                    String value = parts[1].trim();
                    baseUsers.put(chatId, value);
                } catch (NumberFormatException e) {
                    // Неверный формат номера чата, пропускаем строку
                    continue;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            // Обработка ошибки чтения файла
        }
    }

}




