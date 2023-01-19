<template>
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

    <p class="cyber">forum</p>

    <div class="forum-table" id="forum_view" v-for="item in forums" :key="item.id" @click="showForum(item.id)">
      <div>{{ item.name }}</div>
<!--      <div v-if="forum_id !== null && forum_id === item.id">-->
<!--        <div class="comments" v-for="i in comments" :key="i.id">-->
<!--          {{ i }}-->
<!--        </div>-->
<!--      </div>-->
    </div>


  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Forum",
  data() {
    return {
      forum_id: null,
      forums: null
    }
  },
  methods: {
    showForum(id) {
      this.$data.forum_id = id;
      let msg = '/game_aggregator/forum/'
      this.$router.push(msg + this.forum_id, () => localStorage.setItem('idForum', this.$data.forum_id))
    },
    getForums() {
      axios
          .get('http://localhost:8083/game_aggregator/forum/')
          .then((response) => {
            this.forums = response.data;
          })
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
    this.getForums();
  }
}
</script>

<style>
@import "../assets/forum.css";
@import "../assets/main.css";
</style>