import axios from 'axios';

const httpPath = 'http://localhost:8080/microcom/user/'

let api = {
    async getFollowers (userId) {
        try {
            let res = await axios.post(httpPath + "getFollowers/" + userId)
            return res.data
        }catch (err) {
            console.log(err);
        }
    },
    async getFollowing(userId){
        try {
            let res = await axios.post(httpPath + "getFollowing/" + userId)
            return res.data
        }catch (err) {
            console.log(err);
        }
    },
    async isFollow (userId,targetId){
        try {
            let res = await axios.post(httpPath + "isFollow/" + userId + "/" + targetId)
            return res.data
        }catch (err) {
            console.log(err);
        }
    },
    async follow (userId,targetId){
        try {
            let res = await axios.post(httpPath + "follow/" + userId + "/" + targetId)
            return res.data
        }catch (err) {
            console.log(err);
        }
    },
    async unfollow (userId,targetId){
        try {
            let res = await axios.post(httpPath + "unfollow/" + userId + "/" + targetId)
            return res.data
        }catch (err) {
            console.log(err);
        }
    }
}

export default api;