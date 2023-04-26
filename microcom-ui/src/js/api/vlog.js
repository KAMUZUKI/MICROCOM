import axios from 'axios';

const httpPath = 'http://localhost:8080/microcom/vlog/'

let api = {
    async findWithPage(page) {
        try {
            const response = await axios.get(httpPath + "findWithPage/" + page);
            return response.data.data;
        }catch (err) {
            throw new Error(err);
        }
    },
    add(vlog) {
        axios.get(httpPath + "add",vlog)
    },
    delete(vlogId) {
        axios.delete(httpPath + "delete/" + vlogId)
    }
}

export default api;