<template>
  <div>
    <p class="cyber">Admin</p>
    <div class="main" id="login">
        <input type="checkbox" id="chk" aria-hidden="true">

        <div class="signup">
          <form>
            <label class="unfocus__login">Login</label>
            <input type="text" name="txt" placeholder="Admin name" required="">
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
  name: "Admin",
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