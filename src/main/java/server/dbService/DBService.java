package server.dbService;


import org.hibernate.Query;
import org.hibernate.Session;
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


    public DBService() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Location loc1 = new Location();
        loc1.setLatitude(100F);
        loc1.setLongitude(100F);
        loc1.setTime(new Date());

        User user1 = new User();
        user1.setName("Alex");
        user1.setPassword("dzrwqmsadDJNKnd2ie2d3Ddsd");
        user1.setGender(18);
        user1.setToken("vk");
        user1.setExist(true);
        user1.setExpires(System.currentTimeMillis() / 1000L);
        user1.setPhoto("abc.png");

        List<Location> loc = new ArrayList<Location>();
        loc.add(loc1);
        user1.setLocations(loc);

        session.save(user1);

        Query query = session.createQuery("from User user where user.id = 5013");
        List<User> list = query.list();
        User user = list.get(0);
        System.out.println(user.getId() + " : " + user.getLocations());

        session.getTransaction().commit();
        System.out.println("Done. Add User.");



        /*Dialog d = new Dialog();
        d.setTitle("Hello");
        session.save(d);

        Messanger m = new Messanger();
        m.setDialog(d);
        m.setMsg("Hello World!");
        m.setDateTime(new Date());
        m.setRead(false);
        session.save(m);*/
/*
        User user1 = new User();
        user1.setName("Alex");
        user1.setPassword("dzrwqmsadDJNKnd2ie2d3Ddsd");
        user1.setGender(18);
        user1.setToken("vk");
        user1.setExist(true);
        user1.setExpires(System.currentTimeMillis() / 1000L);
        user1.setPhoto("abc.png");

        Location loc1 = new Location();
        loc1.setLatitude(100F);
        loc1.setLongitude(100F);
        loc1.setTime(new Date());

        Location loc2 = new Location();
        loc2.setLatitude(100F);
        loc2.setLongitude(100F);
        loc2.setTime(new Date());

        List<Location> locationList = new ArrayList<Location>();
        locationList.add(loc1);
        locationList.add(loc2);

        user1.setLocations(locationList);*/

        /*Meet meet = new Meet();
        meet.setTitle("Meet1");
        meet.setDescription("Go to cinema");
        meet.setPhoto("abc.png");
        meet.setDateTime(new Date());
        meet.setAccess(Access.PUBLIC);
        meet.setStatus("Status");
        meet.setLastUpdate(new Date());
        meet.setWhatChange("Wall");
        meet.setType(1);

        Location loc1 = new Location();
        loc1.setLatitude(100F);
        loc1.setLongitude(100F);
        loc1.setTime(new Date());



        meet.setLocation(loc1);
        meet.setWall(wall);
        session.save(meet);*/

        /*User user1 = new User();
        user1.setName("Alex");
        user1.setPassword("dzrwqmsadDJNKnd2ie2d3Ddsd");
        user1.setGender(18);
        user1.setToken("vk");
        user1.setExist(true);
        user1.setExpires(System.currentTimeMillis() / 1000L);
        user1.setPhoto("abc.png");

        Location loc1 = new Location();
        loc1.setLatitude(100F);
        loc1.setLongitude(100F);
        loc1.setTime(new Date());



        Place place = new Place();
        place.setLocation(loc1);
        place.setWall(wall);
        place.setUser(user1);
        place.setTitle("Title1");
        place.setDescription("Desc1");
        place.setStatus("Status1");
        place.setImage("top.png");

        session.save(place);*/

/*
        Location loc1 = new Location();
        loc1.setLatitude(100F);
        loc1.setLongitude(100F);
        loc1.setTime(new Date());

        Wall wall = new Wall();
        wall.setMsg("Wall msg");
        wall.setDateTime(new Date());

        Inviters inviters = new Inviters();

        Meet meet = new Meet();
        meet.setLocation(loc1);
        meet.setWall(wall);
        meet.setTitle("Meet1");
        meet.setDescription("Go to cinema");
        meet.setPhoto("abc.png");
        meet.setDateTime(new Date());
        meet.setAccess(Access.PUBLIC);
        meet.setStatus("Status");
        meet.setLastUpdate(new Date());
        meet.setWhatChange("Wall");
        meet.setType(1);
*/


  /*      User user1 = new User();
        user1.setName("Alex");
        user1.setPassword("dzrwqmsadDJNKnd2ie2d3Ddsd");
        user1.setGender(18);
        user1.setToken("vk");
        user1.setExist(true);
        user1.setExpires(System.currentTimeMillis() / 1000L);
        user1.setPhoto("abc.png");

        User user2 = new User();
        user2.setName("Alex1");
        user2.setPassword("dzrwqmsadDJNKnd2ie2d3Ddsd1");
        user2.setGender(18);
        user2.setToken("vk");
        user2.setExist(true);
        user2.setExpires(System.currentTimeMillis() / 1000L);
        user2.setPhoto("abc2.png");

        Friends frd1 = new Friends();
        frd1.setUser1(user1);
        frd1.setUser1(user2);

        //session.save(frd1);

        List<Friends> arr = new ArrayList<Friends>();
        arr.add(frd1);

        user2.setFriendsList1(arr);

        session.save(user2);*/

/*        inviters.setUserTo(user1);
        inviters.setUserFrom(user2);
        inviters.setMeet(meet);

        inviters.setTime(new Date());
        inviters.setAdmin(false);
        inviters.setInvite(true);
        inviters.setChange(false);*/



        /*Dialog dialog1 = new Dialog();
        dialog1.setTitle("Hello");

        Dialog dialog2 = new Dialog();
        dialog2.setTitle("Hello1");

        Set<Dialog> dialogSet = new HashSet<Dialog>();
        dialogSet.add(dialog1);
        dialogSet.add(dialog2);



        user1.setDialogs(dialogSet);*/


        /*Set<User> userSet = new HashSet<User>();
        userSet.add(user1);
        userSet.add(user2);

        dialog.setUsers(userSet);
        */

    }
}
