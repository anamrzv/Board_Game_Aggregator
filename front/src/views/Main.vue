<template>
  <div>
    <div id="header"></div>

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
<!--          TODO: фильтры сделать и привязать, create style for filters in css file-->
          <div class="body_for_filters">
            <p>minPlayers: <input type="range"></p>
          </div>


        </div>
      </b-sidebar>
    </div>

    <!--    <div v-for="i in games" :key="i.id">-->
    <!--      {{i}}-->

    <!--      <p>min players<input type="range" min="1" max="20" value="2"></p>-->
    <!--    </div>-->


    <div class="body-table">
      <div id="games" v-for="item in games" :key="item.id">
        <div class="games_container">
          <p id="name_settings">{{ item.name }}</p>
          <img class="for_games_image" width="200px" height="178" v-bind:src="item.image">
          <p>{{ item.minPlayersNumber }} - {{ item.maxPlayersNumber }}</p>
          <p>{{ item.minPlayAge }} +</p>
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
      games: null,
      settings: []
    };
  },
  methods: {
    getGames() {
      axios
          .get('http://localhost:8083/game_aggregator/game')
          .then((response) => {
            this.games = response.data;
          })
    },
    searching() {
      axios
          .get('')
    },
    logout() {
      this.$swal.fire({
        icon: "success",
        text: "До встречи!",
        title: "Вы успешно вышли",
      });
      this.$router.push({name: "auth-page"}, () => localStorage.clear());
    }
  },
  mounted() {
    this.getGames();
  }
}
</script>

<style>
@import "../assets/main.css";
@import "../assets/fonts.css";
</style>