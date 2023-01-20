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

    <p class="cyber"></p>
    <div>{{ game_info }}</div>

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
    getGameInfo() {
      axios
          let game_msg = 'http://localhost:8083/game_aggregator/game'
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

</style>