import request from "@/js/api/request"

let api = {
    async getCategory() {
        try {
            const res = await request.post("category/getCategory")
            return res
        } catch (err) {
            return err
        }
    }
}

export default api;