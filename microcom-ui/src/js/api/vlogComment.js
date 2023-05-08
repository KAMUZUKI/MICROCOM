import axios from 'axios';

const httpPath = 'http://localhost:8080/microcom/vlogcomment/'

let api = {
    async save (comment) {
        try {
            let res = await axios.post(httpPath + "save",comment,)
            return res.data
        }catch (err) {
            console.log(err);
        }
    },
    async findByVlogId(vlogId,page) {
        try {
            let res = await axios.get(httpPath + "findByVlogId/" + vlogId + "/" + page)
            return res.data
        }catch (err) {
            console.log(err);
        }
    },
    async findChild(vlogId,parentId,page) {
        try {
            let res = await axios.get(httpPath + "findChildren/" + vlogId + "/" + parentId + "/" + page)
            return res.data
        }catch (err) {
            console.log(err);
        }
    },
    delete(comment) {
        axios.delete(httpPath + "delete",comment).then((res) => {
            console.log(res);
        }).catch((err) => {
            console.log(err);
        })
    }
}

export default api;