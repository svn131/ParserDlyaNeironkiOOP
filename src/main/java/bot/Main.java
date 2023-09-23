package bot;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONObject;

import java.io.PrintStream;
import java.util.*;


public class Main {

    {PrintStream out = new PrintStream(new FileOutputStream("C:/logOutput.txt"));
       System.setOut(out);}

    WriteResultTxt writeResultTxt = new WriteResultTxt();

    MyConnection myConnection = new MyConnection();

    List<Igra> listIgr = new ArrayList<>();


    ExecutorClass executorClass = new ExecutorClass(myConnection.getObrabotkaSsylok(),listIgr);


    int min = 3000; // от 5000 до 10000 перед новым циклом от 5 с до 10 с
    int max = 5000;


    public Main() throws FileNotFoundException {
    }


    public static void main(String[] args) throws IOException, InterruptedException, TelegramApiException {

        Main main = new Main(); // Вынести логику конекта в отдельный класс

        main.run();


    }

    public void run() throws IOException, InterruptedException, TelegramApiException {



        System.out.println("Логика бота стартанула");

        writeResultTxt.tempWriteOtladka(); //


        while (true) {


            try {

                JSONObject jsonObject = myConnection.connectIgetJson();

                executorClass.processJson(jsonObject);

                myConnection.disconect();

            } catch (java.net.ConnectException e) {
                System.out.println("Ошибка подключения: " + e.getMessage());
                // Ожидание 1 минут перед повторной попыткой отправки запроса в случае  400 ошибки
                int sleepTime = 1 * 60 * 1000; // 1 минута в миллисекундах
                Thread.sleep(sleepTime);
            }



            for (Igra igra : listIgr) {
                System.out.println(igra.toString());
            }


            signal(); // проверяем если что то есть интерестное будет сигнал если есть результат будет результат





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

            System.out.println("SSSSSSSSSSSignalMetod igratime " + igra.time + " igraTotal na vtoroi minute =  " + igra.masivMinut[0].totalOne + "  predlagaemuy total na 8oy " + igra.masivMinut[6].totalTwo);

            if (igra.zamokWrite) {
                writeResultTxt.writeResultTxt(igra);
                igra.samounochtogitel = System.currentTimeMillis();
                igra.zamokWrite=false;
                igra.zamokResult = false;



            }
           else if (igra.samounochtogitel+ 480_000 < System.currentTimeMillis()) { // самоуничожение чеоез 8 минуты
                igraIterator.remove();
            }


        }
    }
}


