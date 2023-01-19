<template>
<div>
  <div>
    <div id="header">
      <div class="icons_container">
        <div class="icon" id="back_icon">
          <button @click="goHome" title="homepage"></button>
        </div>
        <div class="icon" id="logout_icon">
          <button @click="logout" title="logout"></button>
        </div>
      </div>
    </div>
  </div>

</div>
</template>

<script>
export default {
  name: "Game"
}
</script>

<style scoped>

</style>