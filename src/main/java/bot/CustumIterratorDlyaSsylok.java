//package bot;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.NoSuchElementException;
//
//public class CustumIterratorDlyaSsylok {
//    private Iterator<String> iterator;
//    private Map<String, Long> localMap;
//
//    public void CustumIterratorDlyaSsylok(Map<String, Long> map) {
//        localMap = new HashMap<>(map);
//        iterator = localMap.keySet().iterator();
//    }
//
//    public String getNextKey() {
//        try {
//            if (!iterator.hasNext()) {
//                iterator = localMap.keySet().iterator();
//            }
//            return iterator.next();
//        } catch (NoSuchElementException e) {
//            // Обработка исключения или выброс другого исключения,
//            // если требуется особая логика
//            return null; // или любое другое значение по вашему выбору
//        }
//    }
//}
