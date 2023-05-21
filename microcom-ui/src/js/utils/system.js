let system = {
    logout: function () {
        localStorage.removeItem("user");
        localStorage.removeItem("articleDetail");
        localStorage.removeItem("likeList");
        localStorage.removeItem("OAuthToken");
        localStorage.removeItem("tokeninfo");
    }
}

export default system;