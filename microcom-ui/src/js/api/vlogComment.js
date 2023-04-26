import axios from 'axios';

const httpPath = 'http://localhost:8080/microcom/vlogcomment/'

let api = {
    save (comment) {
        axios.post(httpPath + "save", comment)
    },
    findByVlogId(vlogId) {
        axios.get(httpPath + "findByVlogId/" + vlogId)
    },
    findChild(vlogId,parentId) {
        axios.get(httpPath + "findChildren/" + vlogId + "/" + parentId)
    },
    delete(comment) {
        axios.delete(httpPath + "delete",comment)
    }
}

export default api;