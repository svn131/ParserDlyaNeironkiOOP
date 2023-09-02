package bot;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class Bot extends TelegramLongPollingBot {

    boolean dobavitNovogoUsera = false;

    Map<Long, String> baseUsers = new HashMap<>();

    public Bot() {
        ReadOnWrite.loadBaseUsers("C:/userBasesMapa.txt", baseUsers);
    }


    @Override
    public String getBotUsername() {
        return "SportsBot10_bot"; // Метод, который возвращает username бота.
    }

    @Override
    public String getBotToken() {
        return "6493956819:AAHbHe4wsGPvvD97SfEyLJK5k2jJJZgIzZ0"; // Метод, который возвращает token бота.
    }


    public void onUpdateReceived(Update update) {  //основной метод

        if (update.hasMessage()) {


            if (dobavitNovogoUsera) {

                dobavitNovogoUsera = false;
                Message message = update.getMessage(); //@todo добавить лоику если уже кто то из активированых что то пришлет перед новым ползоватлем
                Long chatId = message.getChatId(); // Получаем chat ID пользователя
                // Дальше можно использовать chatId для отправки сообщения обратно пользователю или для других действий.

                // Пример вывода chat ID в консоль:
                System.out.println("Chat ID: " + chatId);

                User user = message.getFrom();
                String username = user.getUserName(); // Получаем юзернейм пользователя

                String firstName = user.getFirstName(); // Получение имени пользователя
                String lastName = user.getLastName(); // Получение фамилии пользователя


                String value = ""; // Инициализируем значение

                if (username != null) {
                    value += "@" + username;
                }
                if (firstName != null) {
                    value += " " + firstName;
                }
                if (lastName != null) {
                    value += " " + lastName;
                }

                String currentDate = LocalDateTime.now().toString();
                value += " " + currentDate;


                System.out.println("Dobavka v mapu");
                baseUsers.put(chatId, value); // Добавляем в хеш-карту


                System.out.println("Содержимое Map:");
                for (Map.Entry<Long, String> entry : baseUsers.entrySet()) {
                    Long key = entry.getKey();
                    String val = entry.getValue();
                    System.out.println("Key: " + key + ", Value: " + val);
                }


                // Пример вывода значения из хеш-карты:
                System.out.println("User Info: " + baseUsers.get(chatId));


                // Открытие файлового потока для чтения

///////////////////////////////////////////////////////////////////////////////
                if (!ReadOnWrite.checkChatIdExists("C:/userBasesMapa.txt", chatId)) {
                    try (FileWriter writer = new FileWriter("C:/userBasesMapa.txt", true)) {
                        writer.write(chatId + " " + value + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Обработка ошибки записи файла
                    }
                }


                /////////////////////////////////////////////////////////////////////////////////////


                sendOk(chatId);

            } else {
                sendError(update.getMessage().getChatId());
                System.out.println("Попытка доступа при закрытой заслонке ");
            }

            ////
            Message message = update.getMessage();
            String text = message.getText();

            if (text.contains("Delates")) {
                // @todo логика удаления пользователя
            } else if (text.equals("Vhodi")) {

                dobavitNovogoUsera = true;
            } else if (text.equals("S")) { // @todo Метод для отладки
                sendArrayDataToAll(new String[]{"data1", "data2", "data3"});
            }


        }
    }


    // Метод для отправки массива строк пользователю
    private void sendArrayData(Long chatId, String[] dataArray) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(String.join("\n", dataArray));
        sendMessage.setChatId(chatId);
        try {
            execute(sendMessage); // Синхронное выполнение для простоты
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.out.println("Data sent successfully to chatId: " + chatId);
    }


    //@todo Метод для отправки сообщения пользователю доделать..
    public void sendMessage(Long chatId, String messageText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(messageText);
        sendMessage.setChatId(chatId);
        try {
            executeAsync(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void sendArrayDataToAll(String[] dataArray) {
        System.out.println("Delaetsya sendArrayDataToAll");

        List<Long> allowedChatIds = getAllowedChatIds(); // Получаем список разрешенных chatId

        System.out.println("Allowed chatIds: " + allowedChatIds);

        for (Long chatId : allowedChatIds) {
            System.out.println("Sending data to chatId: " + chatId);
            sendArrayData(chatId, dataArray); // Отправляем массив строк каждому пользователю по chatId
        }
    }


    // Метод для получения списка всех разрешенных chatId
    private List<Long> getAllowedChatIds() {
        System.out.println("getAllowedChatIds");
        List<Long> allowedChatIds = new ArrayList<>(baseUsers.keySet());
        return allowedChatIds;
    }


    public void sendOk(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Вы успешно подписаны на бота");
        sendMessage.setChatId(chatId);
        try {
            executeAsync(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendError(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Ошика доступа обратитесь к Администраторам @GOLDGAME77777 @BakharevDen");
        sendMessage.setChatId(chatId);
        try {
            executeAsync(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}


