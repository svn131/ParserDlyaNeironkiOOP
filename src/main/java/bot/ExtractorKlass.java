package bot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;


public class ExtractorKlass {

    ObrabotkaSsylok obrabotkaSsylok;
    Map<Integer, double[]> mapaCefov;

    Set<Dop> setDopov = new HashSet<>();//доп логика кефы и прочие - на данный момент устранение багга с четвертями 3 например а временем 4-20


    public ExtractorKlass(ObrabotkaSsylok obrabotkaSsylok, Map<Integer, double[]> mapaCefov) {
        this.obrabotkaSsylok = obrabotkaSsylok;
        this.mapaCefov = mapaCefov;
    }

    public List<List<String>> processJson(JSONObject jsonObject) {

        List<List<String>> allValues = new ArrayList<>();

        JSONArray valueList = jsonObject.optJSONArray("Value");
        if (valueList != null && valueList.length() > 0) {
            for (int j = 0; j < valueList.length(); j++) {
                JSONObject gameObj = valueList.getJSONObject(j); // получаем из джейсона валью - там был лист джейсонов вот получаем с него каждый раз по одному джейсону с игрой
                List<String> values = new ArrayList<>();


                String o1 = gameObj.optString("O1", "0");
                String o2 = gameObj.optString("O2", "0");
                values.add(o1);
                values.add(o2);

                JSONObject scObject = gameObj.optJSONObject("SC");


                if (scObject != null) {
                    JSONArray psList = scObject.optJSONArray("PS");
                    if (psList != null) {
                        for (int i = 0; i < psList.length(); i++) {
                            JSONObject psObject = psList.getJSONObject(i);
                            String nf = psObject.optString("NF", "");
                            int s1 = psObject.optInt("S1", 0);
                            int s2 = psObject.optInt("S2", 0);


                            if ("1-я Четверть".equals(nf)) {
                                values.add(String.valueOf(s1));
                                values.add(String.valueOf(s2));
                                break;
                            }
                        }


                    }

                    String ts = scObject.optString("TS", "0");
                    values.add(ts);

                    JSONObject fsObject = scObject.optJSONObject("FS");
                    if (fsObject != null) {
                        int s1 = fsObject.optInt("S1", 0);
                        int s2 = fsObject.optInt("S2", 0);
                        values.add(String.valueOf(s1));
                        values.add(String.valueOf(s2));
                    }


                    System.out.println(gameObj.toString());

                    System.out.println("V=================================================");
//                    System.out.println(extractPCObject(gameObj));
                    System.out.println("/|======================================================");

                    int serialKey = extractSerialKeyGame(gameObj);
                    values.add(String.valueOf(serialKey));
                    extractEArray2(gameObj);

///////////////////////////////////////////// Логика доп
                    if (setDopov.contains(serialKey)) {
                        Dop dop = new Dop(serialKey); // логика доп - читает тоже что и выше на пару строк но пока лучший вариант и как по памяти  и скорости лучще создвать постоянно и пытатся ложить или  сначала коннтайнс если такого нет тогда толькосоздавать ?
//                   String chetvert = scObject.getString("CPS");
//
//                   dop.setChetvert(chetvert);
                        setDopov.add(dop);
                    }
                    //выше можно добаввлять в сущность любые пармаетры -например черз локальные переменные метода илиже получить сийник в самом верху и положить сущность там - и исплльзовать их в других стратегиях
 ///////////////////////////////////////// Логика допа просто добаляем все игры из джеймона по баскетболу какие есть даже те что заканчиваються
                }


                if(scObject.getString("CPS").equals("1-я Четверть")) { // добавляем в лист - игры тольк где первая четветь - остальные игнорируем
                    allValues.add(values);
                }
                Integer ssilka = gameObj.getInt("I");
                obrabotkaSsylok.podgotovkaUrl(ssilka);
            }
        }

        return allValues;
    }






    public void extractEArray2(JSONObject json) {
        int seriinikIgry = json.getJSONArray("O2IS").optInt(0, 0);

        try {
            JSONArray sgArray = json.getJSONArray("SG");

            for (int i = 0; i < sgArray.length(); i++) {
                JSONObject sgObject = sgArray.getJSONObject(i);

                JSONArray eArray = sgObject.getJSONArray("E");
                System.out.println("Массив E: " + eArray);

                for (int j = 0; j < eArray.length(); j++) {
                    JSONObject eObject = eArray.getJSONObject(j);


                    double c = eObject.getDouble("C");
                    int g = eObject.getInt("G");
                    int t = eObject.getInt("T");
                    double p = eObject.optDouble("P", 0.0);

                    // Обработка элемента массива E
                    if (p > 30.0 && p < 100.0 && t == 9 && g == 17) { //@todo тонкая настройка бота
                        System.out.println("Найден подходящий элемент:");

                        System.out.println("C: " + c);
                        System.out.println("G: " + g);
                        System.out.println("T: " + t);
                        System.out.println("P: " + p);

                        //////////////////////////////////////////////////////////////////////////////////
                        for (Map.Entry<Integer, double[]> entry : mapaCefov.entrySet()) {

                            Integer key = entry.getKey();
                            double[] value = entry.getValue();
                            System.out.println("Ключ: " + key);
                            System.out.println("Значение: " + Arrays.toString(value)); //@todo вывод в кнсоль для отладки
                        }
                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
///////////////////////////////////////////////////////////////////////////////////////

                        double temp[] = {p, c}; // тотал кеф
                        mapaCefov.put(seriinikIgry, temp); //@todo пусть перезаписываеться потсоянно так как точно не известно когда сработает сигнал и должны быть актуальные значения, что бы на момент ставки помнить значения запишем их отдельно в методе сигнал в другую переменную - ыяснить нужно ли добавить логику что когда "" или нулл - бывают ли такие ситуации если да -то ими не затиирать


                    }
                }
            }
        } catch (JSONException e) {
            System.out.println("В метода extractEArray2 не оказалось чегото из SG , E ,C , G, T, P");
        }
    }


    public int extractSerialKeyGame(JSONObject json) {
        int seriinikIgry = json.getJSONArray("O2IS").optInt(0, 0); // @todo проверить чтобы не было исключений
        return seriinikIgry;
    }



}
