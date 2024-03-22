package uub.logicallayer;

import java.util.Hashtable;
import java.util.Map;

public class Lock {
    private static Map<Integer, Object> hashtable = new Hashtable<>();


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
