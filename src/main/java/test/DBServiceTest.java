package test;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import server.dbService.HibernateUtil;
import server.dbService.InsertRequest;
import server.dbService.tables.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 05.12.13
 * Time: 22:06
 * To change this template use File | Settings | File Templates.
 */
public class DBServiceTest {
    private String[] girls = new String []{"Авелина","Авиталь","Агата","Аглая","Агнесса","Агния","Ада","Аделина","Аделия","Аделя","Адиля","Адина","Адия","Адэлина","Азалия","Азиза","Аида","Аиша","Айгерим","Айгуль","Айдана","Айжан","Айзада","Айзиля","Айлана","Айлин","Айлина","Айна","Айназ","Айнара","Айнура","Айсана","Айсулу","Айсылу","Айша","Айя","Акбота","Акерке","Акмарал","Аксиния","Аксинья","Алана","Алевтина","Алекса","Александра","Александрина","Алексия","Алена","Алеся","Алика","Алима","Алина","Алиса","Алисия","Алиша","Алия","Алла","Алсу","Алтана","Алтынай","Алуа","Альбина","Альвина","Альмира","Альфия","Амалия","Амели","Амелия","Амилия","Амина","Аминат","Амира","Анаит","Анара","Анастасия","Ангелина","Анелия","Анель","Анеля","Анжела","Анжелика","Анжелина","Аниса","Анисия","Анисья","Анита","Анна","Антонина","Анфиса","Анэля","Аня","Аполинария","Аполлинария","Ариадна","Ариана","Арианна","Арина","Арсения","Ару","Аружан","Асель","Асем","Асия","Асмик","Ася","Аурика","Афина","Аэлита","Аяна","Аяулым","Бажена","Балауса","Балжан","Балнур","Белла","Богдана","Божена","Ботагоз","Валентина","Валерия","Ванесса","Варвара","Василина","Василиса","Венера","Вера","Вероника","Веста","Виктория","Вилена","Виола","Виолета","Виолетта","Вита","Виталина","Виталия","Влада","Владислава","Владлена","Газиза","Галина","Галия","Гаяне","Глафира","Глория","Гузаль","Гузель","Гульдана","Гульназ","Гульнара","Гульшат","Дайана","Далила","Дамира","Дана","Данара","Даная","Даниела","Даниэла","Даниэлла","Дания","Дарига","Дарина","Дария","Дарья","Даша","Даяна","Джамиля","Джессика","Джулия","Джульетта","Диана","Дилара","Дильназ","Диля","Диляра","Дина","Динара","Доминика","Ева","Евангелина","Евгения","Евдокия","Евелина","Екатерина","Елдана","Елена","Елизавета","Елисавета","Еркежан","Есения","Жайна","Жамиля","Жанайым","Жанар","Жанель","Жания","Жанна","Жансая","Жасмин","Жибек","Жулдыз","Залина","Залия","Замира","Зара","Зарема","Зарина","Зере","Зиля","Зинаида","Злата","Златослава","Зоя","Зульфия","Иванна","Иветта","Изабелла","Илана","Илария","Илина","Илона","Ильвина","Ильмира","Ильназ","Ильнара","Иляна","Инара","Инга","Индира","Инесса","Инжу","Инкар","Инна","Иоанна","Иоланта","Ира","Ирада","Ирина","Ия","Калерия","Камила","Камилла","Камиля","Капитолина","Карима","Карина","Каріна","Каролина","Катарина","Катерина","Катрин","Катя","Кира","Кириена","Кристина","Ксения","Ксенья","Куаныш","Куралай","Лада","Лала","Лана","Лариса","Латифа","Лаура","Лейла","Лейсан","Леля","Лена","Лера","Леся","Лея","Лиана","Лида","Лидия","Лизавета","Лика","Лилиана","Лилит","Лилия","Лиля","Лина","Линара","Линда","Лия","Лола","Лолита","Луиза","Лэйла","Люба","Любава","Любовь","Людмила","Ляля","Мадина","Майя","Малика","Маргарита","Марго","Мари","Мариам","Марианна","Марика","Марина","Мария","Марта","Маруся","Марфа","Марья","Марьям","Марьяна","Махаббат","Медина","Мелания","Меланья","Мелиса","Мелисса","Меруерт","Мила","Милада","Милана","Милания","Милена","Милина","Милла","Милослава","Миляуша","Мира","Мирослава","Мирра","Мишель","Мия","Моника","Муслима","Мэри","Мээрим","Нагима","Надежда","Надия","Назгуль","Назерке","Назира","Назым","Наиля","Наргиз","Наргиза","Насиба","Настасья","Натали","Наталия","Наталья","Нафиса","Нелли","Нигина","Ника","Николетта","Николина","Николь","Нина","Нино","Нонна","Нурай","Нурия","Нурсулу","Оксана","Олександра","Олена","Олеся","Оливия","Ольга","Оля","Павла","Патимат","Паулина","Пелагея","Перизат","Полина","Рада","Радмила","Раиля","Раиса","Раксана","Ралина","Рамиля","Рамина","Рания","Раяна","Регина","Рената","Риана","Рианна","Римма","Рина","Рината","Рита","Роза","Розалина","Розалия","Роксана","Роксолана","Рузанна","Румия","Русалина","Руслана","Руфина","Сабина","Сабира","Сабрина","Саида","Салима","Салтанат","Самина","Самира","Сандра","Сания","Сара","Сафина","Сафия","Светлана","Святослава","Сева","Севинч","Сема","Серафима","Ситора","Снежана","Снежанна","Соня","Софи","София","Софья","Станислава","Стелла","Стефани","Стефания","Сусанна","Сымбат","Сюзанна","Таисия","Таисья","Тамара","Тамила","Таня","Татьяна","Тахмина","Тереза","Тогжан","Толганай","Томирис","Тоня","Ульяна","Устинья","Фаина","Фарида","Фариза","Фатима","Хадиджа","Хадижа","Христина","Шахноза","Шолпан","Шугыла","Шынар","Ыкылас","Эвелина","Эвилина","Эвита","Элана","Элен","Элеонора","Элиза","Элизабет","Элина","Элиф","Элла","Эллада","Эллина","Элона","Эльвина","Эльвира","Эльза","Эльмира","Эльнара","Эмили","Эмилия","Эмма","Энже","Эрика","Эсмира","Юлиана","Юлия","Юля","Юстина","Яна","Янина","Ярина","Ярослава","Ясмина"};
    private String[] boys = new String[]{"Аарон", "Абай", "Абдулла", "Абзал", "Абулхаир", "Абылай", "Адам", "Адилет", "Адиль", "Адият", "Адриан", "Ажар", "Азамат", "Азат", "Азиз", "Азизбек", "Азим", "Айару", "Айбар", "Айбек", "Айгиз", "Айдай", "Айдан", "Айдар", "Айдос", "Айдын", "Айзат", "Айнур", "Айрат", "Айшат", "Акбар", "Акжан", "Акжол", "Аким", "Акмаль", "Алан", "Алдар", "Алдияр", "Алекс", "Александр", "Алексей", "Алем", "Ален", "Али", "Алик", "Алим", "Алихан", "Алишер", "Алмаз", "Алмас", "Алмат", "Альберт", "Альмир", "Альтаир", "Альфред", "Амаль", "Аман", "Амиль", "Амин", "Амир", "Амиран", "Амирхан", "Анатолий", "Анвар", "Андрей", "Ансар", "Антон", "Ануар", "Арай", "Арайлым", "Арам", "Ардак", "Арзу", "Ариет", "Аристарх", "Аркадий", "Арлан", "Арман", "Армен", "Арнур", "Арсен", "Арсений", "Арсентий", "Арслан", "Артём", "Артемий", "Артур", "Архип", "Арыстан", "Асет", "Аскар", "Аслан", "Асхат", "Афанасий", "Ахмад", "Ахмед", "Ахмет", "Ашот", "Аяз", "Аян", "Аянат", "Батыр", "Бауыржан", "Бахтияр", "Бекболат", "Бекзат", "Бексултан", "Богдан", "Борис", "Борислав", "Бронислав", "Булат", "Вадим", "Валентин", "Валерий", "Валерик", "Василий", "Вениамин", "Вика", "Виктор", "Вильдан", "Виталий", "Владеслав", "Владимир", "Владислав", "Влас", "Всеволод", "Вячеслав", "Габриэль", "Гавриил", "Гаджи", "Гаухар", "Геворг", "Геннадий", "Георгий", "Герман", "Глеб", "Гордей", "Григорий", "Гульмира", "Давид", "Давлат", "Давыд", "Далер", "Дамиан", "Дамиль", "Дамир", "Данель", "Даниал", "Даниел", "Даниель", "Даниил", "Данил", "Данила", "Данило", "Даниль", "Данис", "Даниэл", "Даниэль", "Даниял", "Данияр", "Дарий", "Дархан", "Дарын", "Дарьяна", "Дастан", "Даулет", "Даурен", "Даян", "Дементий", "Демид", "Демьян", "Дени", "Дениз", "Денис", "Джамал", "Джамиль", "Диас", "Дидар", "Димитрий", "Динар", "Динислам", "Динияр", "Дионис", "Дияр", "Дмитрий", "Добрыня", "Доминик", "Досжан", "Дулат", "Дэвид", "Дэниз", "Евгений", "Евсей", "Егор", "Елизар", "Елисей", "Елнур", "Ерасыл", "Ерболат", "Еремей", "Ержан", "Ерлан", "Ермек", "Ернар", "Ерофей", "Ефим", "Ефрем", "Жан", "Жангир", "Жандос", "", "Жанибек", "Зангар", "Заур", "Захар", "Захарий", "Зуфар", "Ибрагим", "Ибрахим", "Иван", "Игнат", "Игнатий", "Игорь", "Идрис", "Илиан", "Илия", "Илларион", "Ильгам", "Ильгар", "Ильгиз", "Ильдар", "Ильмир", "Ильнар", "Ильнур", "Ильсур", "Ильфат", "Ильхам", "Ильшат", "Илья", "Ильяс", "Имран", "Иннокентий", "Инсаф", "Иоанн", "Иосиф", "Ираклий", "Иса", "Искандар", "Искандер", "Ислам", "Исмаил", "Кайрат", "Кайсар", "Камиль", "Канат", "Карен", "Карим", "Каусар", "Кевин", "Керим", "Ким", "Кирилл", "Клим", "Климентий", "Константан", "Константин", "Коркем", "Кристиан", "Кузьма", "Лаврентий", "Лев", "Леван", "Левон", "Ленар", "Лео", "Леон", "Леонард", "Леонид", "Лука", "Лукас", "Лукьян", "Любомир", "Ляйсан", "Магомед", "Мадияр", "Майкл", "Макар", "Макс", "Максат", "Максим", "Максимилиан", "Малик", "Мансур", "Марат", "Марк", "Маркус", "Марсель", "Мартин", "Матвей", "Матфей", "Микаил", "Микаэль", "Милан", "Милолика", "Мирас", "Мирон", "Мирослав", "Митя", "Михаил", "Михаэль", "Мстислав", "Мурад", "Мурат", "Муса", "Муслим", "Мустафа", "Мухаммад", "Мухаммед", "Надир", "Назар", "Назарий", "Наиль", "Нариман", "Натан", "Наташа", "Наум", "Нестор", "Ник", "Никита", "Никола", "Николай", "Николас", "Никон", "Нияз", "Нургали", "Нуржан", "Нурислам", "Нурлан", "Нурлыбек", "Нурсултан", "Олег", "Олжас", "Омар", "Орест", "Орхан", "Оскар", "Осман", "Остап", "Павел", "Петр", "Платон", "Прохор", "Равиль", "Радик", "Радим", "Радислав", "Радмир", "Радомир", "Раиль", "Райнур", "Райхан", "Рамазан", "Рамзан", "Рамиль", "Рамис", "Ранель", "Ранис", "Расим", "Растислав", "Расул", "Ратмир", "Рауан", "Рауль", "Рауф", "Раушан", "Рафаэль", "Рахат", "Рахим", "Рахман", "Рашид", "Раян", "Реналь", "Ренат", "Ризван", "Ринат", "Рифат", "Рихард", "Ричард", "Ришат", "Рияз", "Роберт", "Родион", "Ролан", "Роман", "Ростислав", "Рубен", "Рудольф", "Рузаль", "Руслан", "Рустам", "Рустем", "Рушан", "Сабир", "Савва", "Савелий", "Саид", "Салават", "Салим", "Салих", "Самат", "Самвел", "Самир", "Самуил", "Санжар", "Сармат", "Саян", "Саят", "Светозар", "Святогор", "Святослав", "Севастьян", "Семен", "Серафим", "Сергей", "Спартак", "Станислав", "Степан", "Стефан", "Сулейман", "Султан", "Сурен", "Тагир", "Таир", "Талгат", "Тамерлан", "Тамир", "Тамирлан", "Тарас", "Тахир", "Теймур", "Темирлан", "Темур", "Тигран", "Тимерлан", "Тимофей", "Тимур", "Тихон", "Трофим", "Улан", "Умар", "Усман", "Фадей", "Фарид", "Фарис", "Фархад", "Федор", "Феликс", "Фидан", "Филипп", "Хаким", "Хамза", "Хасан", "Хусейн", "Чингиз", "Чингис", "Шамиль", "Эдвард", "Эдгар", "Эдем", "Эдуард", "Элиана", "Эльдар", "Эльмар", "Эльмир", "Эмиль", "Эмин", "Эмир", "Эрвин", "Эрик", "Эрнест", "Юлиан", "Юлий", "Юнус", "Юра", "Юрий", "Юсуф", "Яков", "Ян", "Янис", "Яромир", "Ярослав", "Ясин", "Ясмин"};

