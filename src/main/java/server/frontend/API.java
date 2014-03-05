package server.frontend;

import json.JSONObject;
import server.dbService.DBService;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 07.12.13
 * Time: 12:40
 * To change this template use File | Settings | File Templates.
 */
// API use to connect HTTP REQUEST and DB SERVICE
public class API {
    private DBService dbService;

    public API() {
        dbService = new DBService();
    }

    // {users.get} http://localhost/users.get
    // Input: {users_id:[103213,3213,312312,33213213,3213123], fields:["name", "last_name", "age", "photo"]}
    /* Output:
        response: [{
        user_id: 205387401,
        name: 'Tom',
        last_name: 'Cruise',
        age: '20',
        photo: 'http://cs402330.vk.me/v402330401/9760/pV6sZ5wRGxE.jpg',
        }, {user_id: 205387401,name: 'Tom',last_name: 'Cruise',age: '20',photo: 'http://cs402330.vk.me/v402330401/9760/pV6sZ5wRGxE.jpg'}, ...]
     */
    public void getUsers(String json) {
        String str = "{users_id:[8018, 8019, 8020, 8021], fields:[\"name\", \"last_name\", \"age\", \"photo\"]}";
    }

    public static boolean isMethodAPIExist(String method) {
        APIFUNC[] methods = APIFUNC.values();
        for (APIFUNC m : methods) {
            if (method.equalsIgnoreCase(m.getMethod())) {
                return true;
            }
        }
        return false;
    }
}
