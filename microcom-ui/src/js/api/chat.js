import axios from 'axios'

let path = "http://localhost:7070"
let api = {
    
    /**
     * 根据ID获取用户信息
     */
    getUser(id) {
        return '/chat/' + id
    },

    /**
     * 获取在线用户列表
     */
    getOnline() {
        axios.get(path + 'online/list').then(res => {
            return res.data.data
        }).catch(err => {
            return err
        })
    },

    /**
     * WebSocket服务器链接接口
     */
    websocket(id) {
        return 'ws://localhost:8080/chat/' + id
    },

    /**
     * 获取群发消息列表
     */
    getCommon() {
        return '/chat/common'
    },

    /**
     * 获取与指定窗口的消息列表
     */
    getSelf(fromId, toId) {
        return '/chat/self/' + fromId + '/' + toId
    },

    /**
     * 向指定窗口推送消息
     */
    pushId(message) {
        axios.get(path + 'push/' + message.to,message).then(res => {
            return res.data.data
        }).catch(err => {
            return err
        })
    },

    /**
     * 注销用户
     */
    logout(id) {
        return '/chat/' + id
    }
}

export default api;