package bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Main {

    Double balance = 0.0;

    static String[] temp = new String[200];
    static int cursorTemp = 0;


    static int totalObshyi = 25; // 30
    static int time = 300; // 300 это пять минут


    // логика неповторений если на 3х минутах тотал совпал
    static List<String> listTehktoBil = new ArrayList<>(); // переделать с дата тайм

    static int shethikPoGlavnomyCiclu = 0; // переделать с дата тайм
    // логика неповторений если на 3х минутах тотал совпал

    static List<String> resultat = new ArrayList<>();
    static int resulTime = 1500; // 15 минут проверка результата ставки

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


                        List<List<String>> result = processJson(jsonObject);
                        for (List<String> innerList : result) {
                            for (String value : innerList) {
                                System.out.println(value);
                                temp[cursorTemp] = value;
                                cursorTemp++;
                            }
                        }



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


            System.out.println("Hello world!");

            int min = 10000;
            int max = 30000;

            Random random = new Random();
            int randomNumber = random.nextInt(max - min + 1) + min;


            // логика неповторений если на 3х минутах тотал совпал
            shethikPoGlavnomyCiclu += randomNumber;
            if (shethikPoGlavnomyCiclu > 120000) {
                listTehktoBil = new ArrayList<>(); // чистит лист повторений
                shethikPoGlavnomyCiclu = 0;
            }
            // логика неповторений если на 3х минутах тотал совпал

            Thread.sleep(randomNumber);

            temp = new String[200];
            cursorTemp = 0;
        }
    }



    public static void signal() throws InterruptedException {
        String[] signalArr = new String[6];
        String[] resultArr = new String[7];
        for (int i = 0; temp[i] != null; i += 7) {


//            System.out.println(temp[i]); //команда1
//            System.out.println(temp[i + 1]); //команда2
//            System.out.println(temp[i + 2]); // время
//            System.out.println(temp[i + 3]); //счет команда1
//            System.out.println(temp[i + 4]); //счет команда2
//            System.out.println(temp[i + 5]); //коффицент тм
//            System.out.println(temp[i + 6]); //значения тм ставки


            if (Integer.parseInt(temp[i + 2]) < time && Integer.parseInt(temp[i + 3]) + Integer.parseInt(temp[i + 4]) > totalObshyi && !listTehktoBil.contains(temp[i])) {

                signalArr[0] = ("Команда 1 " + (temp[i]));
                signalArr[1] = ("Команда 2 " + (temp[i + 1]));
                signalArr[2] = ("Прошло время " + Integer.parseInt(temp[i + 2]) / 60 + " : " + Integer.parseInt(temp[i - 3]) % 60);
                signalArr[3] = ("Счет  1ой " + (temp[i + 3]));
                signalArr[4] = ("Счет 2ой " + (temp[i + 4]));
                signalArr[5] = ("Коффцент тм тотал " + (temp[i + 5]));
                signalArr[6] = ("Ставка тотал " + (temp[i + 6]));

                // логика неповторений если на 3х минутах тотал совпал - так же смотреть в иф выще
                listTehktoBil.add(temp[i]);
                shethikPoGlavnomyCiclu = 0; // передать на дата тайм
                // логика неповторений если на 3х минутах тотал совпал

                resultat.add(temp[i]);
                System.out.println("Добавлен в результат " + temp[i]);

                bot.sendArrayDataToAll(signalArr);

                Thread.sleep(100);

                // ниже логика вывода результата после 15 мин
            } else if (Integer.parseInt(temp[i + 2]) > resulTime && resultat.contains(temp[i])) {

                int total12 = Integer.parseInt(temp[i + 3]) + Integer.parseInt(temp[i + 4]);
                System.out.println("---------------------------------------------------------------9000000000");
                if (total12 <= totalObshyi) { // от знака зависит ставка-результат - тотал больше или меньше - продумать при тотал с десятичой дробью
                    System.out.println("---------------------------------------------------------------1000000000");
                    resultArr[0] = ("Команда 1 " + (temp[i]));
                    resultArr[1] = ("Команда 2 " + (temp[i + 1]));
                    resultArr[2] = ("Прошло время " + Integer.parseInt(temp[i + 2]) / 60 + " : " + Integer.parseInt(temp[i - 3]) % 60);
                    resultArr[3] = ("Общий тотал " + total12);
                    resultArr[4] = "ВЫИГРАЛА";

                    bot.sendArrayDataToAll(resultArr);
                    resultat.remove(temp[i]);

                } else if (total12 > totalObshyi) {
                    System.out.println("---------------------------------------------------------------11000000000");
                    resultArr[0] = ("Команда 1 " + (temp[i]));
                    resultArr[1] = ("Команда 2 " + (temp[i + 1]));
                    resultArr[2] = ("Прошло время " + Integer.parseInt(temp[i + 2]) / 60 + " : " + Integer.parseInt(temp[i - 3]) % 60);
                    resultArr[3] = ("Общий тотал " + total12);
                    resultArr[4] = "Проиграла";

                    bot.sendArrayDataToAll(resultArr);
                    resultat.remove(temp[i]);

                }


            }
        }
    }



    public static List<List<String>> processJson(JSONObject jsonObject) {
        List<List<String>> allValues = new ArrayList<>();

        JSONArray valueList = jsonObject.optJSONArray("Value");
        if (valueList != null && valueList.length() > 0) {
            for (int j = 0; j < valueList.length(); j++) {
                JSONObject gameObj = valueList.getJSONObject(j);
                List<String> values = new ArrayList<>();

                String o1 = gameObj.optString("O1", "0");
                String o2 = gameObj.optString("O2", "0");
                values.add(o1);
                values.add(o2);

                JSONObject scObject = gameObj.optJSONObject("SC");
                if (scObject != null) {
                    JSONArray psList = scObject.optJSONArray("PS");
                    if (psList != null) {
                        for (int i = 0; i < psList.length(); i++) {
                            JSONObject psObject = psList.getJSONObject(i);
                            String nf = psObject.optString("NF", "");
                            int s1 = psObject.optInt("S1", 0);
                            int s2 = psObject.optInt("S2", 0);

                            if ("1-я Четверть".equals(nf)) {
                                values.add(String.valueOf(s1));
                                values.add(String.valueOf(s2));
                                break;
                            }
                        }
                    }

                    String ts = scObject.optString("TS", "0");
                    values.add(ts);

                    JSONObject fsObject = scObject.optJSONObject("FS");
                    if (fsObject != null) {
                        int s1 = fsObject.optInt("S1", 0);
                        int s2 = fsObject.optInt("S2", 0);
                        values.add(String.valueOf(s1));
                        values.add(String.valueOf(s2));
                    }

                    JSONArray eArray = gameObj.optJSONArray("E");
                    if (eArray != null) {
                        for (int i = 0; i < eArray.length(); i++) {
                            JSONObject eObject = eArray.getJSONObject(i);
                            int t = eObject.optInt("T", 0);
                            if (t == 9) {
                                double c = eObject.optDouble("C", 0.0);
                                double p = eObject.optDouble("P", 0.0);
                                values.add(String.valueOf(c));
                                values.add(String.valueOf(p));
                                break;
                            }
                        }
                    }
                }

                allValues.add(values);
            }
        }

        return allValues;
    }


}









