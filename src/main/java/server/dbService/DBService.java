package server.dbService;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import server.dbService.tables.Dialog;
import server.dbService.tables.Location;
import server.dbService.tables.User;

import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 22.11.13
 * Time: 22:39
 * To change this template use File | Settings | File Templates.
 */
public class DBService {

    private Session session;

    public DBService() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public List getUser(Integer userId, List<String> fieldsList) {
        StringBuffer req = new StringBuffer();
        ((LinkedList<String>)fieldsList).addFirst("id");

        String field = separateListSymbolFields(fieldsList, ",");

        req.append(String.format("select %s from User user where user.id=%d", field, userId));
        return session.createQuery(req.toString()).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
    }

    public List getAllPlacesInCoordinates(LocationRange locRange, List<String> fieldsList)
    {
        String field = separateListSymbolFields(fieldsList, ",");
        String sql = String.format(Locale.ENGLISH,"select %s from place"
                    + " inner join location on  place.loc_id = location.loc_id"
                        + " where (location.latitude between %.5f and %.5f) and (location.longitude between %.5f and %.5f);"
                            , field, locRange.leftLatitude, locRange.rightlLatitude, locRange.leftLongitude, locRange.rightlLongitude);
        return  session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
    }

    public List getAllMeetsInCoordinates(LocationRange locRange, List<String> fieldsList)
    {
        String field = separateListSymbolFields(fieldsList, ",");
        String sql = String.format(Locale.ENGLISH, "select %s from meet"
                + " inner join location on  meet.loc_id = location.loc_id"
                    + " where (location.latitude between %.5f and %.5f)  and (location.longitude between %.5f and %.5f);"
                        , field, locRange.leftLatitude, locRange.rightlLatitude, locRange.leftLongitude, locRange.rightlLongitude);
        return session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
    }

    public List  getAllUsersInCoordinates(LocationRange locRange, List<String> fieldsList)
    {
        String field = separateListSymbolFields(fieldsList, ",");
        String sql = String.format(Locale.ENGLISH,"select %s from user"
                + " inner  join user_location on user_location.user_id = user.id inner join  location"
                    + " on location.loc_id = user_location.loc_id_extra "
                        + " where (location.latitude between %.5f and %.5f )  and (location.longitude between %.5f and %.5f);"
                            , field, locRange.leftLatitude, locRange.rightlLatitude, locRange.leftLongitude, locRange.rightlLongitude);
        return session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
    }

    public List getAllDialogsByUserId(int userId, List<String> fieldsList) {
        String field = separateListSymbolFields(fieldsList, ",");
        String sql = String.format(Locale.ENGLISH,"select %s from user " +
                            "inner join dialog_user " +
                                "inner join dialog on user.id=dialog_user.user_id and dialog_user.dialog_id=dialog.id and user_id=%d;"
                                    , field, userId);
        return session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
    }

    public List getAllMessageByDialogId(int dialogId, List<String> fieldsList) {
        String field = separateListSymbolFields(fieldsList, ",");
        String sql = String.format(Locale.ENGLISH,"select %s from dialog inner join messanger on dialog_id= %d and dialog.id=messanger.dialog_id"
                            , field, dialogId);
        return session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
    }

    public List getAllMeetsByUserId(int userId, List<String> fieldsList) {
        String field = separateListSymbolFields(fieldsList, ",");
        String sql = String.format(Locale.ENGLISH, "select %s from user inner join participants" +
                " inner join meet on user.id=participants.user_id and meet.meet_id=participants.meet_id and user.id=%d"
                    , field, userId);
        return session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
    }

    public List getInfoMeetsById(int meetId, List<String> fieldsList) {
        String field = separateListSymbolFields(fieldsList, ",");
        String sql = String.format(Locale.ENGLISH, "select %s from meet as m inner join user_location as ul " +
                "inner join wall as w on m.loc_id=ul.loc_id and m.wall_id=w.wall_id and meet_id=%d"
                    , field, meetId);
        return session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
    }

