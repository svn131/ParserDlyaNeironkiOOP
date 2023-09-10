package bot;

import org.fusesource.jansi.Ansi;
import org.w3c.dom.ls.LSOutput;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SignalTest2 {

    Bot bot = new Bot();
    SignalClass signalClass = new SignalClass();
    String[] temp = new String[300];

    Double balance = 10000.0;
    int naOdnuIgru = 1000;


    //////////////////////////////////////////////////
    int resulTime = 601; // 25 минут проверка результата ставки // обратить внимие что мы берем поля ски общие а желательно при результате брать ски четверти- или же складывать значение при тай не более 10 минут последнее значение

    int totalObshyiDlyaSignla = 20; // 30
    int timeSignalDo = 300; // 300 это пять минут

    Map<Integer, double[]> mapaCefov = new HashMap<>();
    Map<String, double[]> mapFixedStavka = new HashMap<>();

////////////////////////////

    public static void main(String[] args) throws InterruptedException {

        SignalTest2 signalTest2 = new SignalTest2();
        signalTest2.run();
    }

    public void run() throws InterruptedException {


//        O1,O2,280,10,11,11625
//        11625, 55.0, 1.8
//
//        O1,O2,610,10,11,11625
//        11625, 55.0, 1.8
//
//        O3,O4,280,10,11,11000
//        11000, 55.0, 1.8
//
//        O3,O4,610,10,11,11000
//        11000, 55.0, 1.8
//
//        O5,O6,210,10,11,11111
//        11111, 55.0, 1.8
//
//        O5,O6,610,10,11,11111
//        11111, 55.0, 1.8



        while (true) {
            System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).a("Введите строку для теста"));

            Scanner scn = new Scanner(System.in);
            String s = scn.nextLine();
            String ss = scn.nextLine();

            temp = s.split(","); // присваиваем массив


            String[] splitMapArr = ss.split(",");
            int serialNumber = Integer.parseInt(splitMapArr[0]);
            double[] cefIstvkaarr = new double[]{Double.parseDouble(splitMapArr[1]), Double.parseDouble(splitMapArr[2])};


            mapaCefov.put(serialNumber, cefIstvkaarr); // присваиваем мапу


            System.out.println(Arrays.toString(temp));


            signal();



        }
    }


    public void signal() throws InterruptedException {

        int i = 0;

        double[] cefIstvkaarr = mapaCefov.get(Integer.parseInt(temp[i + 5])); // извлекаем кеф и ставку по серийнику
        System.out.println("Серийник запрос " + temp[i + 5] + " Cчет 1й и 2й " + temp[i + 3] + " " + temp[i + 4]);


        System.out.println("11111111111");
        if (cefIstvkaarr != null && Integer.parseInt(temp[i + 2]) < timeSignalDo && Integer.parseInt(temp[i + 3]) + Integer.parseInt(temp[i + 4]) > totalObshyiDlyaSignla && !mapFixedStavka.containsKey(temp[i + 5])) { // если в мапе спели прочитаться значения кефов и тоталов и у нас время меньше заложенных 5 минут и тотал 1 и тотал второй  больще нашего значния тоталОбщий(сигнал) и по команде 1(теперь сирийнику) нету такого в листе тех кто то уже обрабатывался в теченни трех минут то
            System.out.println("222222222222222222");

            signalClass.signalStavki(temp, i, cefIstvkaarr, mapFixedStavka, bot);


        }
        System.out.println((temp[i + 2]) +" ---------- "+(temp[i + 5]));
        for (Map.Entry<String, double[]> entry : mapFixedStavka.entrySet()) {
            String key = entry.getKey();
            double[] value = entry.getValue();

            System.out.println("Key: " + key);
            System.out.println("Value: " + Arrays.toString(value));
        }
        if (Integer.parseInt(temp[i + 2]) > resulTime && mapFixedStavka.containsKey(temp[i + 5])) { // если время уже более того например 15 мин когда пора проверить результат и в листе результатов есть такая команда1 у которой стоит проверить результат то
            System.out.println("333333333333333333");
            balance = signalClass.signalResulta(temp, i, mapFixedStavka, balance, naOdnuIgru, bot);

        }
    }

}

//  try {
//          cefIstvkaarr = new double[]{Double.parseDouble(splitMapArr[1]), Double.parseDouble(splitMapArr[2])};
//          } catch (NumberFormatException e) {
//          cefIstvkaarr = null;
//          }