const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  devServer:{
    port:8081, // 启动端口号
  },
  transpileDependencies: [
    'quasar'
  ],

  publicPath: process.env.NODE_ENV === "production" ? "/dist/" : "/",

  pluginOptions: {
    quasar: {
      importStrategy: 'kebab',
      rtlSupport: false
    }
  }
})

// 配置的意思
// module.exports = {
//   devServer: {
//     // 自动加载这个文件
//     // 默认值 为localhost 127.0.0.1
//     // 当前端口默认为8080
//     host: "localhost",
//     // 当前vue服务器的端口
//     port: "8080",
//     // 默认有的
//     // 前端跨域
//     proxy: {
//       // "/demo": {
//       //   // 配置你要让那些路径要跨域
//       //   // 你可以让该路径前面添加api
//       //   // login
//       //   // /aa/login
//       //   // 是后台路径
//       //   target: "http://175.178.212.207:8080/demo",
//       //   // true 代表跨域
//       //   changOrigin: true,
//       //   pathRewrite: {
//       //     //重写 login
//       //     "^/api": "",
//       //   },
//       // },
//     },
//   },
// };
