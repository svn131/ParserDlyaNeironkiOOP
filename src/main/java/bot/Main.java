package bot;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

import org.json.JSONObject;

import java.util.*;


public class Main {

    WriteResultTxt writeResultTxt = new WriteResultTxt();

    MyConnection myConnection = new MyConnection();


    Map<Integer, double[]> mapaCefov = new HashMap<>();

    ExecutorClass executorClass = new ExecutorClass(myConnection.getObrabotkaSsylok(), mapaCefov);
    MapperJsonVsArray mapperJsonVsArray = new MapperJsonVsArray(executorClass);

    int min = 5000; // от 5000 до  10000 перед новым циклом от 5 с до 10 с
    int max = 10000;


    List<Igra> listIgr = new ArrayList<>();


    public static void main(String[] args) throws IOException, InterruptedException, TelegramApiException {

        Main main = new Main(); // Вынести логику конекта в отдельный класс

        main.run();


    }

    public void run() throws IOException, InterruptedException, TelegramApiException {

//        myConnection.myregisterBot();

        System.out.println("Логика бота стартанула");


        while (true) {


            try {

                JSONObject jsonObject = myConnection.connectIgetJson();

                mapperJsonVsArray.mapJsonToArray(jsonObject, listIgr);// ложим в мапер наш джейсон и получаем ужет олько нужные поля в масиве в  локалный темп

                myConnection.disconect();

            } catch (java.net.ConnectException e) {
                System.out.println("Ошибка подключения: " + e.getMessage());
                // Ожидание 1 минут перед повторной попыткой отправки запроса в случае  400 ошибки
                int sleepTime = 1 * 60 * 1000; // 10 минут в миллисекундах
                Thread.sleep(sleepTime);
            }

            for (Igra igra : listIgr) {
                System.out.println(igra.toString());
            }


            signal(); // проверяем если что то есть интерестное будет сигнал если есть результат будет результат


            System.out.println("Hello world!");


            Random random = new Random();
            int randomNumber = random.nextInt(max - min + 1) + min; // зависит от наших переменных max min в этом класе 5000 1000 от 5 до 10 сек. на накждый запрос
            Thread.sleep(randomNumber);


            System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");

        }
    }


    public void signal()  {

        for (Igra igra : listIgr) {
            // todo если играт айм нулл ? добавить ?

            if (igra.time / 60 > 12) {
                writeResultTxt.writeResultTxt(igra);
                listIgr.remove(igra);
            }

        }

    }

}