    private Random random;
    private final int limit = 1000;
    private int k = 0;

    // DB
    private Session session;

    @org.junit.Before
    public void setUp() throws Exception {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        random = new Random();
    }

    @Test
    public void createUsers() {

        for (int i = 0; i < 1000; i++) {
            Location loc = getRandomLocation();

            User user = new User();
            String [] arr = random.nextInt(2) == 0 ? girls: boys;
            String name = arr[random.nextInt(arr.length - 1)];
            user.setName(name);
            user.setPassword("dzrwqmsadDJNKnd2ie2d3Ddsd");
            user.setGender(random.nextInt(20) + 5);
            user.setToken("vk");
            user.setExist(true);
            user.setExpires(System.currentTimeMillis() / 1000L);
            user.setPhoto("abc.png");
            user.getLocations().add(loc);

            session.save(user);
        }

        session.getTransaction().commit();
    }


    @Test
    public void insertMessage() {
        User user = (User) session.createQuery("from User user where user.id = 5020").list().get(0);
        Dialog dialog = (Dialog) session.createQuery("from Dialog dialog where dialog.id = 5020").list().get(0);

        Messanger messanger = new Messanger();
        messanger.setUser(user);
        messanger.setDialog(dialog);
        messanger.setMsg("Message");
        messanger.setDateTime(new Date());
        messanger.setRead(false);

        session.save(messanger);
        session.getTransaction().commit();
    }

