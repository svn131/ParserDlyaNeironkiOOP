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

            if(cursor > otladkaNaskolkoMasiv -2 ){ // при заплненноси масива на 998 - начинаем затирать тарые значения новыми с 0 индекса начиная
                cursor = 0;
            }


        }



    }

    public boolean custumArrContains(String s){ // роверяем еть ли такая ссылка в масиве слежения
        for (String ss :arrSlighenya){
            if ( ss.equals(s)) {
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
        } catch (NoSuchElementException e) {
            // Обработка исключения или выброс другого исключения,
            // если требуется особая логика
            return ""; // или любое другое значение по вашему выбору
        }




    }


}
