package uub.logicallayer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Lock {
    private static Map<Integer, Object> hashtable = new ConcurrentHashMap<Integer, Object>();


    public static Object get(int i) {
    	Object object = hashtable.get(i);

        if (object == null ) {
            hashtable.put(i, new Object());
            return hashtable.get(i);
        }else {
        return object;
        }
    }

}
