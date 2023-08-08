package bot;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * WeatherBot by @senior_javist (Telegram Channel)
 */
public class Weather {
    private String weatherText;
    private String APIkey = ""; // Сюда требуется вствавить API ключ сайта "openweathermap.org"

    // Метод для подключения к Web-странице и получения с неё данных.
    public String getUrlContent(String urlAddress) {
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlAddress);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            } // Считываем содержимое страницы построчно и добавляем его в объект StringReader
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Город не найден!");
        }

        return content.toString(); // Получение строки с данными.
    }

    // Метод, который получает текущую погоду в указанном городе.
    public String getWeather(String city) {
        // Делаем запрос к OpenWeatherMap. Возвращаемое значение метода getUrlContent() присваивается строке output.
        String output = getUrlContent(
                "https://api.openweathermap.org/data/2.5/weather?q="
                        + city
                        + "&appid=" + APIkey + "&units=metric"
        );

        /**
         * Если output не пустой, то создаём json объект из строки и извлекаем из нее
         * необходимые данные. В конце возвращаем данные в текстовом формате.
         */
        if (!output.isEmpty()) {
            JSONObject object = new JSONObject(output);
            weatherText = "Погода в городе " + city + ":"
                    + "\n\nТемпература: " + object.getJSONObject("main").getDouble("temp")
                    + "\nОщущается: " + object.getJSONObject("main").getDouble("feels_like")
                    + "\nВлажность: " + object.getJSONObject("main").getDouble("humidity")
                    + "\nДавление: " + object.getJSONObject("main").getDouble("pressure");
        }
        return weatherText;
    }
}
