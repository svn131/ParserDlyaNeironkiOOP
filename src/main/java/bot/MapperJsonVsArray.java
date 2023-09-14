package bot;

import org.json.JSONObject;

import java.util.List;

public class MapperJsonVsArray {
    ExecutorClass executorClass;

    public MapperJsonVsArray(ExecutorClass executorClass) {
        this.executorClass = executorClass;
    }

    public void mapJsonToArray(JSONObject jsonObject, List<Igra> listIgr) { // маппер производит конвертацию в перемнную аргумента поэтому войд

        List<List<String>> result = executorClass.processJson(jsonObject); // ложим в наш метод и получаем лист листов с играми


        System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
        for (List<String> sublist : result) {
            for (String item : sublist) {
                System.out.println(item);
            }
        }
        System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");


        for (List<String> innerList : result) {

            int seriynik = Integer.parseInt(innerList.get(5));

            boolean  takoiNet = true;



            for (Igra igra : listIgr) {

                if (igra.seriinik == seriynik) { // обновляем значеия если есть

                    prisvoeniePoley(igra, innerList);
                    takoiNet = false;
                    break;
                }

            }



            if(takoiNet) { // пропускаем если только такой игры нет - н хешь мапе проще будет ведь там уникльные значения

                System.out.println(" листе нет такой игры иф такой нет - серийник -- " + seriynik);

               Igra igra = new Igra(seriynik);
               prisvoeniePoley(igra, innerList);

                listIgr.add(igra);
            }
        }
    }

    public void prisvoeniePoley(Igra igra, List<String> innerList) {
        System.out.println("vvvvvvvvvvvvvvvvvvvvvvv");

        int time = Integer.parseInt(innerList.get(2));
        int t1 = Integer.parseInt(innerList.get(3));
        int t2 = Integer.parseInt(innerList.get(4));
        double pT = Double.parseDouble(innerList.get(6));
        igra.time = time; // todo олько ради использования в майн


        String o1 = innerList.get(0);
        String o2 = innerList.get(1);

        igra.o1 = o1;
        igra.o2 = o2;


        switch (time / 60) {

            case 1:
                if (igra.zamok1) {
                    igra.totalOne1 = t1;
                    igra.totalTwo1 = t2;
                    igra.predlagaemyiTotalNa1Min = pT;
                    igra.zamok1 = false;
                }
                break;
            case 2:
                if (igra.zamok2) {
                    igra.totalOne2 = t1;
                    igra.totalTwo2 = t2;
                    igra.predlagaemyiTotalNa2Min = pT;
                    igra.zamok2 = false;
                }
                break;
            case 3:
                if (igra.zamok3) {
                    igra.totalOne3 = t1;
                    igra.totalTwo3 = t2;
                    igra.predlagaemyiTotalNa3Min = pT;
                    igra.zamok3 = false;
                }
                break;
            case 4:
                if (igra.zamok4) {
                    igra.totalOne4 = t1;
                    igra.totalTwo4 = t2;
                    igra.predlagaemyiTotalNa4Min = pT;
                    igra.zamok4 = false;
                }
                break;
            case 5:
                if (igra.zamok5) {
                    igra.totalOne5 = t1;
                    igra.totalTwo5 = t2;
                    igra.predlagaemyiTotalNa5Min = pT;
                    igra.zamok5 = false;
                }
                break;
            case 6:
                if (igra.zamok6) {
                    igra.totalOne6 = t1;
                    igra.totalTwo6 = t2;
                    igra.predlagaemyiTotalNa6Min = pT;
                    igra.zamok6 = false;
                }
                break;
            case 7:
                if (igra.zamok7) {
                    igra.totalOne7 = t1;
                    igra.totalTwo7 = t2;
                    igra.predlagaemyiTotalNa7Min = pT;
                    igra.zamok7 = false;
                }
                break;
            case 8:
                if (igra.zamok8) {
                    igra.totalOne8 = t1;
                    igra.totalTwo8 = t2;
                    igra.predlagaemyiTotalNa8Min = pT;
                    igra.zamok8 = false;
                }
                break;
            case 9:
                if (igra.zamok9) {
                    igra.totalOne9 = t1;
                    igra.totalTwo9 = t2;
                    igra.predlagaemyiTotalNa9Min = pT;
                    igra.zamok9 = false;
                }
                break;
            default:
                if (igra.zamokResult && time < 720) { // когда более 10 минут или  10 то кладеться результат а так же когда менее одной
                    igra.resultTotal = t1 + t2;
                    igra.zamokResult = false;
                    igra.zamokWrite = true;// постоянно будет фолсе напечаталась поменяла а тру опять пришла сюда и поменяла фолсе
                }
        }



            //todo а можно в игру положить сущнасть минута и нкаплиать всю инфу в ней наполняя поле игры лист минут каждый раз новой минутой и тогда можно будет проводить тесты хоть на сколько минут

        }


    }


