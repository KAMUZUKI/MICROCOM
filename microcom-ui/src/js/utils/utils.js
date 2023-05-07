import api from "@/js/api/chat"

let utils = {
    parseDateToPast: function (pastTime) {
        const currentTime = Date.now(); // 当前时间的时间戳
        const timeDiff = currentTime - pastTime; // 时间差，以毫秒为单位
        let timeAgo = "";
        const seconds = Math.floor(timeDiff / 1000); // 将时间差转换为秒数
        if (seconds < 60) {
            timeAgo = `刚刚`;
        } else {
            const minutes = Math.floor(seconds / 60); // 将时间差转换为分钟数
            if (minutes < 60) {
                timeAgo = `${minutes}分钟前`;
            } else {
                const hours = Math.floor(minutes / 60); // 将时间差转换为小时数
                if (hours < 24) {
                    timeAgo = `${hours}小时前`;
                } else {
                    const days = Math.floor(hours / 24); // 将时间差转换为天数
                    if (days < 7) {
                        timeAgo = `${days}天前`;
                    } else {
                        const weeks = Math.floor(days / 7); // 将时间差转换为周数
                        if (weeks < 4) {
                            timeAgo = `${weeks}周前`;
                        } else {
                            const months = Math.floor(days / 30); // 将时间差转换为月数
                            if (months < 12) {
                                timeAgo = `${months}月前`;
                            } else {
                                const years = Math.floor(days / 365); // 将时间差转换为年数
                                timeAgo = `${years}年前`;
                            }
                        }
                    }
                }
            }
        }
        return timeAgo;
    },
    parseDate: function (time) {
        const date = new Date(time);
        const year = date.getFullYear();
        const month = date.getMonth() + 1;
        const day = date.getDate();
        const hour = date.getHours(); // 小时
        const minute = date.getMinutes(); // 分钟
        const second = date.getSeconds(); // 秒
        return `${year}-${month}-${day} ${hour}:${minute}:${second}`;
    },
    getTime: function () {
        const date = new Date();
        const year = date.getFullYear();
        const month = date.getMonth() + 1;
        const day = date.getDate();
        const hour = date.getHours(); // 小时
        const minute = date.getMinutes(); // 分钟
        const second = date.getSeconds(); // 秒
        return `${year}-${month}-${day} ${hour}:${minute}:${second}`;
    },
    getURLParams: function () {
        console.log('$route.query:', this.$route.query)
        const query = this.$route.query
        const data = this.formatURLData(location.search)
        console.log('hash:', location.hash)
        console.log('search:', location.search, location.search && data)
        const name = (query && query.name) || (data && data.name)
        console.log('params.name:', name)
    },
    getImg: function (url) {
        return url.split(',')
    },
    formatVlog: function (vlogs) {
        vlogs.forEach(async vlog => {
            var user = await api.getUser(vlog.author)
            vlog.name = user.name
            vlog.head = user.head
        })
        return vlogs
    },
    debounce: function (func, delay) {
        let timerId;
        return (...args) => {
            if (timerId) {
                clearTimeout(timerId);
            }
            timerId = setTimeout(() => {
                func(...args);
                timerId = null;
            }, delay);
        };
    }
}

export default utils;