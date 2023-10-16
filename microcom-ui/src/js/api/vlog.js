import request from '@/js/api/request'

let api = {
    findWithPage(size, page) {
        return request({
            url: "vlog/findWithPage/" + size + "/" + page,
            method: "get",
        })
    },
    recommendWithPage(userId,size, page) {
        return request({
            url: "vlog/recommendWithPage/" + userId + "/" + size + "/" + page,
            method: "get",
        })
    },
    findWithPageNow(size, page) {
        return request({
            url: "vlog/findWithPage/" + size + "/" + page,
            method: "get",
        })
    },
    add(vlog) {
        return request({
            url: "vlog/add",
            method: "post",
            data: vlog
        })
    },
    delete(vlogId) {
        return request({
            url: "vlog/delete/" + vlogId,
            method: "delete",
        })
    },
    findWithPageById(userId, size, page) {
        return request({
            url: "vlog/findWithPageById/" + userId + "/" + size + "/" + page,
            method: "get",
        })
    }
}

export default api;