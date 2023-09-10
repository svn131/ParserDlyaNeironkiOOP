package bot;

import org.fusesource.jansi.Ansi;

import java.util.*;

public class SignalTest {

    static Map<String, double[]> mapFixedStavka = new HashMap<>(); // @todo загадать загадку


    static double balance = 10000.0;
    static double naOdnuIgru = 1000;


    static String[] temp = new String[300]; // 200


    static int totalObshyi = 20; // 30
    static int time = 300; // 300 это пять минут


    static Map<Integer, double[]> mapaCefov = new HashMap<Integer, double[]>();


    // логика неповторений если на 3х минутах тотал совпал
    static List<String> listTehktoBil = new ArrayList<>(); // переделать с дата тайм

    static int shethikPoGlavnomyCiclu = 0; // переделать с дата тайм
    // логика неповторений если на 3х минутах тотал совпал

    static List<List<Double>> resultat = new ArrayList<>();  //

    static int resulTime = 500; // 15 минут проверка результата ставки время в сек.

    static Bot bot = new Bot();

    public static void main(String[] args) throws InterruptedException {

        temp[0] = "Атлетико Темперли";
        temp[1] = "Ставочка";
        temp[2] = "299";
        temp[3] = "24";
        temp[4] = "13";
        temp[5] = "1092325";
        mapaCefov.put(1092325, new double[]{1.8, 57.0});


        Thread.sleep(1000);
        signal();

        Thread.sleep(1000);
        System.out.println("-----------------------------------------------------------------------");

        temp[0] = "Атлетико Темперли";
        temp[1] = "Ставочка";
        temp[2] = "600";
        temp[3] = "24";
        temp[4] = "2";
        temp[5] = "1092325";

        signal();

        Thread.sleep(1000);
        System.out.println("-----------------------------------------------------------------------");

        temp[0] = "Атлетико Темперли";
        temp[1] = "Ставочка";
        temp[2] = "600";
        temp[3] = "57";
        temp[4] = "0";
        temp[5] = "1092325";

        signal();

        Thread.sleep(1000);
        System.out.println("-----------------------------------------------------------------------");

        temp[0] = "Атлетико Темперли";
        temp[1] = "Ставочка";
        temp[2] = "600";
        temp[3] = "24";
        temp[4] = "50";
        temp[5] = "1092325";

        signal();

    }
//@todo добавить мапу логику что бы замена шла только на цифры а не на всякие "" - а что если на 15ой минуте уже совсем другие цифры ?

    public static void signal() throws InterruptedException {

        for (int i = 0; temp[i] != null; i += 6) {

            double[] cefIstvkaarr = mapaCefov.get(Integer.parseInt(temp[i + 5])); // извлекаем кеф и ставку по серийнику
            System.out.println("Серийник запрос" + temp[i + 5]);

            if (cefIstvkaarr != null && Integer.parseInt(temp[i + 2]) < time && Integer.parseInt(temp[i + 3]) + Integer.parseInt(temp[i + 4]) > totalObshyi && !mapFixedStavka.containsKey(temp[i + 5])) { // если в мапе спели прочитаться значения кефов и тоталов и у нас время меньше заложенных 5 минут и тотал 1 и тотал второй  больще нашего значния тоталОбщий(сигнал) и по команде 1(теперь сирийнику) нету такого в листе тех кто то уже обрабатывался в теченни трех минут то

                signalStavki(temp, i, cefIstvkaarr);


            }
            if (Integer.parseInt(temp[i + 2]) > resulTime && mapFixedStavka.containsKey(temp[i + 5])) { // если время уже более того например 15 мин когда пора проверить результат и в листе результатов есть такая команда1 у которой стоит проверить результат то

                signalResulta(temp, i, mapFixedStavka);

            }
        }
    }


    public static void signalStavki(String[] temp, int i, double[] cefIstvkaarr) throws InterruptedException {

        String[] signalArr = new String[7]; // переменная чисто для конкотинации доп слов


        signalArr[0] = ("Команда 1 " + (temp[i]));
        signalArr[1] = ("Команда 2 " + (temp[i + 1]));
        signalArr[2] = ("Прошло время " + Integer.parseInt(temp[i + 2]) / 60 + " : " + Integer.parseInt(temp[i + 2]) % 60);
        signalArr[3] = ("Счет  1ой " + (temp[i + 3]));
        signalArr[4] = ("Счет 2ой " + (temp[i + 4]));


        System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
        System.out.println("Серйник " + temp[i + 5]);
        signalArr[5] = ("Коффцент тм тотал " + cefIstvkaarr[0]);
        signalArr[6] = ("Ставка тотал м " + cefIstvkaarr[1]);


        mapFixedStavka.put(temp[i + 5], new double[]{cefIstvkaarr[0], cefIstvkaarr[1]});//@todo если буду ошибки переделать на имя первой команды - а ейчас идентиться по серийнику


        System.out.println("Добавлен в результат " + temp[i + 5]);

//                bot.sendArrayDataToAll(signalArr);

        System.out.println("SIGNAL " + Arrays.toString(signalArr));

        Thread.sleep(100);
    }

    public static void signalResulta(String[] temp, int i, Map<String, double[]> mapFixedStavka) {

        String[] resultArr = new String[5];

        int total12 = Integer.parseInt(temp[i + 3]) + Integer.parseInt(temp[i + 4]);

        double kefTM = mapFixedStavka.get(temp[i + 5])[0];
        double stavkaTM = mapFixedStavka.get(temp[i + 5])[1];  // получаем из мапы значения


        System.out.println("---------------------------------------------------------------9000000000");
//                if (total12 <= stavkaTM) { // от знака зависит ставка-результат - тотал больше или меньше - продумать при тотал с десятичой дробью
        System.out.println("---------------------------------------------------------------1000000000");
        resultArr[0] = ("Команда 1 " + (temp[i]));
        resultArr[1] = ("Команда 2 " + (temp[i + 1]));
        resultArr[2] = ("Прошло время " + Integer.parseInt(temp[i + 2]) / 60 + " : " + Integer.parseInt(temp[i + 2]) % 60);
        resultArr[3] = ("Общий тотал " + total12);
        resultArr[4] = total12 <= stavkaTM ? "ВЫИГРАЛА" : "Проиграла";

//                    bot.sendArrayDataToAll(resultArr);
        System.out.println("SIGNAL RESULT " + Arrays.toString(resultArr));

        balance = balance - naOdnuIgru + stavka(kefTM, stavkaTM, total12, naOdnuIgru); // рачет финансов
        System.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW).a("Баланс").reset() + " " + balance);

        mapFixedStavka.remove(temp[i]); // чистим лист после проверки резуьтата


    }


    public static double stavka(double kef, double total, int total12, double naOdnuIgru) {
        if (total12 < total) {
            return naOdnuIgru * kef;
        } else if (total12 == total) {
            return naOdnuIgru;
        } else {
            return 0.0;
        }

    }


}
