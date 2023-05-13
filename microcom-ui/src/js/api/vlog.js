import request from '@/js/api/request'

let api = {
    async findWithPage(size,page) {
        try {
            const response = await request.get("vlog/findWithPage/" + size + "/" + page)
            return response
        }catch (err) {
            return err
        }
    },
    findWithPageNow(size,page) {
        return request.get("vlog/findWithPage/" + size + "/" + page)
    },
    add(vlog) {
        request.get("vlog/add",vlog)
    },
    delete(vlogId) {
        request.delete("vlog/delete/" + vlogId)
    },
    async findWithPageById(userId,size,page) {
        try {
            const response = await request.get("vlog/findWithPageById/" + userId + "/" + size + "/" + page)
            return response
        }catch (err) {
            return err
        }
    }
}

export default api;