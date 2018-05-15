var config = require('../config')
import axios from 'axios'

const orderUrl = config.apiUri + '/orders'

function loadOrders(successCallback, errorCallback) {
  axios.get(orderUrl)
    .then(successCallback)
    .catch(errorCallback)
}

function insertOrder(order, successCallback, errorCallback) {
  axios.post(orderUrl, order)
    .then(successCallback)
    .catch(errorCallback)
}

function removeOrder(orderId, successCallback, errorCallback) {
  var url = orderUrl + '/' + orderId
  axios.delete(url)
    .then(successCallback)
    .catch(errorCallback)
}

export default {
  loadOrders,
  insertOrder,
  removeOrder
}
