#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
#  untitled.py
#  
#  Copyright 2013 root <root@ubuntu>
#  
#  This program is free software; you can redistribute it and/or modify
#  it under the terms of the GNU General Public License as published by
#  the Free Software Foundation; either version 2 of the License, or
#  (at your option) any later version.
#  
#  This program is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.
#  
#  You should have received a copy of the GNU General Public License
#  along with this program; if not, write to the Free Software
#  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
#  MA 02110-1301, USA.
#  
#  
import MySQLdb
import random
import hashlib
import time
import datetime

boys = ['Аарон', 'Абай', 'Абдулла', 'Абзал', 'Абулхаир', 'Абылай', 'Адам', 'Адилет', 'Адиль', 'Адият', 'Адриан', 'Ажар', 'Азамат', 'Азат', 'Азиз', 'Азизбек', 'Азим', 'Айару', 'Айбар', 'Айбек', 'Айгиз', 'Айдай', 'Айдан', 'Айдар', 'Айдос', 'Айдын', 'Айзат', 'Айнур', 'Айрат', 'Айшат', 'Акбар', 'Акжан', 'Акжол', 'Аким', 'Акмаль', 'Алан', 'Алдар', 'Алдияр', 'Алекс', 'Александр', 'Алексей', 'Алем', 'Ален', 'Али', 'Алик', 'Алим', 'Алихан', 'Алишер', 'Алмаз', 'Алмас', 'Алмат', 'Альберт', 'Альмир', 'Альтаир', 'Альфред', 'Амаль', 'Аман', 'Амиль', 'Амин', 'Амир', 'Амиран', 'Амирхан', 'Анатолий', 'Анвар', 'Андрей', 'Ансар', 'Антон', 'Ануар', 'Арай', 'Арайлым', 'Арам', 'Ардак', 'Арзу', 'Ариет', 'Аристарх', 'Аркадий', 'Арлан', 'Арман', 'Армен', 'Арнур', 'Арсен', 'Арсений', 'Арсентий', 'Арслан', 'Артём', 'Артемий', 'Артур', 'Архип', 'Арыстан', 'Асет', 'Аскар', 'Аслан', 'Асхат', 'Афанасий', 'Ахмад', 'Ахмед', 'Ахмет', 'Ашот', 'Аяз', 'Аян', 'Аянат', 'Батыр', 'Бауыржан', 'Бахтияр', 'Бекболат', 'Бекзат', 'Бексултан', 'Богдан', 'Борис', 'Борислав', 'Бронислав', 'Булат', 'Вадим', 'Валентин', 'Валерий', 'Валерик', 'Василий', 'Вениамин', 'Вика', 'Виктор', 'Вильдан', 'Виталий', 'Владеслав', 'Владимир', 'Владислав', 'Влас', 'Всеволод', 'Вячеслав', 'Габриэль', 'Гавриил', 'Гаджи', 'Гаухар', 'Геворг', 'Геннадий', 'Георгий', 'Герман', 'Глеб', 'Гордей', 'Григорий', 'Гульмира', 'Давид', 'Давлат', 'Давыд', 'Далер', 'Дамиан', 'Дамиль', 'Дамир', 'Данель', 'Даниал', 'Даниел', 'Даниель', 'Даниил', 'Данил', 'Данила', 'Данило', 'Даниль', 'Данис', 'Даниэл', 'Даниэль', 'Даниял', 'Данияр', 'Дарий', 'Дархан', 'Дарын', 'Дарьяна', 'Дастан', 'Даулет', 'Даурен', 'Даян', 'Дементий', 'Демид', 'Демьян', 'Дени', 'Дениз', 'Денис', 'Джамал', 'Джамиль', 'Диас', 'Дидар', 'Димитрий', 'Динар', 'Динислам', 'Динияр', 'Дионис', 'Дияр', 'Дмитрий', 'Добрыня', 'Доминик', 'Досжан', 'Дулат', 'Дэвид', 'Дэниз', 'Евгений', 'Евсей', 'Егор', 'Елизар', 'Елисей', 'Елнур', 'Ерасыл', 'Ерболат', 'Еремей', 'Ержан', 'Ерлан', 'Ермек', 'Ернар', 'Ерофей', 'Ефим', 'Ефрем', 'Жан', 'Жангир', 'Жандос', '', 'Жанибек', 'Зангар', 'Заур', 'Захар', 'Захарий', 'Зуфар', 'Ибрагим', 'Ибрахим', 'Иван', 'Игнат', 'Игнатий', 'Игорь', 'Идрис', 'Илиан', 'Илия', 'Илларион', 'Ильгам', 'Ильгар', 'Ильгиз', 'Ильдар', 'Ильмир', 'Ильнар', 'Ильнур', 'Ильсур', 'Ильфат', 'Ильхам', 'Ильшат', 'Илья', 'Ильяс', 'Имран', 'Иннокентий', 'Инсаф', 'Иоанн', 'Иосиф', 'Ираклий', 'Иса', 'Искандар', 'Искандер', 'Ислам', 'Исмаил', 'Кайрат', 'Кайсар', 'Камиль', 'Канат', 'Карен', 'Карим', 'Каусар', 'Кевин', 'Керим', 'Ким', 'Кирилл', 'Клим', 'Климентий', 'Константан', 'Константин', 'Коркем', 'Кристиан', 'Кузьма', 'Лаврентий', 'Лев', 'Леван', 'Левон', 'Ленар', 'Лео', 'Леон', 'Леонард', 'Леонид', 'Лука', 'Лукас', 'Лукьян', 'Любомир', 'Ляйсан', 'Магомед', 'Мадияр', 'Майкл', 'Макар', 'Макс', 'Максат', 'Максим', 'Максимилиан', 'Малик', 'Мансур', 'Марат', 'Марк', 'Маркус', 'Марсель', 'Мартин', 'Матвей', 'Матфей', 'Микаил', 'Микаэль', 'Милан', 'Милолика', 'Мирас', 'Мирон', 'Мирослав', 'Митя', 'Михаил', 'Михаэль', 'Мстислав', 'Мурад', 'Мурат', 'Муса', 'Муслим', 'Мустафа', 'Мухаммад', 'Мухаммед', 'Надир', 'Назар', 'Назарий', 'Наиль', 'Нариман', 'Натан', 'Наташа', 'Наум', 'Нестор', 'Ник', 'Никита', 'Никола', 'Николай', 'Николас', 'Никон', 'Нияз', 'Нургали', 'Нуржан', 'Нурислам', 'Нурлан', 'Нурлыбек', 'Нурсултан', 'Олег', 'Олжас', 'Омар', 'Орест', 'Орхан', 'Оскар', 'Осман', 'Остап', 'Павел', 'Петр', 'Платон', 'Прохор', 'Равиль', 'Радик', 'Радим', 'Радислав', 'Радмир', 'Радомир', 'Раиль', 'Райнур', 'Райхан', 'Рамазан', 'Рамзан', 'Рамиль', 'Рамис', 'Ранель', 'Ранис', 'Расим', 'Растислав', 'Расул', 'Ратмир', 'Рауан', 'Рауль', 'Рауф', 'Раушан', 'Рафаэль', 'Рахат', 'Рахим', 'Рахман', 'Рашид', 'Раян', 'Реналь', 'Ренат', 'Ризван', 'Ринат', 'Рифат', 'Рихард', 'Ричард', 'Ришат', 'Рияз', 'Роберт', 'Родион', 'Ролан', 'Роман', 'Ростислав', 'Рубен', 'Рудольф', 'Рузаль', 'Руслан', 'Рустам', 'Рустем', 'Рушан', 'Сабир', 'Савва', 'Савелий', 'Саид', 'Салават', 'Салим', 'Салих', 'Самат', 'Самвел', 'Самир', 'Самуил', 'Санжар', 'Сармат', 'Саян', 'Саят', 'Светозар', 'Святогор', 'Святослав', 'Севастьян', 'Семен', 'Серафим', 'Сергей', 'Спартак', 'Станислав', 'Степан', 'Стефан', 'Сулейман', 'Султан', 'Сурен', 'Тагир', 'Таир', 'Талгат', 'Тамерлан', 'Тамир', 'Тамирлан', 'Тарас', 'Тахир', 'Теймур', 'Темирлан', 'Темур', 'Тигран', 'Тимерлан', 'Тимофей', 'Тимур', 'Тихон', 'Трофим', 'Улан', 'Умар', 'Усман', 'Фадей', 'Фарид', 'Фарис', 'Фархад', 'Федор', 'Феликс', 'Фидан', 'Филипп', 'Хаким', 'Хамза', 'Хасан', 'Хусейн', 'Чингиз', 'Чингис', 'Шамиль', 'Эдвард', 'Эдгар', 'Эдем', 'Эдуард', 'Элиана', 'Эльдар', 'Эльмар', 'Эльмир', 'Эмиль', 'Эмин', 'Эмир', 'Эрвин', 'Эрик', 'Эрнест', 'Юлиан', 'Юлий', 'Юнус', 'Юра', 'Юрий', 'Юсуф', 'Яков', 'Ян', 'Янис', 'Яромир', 'Ярослав', 'Ясин', 'Ясмин']
girls = ['Авелина','Авиталь','Агата','Аглая','Агнесса','Агния','Ада','Аделина','Аделия','Аделя','Адиля','Адина','Адия','Адэлина','Азалия','Азиза','Аида','Аиша','Айгерим','Айгуль','Айдана','Айжан','Айзада','Айзиля','Айлана','Айлин','Айлина','Айна','Айназ','Айнара','Айнура','Айсана','Айсулу','Айсылу','Айша','Айя','Акбота','Акерке','Акмарал','Аксиния','Аксинья','Алана','Алевтина','Алекса','Александра','Александрина','Алексия','Алена','Алеся','Алика','Алима','Алина','Алиса','Алисия','Алиша','Алия','Алла','Алсу','Алтана','Алтынай','Алуа','Альбина','Альвина','Альмира','Альфия','Амалия','Амели','Амелия','Амилия','Амина','Аминат','Амира','Анаит','Анара','Анастасия','Ангелина','Анелия','Анель','Анеля','Анжела','Анжелика','Анжелина','Аниса','Анисия','Анисья','Анита','Анна','Антонина','Анфиса','Анэля','Аня','Аполинария','Аполлинария','Ариадна','Ариана','Арианна','Арина','Арсения','Ару','Аружан','Асель','Асем','Асия','Асмик','Ася','Аурика','Афина','Аэлита','Аяна','Аяулым','Бажена','Балауса','Балжан','Балнур','Белла','Богдана','Божена','Ботагоз','Валентина','Валерия','Ванесса','Варвара','Василина','Василиса','Венера','Вера','Вероника','Веста','Виктория','Вилена','Виола','Виолета','Виолетта','Вита','Виталина','Виталия','Влада','Владислава','Владлена','Газиза','Галина','Галия','Гаяне','Глафира','Глория','Гузаль','Гузель','Гульдана','Гульназ','Гульнара','Гульшат','Дайана','Далила','Дамира','Дана','Данара','Даная','Даниела','Даниэла','Даниэлла','Дания','Дарига','Дарина','Дария','Дарья','Даша','Даяна','Джамиля','Джессика','Джулия','Джульетта','Диана','Дилара','Дильназ','Диля','Диляра','Дина','Динара','Доминика','Ева','Евангелина','Евгения','Евдокия','Евелина','Екатерина','Елдана','Елена','Елизавета','Елисавета','Еркежан','Есения','Жайна','Жамиля','Жанайым','Жанар','Жанель','Жания','Жанна','Жансая','Жасмин','Жибек','Жулдыз','Залина','Залия','Замира','Зара','Зарема','Зарина','Зере','Зиля','Зинаида','Злата','Златослава','Зоя','Зульфия','Иванна','Иветта','Изабелла','Илана','Илария','Илина','Илона','Ильвина','Ильмира','Ильназ','Ильнара','Иляна','Инара','Инга','Индира','Инесса','Инжу','Инкар','Инна','Иоанна','Иоланта','Ира','Ирада','Ирина','Ия','Калерия','Камила','Камилла','Камиля','Капитолина','Карима','Карина','Каріна','Каролина','Катарина','Катерина','Катрин','Катя','Кира','Кириена','Кристина','Ксения','Ксенья','Куаныш','Куралай','Лада','Лала','Лана','Лариса','Латифа','Лаура','Лейла','Лейсан','Леля','Лена','Лера','Леся','Лея','Лиана','Лида','Лидия','Лизавета','Лика','Лилиана','Лилит','Лилия','Лиля','Лина','Линара','Линда','Лия','Лола','Лолита','Луиза','Лэйла','Люба','Любава','Любовь','Людмила','Ляля','Мадина','Майя','Малика','Маргарита','Марго','Мари','Мариам','Марианна','Марика','Марина','Мария','Марта','Маруся','Марфа','Марья','Марьям','Марьяна','Махаббат','Медина','Мелания','Меланья','Мелиса','Мелисса','Меруерт','Мила','Милада','Милана','Милания','Милена','Милина','Милла','Милослава','Миляуша','Мира','Мирослава','Мирра','Мишель','Мия','Моника','Муслима','Мэри','Мээрим','Нагима','Надежда','Надия','Назгуль','Назерке','Назира','Назым','Наиля','Наргиз','Наргиза','Насиба','Настасья','Натали','Наталия','Наталья','Нафиса','Нелли','Нигина','Ника','Николетта','Николина','Николь','Нина','Нино','Нонна','Нурай','Нурия','Нурсулу','Оксана','Олександра','Олена','Олеся','Оливия','Ольга','Оля','Павла','Патимат','Паулина','Пелагея','Перизат','Полина','Рада','Радмила','Раиля','Раиса','Раксана','Ралина','Рамиля','Рамина','Рания','Раяна','Регина','Рената','Риана','Рианна','Римма','Рина','Рината','Рита','Роза','Розалина','Розалия','Роксана','Роксолана','Рузанна','Румия','Русалина','Руслана','Руфина','Сабина','Сабира','Сабрина','Саида','Салима','Салтанат','Самина','Самира','Сандра','Сания','Сара','Сафина','Сафия','Светлана','Святослава','Сева','Севинч','Сема','Серафима','Ситора','Снежана','Снежанна','Соня','Софи','София','Софья','Станислава','Стелла','Стефани','Стефания','Сусанна','Сымбат','Сюзанна','Таисия','Таисья','Тамара','Тамила','Таня','Татьяна','Тахмина','Тереза','Тогжан','Толганай','Томирис','Тоня','Ульяна','Устинья','Фаина','Фарида','Фариза','Фатима','Хадиджа','Хадижа','Христина','Шахноза','Шолпан','Шугыла','Шынар','Ыкылас','Эвелина','Эвилина','Эвита','Элана','Элен','Элеонора','Элиза','Элизабет','Элина','Элиф','Элла','Эллада','Эллина','Элона','Эльвина','Эльвира','Эльза','Эльмира','Эльнара','Эмили','Эмилия','Эмма','Энже','Эрика','Эсмира','Юлиана','Юлия','Юля','Юстина','Яна','Янина','Ярина','Ярослава','Ясмина']

