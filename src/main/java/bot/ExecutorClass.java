package bot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSOutput;

import java.util.*;


public class ExecutorClass {

    ObrabotkaSsylok obrabotkaSsylok;
    List<Igra> listIgr;


    public ExecutorClass(ObrabotkaSsylok obrabotkaSsylok, List<Igra> listIgr) {
        this.obrabotkaSsylok = obrabotkaSsylok;
        this.listIgr = listIgr;

    }

    public void processJson(JSONObject jsonObject) {


        JSONArray valueList = jsonObject.optJSONArray("Value");
        if (valueList != null && valueList.length() > 0) {
            for (int j = 0; j < valueList.length(); j++) {
                JSONObject gameObj = valueList.getJSONObject(j); // получаем из джейсона валью - там был лист джейсонов вот получаем с него каждый раз по одному джейсону с игрой

//                int serialKey = extractSerialKeyGame(gameObj); // получаем сирийник игры//todo
                Integer ssilkaSerialKey = gameObj.getInt("I"); //todo доработать брать только ту иггру по которой сделали запрос - это для SG а для ссылок брать все
                Igra igra = extractIgraPoSeriniku(ssilkaSerialKey); // получаем игру//todo

                String o1 = gameObj.optString("O1");//todo
                String o2 = gameObj.optString("O2");//todo

                igra.setO1iO2(o1, o2);//todo

                JSONObject scObject = gameObj.optJSONObject("SC");


                String ts = scObject.optString("TS"); //todo null ???
                if(ts==null|| ts.isEmpty()){
                    continue;
                }

                int time = Integer.parseInt(ts); //todo

                igra.time = time;


// в "SC" берем "PS" в нем смотрим каждый джейсон


                if (scObject != null) {
                    JSONArray psList = scObject.optJSONArray("PS");//todo
                    if (psList != null) {

                        for (int i = 0; i < psList.length(); i++) {
                            JSONObject psObject = psList.getJSONObject(i);
                            System.out.println("ИЩУ ошибку 1");
                            if (psObject.getInt("Key") == 1) { //todo первая четверть результаты
                                JSONObject value = psObject.getJSONObject("Value");
                                System.out.println("ИЩУ ошибку 2");
                                if (value.has("S1") && value.has("S2")) {
                                    System.out.println("ИЩУ ошибку 3");
                                    int s1 = value.getInt("S1");//todo
                                    int s2 = value.getInt("S2");//todo
                                    igra.setTotalOneandTwo(time, s1, s2);
                                    igra.setResultTotal(time, s1, s2);//todo

                                    break;
                                } else {
                                    System.out.println("Не сработало строка 60");
                                }
                            }
                        }


                    }

                }


                System.out.println(gameObj.toString());


                extractPredlagaemyiTotal(gameObj, time, igra);


                System.out.println("Seeeeeeeeeeeeeeeeee " + ssilkaSerialKey  + " время" + ts); ////////////////////////////////////////////////////Важно отладка



                if (time <= 600) {
                    obrabotkaSsylok.podgotovkaUrl(ssilkaSerialKey );
                }

            }








        }

    }


    public void extractPredlagaemyiTotal(JSONObject json, int time, Igra igra) {
        System.out.println("111112");
        try {
            JSONArray sgArray = json.getJSONArray("SG");//todo
            System.out.println("111113");

            outerLoop:
            // Метка для внешнего цикла
            for (int i = 0; i < sgArray.length(); i++) {
                JSONObject sgObject = sgArray.getJSONObject(i);

                System.out.println("111114");
                if (sgObject.getInt("P") == 1) {
                    JSONArray eArray = sgObject.getJSONArray("E");//todo


                    System.out.println("Массив E: " + eArray);
                    System.out.println("111115");

                    for (int j = 0; j < eArray.length(); j++) {
                        JSONObject eObject = eArray.getJSONObject(j);

                        System.out.println("111116");

                        if (eObject.has("G") && eObject.has("T")) {
                            int gValue = eObject.getInt("G");
                            int tValue = eObject.getInt("T");

                            if (gValue == 17 && tValue == 9) {
//                                double cValue = eObject.getDouble("C");
                                double pValue = eObject.optDouble("P", 0.0);
//                                System.out.println("C----: " + cValue);
                                System.out.println("P----:" + pValue);

                                if (pValue != 0.0) {
                                    igra.setPredlagaemyiBukmekermTotal(time, pValue);
                                }

                                break outerLoop; // Выход из внешнего цикла
                            }

                        }
                    }
                }
            }

        } catch (JSONException e) {
            System.out.println("Json SGGGGGGGG не был обнаружен");;
        }


    }

    public Igra extractIgraPoSeriniku(int seriinik){

        for (Igra igra: listIgr){
           if(igra.seriinik == seriinik){
               return igra;
           }
        }
        Igra igra = new Igra(seriinik);
        listIgr.add(igra);
        return igra;
    }

}




//https://1xstavka.ru/LiveFeed/GetGameZip?id=478130639&isSubGames=true&GroupEvents=true&allEventsGroupSubGames=true&countevents=250&partner=51&grMode=2&marketType=1&gr=44&isNewBuilder=true