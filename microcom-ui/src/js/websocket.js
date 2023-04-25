// my-websocket-plugin.js
import { message } from "ant-design-vue"
import { reactive } from 'vue';
const WebSocketPlugin = {
    install: (app, options) => {
      const websocket = new WebSocket(options.websocketUrl);
      const messages = reactive(options.messages);
  
      //链接发送错误时调用
      websocket.onerror = function () {
        message.error('WebSocket链接错误');
      };

      //链接成功时调用
      websocket.onopen = function () {
        message.success('WebSocket链接成功');
      };
  
      websocket.onmessage = function (event) {
        messages.value.push(event.data);
      };
  
      websocket.onclose = function () {
        message.warning('WebSocket链接关闭');
      };
  
      app.provide('websocket', websocket);
    }
  };
  
  export default WebSocketPlugin;