    @Test
    public void insertFriends() {
        /*User user1 = (User) session.createQuery("from User user where user.id = 5020").list().get(0);
        User user2 = (User) session.createQuery("from User user where user.id = 5021").list().get(0);
        user1.getFriendsList2().add(user2);

        session.update(user1);
        session.getTransaction().commit();*/
    }

/*    @Test
    public void insertPlace() throws SQLException {
        InsertRequest ir = new InsertRequest();
        ir.insertData("place", "NULL, 10, 20, 30, 'dsad', 'dsads', 'dasd', 'dasds'");
    }*/

    @Test
    public void insertMeet() {
        User user = getRandomUser();

        Location loc = new Location();
        loc.setLatitude((random.nextFloat() * 100) % 180);
        loc.setLongitude((random.nextFloat() * 100) % 180);
        loc.setTime(new Date());

        Wall wall = new Wall();
        wall.setMsg("dsadasd");
        wall.setDateTime(new Date());

        Meet meet = new Meet();
        meet.setAdmin(user);
        meet.setTitle("dasdsad");
        meet.setDescription("dasdsad");
        meet.setPhoto("dasdsad");
        meet.setDateTime(new Date());
        meet.setAccess(Access.PUBLIC);
        meet.setStatus("dasdsad");
        meet.setLastUpdate(new Date());
        meet.setWhatChange("Ok");
        meet.setType(1);
        meet.setWall(wall);
        meet.setLocation(loc);

        session.save(meet);
        session.getTransaction().commit();
    }

