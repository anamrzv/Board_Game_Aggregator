<template>
  <div>
    <div id="header">
      <div class="icons_container">
        <div class="icon" id="logout_icon">
          <button @click="logout" title="logout"></button>
        </div>
      </div>
    </div>

    <p v-if="games !==null" class="cyber">hi, {{games[0].shop.name}}</p>

    <div class="body-table" id="shop-body">
      <div v-for="i in games" :key="i.id">
        <div class="shop_cart">

          <div class="align">
            <p>{{i.shop.city}}</p>
            <p>{{i.shop.address}}</p>
            <p>{{i.shop.phone}}</p>
          </div>

        </div>
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
@import "../assets/shop.css";
</style>