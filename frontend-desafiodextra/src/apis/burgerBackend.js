var config = require('../config')
import axios from 'axios'

const burgerUrl = config.apiUri + '/burgers'

function loadBurgers(successCallback, errorCallback) {
  axios.get(burgerUrl)
    .then(successCallback)
    .catch(errorCallback)
}

function insertBurger(burger, successCallback, errorCallback) {
  axios.post(burgerUrl, burger)
    .then(successCallback)
    .catch(errorCallback)
}

function removeBurger(burgerId, successCallback, errorCallback) {
  var url = burgerUrl + '/' + burgerId
  axios.delete(url)
    .then(successCallback)
    .catch(errorCallback)
}

export default {
  loadBurgers,
  insertBurger,
  removeBurger
}
