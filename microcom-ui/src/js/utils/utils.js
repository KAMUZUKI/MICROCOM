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
    getURLParams: function () {
        console.log('$route.query:', this.$route.query)
        const query = this.$route.query
        const data = this.formatURLData(location.search)
        console.log('hash:', location.hash)
        console.log('search:', location.search, location.search && data)
        const name = (query && query.name) || (data && data.name)
        console.log('params.name:', name)
    }
}

export default utils;