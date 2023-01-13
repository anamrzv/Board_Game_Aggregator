<template>
  <div>
    <div id="header">
    </div>

    <div class="body-table">
      <div id="games" v-for="item in 10" :key="item.id">
        {{ item }}
      </div>
    </div>

  </div>
</template>

<script>
// import axios from "axios";
export default {
 name: "main",
  data() {
    return {
      games: []
    }
  },
  methods: {
    logout() {
      this.$swal.fire({
        icon: "success",
        text: "До встречи!",
        title: "Вы успешно вышли",
      });
      this.$router.push({name: "auth-page"}, () => localStorage.clear());
    }
  }
}
</script>

<style>
@import "../assets/main.css";
</style>