start = datetime.datetime(1970, 01, 01, 0, 0, 0, 0)
end = datetime.datetime(2013, 01, 01, 0, 0, 0, 0)

def getRandomTimeStamp():
	random_unixtime = random.randrange(time.mktime(start.timetuple()),time.mktime(end.timetuple()))
	t = datetime.datetime.fromtimestamp(random_unixtime)
	return "now()"

class User:
	
	def __init__(self):
		self.userId = 0
		self.age = random.randint(0, 1) 
		
		if self.age == 0: 
			self.name = girls[random.randint(0, len(girls) - 1)]
		else:
			self.name = boys[random.randint(0, len(boys) - 1)]
		
		h = hashlib.sha1()
		h.update(self.name)	
		self.password = h.hexdigest()
		self.online = getRandomTimeStamp()
		self.token = 0
		self.expires = 0
		self.isExist = self.age
		
def getLocation():
	return random.uniform(-180, 180)
		
class Location:
	#latitude and longitude : -180 : 180 meridian
	def __init__(self):
		self.locId = 0 
		self.latitude =  getLocation()
		self.longitude = getLocation()
		self.dateTime = getRandomTimeStamp()

class UserLocation:
	#latitude and longitude : -180 : 180 meridian
	def __init__(self, nUsers, nLoc):
		self.locId = 0
		self.userId = random.randint(1, nUsers) 
		self.locIdExtra = random.randint(1, nLoc)
		
