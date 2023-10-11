import request from "@/js/api/request";
import creditRequest from "@/js/api/creditRequest";

const token = localStorage.getItem('tokeninfo');

export function getLikeList(userId) {
    return request({
        url: `/user/getLikeList/${userId}`,
        method: 'get',
    })
}

export function userLogin() {
    return request({
        url: `user/logout/${token}`,
        method: 'post',
    })
}

export function oauthLogin(source) {
    return request({
        url: `oauth/login/${source}`,
        method: 'post',
    })
}

export function oauthVerify(params) {
    return request({
        url: `oauth/verify/${params}`,
        method: 'post',
    })
}

export function userLogout() {
    return request({
        url: `user/login/${token}`,
        method: 'post',
    })
}

export function userRegister() {
    return request({
        url: `user/register/${token}`,
        method: 'post',
    })
}

export function getFollowers(userId) {
    return request({
        url: `user/getFollowers/${userId}`,
        method: 'get',
    })
}

export function getFollowing(userId) {
    return request({
        url: `user/getFollowing/${userId}`,
        method: 'get',
    })
}

export function getInterconnection(userId) {
    return request({
        url: `user/getInterconnection/${userId}`,
        method: 'get',
    })
}

export function isFollow(userId, targetId) {
    return request({
        url: `user/isFollow/${userId}/${targetId}`,
        method: 'get',
    })
}

export function follow(userId, targetId) {
    return request({
        url: `user/follow/${userId}/${targetId}`,
        method: 'post',
    })
}

export function unfollow(userId, targetId) {
    return request({
        url: `user/unfollow/${userId}/${targetId}`,
        method: 'post',
    })
}

export function updateUserById(user) {
    return creditRequest({
        url: `user/updateUserById`,
        method: 'post',
        data: user
    })
}

export function getTokenInfo() {
    return request({
        url: `user/tokenInfo/${token}`,
        method: 'post',
    })
}

export default { getLikeList, userLogin, oauthLogin, oauthVerify, userLogout, userRegister, getFollowers, getFollowing, getInterconnection, isFollow, follow, unfollow, updateUserById, getTokenInfo };