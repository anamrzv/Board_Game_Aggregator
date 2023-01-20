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
      <b-button class="cyber">games</b-button>
    </div>


  </div>
</template>

<script>
export default {
  name: "Favorite",
  methods: {
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
  }
}
</script>

<style>
@import "../assets/fav.css";
</style>