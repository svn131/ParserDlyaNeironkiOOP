package bot;

import org.json.JSONObject;

import java.util.List;

public class MapperJsonVsArray {
    ExecutorClass executorClass;

    public MapperJsonVsArray(ExecutorClass executorClass) {
        this.executorClass = executorClass;
    }

    public void mapJsonToArray(JSONObject jsonObject, String[] temp) { // маппер производит конвертацию в перемнную аргумента поэтому войд

        List<List<String>> result = executorClass.processJson(jsonObject); // ложим в наш метод и получаем лист листов с играми
        for (List<String> innerList : result) {
            int cursorTemp = 0;
            for (String value : innerList) {
                System.out.println(value);
                temp[cursorTemp] = value;
                cursorTemp++;                 // переводим все просто в массив
            }

        }

    }
}
