<template>
  <div>
    <div class=container>
      <div class="screen">
        <div class="cyber">board games</div>
        <div class="screen__content">
          <p class="login__link">Who am I?</p><br>
          <a href="game_aggregator/auth/user" class="login__link">User</a>
          <a href="game_aggregator/auth/admin" class="login__link">Admin</a>
          <a href="game_aggregator/auth/shop" class="login__link">Shop owner</a>
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
@import "../assets/login.css";
@import "../assets/fonts.css";
</style>