    @Test
    public void getUser() {
        Query query = session.createQuery("from User user where user.id = 5020");
        List<User> list = query.list();
        User user = list.get(0);
        System.out.println(user.getId() + " : " + user.getLocations());
        session.getTransaction().commit();
    }

    @Test
    public void createDialog() {
        for (int i = 0; i < 1000; i++) {
            User user1 = getRandomUser();
            User user2 = getRandomUser();

            String [] arr = random.nextInt(2) == 0 ? girls: boys;
            String title = arr[random.nextInt(arr.length - 1)];

            Dialog dialog = new Dialog();
            dialog.setTitle(title);

            user1.getDialogs().add(dialog);
            user2.getDialogs().add(dialog);

            session.update(user1);
            session.update(user2);
        }
        session.getTransaction().commit();
    }

    @Test
    public void createMessanger() {
        for (int i = 0; i < 1000; i++) {
            User rUser = getRandomUser();
            Dialog rDialog = getRandomDialog();

            Messanger messanger = new Messanger();
            messanger.setDialog(rDialog);
            messanger.setUser(rUser);
            messanger.setMsg("Hello World!");
            messanger.setDateTime(new Date());
            messanger.setRead(false);
            session.save(messanger);
        }
        session.getTransaction().commit();
    }

    @Test
    public void createNewUserLocation() {
        for (int i = 0; i < 1000; i++) {
            User user1 = getRandomUser();

            Location loc = new Location();
            loc.setLatitude((random.nextFloat() * 100) % 180);
            loc.setLongitude((random.nextFloat() * 100) % 180);
            loc.setTime(new Date());

            user1.getLocations().add(loc);
            session.update(user1);
        }
        session.getTransaction().commit();
    }

