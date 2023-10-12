import request from "@/js/api/request"

export function getArticleById(articleId) {
  return request({
    url: `/article/getArticleTop/${articleId}`,
    method: 'get',
  })
}

export function getArticleByPage(params) {
  return request({
    url: `/article/getArticleByPage`,
    method: 'get',
    params
  })
}

export function getAllArticle() {
  return request({
    url: `/article/getAllArticle`,
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

export default { getArticleById, getAllArticle, getArticleTop, userLike };