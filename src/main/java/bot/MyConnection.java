package bot;


import org.json.JSONObject;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyConnection {

    Bot bot = new Bot();
    String url = "https://1xstavka.ru/LiveFeed/Get1x2_VZip?sports=3&count=50&antisports=188&mode=4";
    String queryString = "&country=1&partner=51&getEmpty=true&noFilterBlockEvent=true";
    ObrabotkaSsylok obrabotkaSsylok = new ObrabotkaSsylok();
    HttpURLConnection con;


    public JSONObject connectIgetJson() throws IOException {

        URL obj = new URL(url + obrabotkaSsylok.getSsilka() + queryString);
        con = (HttpURLConnection) obj.openConnection(); // Устаавливаем конект с нужным сеером(страницей)

        // Set request method and headers
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

        // Get the server response
        int responseCode = con.getResponseCode();
        System.out.println("Server Response: " + responseCode);


        StringBuilder response = new StringBuilder();
        // Read the server response
        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = con.getInputStream(); //  открывает поток входных данных (InputStream) из объекта HttpURLConnection
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            // добавляем ответ от сервера джейсон  в стрингбилдер

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();


        } else {
            System.out.println("Failed to get server response");
        }

        System.out.println(response.toString());
        return new JSONObject(response.toString());// Поэкперементировать вывести в консоль - посмотреть как выглядит разделитель
    }

    public void myregisterBot() throws TelegramApiException {

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }

    public void disconect() {
        con.disconnect();
    }

    public ObrabotkaSsylok getObrabotkaSsylok() {
        return obrabotkaSsylok;
    }
}
