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
            <button @click="login" title="login"></button>
          </div>
        </div>

        <div v-else>
          <div class="for_forum">
            <button @click="forums" title="forums"></button>
          </div>
          <div class="for_fav">
            <button @click="userFav" title="favorite"></button>
          </div>
          <div class="for_cart">
            <button @click="userCart" title="cart"></button>
          </div>
          <div class="for_logout">
            <button @click="logout" title="logout"></button>
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
      <b-sidebar id="my-sidebar" title="Filters" shadow>
        <div class="px-3 py-2">
          <div class="body_for_filters">
            <form class="filters_form">
              <p>minPlayers: <input id="text_form" type="text" v-model.trim="filters.minPlayers" maxlength="2"><input
                  type="range" value="0" min="0" max="20" step="1" v-model="filters.minPlayers"></p>
              <p>maxPlayers: <input id="text_form" type="text" v-model.trim="filters.maxPlayers" maxlength="2"><input
                  type="range" value="20" min="2" max="20" step="1" v-model="filters.maxPlayers"></p>
              <p>recPlayers: <input id="text_form" type="text" v-model.trim="filters.recPlayers" maxlength="2"><input
                  type="range" min="0" max="20" step="1" v-model="filters.recPlayers"></p>
              <p>minPlayTime: <input title="min" id="text_form_min" type="text" v-model.trim="filters.minPlayTime"
                                     maxlength="5" value="0">min</p>
              <p>maxPlayTime: <input title="min" id="text_form_min" type="text" v-model.trim="filters.maxPlayTime"
                                     maxlength="5" value="60000">min</p>
              <p>minPublishYear: <input title="min" id="text_form_year" type="text"
                                        v-model.trim="filters.minPublishYear"
                                        maxlength="4" value="1969">year</p>
              <p>minPlayAge: <input id="text_form_age" type="text" v-model.trim="filters.minPlayAge"
                                    maxlength="2"><input
                  type="range" value="0" min="0" max="120" step="10" v-model="filters.minPlayAge"></p>
              <p>maxPlayAge: <input id="text_form_age" type="text" v-model.trim="filters.maxPlayAge"
                                    maxlength="3"><input
                  type="range" value="120" min="0" max="99" step="10" v-model="filters.maxPlayAge"></p>
              <p>difficult: <input id="text_form" type="text" v-model.trim="filters.difficult" maxlength="2"><input
                  type="range" min="1" max="5" step="1" v-model="filters.difficult"></p>
              <p>maxWeight: <input title="kg" id="text_form_min" type="text" v-model.trim="filters.maxWeight"
                                   maxlength="3" value="150">kg</p>
              <p>preferDesigner: <input type="text" v-model.trim="filters.preferDesigner"></p>
              <p>genre: <input type="text" v-model.trim="filters.genre"></p>
              <p>mechanic: <input type="text" v-model.trim="filters.mechanic"></p>
              <p>publisher: <input type="text" v-model.trim="filters.publisher"></p>
              <p>country: <input type="text" v-model.trim="filters.country"></p>
              <p>theme: <input type="text" v-model.trim="filters.theme"></p>
            </form>
          </div>
        </div>
      </b-sidebar>
    </div>

    <div class="body-table">
      <div id="games" v-for="item in games" :key="item.id" @click="showGame(item.id)">
        <div class="games_container">
          <p id="name_settings">{{ item.name }}</p>
          <img class="for_games_image" width="200px" height="178" v-bind:src="item.image">
          <p>{{ item.minPlayersNumber }} - {{ item.maxPlayersNumber }}</p>
          <p>{{ item.minPlayAge }} +</p>

          <div class="icon" id="add_to_cart">
            <button @click="showShops(item.id)"></button>
          </div>

          <div v-if="requestPermissionValue !== 'null'" class="back_for_button">
              <div v-if="item.id === get_gameId">
                <div v-if="shops_for_game !== null">
                  <div v-for="k in shops_for_game.prices" :key="k.id">
                    <div  class="shops_view" v-for="z in k" :key="z.id" @click="addToCart(item.id, z.id)">{{ z.name }}</div>
                  </div>
                </div>
              </div>

          </div>


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
      shops_for_game: null,
      get_gameId: null,
      games: null,
      settings: [],
      search: null,
      filters: {
        minPlayers: 0,
        maxPlayers: 20,
        recPlayers: null,
        minPlayTime: 0,
        maxPlayTime: 60000,
        minPublishYear: 1969,
        minPlayAge: 0,
        maxPlayAge: 100,
        difficult: null,
        maxWeight: 100,
        preferDesigner: null,
        genre: null,
        mechanic: null,
        publisher: null,
        country: null,
        theme: null
      }
    };
  },
  methods: {
    showGame(game_id){
      let msg = '/game_aggregator/game/'
      this.$router.push(msg + game_id)
    },
    showShops(game_id) {
      if (this.shops_for_game == null && this.get_gameId == null) {
        let msg3 = 'http://localhost:8083/game_aggregator/game/';
        axios
            .get(msg3 + game_id).then((res) => {
          this.shops_for_game = res.data
          this.get_gameId = game_id;
        })
      } else {
        this.shops_for_game = null;
        this.get_gameId = null
      }
    },
    addToCart(game_id, shop_id) {
      let msg1 = 'http://localhost:8083/game_aggregator/game/';
      let msg2 = '/add_cart';

      axios
          .post(msg1 + game_id + msg2, {
            login: localStorage.getItem("login"),
            shopId: shop_id,
            gameId: game_id
          })
    },
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
    forums() {
      this.$router.push({name: "forum-page"})
    },
    userFav() {
      this.$router.push({name: "favorite-page"})
    },
    searching() {
      let i;
      let s = "";
      for (i = 0; i < this.filters.length; i++) {
        if (this.filters[i].equal != null) {
          s = s + this.filters[i];
        }
      }
      axios
          .get('http://localhost:8083/game_aggregator/game', {params: {search: s}})
          .then((response) => {
            this.games = response.data;
          })
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