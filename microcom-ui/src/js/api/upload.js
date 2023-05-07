import axios from 'axios';

const httpPath = 'http://localhost:8080/microcom/upload/'

let api = {
    async uploadImage(file) {
        try {
            const formData = new FormData()
            formData.append('file', file) // file 是一个 File 对象
            let res = await axios.post(httpPath + "image", formData)
            return res.data
        } catch (err) {
            console.log(err);
        }
    }
}

export default api;