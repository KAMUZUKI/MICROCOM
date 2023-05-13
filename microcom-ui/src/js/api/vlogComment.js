import request from "@/js/api/request";

let api = {
    async save (comment) {
        try {
            let res = await request.post("vlogcomment/save",comment)
            return res
        }catch (err) {
            return err
        }
    },
    async findByVlogId(vlogId,page) {
        try {
            let res = await request.get("vlogcomment/findByVlogId/" + vlogId + "/" + page)
            return res
        }catch (err) {
            return err
        }
    },
    async findChild(vlogId,parentId,page) {
        try {
            let res = await request.get("vlogcomment/findChildren/" + vlogId + "/" + parentId + "/" + page)
            return res
        }catch (err) {
            return err
        }
    },
    delete(comment) {
        request.delete("delete",comment).then((res) => {
            return res
        }).catch((err) => {
            return err
        })
    }
}

export default api;