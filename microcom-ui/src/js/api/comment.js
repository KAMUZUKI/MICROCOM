import request from "@/js/api/request"

export function getComments(articleId) {
    return request({
      url: `/comment/getComments/${articleId}`,
      method: 'get',
    })
}

export function addComment(params) {
    return request({
      url: `/comment/addComment`,
      method: 'get',
      params
    })
}

export default {getComments};