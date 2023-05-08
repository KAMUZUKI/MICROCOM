import axios from 'axios';

const httpPath = 'http://localhost:8080/microcom/upload/'

let api = {
    async uploadImage(file) {
        try {
            const formData = new FormData()
            formData.append('file', file) // file 是一个 File 对象
            const res = await axios.post(httpPath + "image", formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })
            return res.data
        } catch (err) {
            return err
        }
    }
}

export default api;