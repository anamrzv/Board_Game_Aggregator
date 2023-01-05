<template>
  <div>
    <p class="cyber">Shop</p>
    <div class="main">
      <input type="checkbox" id="chk" aria-hidden="true">

      <div class="signup">
        <form>
          <label for="chk" aria-hidden="true">Sign up</label>
          <input type="text" name="txt" placeholder="User name" required="">
          <input type="password" name="pswd" placeholder="Password" required="">
          <button>Sign up</button>
        </form>
      </div>

      <div class="login">
        <form>
          <label for="chk" aria-hidden="true">Login</label>
          <input type="email" name="email" placeholder="Email" required="">
          <input type="password" name="pswd" placeholder="Password" required="">
          <button>Login</button>
        </form>
      </div>
    </div>
    <button class="back__button" @click="goHome">
      back
    </button>
  </div>
</template>

<script>
export default {
  name: "Shop",
  methods: {
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