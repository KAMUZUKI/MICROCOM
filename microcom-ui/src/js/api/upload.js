import axios from 'axios';

const httpPath = 'http://localhost:8080/microcom/upload/'

let api = {
     uploadImage(file) {
        try {
            const formData = new FormData()
            formData.append('file', file) // file 是一个 File 对象
            return axios.post(httpPath + "image", formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })
        } catch (err) {
            return err
        }
    },
    test(file) {
        try {
            const formData = new FormData()
            formData.append('image', file) // file 是一个 File 对象
            return axios.post(httpPath + "test", formData, {
                headers: {
                    'Content-Type': '`multipart/form-data`'
                }
            })
        } catch (err) {
            return err
        }
    }
}

export default api;