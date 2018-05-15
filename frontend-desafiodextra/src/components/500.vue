<template>
  <div class="container container-table">
      <div class="row vertical-10p">
        <div class="container">
          <img src="/static/img/logo.png" class="center-block logo">
          <div class="text-center col-sm-6 col-sm-offset-3">
            <h1>An error ocurred</h1>
            <h4>Contact your system administrator</h4>
            <button v-on:click='backToHome'>Back to home</button>
          </div>
        </div>
      </div>
  </div>
</template>
<script>
  export default {
    name: 'error',
    methods: {
      backToHome () {
        window.location.href = '/'
      }
    }
  }
</script>

<style>
</style>
