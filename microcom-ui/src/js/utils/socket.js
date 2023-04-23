function socket(id) {
    const socket = new WebSocket('http://localhost:7070/chat/' + id); // 这里的url需要替换成你自己的WebSocket服务端地址
    return socket;
}

export default socket;