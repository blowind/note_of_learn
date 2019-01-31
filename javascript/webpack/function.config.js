const path= require('path');

/* 
此处为实测，待后续完善

使用时命令行：
webpack --env.production --config function.config.js */

module.exports = function(env = {}, argv) {
  const plugins = [];
  const isProduction = env['production'];

  if(isProduction) {
    plugins.push();
  }

  return {
    plugins: plugin,
    devtool: isProduction ? undefined : 'source-map',
  };
}