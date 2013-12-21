package utils;

import server.msgsystem.Address;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 21.12.13
 * Time: 9:15
 * To change this template use File | Settings | File Templates.
 */
public class AddressContext {
    private Map<Class<?>, Address> context = new HashMap<Class<?>, Address>();

    public void add(Class<?> clazz, Address obj) {
        if (context.containsKey(clazz)) {
            return;
        }
        context.put(clazz, obj);
    }

    public Address get(Class<?> clazz) {
        return context.get(clazz);
    }
}