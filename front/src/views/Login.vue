<template>
  <div id="login-body">

    <div class="header">
      <div>
        <p>Морозова Анастасия Александровна P3230</p>
        <p>Вариант 5547</p>
      </div>
    </div>

    <div class="container">
      <div class="screen">
        <div class="screen__content">
          <form class="login">
            <div class="login__field">
              <i class="login__icon fas fa-user"></i>
              <input type="text" class="login__input" id="loginInput" required placeholder="Логин" v-model.trim="login">
            </div>
            <div class="login__field">
              <i class="login__icon fas fa-lock"></i>
              <input type="password" class="login__input" id="passwordInput" required placeholder="Пароль"
                     v-model.trim="password">
            </div>
            <a id="forError" style="font-size: 10px; font-style: oblique; color: #4C489D"></a>
            <button class="button login__submit" @click="loging">
              <span class="button__text">войти</span>
              <i class="button__icon fas fa-chevron-right"></i>
            </button>
          </form>
          <div class="social-login">
            <a href="/" @click="register">регистрация</a>
          </div>
        </div>
        <div class="screen__background">
          <span class="screen__background__shape screen__background__shape4"></span>
          <span class="screen__background__shape screen__background__shape3"></span>
          <span class="screen__background__shape screen__background__shape2"></span>
          <span class="screen__background__shape screen__background__shape1"></span>
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