class Place:
	
	def __init__(self, nLoc, nUser, nWall):
		self.placeId = 0
		self.locId = random.randint(1, nLoc) 
		self.userId = random.randint(1, nUser) 
		self.wallId = random.randint(1, nWall) 
		self.title = girls[random.randint(0, len(girls) - 1)]
		self.description =  ""
		self.status = girls[random.randint(0, len(girls) - 1)]
		self.img = ""
		
class Wall:
	
	def __init__(self, numUsers):
		self.wallId = 0 
		self.userId = random.randint(1, numUsers) 
		self.dateTime = getRandomTimeStamp()
		self.msg = ""
		
class Messanger:
	
	def __init__(self, numDialogs):
		self.msgId = 0
		self.dialogId = random.randint(1, numDialogs) 
		self.msg = ""
		self.dateTime = getRandomTimeStamp() 
		self.isRead = random.randint(0, 1) 
		
class Dialog:
	
	def __init__(self):
		self.dialogId = 0
		self.title = boys[random.randint(0, len(boys) - 1)]
		
class DialogUser:
	
	def __init__(self, numDialogs, numUsers):
		self.dialogUserId = 0
		self.dialogId = random.randint(1, numDialogs)
		self.userId = random.randint(1, numUsers)

class LikeMeet:
	
	def __init__(self, nMeet, nUser):
		self.likeId = 0
		self.userId = random.randint(1, nUser)
		self.meetId = random.randint(1, nMeet)
		self.like = 1

