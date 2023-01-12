-- --Триггер на максимальное количество любимых форумов (10)
-- create or replace function LovedForums() returns trigger as
-- $$
-- begin
--     if (select count(forum_id) from users_fav_forums group by user_id) > 10
--     then
-- delete
-- from users_fav_forums
-- where user_id = new.user_id
--   and forum_id = new.forum_id;
-- raise exception 'Превышено максимальное количество избранных форумов';
-- end if;
-- end;
-- $$ language plpgsql;

drop trigger if exists "CheckLovedForums" on users_fav_forums;
create trigger CheckLovedForums
    after insert
    on users_fav_forums
    for each row
    execute procedure LovedForums();


--Сделать магазины сети VIP
CREATE OR REPLACE FUNCTION ChangeAllShopsStatus() RETURNS TRIGGER AS
$$
begin
    update Shop set Shop_Type_ID = new.Shop_Type_ID where Name = new.Name;
    return null;
end;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS "ShopUpdated" ON Shop;
CREATE TRIGGER ShopUpdated
    AFTER UPDATE
    ON Shop
    FOR EACH ROW WHEN ((OLD.Shop_Type_ID) IS DISTINCT FROM (NEW.Shop_Type_ID))
EXECUTE PROCEDURE ChangeAllShopsStatus();


--Повысить счетчик покупок у игры
CREATE OR REPLACE FUNCTION IncOwnCounter() RETURNS TRIGGER AS
$$
begin
    update Board_Game set Own = Own + 1 where ID = new.Game_ID;
    return null;
end;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS "GameWasBought" ON Carts_of_Users;
CREATE TRIGGER GameWasBought
    AFTER UPDATE
    ON Carts_of_Users
    FOR EACH ROW WHEN ((OLD.Date_of_buy) IS DISTINCT FROM (NEW.Date_of_buy))
EXECUTE PROCEDURE IncOwnCounter();


--Повысить счетчик вишлиста у игры
CREATE OR REPLACE FUNCTION IncWishCounter() RETURNS TRIGGER AS
$$
begin
    update Board_Game set Wishlist = Wishlist + 1 where ID = new.Game_ID;
    return null;
end;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS "GameWasWished" ON Favorites_of_Users;
CREATE TRIGGER GameWasWished
    AFTER INSERT
    ON Favorites_of_Users
    FOR EACH ROW
EXECUTE PROCEDURE IncWishCounter();

--Понизить счетчик вишлиста у игры
CREATE OR REPLACE FUNCTION DecWishCounter() RETURNS TRIGGER AS
$$
begin
    update Board_Game set Wishlist = Wishlist - 1 where ID = old.Game_ID;
    return null;
end;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS "GameWasUnwished" ON Favorites_of_Users;
CREATE TRIGGER GameWasUnwished
    AFTER DELETE
    ON Favorites_of_Users
    FOR EACH ROW
EXECUTE PROCEDURE DecWishCounter();