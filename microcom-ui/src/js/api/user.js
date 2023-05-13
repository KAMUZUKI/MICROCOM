import request from "@/js/api/request";

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
    }
}

export default api;