class AccessFriends:
	
	def __init__(self, nMeet, nUser):
		self.accessId = 0
		self.meetId = random.randint(1, nMeet)
		self.userId = random.randint(1, nUser)
		
class Inviters:
	
	def __init__(self, nMeet, nUser):
		self.inviteId = 0
		self.meetId = random.randint(1, nMeet) 
		self.userIdFrom = random.randint(1, nUser) 
		self.userUserTo = random.randint(1, nUser)
		while (self.userUserTo == self.userIdFrom):
			self.userUserTo = random.randint(0, 1000)
		self.isInvite = random.randint(0, 1)
		self.isAdmin = random.randint(0, 1)
		self.lastUpdate = getRandomTimeStamp()
		self.isChange = random.randint(0, 1)
		
class Friend:
	
	def __init__(self, nUser):
		self.friendId = 0
		self.userId1 = random.randint(1, nUser) 
		self.userId2 = random.randint(1, nUser) 
		while (self.userId1 == self.userId2):
			self.userId2 = random.randint(0, nUser)

class Meet:
	
	def __init__(self, nLoc, nWall):
		self.meetId = 0
		self.locId = random.randint(1, nLoc) 
		self.wallId = random.randint(1, nWall) 

		self.age = random.randint(0, 1) 
		
		if self.age == 0: 
			self.title = girls[random.randint(0, len(girls) - 1)]
		else:
			self.title = boys[random.randint(0, len(boys) - 1)]
		
		self.description = "" 
		self.photo = ""
		self.dateTime = getRandomTimeStamp()
		self.access = "'" + random.choice(['friends','public','private','all_friends']) +  "'"
		self.status = girls[random.randint(0, len(girls) - 1)] + boys[random.randint(0, len(boys) - 1)]
		self.lastUpdate = getRandomTimeStamp()
		self.whatChange = "" 

