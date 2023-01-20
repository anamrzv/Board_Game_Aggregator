<template>
  <div>
    <div>
      <div id="header">
        <div class="icons_container">
          <div class="icon" id="back_icon">
            <button @click="goHome" title="homepage"></button>
          </div>
          <div class="icon" id="logout_icon">
            <button @click="logout" title="logout"></button>
          </div>
        </div>
      </div>
    </div>

    <p class="cyber">about</p>
    <div v-if="game_info !== null">
      <div class="game-body">
        <div><p class="cyber" id="game_header">{{ game_info.game.name }}</p>

          <div class="piar">
            <div>у <b>{{ game_info.game.wishlistCount }}</b> людей в избранном</div>
            <div>купили <b>{{ game_info.game.ownCount }}</b> раз</div>
          </div>


          <div class="main_info">
            <img width="450px" height="406" v-bind:src="game_info.game.image" class="leftimg">
            <div class="description">
              <div class="icon_p" id="icon_addGameToFav" title="add to favorite">
                <button @click="addGameToFav"></button>
              </div>
              <b>Свойства:</b><br>Минимум игроков: <b>{{ game_info.game.minPlayersNumber }}</b>
              <br>
              Максимум игроков: <b>{{ game_info.game.maxPlayersNumber }}</b>
              <br>
              Рекомендовано игроков: <b>{{ game_info.game.recPlayersNumber }}</b>
              <br>
              Минимальная продолжительность игры: <b>{{ game_info.game.minPlayTime }} мин</b>
              <br>
              Максимальная продолжительность игры: <b>{{ game_info.game.maxPlayTime }} мин</b>
              <br>
              Год выпуска: <b>{{ game_info.game.publishYear }}</b>
              <br>
              Дизайнеры: <b>{{ game_info.game.designers }}</b>
              <br>
              Минимальный возраст: <b>{{ game_info.game.minPlayAge }}</b>
              <br>
              Максимальный возраст: <b>{{ game_info.game.maxPlayAge }}</b>
              <br>
              Сложность (от 1 до 5): <b>{{ game_info.game.difficultness }}</b>
              <br>
              Вес: <b>{{ game_info.game.weight }} г</b>
              <br>
              Жанр: <b>{{ game_info.game.genre.name }}</b>
              <br>
              Механика: <b>{{ game_info.game.mechanics.name }}</b>
              <br>
              Производитель: <b>{{ game_info.game.publisher.name }} ({{ game_info.game.publisher.country }})</b>
              <br>
              Темы: <b v-for="item in game_info.game.themes" :key="item.id">{{ item.name }} </b>
            </div>

            <div class="for-buy">
              <div class="showPrice">
                <b-button v-b-toggle.collapse-1 class="buy_header">Price</b-button>
                <br>
                <b-collapse id="collapse-1">
                  <div v-for="i in game_info.prices" :key="i.id" class="prices">
                    <div>{{ i.shop.name }}</div>
                    <div>{{ i.price }}</div>
                  </div>
                </b-collapse>
              </div>


            </div>
          </div>

          <div class="add_info">
            <div class="opisanie">
              <b>Описание:</b><br>
              <p>{{ game_info.game.description }}</p>
              <video controls width="100%" v-bind:src="game_info.game.video"/>
            </div>
          </div>
        </div>

        <!--здесь есть нформация о поставщиках, пригодится для страницы платежа в будущем-->
        <!--        <div>-->
        <!--          {{ game_info.prices }}-->
        <!--        </div>-->
      </div>
    </div>
    <p class="cyber">comments</p>

    <div v-if="game_info" class="game-body" id="com">
      <div v-for="item in game_info.comments" :key="item.id">
        <div class="comments_container">
          <div class="username">{{ item.userId }}</div>
          <div class="comment">{{ item.content }}</div>
        </div>
        <div class="for_date">{{ item.date }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Game",
  data() {
    return {
      game_info: null
    }
  },
  methods: {
    addGameToFav() {
      let msg1 = 'http://localhost:8083/game_aggregator/game/'
      let msg2 = '/add_fav'
      axios
          .post(msg1 + this.$data.game_info.game.id + msg2, {
            login: localStorage.getItem('login'), password: "", mail: "", mailPreferences: ""
          })
    },
    getGameInfo() {
      let game_msg = 'http://localhost:8083/game_aggregator/game/'
      axios
          .get(game_msg + localStorage.getItem("game_id")).then((res => this.game_info = res.data))
    },
    goHome() {
      this.$router.push({name: "main"})
    },
    logout() {
      this.$swal.fire({
        icon: "success",
        text: "До встречи!",
        title: "Вы успешно вышли",
      });
      this.$router.push({name: "auth-page"}, () => localStorage.setItem('jwt', null));
    },
  },
  mounted() {
    this.getGameInfo()
  }
}
</script>

<style scoped>
@import "../assets/game.css";
@import "../assets/fonts.css";
</style>