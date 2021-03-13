// craco.config.js
module.exports = {
  plugins: [
    {
      plugin: require("craco-cesium")()
    }
  ],
  style: {
    postcss: {
      plugins: [
        require('tailwindcss'),
        require('autoprefixer'),
      ],
    },
  },
}