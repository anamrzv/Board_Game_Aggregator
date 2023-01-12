<template>
  <div>
    <p class="cyber">User</p>
    <div class="main">
      <input type="checkbox" id="chk" aria-hidden="true">

      <div class="signup">
        <form>
          <label for="chk" aria-hidden="true">Sign up</label>
          <input type="text" name="login" placeholder="User name" required="" v-model.trim="login">
          <input type="email" name="email" placeholder="Email" required="" v-model.trim="mail">
          <input type="password" name="password" placeholder="Password" required="" v-model.trim="password">


          <p class="newsletter">newsletter <input type="checkbox" class="checkmark" v-model="mailPreferences"><span class="check"></span></p>

          <button type="submit" @click="register">Sign up</button>
        </form>
      </div>

      <div class="login">
        <form>
          <label for="chk" aria-hidden="true">Login</label>
          <input type="text" name="login" placeholder="Login" required="">
          <input type="password" name="password" placeholder="Password" required="">
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
  name: "User",
  data(){
    return {
      login: "",
      password: "",
      mailPreferences: false,
      mail: ""
    }
  },
  methods: {
    goHome() {
      this.$router.push({name: "auth-page"})
    },
    register(e) {
      e.preventDefault();
      axios.post('http://localhost:8083/game_aggregator/register', {
        login: this.login,
        password: this.password,
        mail: this.mail,
        mailPreferences: this.mailPreferences
      }).then(() => {
        document.getElementById("forError").innerText = "Вы успешно зарегистрированы";
      }).catch(error => {
        document.getElementById("forError").innerText = error.response.data;
      })
    },
    loging(e) {
      e.preventDefault();
      axios.post('http://localhost:8083/game_aggregator/auth/user', {
        login: this.login,
        password: this.password
      }).then(response => {
        localStorage.setItem("jwt", response.data);
        this.$router.push({name: 'main'});
      }).catch(error => {
        document.getElementById("forError").innerText = error.response.data;
      })
    }
  }
}
</script>

<style>
@import "../assets/user.css";
@import "../assets/login.css";
</style>