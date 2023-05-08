import axios from 'axios';

const httpPath = 'http://localhost:8080/microcom/vlog/'

let api = {
    async findWithPage(page) {
        try {
            const response = await axios.get(httpPath + "findWithPage/" + page)
            return response.data.data
        }catch (err) {
            return err
        }
    },
    add(vlog) {
        axios.get(httpPath + "add",vlog)
    },
    delete(vlogId) {
        axios.delete(httpPath + "delete/" + vlogId)
    },
    async findWithPageById(vlogId,page) {
        try {
            const response = await axios.get(httpPath + "findWithPageById/" + vlogId + "/" + page)
            return response.data.data
        }catch (err) {
            return err
        }
    }
}

export default api;