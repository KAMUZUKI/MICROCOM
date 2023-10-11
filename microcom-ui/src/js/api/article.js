import request from "@/js/api/request"

export function getArticleById(articleId) {
    return request({
      url: `/article/getArticleTop/${articleId}`,
      method: 'get',
    })
}

export function getArticleTop() {
    return request({
      url: `/article/getArticleTop`,
      method: 'get',
    })
}

//原方法changeData
export function userLike() {
    return request({
      url: `/article/userLike`,
      method: 'get',
    })
}

export default {getArticleById,getArticleTop};