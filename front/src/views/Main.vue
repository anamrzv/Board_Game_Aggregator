<template>
  <div>
    <div id="header">

    </div>

    <div class="body-table">
      <div id="games" v-for="item in games" :key="item.id">
        {{ item }}
      </div>
    </div>

  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "main",
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
</style>