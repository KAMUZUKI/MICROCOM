import axios from 'axios'
import config from '@/js/api/config'

let httpPath = config.getChatUrl
let api = {

    websocket(id) {
        return config.getChatWsUrl + id;
    },

    /**
     * 根据ID获取用户信息
     */
    async getUser(id) {
        try {
            const response = await axios.get(httpPath + 'user/' + id)
            return response.data.data
        } catch (err) {
            throw new Error(err)
        }
    },

    /**
     * 获取在线用户列表
     */
    async getOnline() {
        try {
            const response = await axios.get(httpPath + 'online/list')
            return response.data.data
        } catch (err) {
            throw new Error(err)
        }
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
    async getSelf(fromId, toId) {
        try {
            const response = await axios.get(httpPath + 'self/' + fromId + "/" + toId)
            return response.data.data
        } catch (err) {
            throw new Error(err)
        }
    },

    /**
     * 向指定窗口推送消息
     */
    pushId(message) {
        axios.post(httpPath + 'push/' + message.to.id, message).then(res => {
            return res.data.data
        }).catch(err => {
            return err
        })
    },

    /**
     * 注销用户
     */
    logout(id) {
        axios.post(httpPath + 'logout/' + id).then(res => {
            return res.data
        }).catch(err => {
            return err
        })
    },

    join(user) {
        axios.post(httpPath + "join/", user).then(res => {
            if (res.data.code == 200) {
                return ("加入聊天室成功")
            } else {
                return (res.data.msg)
            }
        })
    },

    record(user) {
        axios.post(httpPath + "recordUser/", user).then(res => {
            if (res.data.code == 200) {
                return ("成功")
            } else {
                return (res.data.msg)
            }
        })
    }
}

export default api;