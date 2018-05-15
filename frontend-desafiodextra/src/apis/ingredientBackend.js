var config = require('../config')
import axios from 'axios'

const ingredientUrl = config.apiUri + '/ingredients'

function loadIngredients(successCallback, errorCallback) {
  axios.get(ingredientUrl)
    .then(successCallback)
    .catch(errorCallback)
}

function insertIngredient(ingredient, successCallback, errorCallback) {
  axios.post(ingredientUrl, ingredient)
    .then(successCallback)
    .catch(errorCallback)
}

function removeIngredient(ingredientId, successCallback, errorCallback) {
  var url = ingredientUrl + '/' + ingredientId
  axios.delete(url)
    .then(successCallback)
    .catch(errorCallback)
}

export default {
  loadIngredients,
  insertIngredient,
  removeIngredient
}
