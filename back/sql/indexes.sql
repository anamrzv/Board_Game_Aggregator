--Индексы для ускорения select-запросов по несортированным атрибутам

--Для еженедельных рассылок: SELECT mail FROM Users WHERE wants_mailing = true
CREATE INDEX Mailing_Index ON Users (wants_mailing) WHERE wants_mailing = true;

CREATE INDEX User_Role_Index ON Users (user_role);

--Вывести для игры список магазинов, где она есть
CREATE INDEX Shop_Index ON Game_to_Shop (Game_ID);

--Выбрать все адреса магазинов в городе покупателя
CREATE INDEX City_Index ON Address (City);

--Выбрать ВИП магазины для продвижения
CREATE INDEX Vip_Shop_Index ON Shop (Shop_Type_ID) WHERE Shop_Type_ID = 2;

--Выбрать все игры этого жанра/механики
CREATE INDEX Genre_Index ON Board_Game (Genre_ID);
CREATE INDEX Mechanics_Index ON Board_Game (Mechanics_ID);

--Выбрать все игры по этой теме
CREATE INDEX Theme_Index ON Game_to_Theme (Theme_ID);
