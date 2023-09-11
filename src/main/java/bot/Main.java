package bot;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

import org.json.JSONObject;

import java.util.*;

// плохо что пол допы не тут не вглавном классе но его помжно получить и через ексеуторс класс гетор или через поле. Ексекутос же заинжекчен
//@todo доработь кефсредний допустим 1.8 тотал хороший 55  - значит мы делаем выше 5 минут и тотал умножить на кефф >= 99 - это наше число кторое задам как сейчас общий тотал - потому что букмекер может игграть что тоталом то кефом. А так же добавить наше число 55+(текущее время-300наша планка)/60*11 а затем также умножить на 1.8    --> меньше или равно результато тотала на ставку
//@todo поправить баг с четвиртями когда время идет как с 1ой

public class Main {

    MyConnection myConnection = new MyConnection();

    Double balance = 10000.0;
    int naOdnuIgru = 1000;


    String[] temp = new String[300]; // 200

    int totalObshyiDlyaSignla = 10; // 30 ...............................
    //    int time = 300; // 300 это пять минут
    int timeSignalDo = 300; // отладка выше правильнее.........................

    Map<Integer, double[]> mapaCefov = new HashMap<>();
    Map<String, double[]> mapFixedStavka = new HashMap<>();


    int resulTime = 590; // 10 минут проверка результата ставки время в сек. от начала игры  сравниваетьсяс текущим если более то выводит результат

    Bot bot = new Bot();
    SignalClass signalClass = new SignalClass();
    ExecutorClass executorClass = new ExecutorClass(myConnection.getObrabotkaSsylok(), mapaCefov);
    MapperJsonVsArray mapperJsonVsArray = new MapperJsonVsArray(executorClass);

    int min = 5000; // от 5000 до  10000 перед новым циклом от 5 с до 10 с
    int max = 10000;


    public static void main(String[] args) throws IOException, InterruptedException, TelegramApiException {

        Main main = new Main(); // Вынести логику конекта в отдельный класс

        main.run();


    }

    public void run() throws IOException, InterruptedException, TelegramApiException {

        myConnection.myregisterBot();

        System.out.println("Логика бота стартанула");


        while (true) {


            try {

                JSONObject jsonObject = myConnection.connectIgetJson();

               temp = mapperJsonVsArray.mapJsonToArray(jsonObject, temp);// ложим в мапер наш джейсон и получаем ужет олько нужные поля в масиве в  локалный темп

                myConnection.disconect();

            } catch (java.net.ConnectException e) {
                System.out.println("Ошибка подключения: " + e.getMessage());
                // Ожидание 1 минут перед повторной попыткой отправки запроса в случае  400 ошибки
                int sleepTime = 1 * 60 * 1000; // 10 минут в миллисекундах
                Thread.sleep(sleepTime);
            }

            System.out.println(Arrays.toString(temp));
            signal(); // проверяем если что то есть интерестное будет сигнал если есть результат будет результат


            System.out.println("Hello world!");


            Random random = new Random();
            int randomNumber = random.nextInt(max - min + 1) + min; // зависит от наших переменных max min в этом класе 5000 1000 от 5 до 10 сек. на накждый запрос
            Thread.sleep(randomNumber);

            String[] temp = new String[300];

            System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");

            for (Map.Entry<Integer, double[]> entry : mapaCefov.entrySet()) {
                Integer key = entry.getKey();
                double[] value = entry.getValue();
                System.out.println(key + ": " + value);
            }

            if (mapaCefov == null){
                System.out.println("Mapa nullllllllllllll");
            }
        }
    }


