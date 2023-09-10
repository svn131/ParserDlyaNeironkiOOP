package bot;

import org.fusesource.jansi.Ansi;

import java.util.*;

public class SignalClass {

    //@todo добавить мапу логику что бы замена шла только на цифры а не на всякие "" - а что если на 15ой минуте уже совсем другие цифры ?




        public void signalStavki(String[] temp, int i, double[] cefIstvkaarr,Map<String, double[]> mapFixedStavka) throws InterruptedException {

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

        public void signalResulta(String[] temp, int i, Map<String, double[]> mapFixedStavka,double balance,int naOdnuIgru) {

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


        public double stavka(double kef, double total, int total12, double naOdnuIgru) {
            if (total12 < total) {
                return naOdnuIgru * kef;
            } else if (total12 == total) {
                return naOdnuIgru;
            } else {
                return 0.0;
            }

        }


    }