    @Test
    public void createMeet() {
        for (int i = 0; i < 1000; i++) {
            User user = getRandomUser();

            Location loc = new Location();
            loc.setLatitude((random.nextFloat() * 100) % 180);
            loc.setLongitude((random.nextFloat() * 100) % 180);
            loc.setTime(new Date());

            Wall wall = new Wall();
            wall.setMsg("Wall msg");
            wall.setDateTime(new Date());

            Meet meet = new Meet();
            meet.setAdmin(user);
            meet.setTitle("Meet1");
            meet.setDescription("Go to cinema");
            meet.setPhoto("abc.png");
            meet.setDateTime(new Date());
            meet.setAccess(Access.PUBLIC);
            meet.setStatus("Status");
            meet.setLastUpdate(new Date());
            meet.setWhatChange("Wall");
            meet.setType(1);
            meet.setWall(wall);
            meet.setLocation(loc);

            session.save(meet);
        }
        session.getTransaction().commit();
    }

    @Test
    public void addParticipants() {
        for (int i = 0; i < 100; i++) {
            User user = getRandomUser();
            Meet meet = getRandomMeet();
            meet.getParticipants().add(user);
            session.update(meet);
        }
        session.getTransaction().commit();

    }

    @Test
    public void createFriends() {
        User user1 = getRandomUser();
        User user2 = getRandomUser();

        Friends frd = new Friends();

        frd.setUser1(user1);
        frd.setUser2(user2);

        session.save(frd);
        session.getTransaction().commit();
    }

    @Test
    public void createPlace() {
        Wall wall = new Wall();
        wall.setMsg("Wall msg");
        wall.setDateTime(new Date());

        Location loc = getRandomLocation();
        User user = getRandomUser();

        Place place = new Place();
        place.setLocation(loc);
        place.setWall(wall);
        place.setUser(user);
        place.setTitle("Title1");
        place.setDescription("Desc1");
        place.setStatus("Status1");
        place.setImage("top.png");

        session.save(place);
        session.getTransaction().commit();
    }

    @Test
    public void createMeetNotification() {
        Notification notif = new Notification();

        Meet meet = getRandomMeet();
        User user = getRandomUser();

        notif.setType(NotificationType.MEET.ordinal());
        notif.setTypeId(meet.getId());
        notif.setUserId(user.getId());

        session.save(notif);
        session.getTransaction().commit();
    }

    @Test
    public void createInviters() {
        Meet meet = getRandomMeet();
        User user1 = getRandomUser();
        User user2 = getRandomUser();


        Inviters inviters = new Inviters();
        inviters.setMeet(meet);
    }

    @Test
    public void getPlaces() {
        Meet meet = getRandomMeet();
        System.out.println(getRandomMeet().getAccess());
    }


/*    @Test
    public void getAllPlacesInCoordinatesCall()
    {
        Double leftLatitude =  0.0;
        Double leftLongitude = -25.0;
        Double rightlLatitude = 25.0;
        Double rightlLongitude = 0.0;

        List <Map> list = getAllPlacesInCoordinates(leftLatitude, leftLongitude, rightlLatitude, rightlLongitude);

        list.clear();

        list = getAllMeetsInCoordinates(leftLatitude, leftLongitude, rightlLatitude, rightlLongitude);
        list.clear();

        list = getAllUsersInCoordinates(leftLatitude, leftLongitude, rightlLatitude, rightlLongitude);
        list.clear();
        //getLocationId(leftLatitude, leftLongitude, rightlLatitude, rightlLongitude);
    }*/

/*    public List<Map> getAllPlacesInCoordinates(Double leftLatitude, Double leftLongitude, Double rightlLatitude, Double rightlLongitude, List<Integer> usersId, List<String> fieldsList)
    {
        String field = separateListSymbolFields(fieldsList, ",");
        String sql = String.format(Locale.ENGLISH,"select place.title as title, location.latitude as latitude, location.longitude as longitude, place_id from place"
                            + " inner join location on  place.loc_id = location.loc_id"
                                + " where (location.latitude between %.5f and %.5f) and (location.longitude between %.5f and %.5f);"
                                    , leftLatitude, rightlLatitude, leftLongitude, rightlLongitude);
        return  session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
    }*/

