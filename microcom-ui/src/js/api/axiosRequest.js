import axios from 'axios'
function axiosRequest(method, url, data) {
    let path = "http://localhost:7070/chat/"
    return axios({
        method: method,
        url: path + url,
        data: data
    });
}

export default axiosRequest