package bot;

import org.w3c.dom.ls.LSOutput;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public  class ObrabotkaSsylok {

    int otladkaNaskolkoMasiv = 1000;

    ConcurrentMap<String,Long> ssilkaMap = new ConcurrentHashMap<>();
//    Map<String,Long> ssilkaMap = new HashMap<>();
    String[] arrSlighenya = new String[otladkaNaskolkoMasiv]; // своего роде пропуск только один разх добавить в хешь мапу ссылку строка 31
//    List<String> arrSlighenya = new ArrayList<>();
    int cursor = 0;

   Iterator<String> iterator;

    public  void podgotovkaUrl(String ssilka){

        System.out.println("Пришла ссылка " + ssilka);

        if (ssilka == null) {
            System.out.println("Ссылка нулл");
            return;
        }


        if(ssilkaMap.containsKey(ssilka) && System.currentTimeMillis() - ssilkaMap.get(ssilka)  > 600000){ // закоменченны йметод ниже тоже самое выполняет - что бы понять суть читать можно его

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

        }
        else if (!custumArrContains(ssilka)){  // проверяем еть ли такая ссылка в масиве слежения
            ssilkaMap.put(ssilka,System.currentTimeMillis()); // еси нет джобавляем элемент в хешь мапу
            arrSlighenya[cursor] = ssilka; // и в массив слежения
            cursor++;

            if(cursor > otladkaNaskolkoMasiv -2 ){ // при заплненности масива на 998 - начинаем затирать тарые значения новыми с 0 индекса начиная
                cursor = 0;
            }


        }



    }

    public boolean custumArrContains(String s){ // проверяем есть ли такая ссылка в массиве слежения
        for (String ss :arrSlighenya){
//            if (ss.equals(s)) {  // доложно же работать ??????????????????????????? Ведь выше прверка на нулл
            if ( ss != null && ss.equals(s)) {
              return true;
            }
        }
        return false;
    }



    public String getSsilka (){ // каждый раз тут будет выдаваться новая ссылка при обращении к локальной мапе

        try {
            if (!iterator.hasNext()) {
                iterator = ssilkaMap.keySet().iterator();
            }
            return iterator.next();
        } catch (NullPointerException e) {
            // Обработка исключения или выброс другого исключения,
            // если требуется особая логика
            return "@@"; // или любое другое значение по вашему выбору // если мапа пуста будет возвращться это - допустим кончились все игры на 15 минут и надо парсить вообще будет ли еще https://1xstavka.ru/LiveFeed/Get1x2_VZip?sports=3&count=50&antisports=188&mode=4&country=1&partner=51&getEmpty=true&noFilterBlockEvent=true
        }




    }


}
