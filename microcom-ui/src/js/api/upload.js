import creditRequest from "@/js/api/request"

let api = {
    async uploadImage(file) {
        try {
            const formData = new FormData()
            formData.append('file', file) // file 是一个 File 对象
            const res = await creditRequest.post("upload/image", formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })
            return res
        } catch (err) {
            return err
        }
    }
}

export default api;