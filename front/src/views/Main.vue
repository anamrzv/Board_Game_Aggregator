<template>
  <div>
    <div id="header"></div>

    <div class="search_row">
    <div class="d2">
      <form>
        <input type="text" placeholder="Искать здесь...">
        <button @click="searching"></button>
      </form>
    </div>
      <div class="for_filter">
        <button></button>
      </div>
    </div>

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
      games: null
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