--Заполние таблиц данными
insert into Shop_Type (Name)
values ('Стандартный'), ('VIP');

insert into Shop (Name, Shop_Type_ID, Address, City, Phone)
values ('GaGa',1,'м. Маяковская, Невский, 69', 'Спб', '313-26-44 многоканальный'),
       ('Буквоед',1,'м. Пионерская, Коломяжский, 26', 'Спб', '313-26-44 многоканальный'),
       ('GaGa',1,'м. Дыбенко, ул. Дыбенко, 24 к.1', 'Спб', '313-26-44 многоканальный'),
       ('GaGa',1,'м. Купчино, Балканская пл., 5 О', 'Спб', '313-26-44 многоканальный'),
       ('GaGa',1,'м. Пр-т Просвещения, Хошимина, 13 к.1', 'Спб', '313-26-44 многоканальный'),
       ('Республика',2,'м. Павелецкая, ул. Кожевническая, д.1, стр.1, 13 к.1', 'Москва', '+7 (499) 444-33-67 (доб. 222)'),
       ('Республика',2,'м. ЦСКА, Ходынский бульвар, 4 (ТРЦ «Авиапарк»)', 'Москва', '+7 (499) 444-33-67 (доб. 237)'),
       ('Республика',2,'м. Курская, Чкаловская, ул. Земляной вал, 33 (ТЦ «Атриум», 3-й этаж)', 'Москва', '+7 (499) 444-33-67 (доб. 230)'),
       ('Республика',2,'м. Дмитровская, ул. Большая Новодмитровская, 36 (Дизайн-завод FLACON)', 'Москва', '+7 (499) 444-33-67 (доб. 206)'),
       ('Республика',2,'м. Фрунзенская, Комсомольский пр-т, 24, стр. 1 (ТЦ «К24»)', 'Москва', '+7 (499) 444-33-67 (доб. 209)');

insert into Game_Genre (Name)
values ('Абстрактная стратегия'),
       ('Соревновательная стратегия'),
       ('Кооперативная стратегия'),
       ('Для вечеринок'),
       ('Социальная'),
       ('Стратегия "1 против всех"'),
       ('Развивающая'),
       ('Eurogame/Экономическая стратегия'),
       ('Скрытый предатель'),
       ('Ролевая'),
       ('Головоломка');

insert into Game_Theme (Name)
values ('Постаппокалипсис'),
       ('Киберпанк'),
       ('Мода'),
       ('Вечеринка'),
       ('18+'),
       ('Объяснение слов'),
       ('Разговоры'),
       ('Животные'),
       ('Экономика'),
       ('Война'),
       ('Юмор');

insert into Game_Mechanics (Name)
values ('Карточная'),
       ('Кубики'),
       ('Социальная'),
       ('Расположение объектов в пространстве'),
       ('Расчёт'),
       ('Проверь удачу'),
       ('Сбор коллекции/сета'),
       ('Аукцион'),
       ('Война');

insert into Game_Publisher (Name, Country)
values ('Экономикус','Россия'),
       ('Hasbro', 'Ирландия');

insert into Board_Game (Name, Genre_ID, Image, Video, Description, Max_Players, Min_Players, Recommended_Players, Min_Playtime, Max_Playtime, Year_Publish, Designers, Min_Age, Max_Age, Mechanics_ID, Difficultness, Weight, Wishlist, Own, Publisher)
values ('Монополия: Россия', 8, 'https://www.igrotime.ru/upload/t/800-700im/large_foto/monopolija-rossija.jpg', 'https://www.youtube.com/watch?v=2aHb_2Dt72Y', 'Классическая "Монополия", выполненная специально для нашей страны. На игровом поле – названия городов России, иллюстрация изображают памятники культуры и архитектуры. Правила же полностью идентичны обычной "Монополии" – торгуйте недвижимостью, избегите банкротства и достигните вершин финансового успеха, обойдя конкурентов!', 6, 2, 4, 60,120, 2017, null, 6, 99, 2, 4, 920, 0, 0, 2),
       ('Бункер', 6, 'https://avatars.mds.yandex.net/get-mpic/5251502/img_id827146618041726999.png/orig', 'https://www.youtube.com/watch?v=kfZR0JtJaHQ', 'Случилось страшное: глобальная катастрофа, конец света, апокалипсис – кому как больше нравится. Чтобы выжить, вам срочно нужно попасть в бункер, но в вашей группе слишком много людей, на всех внутри не хватит ни провизии, ни места. А значит кому-то придётся остаться снаружи и пытаться выжить там. Вы же не хотите скитаться по отравленным пустошам в поисках глотка воды, которая ещё и может оказаться отравленной или радиоактивной? Тогда постарайтесь убедить остальных, что без вас им никак не выжить! Внутрь попадут только самые полезные члены группы. Вы получаете случайный набор карт, который определяет ваш пол, возраст, состояние здоровья, профессию и другие черты, а ваша задача – убедить остальных, что этот набор характеристик весьма и весьма эффективен. Как убедить остальных, что ваше гомеопатическое хобби принесёт им пользу? Попробуйте и узнаете, а заодно и прокачаете навык ораторского мастерства!', 16, 4, 8, 20, 40, 2020, NULL, 18, 99, 1, 3, 1100, 0, 0, 1);

insert into Game_to_Theme (Board_ID, Theme_ID)
values (1, 9),
       (2, 1),
       (2, 5),
       (2, 7);

insert into User_Role (Name)
values ('ROLE_USER'),
       ('ROLE_ADMIN'),
       ('ROLE_SHOP');

insert into Users (Login, Hashed_Password, Mail, Wants_Mailing, Salt, User_Role, Shop)
values ('ana.mrzv', '6f8bebb898b8fdbca67cef240e9be19f21d675c2', 'test@mail.ru', true, 'h0wt0d011ph3lp!', 2, null),
       ('dasxunya', '8855c150d3632240ed543f9544bee77a6ddaae92', 'test2@mail.ru', false, 'h0wt0d011ph3lp?', 2, null),
       ('yuko', '1w0ntt3lluh0wxex', 'yuko@mail.ru', true, 'aoaoaoaoaoaoaoa', 1, null),
       ('serge', 'xicpwndyjhget666', 'serge@mail.ru', true, 'qwertyuiopasdfg', 1, null),
       ('shop1', '4bf32cc8d6b13d3a4ffbff034f233a498923d2a2', 'shop@mail.ru', false, 'qwertyuiopasdfg', 3, 'GaGa');

insert into Game_Comment (Game_ID, User_Login, Content)
values (1, 'yuko', 'Лучшая игра в моей жизни, напишу ее реализацию на Си'),
       (2, 'serge', 'Неплохо');

insert into Game_to_Shop (Game_ID, Shop_ID, Price)
values (1,1,5327),
       (1,2,5200),
       (2,3,2870),
       (2,7,2870);

insert into Forum_Topic (Name)
values ('Посоветуйте игру для троих человек'),
       ('Лучшая игра для программиста?');

insert into Topic_Comment (Forum_ID, User_Login, Content)
values (1, 'serge', 'Сам задаюсь этим вопросом....'),
       (2, 'yuko', 'Разработанная им самим');

insert into Carts_of_Users (User_Login, Game_ID, Shop)
values ('yuko', 1, 1),
       ('serge', 2, 3);

insert into Favorites_of_Users (User_Login, Game_ID)
values ('yuko', 2),
       ('serge', 1),
       ('serge', 2);

insert into Users_Fav_Forums (User_Login, Forum_ID)
values ('yuko', 1),
       ('yuko', 2),
       ('serge', 1),
       ('serge', 2);