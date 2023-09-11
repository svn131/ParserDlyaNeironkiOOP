package bot;

import org.w3c.dom.ls.LSOutput;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ObrabotkaSsylok {

    int otladkaNaskolkoMasiv = 1000;

    ConcurrentMap<Integer, Long> ssilkaMap = new ConcurrentHashMap<>(); //@todo загадать загадку

    Integer[] arrSlighenya = new Integer[otladkaNaskolkoMasiv]; // своего роде пропуск только один разх добавить в хешь мапу ссылку строка 31

    int cursor = 0;

    Iterator<Integer> iterator;

    public void podgotovkaUrl(Integer ssilka) {

        System.out.println("Пришла ссылка " + ssilka);

        if (ssilka == null) {
            System.out.println("Ссылка нулл");
            return;
        }

//        if(ssilkaMap.containsKey(ssilka) && System.currentTimeMillis() - ssilkaMap.get(ssilka)  > 4000){ // 2000 для теста - спустя это время старые ссылки не должны выводитя вновь

        if (ssilkaMap.containsKey(ssilka) && System.currentTimeMillis() - ssilkaMap.get(ssilka) > 600000) { // закоменченны йметод ниже тоже самое выполняет - что бы понять суть читать можно его

            ssilkaMap.remove(ssilka);


//            if(ssilkaMap.containsKey(ssilka)){
//
//                Long iTimeIgry  = ssilkaMap.get(ssilka);
//
//                if(iTimeIgry!=null){
//                    if(System.currentTimeMillis() - iTimeIgry  > 600000){ // удаляем значение если оно в мапе более 10 мин. Но сного оно не добавиться благодоря проверки в 31 сроке
//                        ssilkaMap.remove(ssilka);
//                    }
//                }

        } else if (!custumArrContains(ssilka)) {  // проверяем еть ли такая ссылка в масиве слежения
            ssilkaMap.put(ssilka, System.currentTimeMillis()); // еси нет джобавляем элемент в хешь мапу
            arrSlighenya[cursor] = ssilka; // и в массив слежения
            cursor++;

            if (cursor > otladkaNaskolkoMasiv - 2) { // при заплненности масива на 998 - начинаем затирать тарые значения новыми с 0 индекса начиная
                cursor = 0;
            }


        }


    }

    public boolean custumArrContains(Integer s) { // проверяем есть ли такая ссылка в массиве слежения
        for (Integer ss : arrSlighenya) {
//            if (ss.equals(s)) {  // доложно же работать ?????????? Не должно так как арр слежения пи первом обращении нулл
            if (ss != null && ss.equals(s)) {
                return true;
            }
        }
        return false;
    }


    public String getSsilka() { // каждый раз тут будет выдаваться новая ссылка при обращении к локальной мапе

        try {
            if (!iterator.hasNext()) {
                iterator = ssilkaMap.keySet().iterator();
            }
            String s = "&subGames=" + String.valueOf(iterator.next());
            System.out.println("Выданна ссылка " + s);
            return s;
//            return "&subGames="+String.valueOf(iterator.next()); //@todo лучший вариант - выше просто для вывода для тестов

        } catch (NullPointerException e) {

            iterator = ssilkaMap.keySet().iterator(); // создасться когда у нс первый запуск. Или очень просто большой и мапа стала пуста
            // Обработка исключения или выброс другого исключения,
            // если требуется особая логика
            return ""; // или любое другое значение по вашему выбору // если мапа пуста будет возвращться это - допустим кончились все игры на 15 минут и надо парсить вообще будет ли еще https://1xstavka.ru/LiveFeed/Get1x2_VZip?sports=3&count=50&antisports=188&mode=4&country=1&partner=51&getEmpty=true&noFilterBlockEvent=true
        } catch (NoSuchElementException e) {
            System.out.println("При инициализации итератора в ифе мапа оказалась пуста - игры не идут ?");
            return "";
        }


    }

    public void logikaOchistkiSnytuhforMaghorno() {

    }

//@todo добавить логику очистки ссылок если игра остановилась -ее убрали ранее нашего времеени - сняли с турнира- и более не прилитает ее значение - такие нужно просто вызывать прверку раз в 10 условно минут - нашей мапы на нличие таких вещей.
}
