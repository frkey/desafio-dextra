function successMessage (_self, message) {
  _self.$toast(message, {
    className: ['et-info', 'alert'],
    horizontalPosition: 'center',
    duration: 3000,
    mode: 'queue',
    transition: 'slide-left'
  })
}

function errorMessage (_self, message) {
  _self.$toast(message, {
    className: ['et-alert', 'alert'],
    horizontalPosition: 'center',
    duration: 3000,
    mode: 'queue',
    transition: 'slide-left'
  })
}

export default {
  successMessage: successMessage,
  errorMessage: errorMessage
}
