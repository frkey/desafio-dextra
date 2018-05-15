// Import ES6 Promise
import 'es6-promise/auto'

// Import System requirements
import Vue from 'vue'
import VueRouter from 'vue-router'

import { sync } from 'vuex-router-sync'
import routes from './routes'
import store from './vuex/store'
import Toast from 'vue-easy-toast'
import VeeValidate from 'vee-validate'
import * as pagination from 'vuejs-uib-pagination'
import VueResource from 'vue-resource'
import ToggleButton from 'vue-js-toggle-button'
import axios from 'axios'

var config = require('./config')
var baseUrl = config.frontendAddress

// Import Helpers for filters
import { domain, count, prettyDate, pluralize } from './filters'

var qs = require('qs')
console.log(qs)
// Import Views - Top level
import AppView from './components/App.vue'

// Import Install and register helper items
Vue.filter('count', count)
Vue.filter('domain', domain)
Vue.filter('prettyDate', prettyDate)
Vue.filter('pluralize', pluralize)

Vue.use(store)
Vue.use(Toast)
Vue.use(VueRouter)
Vue.use(VueResource)
Vue.use(VeeValidate)
Vue.use(pagination)
Vue.use(ToggleButton)

// Routing logic
var router = new VueRouter({
  routes: routes,
  mode: 'history',
  linkExactActiveClass: 'active',
  scrollBehavior: function (to, from, savedPosition) {
    return savedPosition || { x: 0, y: 0 }
  }
})

axios.interceptors.response.use((response) => { // intercept the global error
  return response
}, function (error) {
  if (error.response.status === 403) {
    window.location.href = baseUrl + '/forbbiden'
    return
  }
  if (error.response.status >= 500) {
    window.location.href = baseUrl + '/error'
    return
  }
  // Do something with response error
  return Promise.reject(error)
})

sync(store, router)

// Start out app!
// eslint-disable-next-line no-new
new Vue({
  el: '#root',
  router: router,
  store: store,
  created: function () {
    window.Vue = this
  },
  render: h => h(AppView)
})