    public void signal() throws InterruptedException {

        for (int i = 0; temp[i] != null; i += 6) {

            double[] cefIstvkaarr = mapaCefov.get(Integer.parseInt(temp[i + 5])); // извлекаем кеф и ставку по серийнику
            System.out.println("Серийник запрос " + temp[i + 5] + " Cчет 1й и 2й " + temp[i + 3] + " " + temp[i + 4]);


            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

            System.out.println(cefIstvkaarr != null);
            System.out.println(Integer.parseInt(temp[i + 2]) < timeSignalDo );
            System.out.println(Integer.parseInt(temp[i + 3]) + Integer.parseInt(temp[i + 4]) > totalObshyiDlyaSignla);
            System.out.println(!mapFixedStavka.containsKey(temp[i + 5]));

            if (cefIstvkaarr != null && Integer.parseInt(temp[i + 2]) < timeSignalDo && Integer.parseInt(temp[i + 3]) + Integer.parseInt(temp[i + 4]) > totalObshyiDlyaSignla && !mapFixedStavka.containsKey(temp[i + 5])) { // если в мапе спели прочитаться значения кефов и тоталов и у нас время меньше заложенных 5 минут и тотал 1 и тотал второй  больще нашего значния тоталОбщий(сигнал) и по команде 1(теперь сирийнику) нету такого в мапе кефов то
                System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
                signalClass.signalStavki(temp, i, cefIstvkaarr, mapFixedStavka, bot);


            }
            if (Integer.parseInt(temp[i + 2]) > resulTime && mapFixedStavka.containsKey(temp[i + 5])) { // если время уже более того например 15 мин когда пора проверить результат и в фикседмапе(фиксация ставки) есть такая команда1 у которой стоит проверить результат то
                System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
                balance = signalClass.signalResulta(temp, i, mapFixedStavka, balance, naOdnuIgru, bot);

            }
        }

    }


}


/////////////////////////////////////////////////////////////////////////////////////////////


//    public void run() throws IOException, InterruptedException {
//        startBot();
//        System.out.println("Логика бота стартанула");
//
//        String url = "https://1xstavka.ru/LiveFeed/Get1x2_VZip?sports=3&count=50&antisports=188&mode=4";
//        String queryString = "&country=1&partner=51&getEmpty=true&noFilterBlockEvent=true";
//
//        while (true) {
//            try {
//                processRequestAndResponse(url, queryString);
//                signal();
//                System.out.println("Hello world!");
//                randomSleep();
//                resetTempArray();
//            } catch (java.net.ConnectException e) {
//                handleConnectException(e);
//            }
//        }
//    }
//
//    private void startBot() throws TelegramApiException {
//        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//        telegramBotsApi.registerBot(bot);
//    }
//
//    private void processRequestAndResponse(String url, String queryString) throws IOException {
//        URL obj = new URL(url + obrabotkaSsylok.getSsilka() + queryString);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//        try {
//            setRequestProperties(con);
//            int responseCode = con.getResponseCode();
//            System.out.println("Server Response: " + responseCode);
//
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                String response = readServerResponse(con.getInputStream());
//                JSONObject jsonObject = new JSONObject(response);
//                List<List<String>> result = processJson(jsonObject);
//
//                for (List<String> innerList : result) {
//                    for (String value : innerList) {
//                        System.out.println(value);
//                        temp[cursorTemp] = value;
//                        cursorTemp++;
//                    }
//                }
//            } else {
//                System.out.println("Failed to get server response");
//            }
//        } finally {
//            con.disconnect();
//        }
//    }
//
//    private void setRequestProperties(HttpURLConnection con) {
//        con.setRequestMethod("GET");
//        con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//    }
//
//    private String readServerResponse(InputStream inputStream) throws IOException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        StringBuilder response = new StringBuilder();
//        String line;
//
//        while ((line = reader.readLine()) != null) {
//            response.append(line);
//        }
//        reader.close();
//        return response.toString();
//    }
//
//    private void resetTempArray() {
//        temp = new String[200];
//        cursorTemp = 0;
//    }
//
//    private void randomSleep() throws InterruptedException {
//        Random random = new Random();
//        int randomNumber = random.nextInt(max - min + 1) + min;
//        Thread.sleep(randomNumber);
//    }
//
//    private void handleConnectException(java.net.ConnectException e) throws InterruptedException {
//        System.out.println("Ошибка подключения: " + e.getMessage());
//
//        int sleepTime = 1 * 60 * 1000; // 1 минута в миллисекундах
//        Thread.sleep(sleepTime);
//    }
