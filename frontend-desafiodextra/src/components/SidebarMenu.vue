<template>
  <ul class="sidebar-menu">
    <li class="header">HOME</li>
    <router-link tag="li" class="pageLink" to="/">
      <a>
        <i class="fa fa-desktop"></i>
        <span class="page">Dashboard</span>
      </a>
    </router-link>

    <li class="header">CRUD</li>
    <router-link tag="li" class="pageLink" to="/ingredients">
      <a>
        <i class="fa fa-tasks"></i>
        <span class="page">Ingredients</span>
      </a>
    </router-link>
    <router-link tag="li" class="pageLink" to="/burgers">
      <a>
        <i class="fa fa-cog"></i>
        <span class="page">Burgers</span>
      </a>
    </router-link>
    <router-link tag="li" class="pageLink" to="/orders">
      <a>
        <i class="fa fa-cog"></i>
        <span class="page">Orders</span>
      </a>
    </router-link>
  </ul>
</template>
<script>
export default {
  name: 'SidebarMenu'
}
</script>
<style>
  /* override default */
  .sidebar-menu>li>a {
    padding: 12px 15px 12px 15px;
  }

  .sidebar-menu li.active>a>.fa-angle-left, .sidebar-menu li.active>a>.pull-right-container>.fa-angle-left {
    animation-name: rotate;
    animation-duration: .2s;
    animation-fill-mode: forwards;
  }

  @keyframes rotate {
    0% {
      transform: rotate(0deg);
    }

    100% {
      transform: rotate(-90deg);
    }
  }
</style>
