var env = {}

if (process.env.NODE_ENV === '' || process.env.NODE_ENV === ' ' || process.env.NODE_ENV === undefined) {
  env = require('./development/')
} else {
  env = require('./' + process.env.NODE_ENV + '/')
}

module.exports = env
