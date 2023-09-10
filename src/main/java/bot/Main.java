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


//@todo доработь кефсредний допустим 1.8 тотал хороший 55  - значит мы делаем выше 5 минут и тотал умножить на кефф >= 99 - это наше число кторое задам как сейчас общий тотал - потому что букмекер может игграть что тоталом то кефом. А так же добавить наше число 55+(текущее время-300наша планка)/60*11 а затем также умножить на 1.8    --> меньше или равно результато тотала на ставку
//@todo поправить баг с четвиртями когда время идет как с 1ой

public class Main {

    ObrabotkaSsylok obrabotkaSsylok = new ObrabotkaSsylok();

    Double balance = 10000.0;
    int naOdnuIgru = 1000;


    String[] temp = new String[300]; // 200
    int cursorTemp = 0;


    int totalObshyi = 20; // 30
    int time = 300; // 300 это пять минут


    Map<Integer, double[]> mapaCefov = new HashMap<>();
    Map<String, double[]> mapFixedStavka = new HashMap<>();


    // логика неповторений если на 3х минутах тотал совпал
    static List<String> listTehktoBil = new ArrayList<>(); // переделать с дата тайм

    static int shethikPoGlavnomyCiclu = 0; // переделать с дата тайм
    // логика неповторений если на 3х минутах тотал совпал

    List<String> resultat = new ArrayList<>();  //
    //int resulTime = 1500; // 15 минут проверка результата ставки
    int resulTime = 500; // 15 минут проверка результата ставки время в сек.

    Bot bot = new Bot();
    SignalClass signalClass = new SignalClass();

    int min = 5000; // от 5000 до  10000 перед новым циклом от 5 с до 10 с
    int max = 10000;

    public static void main(String[] args) throws IOException, InterruptedException {

        Main main = new Main(); // Вынести логику конекта в отдельный класс

        main.run();


    }

