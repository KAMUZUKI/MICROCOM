function getURLParams(){
    console.log('$route.query:', this.$route.query)
    const query = this.$route.query
    const data = this.formatURLData(location.search)
    console.log('hash:', location.hash)
    console.log('search:', location.search, location.search && data)
    const name= (query && query.name) || (data && data.name)
    console.log('params.name:', name)
}

export default getURLParams;