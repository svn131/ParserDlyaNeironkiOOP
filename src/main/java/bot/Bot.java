package bot;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.FileWriter;
import java.util.*;

import java.io.IOException;

public class Bot extends TelegramLongPollingBot {

    boolean dobavitNovogoUsera = false;

    Map<Long,String> baseUsers = new HashMap<>();



    /*
    Создать и получить токен бота нужно в @BotFather в Telegram.
     */
    @Override
    public String getBotUsername() {
        return "mybottJavamybot_bot"; // Метод, который возвращает username бота.
    }

    @Override
    public String getBotToken() {
        return "6650591421:AAHrT3OMJqM5cgxOxZ3nQ9-GKQUXPuPAMtY"; // Метод, который возвращает token бота.
    }


    public void onUpdateReceived(Update update) {  //основной метод
        System.out.println("555555555555555");
        if (update.hasMessage()) {
            System.out.println("222222222");

            if (dobavitNovogoUsera) {
                System.out.println("111111111");
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

                System.out.println("Dobavka v mapu");
                baseUsers.put(chatId, value); // Добавляем в хеш-карту

                // Пример вывода значения из хеш-карты:
                System.out.println("User Info: " + baseUsers.get(chatId));

                try {
                    FileWriter writer = new FileWriter("C:/userBasesMapa.txt", true);
                    writer.write(chatId + " " + value + "\n");
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("333333333");
                sendOk(chatId);

            } else {
                sendError(update.getMessage().getChatId());
                System.out.println("Попытка доступа при закрытой заслонке1 ");
            }

            ////
            Message message = update.getMessage();
            String text = message.getText();

            if (text.contains("Delates")) {
                // @todo логика удаления пользователя
            } else if (text.equals("Vhodi")) {
                System.out.println("Sdelalsy true");
                dobavitNovogoUsera = true;
            }


        }
    }




    // Метод для отправки массива строк пользователю
    private void sendArrayData(Long chatId, String[] dataArray) {
        System.out.println("Delaetsya sendArrayData");


        System.out.println("Sending data to chatId: " + chatId);
        System.out.println("Data Array: " + Arrays.toString(dataArray));

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

//    public void sendArrayDataToAll(String[] dataArray) {
//
//        System.out.println("Delaetsya sendArrayDataToAll");
//
//        List<Long> allowedChatIds = getAllowedChatIds(); // Получаем список разрешенных chatId
//
//        for (Long chatId : allowedChatIds) {
//
//            System.out.println("Sending data to chatId: " + chatId);
//            sendArrayData(chatId, dataArray); // Отправляем массив строк каждому пользователю по chatId
//        }
//    }




    // Метод для получения списка всех разрешенных chatId
    private List<Long> getAllowedChatIds() {
        System.out.println("getAllowedChatIds");
        List<Long> allowedChatIds = new ArrayList<>(baseUsers.keySet());
        return allowedChatIds;
    }


    public void sendOk(Long chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Вы успешно подписаны на бота2");
        sendMessage.setChatId(chatId);
        try {
            executeAsync(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void sendError(Long chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Ошика доступа обратитесь к Администраторам @GOLDGAME77777 @BakharevDen5");
        sendMessage.setChatId(chatId);
        try {
            executeAsync(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}

// Метод для проверки, разрешен ли пользователь получать массив данных
//    private boolean isUserAllowed(Long chatId) {
//        // Здесь вы можете сравнить chatId с существующим массивом разрешенных юзернеймов
//        // Для простоты я использовал хардкод с одним разрешенным юзернеймом: @GOLDGAME77777
//        String allowedUsername = "@GOLDGAME77777";
//        String chatUsername = "@" + getChatUsername(chatId);
//
//        return chatUsername.equals(allowedUsername);
//    }


// Метод для отправки массива строк пользователю по юзернейму
//    private void sendArrayData(String username, List<String> dataArray) throws IOException {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setText(String.join("\n", dataArray));
//        sendMessage.setChatId(poluchenieChatIdPOUserName( username)); // Используем "@" для указания юзернейма
//        try {
//            execute(sendMessage); // Синхронное выполнение для простоты
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }

//    public static Long poluchenieChatIdPOUserName(String username) throws IOException {
//    String botToken = "6650591421:AAHrT3OMJqM5cgxOxZ3nQ9-GKQUXPuPAMtY" ;
////    String username = "@GOLDGAME77777";
//
//    // Формирование URL запроса
//    String urlString = "https://api.telegram.org/bot" + botToken + "/getChat?chat_id=" + username;
//
//    // Отправка GET-запроса и получение ответа
//    URL url = new URL(urlString);
//    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("GET");
//
//    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//    StringBuilder response = new StringBuilder();
//    String line;
//        while ((line = reader.readLine()) != null) {
//        response.append(line);
//    }
//        reader.close();
//
//    // Извлечение chat ID из ответа
//    String jsonResponse = response.toString();
//    int chatIdStartIndex = jsonResponse.indexOf("\"id\":") + 5;
//    int chatIdEndIndex = jsonResponse.indexOf(",", chatIdStartIndex);
//    String chatIdString = jsonResponse.substring(chatIdStartIndex, chatIdEndIndex);
//
//    // Вывод chat ID в консоль
//    long chatId = Long.parseLong(chatIdString);
//        System.out.println("Chat ID для пользователя " + username + ": " + chatId);
//
//        return chatId;
//}




//    @Override
//    public void onUpdateReceived(Update update) {
//        if (update.hasMessage()) {
//            String messageText = update.getMessage().getText(); // Получить текст сообщения
//            Long chatId = update.getMessage().getChatId(); // Получить chatId
//
//            switch (messageText) {
//                case "/start":
//                    sendMessage(chatId, "Привет! Я помогу узнать погоду, просто напиши название города.");
//                    break;
//                case "/help":
//                    sendMessage(chatId, "Чтобы узнать погоду, отправь название города. Если такого города нет, я промолчу!");
//                    break;
//                default:
//                    if (isUserAllowed(chatId)) {
//                        // Если пользователь разрешен, отправить ему массив строк
//                        String[] dataArray = {"data1", "data2", "data3"}; // Пример массива строк
//                        sendArrayData(chatId, dataArray);
//                    }
//            }
//        }
//    }

//    public void onUpdateReceived(Update update) {
//        if (update.hasMessage()) {
//            Message message = update.getMessage();
//            Long chatId = message.getChatId(); // Получаем chat ID пользователя
//            // Дальше можно использовать chatId для отправки сообщения обратно пользователю или для других действий.
//
//            // Пример вывода chat ID в консоль:
//            System.out.println("Chat ID: " + chatId);
//        }
//    } //@todo получить чат ID  по сообщению от пользователя

// Метод, отвечающий за обработку полученного сообщения.

//    @Override
//    public void onUpdateReceived(Update update) {
//        if (update.hasMessage()) {
//            Long chatId = update.getMessage().getChatId(); // Получить chatId
//
//            if (isUserAllowed(chatId)) {
//                String[] dataArray = {"data1", "data2", "data3"}; // Пример массива строк
//                sendArrayData(chatId, dataArray);
//            }
//        }
//    }


// Метод для отправки массива строк пользователю
//    private void sendArrayData(Long chatId, List<String> dataArray) {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setText(String.join("\n", dataArray));
//        sendMessage.setChatId(chatId);
//        try {
//            execute(sendMessage); // Синхронное выполнение для простоты
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }

// Метод для отправки массива строк всем разрешенным пользователям
//    public void sendArrayDataToAll(List<String> dataArray) {
//        List<Long> allowedChatIds = getAllowedChatIds(); // Получаем список разрешенных chatId
//
//        for (Long chatId : allowedChatIds) {
//            sendArrayData(chatId, dataArray); // Отправляем массив строк каждому пользователю
//        }
//    }

//    private String getChatUsername(Long chatId) {
//        GetChatMember getChatMember = new GetChatMember();
//        getChatMember.setChatId(chatId.toString());
//        getChatMember.setUserId((long) chatId.intValue()); // Устанавливаем chatId в качестве userId
//
//        try {
//            ChatMember chatMember = execute(getChatMember);
//            User user = chatMember.getUser();
//            if (user != null) {
//                return user.getUserName(); // Возвращаем юзернейм пользователя
//            }
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//
//        return ""; // Если имя пользователя не найдено, возвращаем пустую строку
//    }



// Точка входа (метод main, в котором происходит создание и инициализация бота
//    public static void main(String[] args) throws IOException {
//        try {
//            Bot bot = new Bot();
//            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//            telegramBotsApi.registerBot(bot);
//        } catch (TelegramApiException e) {
//            throw new RuntimeException(e);
//        }
//
//        Bot bot = new Bot();
//        bot.sendArrayDataToAll(new String[]{"data1", "data2", "data3"});
//
//
//        System.out.println("Логика бота стартанула");
//
//
//    }