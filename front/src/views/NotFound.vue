<template>
  <div id="body-table">
    <div class="errors">
      <h1>Ошибка {{ errorCode }}:</h1>
      <h2>{{ errorMsg }}</h2>
      <button class="back__button" @click="goHome">
        back
      </button>
    </div>

  </div>
</template>

<style>
@import "../assets/body.css";

.errors {
  text-align: center;
  margin-top: 40px;
  color: whitesmoke;
  position: absolute;
  left: 34%;
  top: 35%;
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