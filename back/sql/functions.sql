--Подсчет количества различных игр на сайте
create or replace function different_board_count() returns integer as $$
declare
    cnt integer;
begin
    select count(id) into cnt from board_game;
    return cnt;
end;
$$ language plpgsql;

--Проверить наличие дешевых игр
create or replace function cheap_board_game() returns integer as $$
declare
    cnt integer;
begin
    select count(id) into cnt from board_game
        join game_to_shop on board_game.id = game_to_shop.game_id
                              where game_to_shop.price<500;
    return cnt;
end;
$$ language plpgsql;
