import request from '@/js/api/request'

let api = {
    async findWithPage(size, page) {
        try {
            const response = await request.get("vlog/findWithPage/" + size + "/" + page)
            return response
        } catch (err) {
            return err
        }
    },
    async recommendWithPage(userId,size, page) {
        try {
            const response = await request.get("vlog/recommendWithPage/" + userId + "/" + size + "/" + page)
            return response
        } catch (err) {
            return err
        }
    },
    findWithPageNow(size, page) {
        return request.get("vlog/findWithPage/" + size + "/" + page)
    },
    async add(vlog) {
        try {
            const response = await request.post("vlog/add", vlog)
            return response
        } catch (err) {
            return err
        }
    },
    delete(vlogId) {
        request.delete("vlog/delete/" + vlogId)
    },
    async findWithPageById(userId, size, page) {
        try {
            const response = await request.get("vlog/findWithPageById/" + userId + "/" + size + "/" + page)
            return response
        } catch (err) {
            return err
        }
    }
}

export default api;