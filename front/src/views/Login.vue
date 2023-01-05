<template>
  <div>
    <div class="header"></div>

    <div class=container>
      <div class="screen">
        <div class="cyber">board games</div>
        <div class="screen__content">
          <p class="login__link">Who am I?</p><br>
          <a href="/auth/user" class="login__link">User</a>
          <a href="/auth/admin" class="login__link">Admin</a>
          <a href="/auth/shop" class="login__link">Shop owner</a>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
  export default {
  name: "Login"
}
</script>

<style>
@import "../assets/header.css";
@import "../assets/login.css";
@import "../assets/fonts.css";
</style>
