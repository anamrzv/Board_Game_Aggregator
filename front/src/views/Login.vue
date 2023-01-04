<template>
  <div>
    <div class="header"></div>

    <div class=container>
      <div class="screen">
        <div class="cyber">board games</div>
        <div class="screen__content">
          <a href="" class="login__link">Without login</a>
          <a href="" class="login__link">Login as user</a>
          <a href="" class="login__link">Login as admin</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: 'Login',
  data() {
    return {
      login: "",
      password: ""
    }
  },
  methods: {
    loging(e) {
      e.preventDefault();
      axios.post('http://localhost:8083/api/user/auth', {
        login: this.login,
        password: this.password
      }).then(response => {
        localStorage.setItem("jwt", response.data);
        this.$router.push({name: 'main'});
      }).catch(error => {
        document.getElementById("forError").innerText = error.response.data;
      })
    },
    register(e) {
      e.preventDefault();
      axios.put('http://localhost:8083/api/user/auth', {
        login: this.login,
        password: this.password
      }).then(() => {
        document.getElementById("forError").innerText = "Вы успешно зарегистрированы";
      }).catch(error => {
        document.getElementById("forError").innerText = error.response.data;
      })
    }
  }
}
</script>

<style>
@import "../assets/header.css";
@import "../assets/login.css";
</style>