class MySql:

	def __init__(self, a):
		self.db = MySQLdb.connect(host="localhost", user="geo", passwd="ge0123", db="geo", charset='utf8')
		self.i = 0
		self.cursor = self.db.cursor()
		print ("Welcome to %s" % a)
	
	def createUsers(self, num):
		for i in range(0, num):
			u = User()
			t = self.insertData('user', ['NULL', "'" + u.name + "'", "'" + u.password + "'", u.age, u.online, u.token, u.expires, u.isExist])
			if t == 0:
				return 0
		print ("Created {0} users".format(num))

	def createLocations(self, num):
		for i in range(0, num):
			l = Location()
			self.insertData('location', ['NULL', l.latitude, l.longitude, l.dateTime])
		print ("Created {0} locations".format(num))
		
	def createUserLocation(self, num, nUsers, nLoc):
		for i in range(0, num):
			l = UserLocation(nUsers, nLoc)
			self.insertData('user_location', ['NULL', l.userId, l.locIdExtra])
		print ("Created {0} user_locations".format(num))
		
	def createPlaces(self, num, nLoc, nUser, nWall):
		for i in range(0, num):
			p = Place(nLoc, nUser, nWall)
			self.insertData('place', ['NULL', p.locId, p.wallId, p.userId, "'" + p.title + "'", "'" + p.description + "'", "'" + p.status + "'", "'" + p.img + "'"])
		print ("Created {0} places".format(num))
		
	def createWall(self, num, numUsers):
		for i in range(0, num):
			w = Wall(numUsers)
			self.insertData('wall', ['NULL', w.userId, w.dateTime, "'" + w.msg + "'"])
		print ("Created {0} wall".format(num))
	
	def createMessanger(self, num, numDialods):
		for i in range(0, num):
			m = Messanger(numDialods)
			self.insertData('messanger', ['NULL', m.dialogId, "'" + m.msg + "'", m.dateTime, m.isRead])
		print ("Created {0} messanger".format(num))
	
	def createDialog(self, num):
		for i in range(0, num):
			d = Dialog()
			self.insertData('dialog', ['NULL', "'" + d.title + "'"])
		print ("Created {0} dialog".format(num))
	
	def createDialogUser(self, num, numDialogs,numUsers):
		for i in range(0, num):
			du = DialogUser(numDialogs, numUsers)
			self.insertData('dialog_user', ['NULL', du.dialogId, du.userId])
		print ("Created {0} dialoguser".format(num))
	
	def createMeets(self, num, nLoc, nWall):
		for i in range(0, num):
			m = Meet(nLoc, nWall)
			self.insertData('meet', ['NULL', m.locId, m.wallId, "'" + m.title + "'", "'" + m.description + "'", "'" + m.photo + "'",  m.dateTime, m.access, "'" + m.status + "'", m.lastUpdate, "'" + m.whatChange + "'"])
		print ("Created {0} meet".format(num))

	def createLikeMeet(self, num, nMeet, nUser):
		for i in range(0, num):
			lm = LikeMeet(nMeet, nUser)
			self.insertData('like_meet', ['NULL', lm.userId, lm.meetId, lm.like])
		print ("Created {0} like meet".format(num))

	def createAccessFriends(self, num, nMeet, nUser):
		for i in range(0, num):
			af = AccessFriends(nMeet, nUser)
			self.insertData('access_friends', ['NULL', af.meetId, af.userId])
		print ("Created {0} access friends".format(num))
	
	def createInviters(self, num, nMeet, nUser):
		for i in range(0, num):
			inv = Inviters(nMeet, nUser)
			self.insertData('inviters', ['NULL', inv.meetId, inv.userIdFrom, inv.userUserTo, inv.isInvite, inv.isAdmin, inv.lastUpdate, inv.isChange])
		print ("Created {0} inviters".format(num))
	
	def createFriends(self, num,  nUsers):
		for i in range(0, num):
			f = Friend(nUsers)
			self.insertData('friends', ['NULL', f.userId1, f.userId2])
		print ("Created {0} friends".format(num))
	
	
	def insertData(self, table, values):
		val = ""
		for value in values:
			val += str(value) + ","
		val = val[0 : len(val) - 1]
		sql = """INSERT INTO {0} VALUES({1})""".format(table, val)
		try:
			self.cursor.execute(sql)
		except Exception as err:
			print (err)
			print(sql)
		if self.i == 500:
			self.db.commit()
			self.i = 0
		self.i += 1;
		return sql
		
	def getCountData(self, table):
		sql = """select count(*) from {0}""".format(table)
		self.cursor.execute(sql)
		self.db.commit()
		return int(self.cursor.fetchone()[0])
		
