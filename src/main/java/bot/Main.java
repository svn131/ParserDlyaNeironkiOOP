package bot;

import org.json.JSONTokener;
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
import java.util.function.DoubleToIntFunction;


//@todo доработь кефсредний допустим 1.8 тотал хороший 55  - значит мы делаем выше 5 минут и тотал умножить на кефф >= 99 - это наше число кторое задам как сейчас общий тотал - потому что букмекер может игграть что тоталом то кефом. А так же добавить наше число 55+(текущее время-300наша планка)/60*11 а затем также умножить на 1.8    --> меньше или равно результато тотала на ставку
//@todo поправить баг с четвиртями когда время идет как с 1ой

public class Main {

    static ObrabotkaSsylok obrabotkaSsylok = new ObrabotkaSsylok();

    static Double balance = 10000.0;
    static int naOdnuIgru = 1000;


    static String[] temp = new String[300]; // 200
    static int cursorTemp = 0;


    static int totalObshyi = 20; // 30
    static int time = 300; // 300 это пять минут


    static Map<Integer, Double[]> mapaCefov = new HashMap<Integer, Double[]>();


    // логика неповторений если на 3х минутах тотал совпал
    static List<String> listTehktoBil = new ArrayList<>(); // переделать с дата тайм

    static int shethikPoGlavnomyCiclu = 0; // переделать с дата тайм
    // логика неповторений если на 3х минутах тотал совпал

    static List<String> resultat = new ArrayList<>();  //
//    static int resulTime = 1500; // 15 минут проверка результата ставки
    static int resulTime = 500; // 15 минут проверка результата ставки время в сек.

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


        String url = "https://1xstavka.ru/LiveFeed/Get1x2_VZip?sports=3&count=50&antisports=188&mode=4";

//        String url = "https://1xstavka.ru/LiveFeed/Get1x2_VZip?sports=3&count=50&antisports=188&mode=4&";
        String queryString = "&country=1&partner=51&getEmpty=true&noFilterBlockEvent=true";

//        String queryString = "sports=3&count=50&antisports=188&mode=4&country=1&partner=51&getEmpty=true&noFilterBlockEvent=true";

        while (true) {
            try {

//                System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF "+ obrabotkaSsylok.getSsilka() );
//                URL obj = new URL(url+"subGames="+ssilka+queryString);
//                URL obj = new URL("https://1xstavka.ru/LiveFeed/Get1x2_VZip?sports=3&count=50&antisports=188&mode=4&subGames=474717883%2C474717897%2C474720336%2C474720779%2C474722592&country=1&partner=51&getEmpty=true&noFilterBlockEvent=true");
//                URL obj = new URL("https://1xstavka.ru/LiveFeed/Get1x2_VZip?sports=3&count=50&antisports=188&mode=4&country=1&partner=51&getEmpty=true&noFilterBlockEvent=true");
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

            int min = 5000;
            int max = 10000;

            Random random = new Random();
            int randomNumber = random.nextInt(max - min + 1) + min;


            // логика неповторений если на 3х минутах тотал совпал
            shethikPoGlavnomyCiclu += randomNumber;
            if (shethikPoGlavnomyCiclu > 120000) { // через 2 минуты
                listTehktoBil = new ArrayList<>(); // чистит лист повторений
                shethikPoGlavnomyCiclu = 0;
            }
            // логика неповторений если на 3х минутах тотал совпал

            Thread.sleep(randomNumber);

            temp = new String[200];
            cursorTemp = 0;



        }
    }


    public static List<List<String>> processJson(JSONObject jsonObject) {

        List<List<String>> allValues = new ArrayList<>();

        JSONArray valueList = jsonObject.optJSONArray("Value");
        if (valueList != null && valueList.length() > 0) {
            for (int j = 0; j < valueList.length(); j++) {
                JSONObject gameObj = valueList.getJSONObject(j); // получаем из джейсона валью - там был лист джейсонов вот получаем с него каждый раз по одному джейсону с игрой
                List<String> values = new ArrayList<>();

//              String budushayaSsylka = gameObj.getString("I");

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


//                            if (s1+s2<2){
//                                ssilka = budushayaSsylka;
//                            }


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
                    values.add(String.valueOf(extractPCObject(gameObj)));
                    extractEArray2(gameObj);


                }

                allValues.add(values);
//                String ts = scObject.optString("TS", "0"); убрать черновик

//                JSONObject iObject = scObject.optJSONObject("I");
//              String ssilka = String.valueOf(gameObj.getInt("I")) ;  // @todo поменять логикметодов на интеджер для большей читаемости ипроизводительности
                Integer ssilka = gameObj.getInt("I");  //
//                String ssilka = (iObject != null) ? iObject.optString("I") : null;
//                String ssilka = iObject.optString("value");
                obrabotkaSsylok.podgotovkaUrl(ssilka);
            }
        }