    public List<Map> getAllMeetsInCoordinates(Double leftLatitude, Double leftLongitude, Double rightlLatitude, Double rightlLongitude)
    {
        String sql = String.format(Locale.ENGLISH,"select meet.title as title, location.latitude as latitude, location.longitude as longitude, meet_id from meet" +
                                " inner join location on  meet.loc_id = location.loc_id" +
                                    " where (location.latitude between %.5f and %.5f)  and (location.longitude between %.5f and %.5f);"
                                        , leftLatitude, rightlLatitude, leftLongitude, rightlLongitude);
        return session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
    }

    public List<Map>  getAllUsersInCoordinates(Double leftLatitude, Double leftLongitude, Double rightlLatitude, Double rightlLongitude)
    {
        String sql = String.format(Locale.ENGLISH,"select user.name as name, location.latitude as latitude, location.longitude as longitude, user_id from user " +
                            "inner  join user_location on user_location.user_id = user.id inner join  location" +
                                "on location.loc_id = user_location.loc_id_extra " +
                                    " where (location.latitude between %.5f and %.5f )  and (location.longitude between %.5f and %.5f);"
                                        , leftLatitude, rightlLatitude, leftLongitude, rightlLongitude);
        return session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
    }


    private User getRandomUser() {
        Integer userId1 = random.nextInt((Integer) session.createQuery("select max(user.id) from User user").list().get(0)) ;
        if (userId1 == 0)
            userId1 = 10;
        User user = (User) session.createQuery("from User user where user.id = :userId").setParameter("userId", userId1).list().get(0);
        return user;
    }

    @Test
    public void getRan() {
        /*Integer userId1 = random.nextInt((Integer) session.createQuery("select max(user.id) from User user").list().get(0));
        User user = (User) session.createQuery("from User user where user.id = :userId").setParameter("userId", 8021).list().get(0);
        List<Location> loc = user.getLocations();

        System.out.println("Size: " + loc.size());*/
        List list = session.createQuery("select name,gender,photo from User user where user.id = 103213").list();
        System.out.println(list.size());
    }


    private void getUserLocationById(int userId) {
        List t = session.createQuery("select user.id from User user where user.id = :userId order by ").setParameter("userId", userId).list();
    }

    private Dialog getRandomDialog() {
        Integer maxDialog = (Integer) session.createQuery("select max(dialog.dialogId) from Dialog dialog").list().get(0);
        Dialog dialog = (Dialog) session.createQuery("from Dialog dialog where dialog.dialogId = :maxDialog")
                            .setParameter("maxDialog", random.nextInt(maxDialog));
        return dialog;
    }

    private Meet getRandomMeet() {
        Integer meetId = random.nextInt((Integer) session.createQuery("select max(meet.id) from Meet meet").list().get(0));
        if (meetId == 0)
            meetId = 10;
        Meet meet = (Meet) session.createQuery("from Meet meet where meet.id = :meet_id")
                .setParameter("meet_id", meetId).list().get(0);
        return meet;
    }

    private Place getRandomPlaces() {
        Integer placeId = random.nextInt((Integer) session.createQuery("select max(place.id) from Place place").list().get(0));
        if (placeId == 0)
            placeId = 100;
        Place place = (Place) session.createQuery("from Place place where place.id = :place_id")
                .setParameter("place_id", 3004).list().get(0);
        return place;
    }

    private Location getRandomLocation() {
        Location loc = new Location();
        loc.setLatitude((random.nextFloat() * 100) % 180);
        loc.setLongitude((random.nextFloat() * 100) % 180);
        loc.setTime(new Date());
        return  loc;
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }
}
