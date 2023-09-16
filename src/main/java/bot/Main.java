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

    int min = 2000; // от 5000 до 10000 перед новым циклом от 5 с до 10 с
    int max = 5000;


    List<Igra> listIgr = new ArrayList<>();


    public static void main(String[] args) throws IOException, InterruptedException, TelegramApiException {

        Main main = new Main(); // Вынести логику конекта в отдельный класс

        main.run();


    }

    public void run() throws IOException, InterruptedException, TelegramApiException {

//        myConnection.myregisterBot();

        System.out.println("Логика бота стартанула");

        writeResultTxt.tempWriteOtladka(); //


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




//todo сделать запрос ссылок раз в 40 секунд для каждой игры

    public void signal() {

        System.out.println("SSSSSSSSSSSignalMetod igratime peredlistom ");

        System.out.println(listIgr.isEmpty());

        Iterator<Igra> igraIterator = listIgr.iterator();
        while (igraIterator.hasNext()) {
            Igra igra = igraIterator.next();


            // todo если играт тайм нулл ? добавить ?

            System.out.println("SSSSSSSSSSSignalMetod igratime " + igra.time + " igraTotal na pervoi minute =  " + igra.totalOne1 + "  predlagaemuy total na 8oy " + igra.predlagaemyiTotalNa8Min);

            if (igra.zamokWrite) {
                writeResultTxt.writeResultTxt(igra);
                igra.samounochtogitel = System.currentTimeMillis();
                igra.zamokWrite=false;

            }
           else if (igra.samounochtogitel+ 240000 < System.currentTimeMillis()) { // самоуничожение чеоез 4 минуты
                igraIterator.remove();
            }


        }
    }
}


//    public void signal()  {
//
//        System.out.println("SSSSSSSSSSSignalMetod igratime peredlistom " );
//
//        System.out.println(listIgr.isEmpty());
//        List<Igra> temListIgr = new ArrayList<>();
//
//        for (Igra igra : listIgr) {
//            // todo если играт айм нулл ? добавить ?
//
//            System.out.println("SSSSSSSSSSSignalMetod igratime " + igra.time+ " igraTotal na pervoi minute =  " + igra.totalOne1);
//
//            if (!igra.zamokResult) {
//                writeResultTxt.writeResultTxt(igra);
////                listIgr.remove(igra);
//                temListIgr.add(igra);
//            }
//
//        }
//
//
//        for(Igra igra: temListIgr){
//            listIgr.remove(igra);
//        }
//
//    }
//
//}

//todo  нам минуты вышепяти не нужны для обучения или даже выше например 6ти - а так же минута это сильно большой отрезок нужно мыслить 10 секундными отрезками - разобраться по какой конкретно ссылке приходит инфа именно на игру - и кидать именно их - а остальные у оторых более 6 минув игре и более 12 для прверок не кидать совсем - уменьшение ссылок будет посылать чаще нужные ссылки с ответом.
//todo а так же расширить число ячеек для тотала 59.5 очень маленькое значение. бывает и выше.