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

    <p class="cyber">cart</p>
    <!--сделать удаление без обновления страницы-->
    <div v-if="games_in_cart !== null" class="cart-body">
      <div v-for="item in games_in_cart" :key="item.id">
        <div class="game_cart_view">
          <div class="delete_from_cart" @click="deleteFromCart(item.shop.id, item.game.id)">x</div>
          <img width="200px" height="178" v-bind:src="item.game.image">
          <div>Name<br>{{ item.game.name }}</div>
          <div>Shop<br>{{ item.shop.name }}</div>
          <div>Price<br>{{ item.price }}<B>P</B></div>
        </div>
      </div>
      <div class="total_price">Total:</div>
    </div>


  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Cart",
  data() {
    return {
      games_in_cart: null
    }
  },
  methods: {
    deleteFromCart(shop_id, game_id) {
      axios
          .delete('http://localhost:8083/game_aggregator/user/cart',
              {
                data: {
                  login: localStorage.getItem('login'),
                  shopId: shop_id,
                  gameId: game_id
                }
              }).then((res) => {
        this.games_in_cart = res.data
      })
    },
    getCartGames() {
      axios
          .post('http://localhost:8083/game_aggregator/user/cart',
              {
                login: localStorage.getItem("login"), password: "", mail: "", mailPreferences: ""
              }).then((res) => {
        this.games_in_cart = res.data
      },)
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
    this.getCartGames();
  }
}
</script>

<style>
@import "../assets/cart.css";
@import "../assets/fonts.css";
</style>