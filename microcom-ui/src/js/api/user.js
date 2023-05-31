import request from "@/js/api/request";
import creditRequest from "@/js/api/creditRequest";

const token = localStorage.getItem('tokeninfo');

let api = {
    async getFollowers (userId) {
        try {
            let res = await request.post("user/getFollowers/" + userId)
            return res
        }catch (err) {
            console.log(err);
        }
    },
    async getFollowing(userId){
        try {
            let res = await request.post("user/getFollowing/" + userId)
            return res
        }catch (err) {
            console.log(err);
        }
    },
    async getInterconnection(userId){
        try{
            let res = await request.post("user/getInterconnection/" + userId)
            return res
        }
        catch(err){
            console.log(err);
        }
    },
    async isFollow (userId,targetId){
        try {
            let res = await request.post("user/isFollow/" + userId + "/" + targetId)
            return res
        }catch (err) {
            console.log(err);
        }
    },
    async follow (userId,targetId){
        try {
            let res = await request.post("user/follow/" + userId + "/" + targetId)
            return res
        }catch (err) {
            console.log(err);
        }
    },
    async unfollow (userId,targetId){
        try {
            let res = await request.post("user/unfollow/" + userId + "/" + targetId)
            return res
        }catch (err) {
            console.log(err);
        }
    },
    async updateUserById (user){
        try{
            let res = await creditRequest.post("user/updateUserById",user)
            return res
        }catch (err) {
            console.log(err);
        }
    },
    async getTokenInfo(){
        try{
            let res = await request.post("user/tokenInfo/" + token)
            return res
        }catch (err) {
            console.log(err);
        }
    },
    logout: function () {
        try{
            let res = request.post("user/logout/" + token)
            return res
        }catch (err) {
            console.log(err);
        }
    }
}

export default api;