        return allValues;
    }


    public static Double stavka(Double kef, Double total, int total12, int naOdnuIgru) {
        if (total12 < total) {
            return naOdnuIgru * kef;
        } else {
            return 0.0;
        }

    }

    public static int extractPCObject(JSONObject json) {


        int seriinikIgry = json.getJSONArray("O2IS").optInt(0, 0); // @todo проверить чтобы не было исключений


//        try {
//            JSONArray eArray = json.getJSONArray("E");
//
//            System.out.println("0000000000000000000000000000");
//            System.out.println("Массив E: " + eArray);
//            System.out.println("000000000000000000000000000");
//
//
//            for (int i = 0; i < eArray.length(); i++) {
//                JSONObject eObject = eArray.getJSONObject(i);
//
//
//
//                Double p = eObject.optDouble("P", 0.0);
////                    int ce = eObject.optInt("CE", -1);
//                    Double c = eObject.getDouble("C");
//                    int t = eObject.getInt("T");
//                    int g = eObject.getInt("G");
//
//
//                    if (p > 30 && p < 100  && t == 13 && g == 62) {
////                    if (p > 30 && p < 100  && t == 13 && g == 62 || p > 20 && p < 100  && t == 9 && g == 17) {
//                        System.out.println("sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
////                        return eObject;
//
//                        Double temp[] = {p,c}; // тотал кеф
//
//                        mapaCefov.put(seriinikIgry,temp); //@todo добавить еще датутайм и добавить в майн мханизм отчиски техкому более 30 минут.
//
//                        for (Map.Entry<Integer, Double[]> entry : mapaCefov.entrySet()) {
//                            Integer key = entry.getKey();
//                            Double[] value = entry.getValue();
//                            System.out.println("Ключ: " + key);
//                            System.out.println("Значение: " + Arrays.toString(value));
//                        }
//                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//                    }
//                }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        return seriinikIgry;
    }

    public static void extractEArray2(JSONObject json) {
        int seriinikIgry = json.getJSONArray("O2IS").optInt(0, 0);

        try {
            JSONArray sgArray = json.getJSONArray("SG");

            for (int i = 0; i < sgArray.length(); i++) {
                JSONObject sgObject = sgArray.getJSONObject(i);

                JSONArray eArray = sgObject.getJSONArray("E");
                System.out.println("Массив E: " + eArray);

                for (int j = 0; j < eArray.length(); j++) {
                    JSONObject eObject = eArray.getJSONObject(j);

//                    boolean b = eObject.getBoolean("B");
                    double c = eObject.getDouble("C");
                    int g = eObject.getInt("G");
                    int t = eObject.getInt("T");
                    double p = eObject.optDouble("P", 0.0);

                    // Обработка элемента массива E
                    if (p > 30.0 && p < 100.0 && t == 9 && g == 17) {
                        System.out.println("Найден подходящий элемент:");

                        System.out.println("C: " + c);
                        System.out.println("G: " + g);
                        System.out.println("T: " + t);
                        System.out.println("P: " + p);


                        for (Map.Entry<Integer, Double[]> entry : mapaCefov.entrySet()) {

                            Integer key = entry.getKey();
                            Double[] value = entry.getValue();
                            System.out.println("Ключ: " + key);
                            System.out.println("Значение: " + Arrays.toString(value));
                        }
                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");


                        Double temp[] = {p, c}; // тотал кеф

                        mapaCefov.put(seriinikIgry, temp); //@todo пусть перезаписываеться потсоянно так как точно не известно когда сработает сигнал и должны быть актуальные значения, что бы на момент ставки помнить значения запишем их отдельно в методе сигнал в другую переменную

                        System.out.println("---------------------------");

                        // Добавьте здесь код для дальнейшей обработки найденного элемента
                    }
                }
            }
        } catch (JSONException e) {
//            e.printStackTrace(); выводит непрятные на екране ошибки когда нету в ответе каких либо джейсонов
            System.out.println("В метода extractEArray2 не оказалось чегото из SG , E ,C , G, T, P");
        }
    }


    public static void signal() throws InterruptedException {
        String[] signalArr = new String[7];
        String[] resultArr = new String[7];
        for (int i = 0; temp[i] != null; i += 6) {


            if (Integer.parseInt(temp[i + 2]) < time && Integer.parseInt(temp[i + 3]) + Integer.parseInt(temp[i + 4]) > totalObshyi && !listTehktoBil.contains(temp[i])) {

                signalArr[0] = ("Команда 1 " + (temp[i]));
                signalArr[1] = ("Команда 2 " + (temp[i + 1]));
                signalArr[2] = ("Прошло время " + Integer.parseInt(temp[i + 2]) / 60 + " : " + Integer.parseInt(temp[i + 2]) % 60);
                signalArr[3] = ("Счет  1ой " + (temp[i + 3]));
                signalArr[4] = ("Счет 2ой " + (temp[i + 4]));


                Double[] cefIstvkaarr = mapaCefov.get(Integer.parseInt(temp[i + 5]));
                System.out.println("Серийник запрос" + temp[i + 5]);


                if (cefIstvkaarr != null) { //  страння ошибка больще чм один если убрать
                    System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
                    System.out.println("Серйник " + temp[i + 5]);
                    signalArr[5] = ("Коффцент тм тотал " + cefIstvkaarr[0]);
                    signalArr[6] = ("Ставка тотал м " + cefIstvkaarr[1]);
                } else {
                    signalArr[5] = ("00001");
                    signalArr[6] = ("00001");
                }
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

                    balance -= naOdnuIgru + stavka(Double.parseDouble(temp[i + 5]), Double.parseDouble(temp[i + 6]), total12, naOdnuIgru); // рачет финансов
                    System.out.println(balance);


                } else if (total12 > totalObshyi) {
                    System.out.println("---------------------------------------------------------------11000000000");
                    resultArr[0] = ("Команда 1 " + (temp[i]));
                    resultArr[1] = ("Команда 2 " + (temp[i + 1]));
                    resultArr[2] = ("Прошло время " + Integer.parseInt(temp[i + 2]) / 60 + " : " + Integer.parseInt(temp[i - 3]) % 60);
                    resultArr[3] = ("Общий тотал " + total12);
                    resultArr[4] = "Проиграла";

                    bot.sendArrayDataToAll(resultArr);
                    resultat.remove(temp[i]);


//                    balance -= naOdnuIgru + stavka(Double.parseDouble(temp[i + 5]),Double.parseDouble(temp[i + 6]),total12,naOdnuIgru); // рачет финансов
                    System.out.println("Баланс " + balance);
                }


            }
        }
    }


}
//
//    public static int extractPCObject(JSONObject json) {
//
//
//        int seriinikIgry = json.getJSONArray("O2IS").optInt(0,0); // @todo проверить чтобы не было исключений
//
//
//        try {
//            JSONArray eArray = json.getJSONArray("E");
//
//            System.out.println("0000000000000000000000000000");
//            System.out.println("Массив E: " + eArray);
//            System.out.println("000000000000000000000000000");
//
//
//            for (int i = 0; i < eArray.length(); i++) {
//                JSONObject eObject = eArray.getJSONObject(i);
//
//
//
//                Double p = eObject.optDouble("P", 0.0);
////                    int ce = eObject.optInt("CE", -1);
//                Double c = eObject.getDouble("C");
//                int t = eObject.getInt("T");
//                int g = eObject.getInt("G");
//
//
//                if (p > 20 && p < 100  && t == 13 && g == 62) {
//                    System.out.println("sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
////                        return eObject;
//
//                    Double temp[] = {p,c}; // тотал кеф
//
//                    mapaCefov.put(seriinikIgry,temp); //@todo добавить еще датутайм и добавить в майн мханизм отчиски техкому более 30 минут.
//
//                    for (Map.Entry<Integer, Double[]> entry : mapaCefov.entrySet()) {
//                        Integer key = entry.getKey();
//                        Double[] value = entry.getValue();
//                        System.out.println("Ключ: " + key);
//                        System.out.println("Значение: " + Arrays.toString(value));
//                    }
//                    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//                }
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return seriinikIgry;
//    }
//
//}