    public void run() throws IOException, InterruptedException {

        try {

            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Логика бота стартанула");

        String url = "https://1xstavka.ru/LiveFeed/Get1x2_VZip?sports=3&count=50&antisports=188&mode=4";
        String queryString = "&country=1&partner=51&getEmpty=true&noFilterBlockEvent=true";


        while (true) {


            try {

                URL obj = new URL(url + obrabotkaSsylok.getSsilka() + queryString);
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
                    StringBuilder response = new StringBuilder();  // добавляем ответ от сервера джейсон  в сингбилдер

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    //@todo обратить внимание выше и ниже действия по коментам вроде взаимно уничтожаемые
                    try {
                        JSONObject jsonObject = new JSONObject(response.toString());// привращаем этот стринг билдер в джейсон


                        List<List<String>> result = processJson(jsonObject); // ложим в наш метод и получаем лист листов с играми
                        for (List<String> innerList : result) {
                            for (String value : innerList) {
                                System.out.println(value);           //@todo обратить внимание выше и ниже действия по коментам вроде взаимно уничтожаемые
                                temp[cursorTemp] = value;
                                cursorTemp++;                 // переводим все просто в массив
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

                // Ожидание 1 минут перед повторной попыткой отправки запроса в случае  400 ошибки
                int sleepTime = 1 * 60 * 1000; // 10 минут в миллисекундах
                Thread.sleep(sleepTime);
            }
            System.out.println(Arrays.toString(temp));
            signal(); // проверяем если что то есть будет сигнал если есть результат будет результат


            System.out.println("Hello world!");


            Random random = new Random();
            int randomNumber = random.nextInt(max - min + 1) + min; // зависти от наших пееменных
            Thread.sleep(randomNumber);


            temp = new String[200];
            cursorTemp = 0;


        }
    }


    public List<List<String>> processJson(JSONObject jsonObject) {

        List<List<String>> allValues = new ArrayList<>();

        JSONArray valueList = jsonObject.optJSONArray("Value");
        if (valueList != null && valueList.length() > 0) {
            for (int j = 0; j < valueList.length(); j++) {
                JSONObject gameObj = valueList.getJSONObject(j); // получаем из джейсона валью - там был лист джейсонов вот получаем с него каждый раз по одному джейсону с игрой
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


                    System.out.println(gameObj.toString());

                    System.out.println("V=================================================");
//                    System.out.println(extractPCObject(gameObj));
                    System.out.println("/|======================================================");
                    values.add(String.valueOf(extractSerialKeyGame(gameObj)));
                    extractEArray2(gameObj);


                }

                allValues.add(values);

                Integer ssilka = gameObj.getInt("I");
                obrabotkaSsylok.podgotovkaUrl(ssilka);
            }
        }

        return allValues;
    }


    public Double stavka(Double kef, Double total, int total12, int naOdnuIgru) {
        if (total12 < total) {
            return naOdnuIgru * kef;
        } else {
            return 0.0;
        }

    }

    public int extractSerialKeyGame(JSONObject json) {
        int seriinikIgry = json.getJSONArray("O2IS").optInt(0, 0); // @todo проверить чтобы не было исключений
        return seriinikIgry;
    }

    public void extractEArray2(JSONObject json) {
        int seriinikIgry = json.getJSONArray("O2IS").optInt(0, 0);

        try {
            JSONArray sgArray = json.getJSONArray("SG");

            for (int i = 0; i < sgArray.length(); i++) {
                JSONObject sgObject = sgArray.getJSONObject(i);

                JSONArray eArray = sgObject.getJSONArray("E");
                System.out.println("Массив E: " + eArray);

                for (int j = 0; j < eArray.length(); j++) {
                    JSONObject eObject = eArray.getJSONObject(j);


                    double c = eObject.getDouble("C");
                    int g = eObject.getInt("G");
                    int t = eObject.getInt("T");
                    double p = eObject.optDouble("P", 0.0);

                    // Обработка элемента массива E
                    if (p > 30.0 && p < 100.0 && t == 9 && g == 17) { //@todo тонкая настройка бота
                        System.out.println("Найден подходящий элемент:");

                        System.out.println("C: " + c);
                        System.out.println("G: " + g);
                        System.out.println("T: " + t);
                        System.out.println("P: " + p);


                        for (Map.Entry<Integer, double[]> entry : mapaCefov.entrySet()) {

                            Integer key = entry.getKey();
                            double[] value = entry.getValue();
                            System.out.println("Ключ: " + key);
                            System.out.println("Значение: " + Arrays.toString(value));
                        }
                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");


                        double temp[] = {p, c}; // тотал кеф
                        mapaCefov.put(seriinikIgry, temp); //@todo пусть перезаписываеться потсоянно так как точно не известно когда сработает сигнал и должны быть актуальные значения, что бы на момент ставки помнить значения запишем их отдельно в методе сигнал в другую переменную - ыяснить нужно ли добавить логику что когда "" или нулл - бывают ли такие ситуации если да -то ими не затиирать


                    }
                }
            }
        } catch (JSONException e) {
            System.out.println("В метода extractEArray2 не оказалось чегото из SG , E ,C , G, T, P");
        }
    }


    public void signal() throws InterruptedException {

        for (int i = 0; temp[i] != null; i += 6) {

            double[] cefIstvkaarr = mapaCefov.get(Integer.parseInt(temp[i + 5])); // извлекаем кеф и ставку по серийнику
            System.out.println("Серийник запрос" + temp[i + 5]);

            if (cefIstvkaarr != null && Integer.parseInt(temp[i + 2]) < time && Integer.parseInt(temp[i + 3]) + Integer.parseInt(temp[i + 4]) > totalObshyi && !mapFixedStavka.containsKey(temp[i + 5])) { // если в мапе спели прочитаться значения кефов и тоталов и у нас время меньше заложенных 5 минут и тотал 1 и тотал второй  больще нашего значния тоталОбщий(сигнал) и по команде 1(теперь сирийнику) нету такого в листе тех кто то уже обрабатывался в теченни трех минут то

                signalClass.signalStavki(temp, i, cefIstvkaarr,mapFixedStavka);


            }
            if (Integer.parseInt(temp[i + 2]) > resulTime && mapFixedStavka.containsKey(temp[i + 5])) { // если время уже более того например 15 мин когда пора проверить результат и в листе результатов есть такая команда1 у которой стоит проверить результат то

                signalClass.signalResulta(temp, i, mapFixedStavka,balance,naOdnuIgru);

            }
        }

        }






    }



