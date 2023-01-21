<template>
  <div>
    <p class="cyber">Shop</p>
    <div class="main" id="login">
      <input type="checkbox" id="chk" aria-hidden="true">

      <div class="signup">
        <form>
          <label class="unfocus__login">Login</label>
          <input type="text" name="txt" placeholder="Shop name" required="" v-model.trim="getLogin">
          <input type="password" name="pswd" placeholder="Password" required="" v-model.trim="getPassword">
          <button type="submit" @click="loging">Login</button>
        </form>
      </div>
    </div>
    <button class="back__button" @click="goHome">
      back
    </button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Shop",
  data() {
    return {
      getLogin: null,
      getPassword: null
    }
  },
  methods: {
    loging(e) {
      e.preventDefault();
      axios.post('http://localhost:8083/game_aggregator/auth/shop', {
        login: this.getLogin,
        password: this.getPassword
      }).then(response => {
        localStorage.setItem("shop_login", this.$data.getLogin);
        localStorage.setItem("shop_jwt", response.data);
        this.$router.push({name: 'shop-own'});
      }).catch(error => {
        document.getElementById("forError").innerText = error.response.data;
      })
    },
    goHome() {
      this.$router.push({name: "auth-page"})
    }
  }
}
</script>

<style>
@import "../assets/user.css";
@import "../assets/login.css";
</style>