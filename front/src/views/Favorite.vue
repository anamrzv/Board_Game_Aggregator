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
    <p class="cyber">favorites</p>


    <div class="fav-body">
      <b-button v-b-toggle.collapse-1 @click="getFavGames">games</b-button>
      <b-collapse id="collapse-1">
        <div class="oneLine">
          <div class="forum-table" id="forum_view" v-for="item in fav_games" :key="item.id">
            <span @click="goToGame(item.id)">{{ item.name }}</span>
            <div class="icon_p" id="delete_icon">
              <button @click="deleteGameFromFav(item.id)"></button>
            </div>
          </div>

        </div>
      </b-collapse>
    </div>

    <div class="fav-body">
      <b-button v-b-toggle.collapse-2 @click="getFavForums">forums</b-button>
      <b-collapse id="collapse-2">
        <div class="oneLine">
          <div class="forum-table" id="forum_view" v-for="item in fav_forums" :key="item.id">
            <span @click="goToForum(item.id)">{{ item.name }}</span>

            <div class="icon_p" id="delete_icon">
              <button @click="deleteForumFromFav(item.id)"></button>
            </div>
          </div>
        </div>

      </b-collapse>
    </div>


  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Favorite",
  data() {
    return {
      fav_forums: null,
      fav_games: null
    }
  },
  methods: {
    goToGame() {

    },
    deleteGameFromFav(game_id) {

    },
    deleteForumFromFav(forum_id) {
      axios
          .delete('http://localhost:8083/game_aggregator/user/fav_forums', {
            data: {
              login: localStorage.getItem('login'),
              forum: forum_id
            }
          }).then((res => {
        this.fav_forums = res.data
      }))
    },
    goToForum(forum_id) {
      let msg = '/game_aggregator/forum/'
      this.$router.push(msg + forum_id, () => localStorage.setItem('idForum', forum_id))
    },
    getFavForums() {
      axios
          .post('http://localhost:8083/game_aggregator/user/fav_forums', {
            login: localStorage.getItem('login')
          }).then((res => this.fav_forums = res.data))
    },
    getFavGames() {
      axios
          .post('http://localhost:8083/game_aggregator/user/fav', {
            login: localStorage.getItem('login')
          }).then((res => this.fav_games = res.data))
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
    this.getFavForums()
    this.getFavGames()
  }
}
</script>

<style>
@import "../assets/fav.css";
</style>