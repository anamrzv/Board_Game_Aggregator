<template>
  <div id="body-table">

    <div class="header">
      <div>
        <p>Морозова Анастасия Александровна P3230</p>
        <p>Вариант 5547</p>
      </div>
    </div>

    <div class="errors">
      <h1>Ошибка {{ errorCode }}:</h1>
      <h2>{{ errorMsg }}</h2>
      <button class="gradient-button" @click="goHome">
        Войти
      </button>
    </div>

  </div>
</template>

<style>
@import "../assets/header.css";
@import "../assets/body.css";

.errors {
  color: #000000;
  text-align: center;
  margin-top: 200px;
}
</style>

<script>
export default {
  name: "NotFound",
  props: {
    errorCode: String,
    errorMsg: String
  },
  methods: {
    goHome() {
      this.$router.push({name: "auth-page"})
    }
  }
}
</script>