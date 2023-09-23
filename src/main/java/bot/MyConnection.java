package bot;

import org.brotli.dec.BrotliInputStream;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import org.json.JSONException;




public class MyConnection {


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



//    Exception in thread "main" org.json.JSONException: A JSONObject text must begin with '{' at 0 [character 1 line 1]
//    at org.json.JSONTokener.syntaxError(JSONTokener.java:501)
//    at org.json.JSONObject.<init>(JSONObject.java:208)
//    at org.json.JSONObject.<init>(JSONObject.java:404)
//    at bot.MyConnection.connectIgetJson(MyConnection.java:59)
//    at bot.Main.run(Main.java:63)
//    at bot.Main.main(Main.java:44)


    public void disconect() {
        con.disconnect();
    }

    public ObrabotkaSsylok getObrabotkaSsylok() {
        return obrabotkaSsylok;
    }
}

//
//public class MyConnection {
//
//
//    String url = "https://1xstavka.ru/LiveFeed/Get1x2_VZip?sports=3&count=50&antisports=188&mode=4";
//    String queryString = "&country=1&partner=51&getEmpty=true&noFilterBlockEvent=true";
//    ObrabotkaSsylok obrabotkaSsylok = new ObrabotkaSsylok();
//    HttpURLConnection con;
//
//
//    public JSONObject connectIgetJson() throws IOException {
//
//        URL obj = new URL(url + obrabotkaSsylok.getSsilka() + queryString);
//        con = (HttpURLConnection) obj.openConnection(); // Устаавливаем конект с нужным сеером(страницей)
//
//        // Set request method and headers
//        con.setRequestMethod("GET");
//        con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//
//        // Get the server response
//        int responseCode = con.getResponseCode();
//        System.out.println("Server Response: " + responseCode);
//
//
//        StringBuilder response = new StringBuilder();
//        // Read the server response
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            InputStream inputStream = con.getInputStream(); //  открывает поток входных данных (InputStream) из объекта HttpURLConnection
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//            String line;
//            // добавляем ответ от сервера джейсон  в стрингбилдер
//
//            while ((line = reader.readLine()) != null) {
//                response.append(line);
//            }
//            reader.close();
//
//
//        } else {
//            System.out.println("Failed to get server response");
//        }
//
//        System.out.println(response.toString());
//        return new JSONObject(response.toString());// Поэкперементировать вывести в консоль - посмотреть как выглядит разделитель
//    }
//
//
//
//    public void disconect() {
//        con.disconnect();
//    }
//
//    public ObrabotkaSsylok getObrabotkaSsylok() {
//        return obrabotkaSsylok;
//    }
//}