def main():
	m = MySql('Geo')
	million = 1000
	
	#Create Users
	
	m.createUsers(million)
	nUsers = m.getCountData("user")
	print("Users {0}".format(nUsers))
	
	#Friends
	m.createFriends(million, nUsers)
	
	# Message System
	
	m.createDialog(million)
	nDialogs = m.getCountData("dialog")
	print("Dialogs {0}".format(nDialogs))
	
	m.createMessanger(million, nDialogs)
	m.createDialogUser(million, nDialogs, nUsers)
	
	#Locations
	m.createLocations(million)
	nLoc = m.getCountData("location")
	
	#User Locations
	m.createUserLocation(million, nUsers, nLoc)
	nUserLoc = m.getCountData("user_location")
	
	#Wall
	m.createWall(million, nUsers)
	nWall = m.getCountData("wall")
	
	print("Count(Wall):{0} Count(Loc):{1}".format(nWall, nLoc))
	#Meet
	m.createMeets(million, nUserLoc, nWall)
	nMeet = m.getCountData("meet")
	
	#Inviters
	m.createInviters(million, nMeet, nUsers)
	
	#AccessFriends
	m.createAccessFriends(million, nMeet, nUsers)
	
	#Like_Meet
	m.createLikeMeet(million, nUsers, nMeet)
	
	# Place
	m.createPlaces(million, nLoc, nUsers, nWall)
	return 0

if __name__ == '__main__':
	main()

