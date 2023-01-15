<template>
  <div>
    <!--    шапочка   -->
    <div id="header">

      <div class="icons_container">
        <div v-if="requestPermissionValue === 'null'">
          <div class="for_forum">
            <button class="invisible_forum" title="needed login"></button>
          </div>
          <div class="for_fav">
            <button class="invisible_fav" title="needed login"></button>
          </div>
          <div class="for_cart">
            <button class="invisible_cart" title="needed login"></button>
          </div>
          <div class="for_login">
            <button @click="login"></button>
          </div>
        </div>

        <div v-else>
          <div class="for_forum">
            <!--            TODO: обработать и сделать вьюшки для форума, корзины-->
            <button @click="forumes"></button>
          </div>
          <div class="for_fav">
            <button @click="userFav"></button>
          </div>
          <div class="for_cart">
            <button @click="userCart"></button>
          </div>
          <div class="for_logout">
            <button @click="logout"></button>
          </div>
        </div>
      </div>


    </div>

    <div class="search_row">
      <div class="d2">
        <form>
          <input type="text" placeholder="Search me...">
          <button id="search" @click="searching"></button>
        </form>
      </div>
      <div class="for_filter" title="show filters">
        <b-button v-b-toggle.my-sidebar></b-button>
      </div>
    </div>


    <div>
      <!--      <div>-->
      <!--        <b-button v-b-toggle.my-collapse>Переключатель свернутого содержимого</b-button>-->
      <!--        <b-button v-b-toggle.my-sidebar></b-button>-->
      <!--      </div>-->

      <!--      <b-collapse id="my-collapse">-->
      <!--        <b-card title="Сворачиваемая карточка">-->
      <!--          Привет мир!-->
      <!--        </b-card>-->
      <!--      </b-collapse>-->

      <b-sidebar id="my-sidebar" title="Filters" shadow>
        <div class="px-3 py-2">
          <!--          TODO: фильтры сделать и привязать, create style for filters in css file, add field for hand input-->
          <div class="body_for_filters">
            <form>
              <p>minPlayers: <input type="range"></p>
              <p>maxPlayers: <input type="range"></p>
              <p>recPlayers: <input type="range"></p>
              <p>minPlayTime: <input type="range"></p>
              <p>maxPlayTime: <input type="range"></p>
              <p>minPublishYear: <input type="range"></p>
              <p>minPlayAge: <input type="range"></p>
              <p>maxPlayAge: <input type="range"></p>
              <p>difficult: <input type="range"></p>
              <p>weight: <input type="range"></p>
              <p>preferDesigner: <input type="text"></p>
              <p>genre: <input type="text"></p>
              <p>mechanic: <input type="text"></p>
              <p>publisher: <input type="text"></p>
              <p>country: <input type="text"></p>
              <p>theme: <input type="text"></p>
            </form>
          </div>
        </div>
      </b-sidebar>
    </div>

    <!--    для просмотра возвращаемых значений-->
    <!--        <div v-for="i in games" :key="i.id">-->
    <!--          {{i}}-->
    <!--        </div>-->


    <div class="body-table">
      <div id="games" v-for="item in games" :key="item.id">
        <div class="games_container">
          <p id="name_settings">{{ item.name }}</p>
          <img class="for_games_image" width="200px" height="178" v-bind:src="item.image">
          <p>{{ item.minPlayersNumber }} - {{ item.maxPlayersNumber }}</p>
          <p>{{ item.minPlayAge }} +</p>
          <!--          добавить количество людей, которым понравилась игра||фирма-->
        </div>
      </div>

    </div>

  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Main",
  data() {
    return {
      requestPermissionValue: null,
      games: null,
      settings: []
    };
  },
  methods: {
    userCart() {
      this.$router.push({name: "cart-page"})
    },
    getGames() {
      axios
          .get('http://localhost:8083/game_aggregator/game')
          .then((response) => {
            this.games = response.data;
          })
    },
    forumes() {
      this.$router.push({name: "forum-page"})
    },
    userFav() {
      this.$router.push({name: "favorite-page"})
    },
    searching() {
      axios
          .get('')
    },
    login() {
      this.$router.push({name: "user-page"})
    },
    logout() {
      this.$swal.fire({
        icon: "success",
        text: "До встречи!",
        title: "Вы успешно вышли",
      });
      this.$router.push({name: "auth-page"}, () => localStorage.setItem('jwt', null));
    }
  },
  mounted() {
    this.getGames();
  },
  created() {
    this.requestPermissionValue = localStorage.getItem('jwt')
  }
}
</script>

<style>
@import "../assets/main.css";
@import "../assets/fonts.css";
</style>