<template>
  <div>
    <div id="header">
      <div class="icons_container">
        <div class="icon" id="logout_icon">
          <button @click="logout" title="logout"></button>
        </div>
      </div>

      <div v-for="i in games" :key="i.id" class="body-table">
        {{i}}
      </div>

    </div>


  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "ShopOwn",
  data() {
    return {
      games: null
    }
  },
  methods: {
    getShops() {
      axios
          .post('http://localhost:8083/game_aggregator/shop/stock', {
            name: localStorage.getItem('shop_login')
          }).then((res => this.games = res.data))
    },
    logout() {
      this.$swal.fire({
        icon: "success",
        text: "До встречи!",
        title: "Вы успешно вышли",
      });
      this.$router.push({name: "auth-page"}, () => localStorage.setItem('shop_jwt', null));
    }
  },
  mounted() {
    this.getShops()
  }
}
</script>

<style>

</style>