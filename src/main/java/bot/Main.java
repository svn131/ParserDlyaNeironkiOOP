package bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.time.Duration;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;

public class Main {

    static String[] temp = new String[500];
    static int coursor = 0;

    static int totalObshyi = 25; // 30
    static int time = 300; // 300 это пять минут
static Bot bot = new Bot();

    public static void main(String[] args) throws IOException, InterruptedException {
        try {
//            Bot bot = new Bot();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Логика бота стартанула");



            String url = "https://1xstavka.ru/LiveFeed/Get1x2_VZip";
            String queryString = "sports=3&count=50&antisports=188&mode=4&country=1&partner=51&getEmpty=true&noFilterBlockEvent=true";

            while (true) {
                try {
                    URL obj = new URL(url + "?" + queryString);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                    // Set request method and headers
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

                    // Get the server response
                    int responseCode = con.getResponseCode();
                    System.out.println("Server Response: " + responseCode);

                    // Read the server response
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = con.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        String line;
                        StringBuilder response = new StringBuilder();

                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            printJsonStructure(jsonObject, "", false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Failed to get server response");
                    }

                    con.disconnect();
                } catch (java.net.ConnectException e) {
                    System.out.println("Ошибка подключения: " + e.getMessage());

                    // Ожидание 10 минут перед повторной попыткой отправки запроса
                    int sleepTime = 10 * 60 * 1000; // 10 минут в миллисекундах
                    Thread.sleep(sleepTime);
                }
                System.out.println(Arrays.toString(temp));
                signal();
                coursor = 0;

                temp = new String[200];


                System.out.println("Hello world!");

                int min = 60000;
                int max = 100000;

                Random random = new Random();
                int randomNumber = random.nextInt(max - min + 1) + min;


                Thread.sleep(randomNumber);

            }
        }


        private static void printJsonStructure (JSONObject jsonObject, String indent,boolean shouldSkip) throws
        JSONException {
            if (jsonObject.has("SC")) {
                JSONObject scObject = jsonObject.getJSONObject("SC");
                if (scObject.has("TS")) {
                    int tsValue = scObject.optInt("TS", 0);
                    shouldSkip = false;
                    temp[coursor] = String.valueOf(tsValue);
                    System.out.println("Значение ключа 'TS': " + tsValue);
                    System.out.println("1-------------------------------------- " + coursor);
                    coursor++;
                } else {
                    // Если отсутствует родительский JSON "SC"
                    temp[coursor] = "0";
                    System.out.println("2-------------------------------------- " + coursor);
                    coursor++;
                    shouldSkip = false;
                }


            }

            if (!shouldSkip) {
                for (String key : jsonObject.keySet()) {
                    if (key.equals("O1") || key.equals("O2")) {
                        temp[coursor] = jsonObject.get(key).toString();
                        System.out.println("3-------------------------------------- " + coursor);
                        coursor++;
                        System.out.println("Значение ключа '" + key + "': " + jsonObject.get(key));
                    } else if (key.equals("PS")) {
                        JSONArray psArray = jsonObject.getJSONArray(key);
                        System.out.println("Значения ключа 'PS' " + jsonObject.get(key));

                        for (int i = 0; i < psArray.length(); i++) {
                            JSONObject psObject = psArray.getJSONObject(i);
                            JSONObject valueObject = psObject.getJSONObject("Value");

                            // Добавьте код вывода времени
                            String time = valueObject.optString("T", "");
                            if (!time.isEmpty()) {
                                System.out.println("Время: " + time);
                            }

                            String nfValue = valueObject.optString("NF", "");
                            int s1Value = valueObject.optInt("S1", 0);
                            int s2Value = valueObject.optInt("S2", 0);

                            if (!nfValue.isEmpty() && s1Value != -1 && s2Value != -1) {
                                if (nfValue.equals("1-я Четверть")) {
                                    temp[coursor] = String.valueOf(s1Value);
                                    System.out.println("4-------------------------------------- " + coursor);
                                    coursor++;
                                    temp[coursor] = String.valueOf(s2Value);
                                    System.out.println("5-------------------------------------- " + coursor);
                                    coursor++;
                                    temp[coursor] = nfValue;
                                    System.out.println("6-------------------------------------- " + coursor);
                                    coursor++;
                                    System.out.println("Значение S1: " + s1Value);
                                    System.out.println("Значение S2: " + s2Value);
                                }
                            }
                        }
                    }

                    Object value = jsonObject.get(key);
                    if (value instanceof JSONObject) {
                        printJsonStructure((JSONObject) value, indent + "  ", shouldSkip);
                    } else if (value instanceof JSONArray) {
                        JSONArray jsonArray = (JSONArray) value;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Object arrayValue = jsonArray.get(i);
                            if (arrayValue instanceof JSONObject) {
                                printJsonStructure((JSONObject) arrayValue, indent + " ", shouldSkip);
                            }
                        }
                    }
                }
            }
        }

        public static void signal () throws InterruptedException {
            String[] signalArr = new String[6];
            for (int i = 3; temp[i] != null; i += 6) {


                System.out.println(temp[i]);
                System.out.println(temp[i + 1]);
//            System.out.println(temp[i + 2]); // четверть
//            System.out.println(temp[i - 1]); //команда2
//            System.out.println(temp[i - 2]); //команда1
//            System.out.println(temp[i - 3]); // время

                if (Integer.parseInt(temp[i - 3]) < time && Integer.parseInt(temp[i]) + Integer.parseInt(temp[i]) > totalObshyi) {

                    signalArr[0] = ("Команда 1 " + (temp[i - 2]));
                    signalArr[1] = ("Команда 2 " + (temp[i - 1]));
                    signalArr[2] = ("Прошло время " + Integer.parseInt(temp[i - 3]) / 60 + " : " + Integer.parseInt(temp[i - 3]) % 60);
                    signalArr[3] = (temp[i + 2]);
                    signalArr[4] = ("Счет  1ой " + (temp[i + 1]));
                    signalArr[5] = ("Счет 2ой " + (temp[i]));


           bot.sendArrayDataToAll(signalArr);

           Thread.sleep(100);


                }
            }
        }
    }
