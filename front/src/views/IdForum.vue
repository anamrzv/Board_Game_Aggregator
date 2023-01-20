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

    <p class="cyber" id="forum" @click="goForum">forum</p>

    <div class="main_container">
      <div class="icons_container_forum">
        <div class="icon_p" id="favorite_icon">
          <button @click="addToFav" title="add in favorite"></button>
        </div>
      </div>

      <div v-if="comments" class="forum_container">{{ comments.forum.name }}
      </div>

      <div v-if="comments">
        <div v-for="item in comments.comments" :key="item.id">
          <div class="comments_container">
            <div class="username">{{ item.user }}</div>
            <div class="comment">{{ item.content }}</div>
          </div>
          <div class="for_date">{{ item.date }}</div>
        </div>
      </div>

      <div class="icon_p" id="plus_icon" title="add comment">
        <div class="add_comment">
          <b-button v-b-toggle.add>+</b-button>
          <b-collapse id="add">
          <form id="for_comment">
            <input type="text" required="" v-model.trim="get_comment">
            <button class="icon_p" type="submit" @click="sendComment" title="send">></button>
          </form>
          </b-collapse>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "IdForum",
  data() {
    return {
      get_comment: null,
      forum_id: 0,
      comments: null
    }
  },
  methods: {
    sendComment(){
      let msg ='localhost:8083/game_aggregator/forum/'
    axios
        .post(msg + this.$data.forum_id, {
          login: localStorage.getItem('login'),
          dateTime: null,
          content: this.$data.get_comment
        }).then((response) => {
      this.comments = response.data;
    })
    },
    addToFav() {
      axios
          .post('http://localhost:8083/game_aggregator/forum/fav_forums', {login: localStorage.getItem('login'), forum: this.$data.forum_id})
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
    goForum() {
      this.$router.push({name: "forum-page"})
    },
    getId() {
      let msg = 'http://localhost:8083/game_aggregator/forum/';
      this.$data.forum_id = localStorage.getItem('idForum');
      axios
          .get(msg + this.$data.forum_id)
          .then((response) => {
            this.comments = response.data;
          })
    }
  },
  mounted() {
    this.getId();
  }
}
</script>

<style scoped>
@import "../assets/forum.css";
</style>