    public List getUserFriends(int userId, List<String> fieldsList) {
        String field = separateListSymbolFields(fieldsList, ",");
        String sql = String.format(Locale.ENGLISH, "select %s from friends as f inner join user as u on f.user1=u.id and f.user1=%d", field, userId);
        return session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
    }

    public List getMeetById(int meetId, List<String> fieldsList) {
        String field = separateListSymbolFields(fieldsList, ",");
        String sql = String.format(Locale.ENGLISH, "select %s from meet where meet_id=%d", field, meetId);
        return session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
    }

    public List getDialogById(int dialogId, List<String> fieldsList) {
        String field = separateListSymbolFields(fieldsList, ",");
        String sql = String.format(Locale.ENGLISH, "select %s from meet where dialog_id=%d", field, dialogId);
        return session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
    }

    public List getPlaceById(int placeId, List<String> fieldsList) {
        String field = separateListSymbolFields(fieldsList, ",");
        String sql = String.format(Locale.ENGLISH, "select %s from meet where dialog_id=%d", field, placeId);
        return session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
    }

    public List getWallById(int wallId, List<String> fieldsList) {
        String field = separateListSymbolFields(fieldsList, ",");
        String sql = String.format(Locale.ENGLISH, "select %s from meet where dialog_id=%d", field, wallId);
        return session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
    }

    public List getFriendsMeet(int userId) {
        String sql = String.format(Locale.ENGLISH,"select m.title, group_concat(user2) as frds_id, group_concat(frd.name) as frds_name from " +
                        "(select user2,name from friends as f inner join user as u on f.user2=u.id and user1=%d) as frd " +
                            "inner join participants as p inner join meet as m " +
                                "on frd.user2=p.user_id and m.meet_id=p.meet_id  group by p.meet_id"
                                    , userId);
        return session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
    }

    public boolean updateUser(int userId, Map<String, Object> map) {
        String field = updateRequest(map, "user");
        String sql = String.format(Locale.ENGLISH, "update user set %s where user.id=%d", field, userId);
        return session.createQuery(sql).executeUpdate() > 0 ? true : false;
    }

    private static String updateRequest(Map<String, Object> map, String key) {
        String res = "";

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            res += key + "." + entry.getKey() + "='" + entry.getValue() + "',";
        }

        return res.substring(0, res.length() - 1);
    }

/*    private static List<Map<String, Object>> getMapBySQLList(List list, List<String> fieldsList) {
        List<Map<String, Object>> objList = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            Object[] obj = (Object[]) list.get(i);
            for (int j = 0; j < obj.length; j++) {
                map.put(fieldsList.get(j), obj[j]);
            }
            objList.add(map);
        }
        return objList;
    }*/

    private static String separateMapSymbolFields(Map<String, List<String>> map, String separator) {
        String str = "";
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            str += separateListSymbolFields((List<String>) pairs.getValue(), separator, pairs.getKey().toString())
                                                                                                            + separator;
        }
        return str.substring(0, str.length() - 1);
    }

    private static <T> String separateListSymbolFields(List<T> list, String separator, String key) {
        String separateStr = "";
        for (T item : list) {
            String it = item.toString();
            separateStr += ( new StringBuffer(key).append(".").append(it).append(" as ").append(it).append(separator));
        }
        return separateStr.substring(0, separateStr.length() - 1);
    }

    private static <T> String separateListSymbolFields(List<T> list, String separator) {
        String separateStr = "";
        for (T item : list) {
            String it = item.toString();
            separateStr += ( new StringBuffer(it).append(" as ").append(it).append(separator));
        }
        return separateStr.substring(0, separateStr.length() - 1);
    }

    private static <T> String separateListSymbol(List<T> list, String separator) {
        String separateStr = "";
        for (T item : list) {
            separateStr += (item + separator);
        }
        return separateStr.substring(0, separateStr.length() - 1);
    }
}
