let mockData = {
  getChatMessage: function (id){
    if(id==1){
      return [
        { name: "1", avatar: "https://cdn.quasar.dev/img/avatar4.jpg", text: ['hey, how are you?'], sent: true, stamp: 1618447113314 },
        { name: "1", avatar: "https://cdn.quasar.dev/img/avatar3.jpg", text: ['doing fine, how r you?'], sent: false, stamp: 1618447113314 },
        { name: "1", avatar: "https://cdn.quasar.dev/img/avatar4.jpg", text: ['hey, how are you?'], sent: true, stamp: 1618447113314 },
        { name: "1", avatar: "https://cdn.quasar.dev/img/avatar3.jpg", text: ['doing fine, how r you?'], sent: false, stamp: 1618447113314 },
        { name: "1", avatar: "https://cdn.quasar.dev/img/avatar3.jpg", text: ['doing fine, how r you?doing fine, how r you?doing fine, how r you?doing fine, how r you?doing fine, how r you?doing fine, how r you?'], sent: false, stamp: 1619449113314 },
        { name: "1", avatar: "https://cdn.quasar.dev/img/avatar3.jpg", text: ['doing fine, how r you?'], sent: false, stamp: Date.now() },
      ]
    }else if(id==2){
      return [
        { name: "2", avatar: "https://cdn.quasar.dev/img/avatar4.jpg", text: ['hey, how are you?'], sent: true, stamp: 1618447113314 },
        { name: "2", avatar: "https://cdn.quasar.dev/img/avatar3.jpg", text: ['doing fine, how r you?'], sent: false, stamp: 1618447113314 },
        { name: "2", avatar: "https://cdn.quasar.dev/img/avatar4.jpg", text: ['hey, how are you?'], sent: true, stamp: 1618447113314 },
        { name: "2", avatar: "https://cdn.quasar.dev/img/avatar3.jpg", text: ['doing fine, how r you?'], sent: false, stamp: 1618447113314 },
        { name: "2", avatar: "https://cdn.quasar.dev/img/avatar3.jpg", text: ['doing fine, how r you?doing fine, how r you?doing fine, how r you?doing fine, how r you?doing fine, how r you?doing fine, how r you?'], sent: false, stamp: 1619449113314 },
        { name: "2", avatar: "https://cdn.quasar.dev/img/avatar3.jpg", text: ['doing fine, how r you?'], sent: false, stamp: Date.now() },
      ]
    }else if(id==3){
      return [
        { name: "3", avatar: "https://cdn.quasar.dev/img/avatar4.jpg", text: ['hey, how are you?'], sent: true, stamp: 1618447113314 },
        { name: "3", avatar: "https://cdn.quasar.dev/img/avatar3.jpg", text: ['doing fine, how r you?'], sent: false, stamp: 1618447113314 },
        { name: "3", avatar: "https://cdn.quasar.dev/img/avatar4.jpg", text: ['hey, how are you?'], sent: true, stamp: 1618447113314 },
        { name: "3", avatar: "https://cdn.quasar.dev/img/avatar3.jpg", text: ['doing fine, how r you?'], sent: false, stamp: 1618447113314 },
        { name: "3", avatar: "https://cdn.quasar.dev/img/avatar3.jpg", text: ['doing fine, how r you?doing fine, how r you?doing fine, how r you?doing fine, how r you?doing fine, how r you?doing fine, how r you?'], sent: false, stamp: 1619449113314 },
        { name: "3", avatar: "https://cdn.quasar.dev/img/avatar3.jpg", text: ['doing fine, how r you?'], sent: false, stamp: Date.now() },
      ]
    }else{
      return [
        { name: "other", avatar: "https://cdn.quasar.dev/img/avatar4.jpg", text: ['hey, how are you?'], sent: true, stamp: 1618447113314 },
        { name: "other", avatar: "https://cdn.quasar.dev/img/avatar3.jpg", text: ['doing fine, how r you?'], sent: false, stamp: 1618447113314 },
        { name: "other", avatar: "https://cdn.quasar.dev/img/avatar4.jpg", text: ['hey, how are you?'], sent: true, stamp: 1618447113314 },
        { name: "other", avatar: "https://cdn.quasar.dev/img/avatar3.jpg", text: ['doing fine, how r you?'], sent: false, stamp: 1618447113314 },
        { name: "other", avatar: "https://cdn.quasar.dev/img/avatar3.jpg", text: ['doing fine, how r you?doing fine, how r you?doing fine, how r you?doing fine, how r you?doing fine, how r you?doing fine, how r you?'], sent: false, stamp: 1619449113314 },
        { name: "other", avatar: "https://cdn.quasar.dev/img/avatar3.jpg", text: ['doing fine, how r you?'], sent: false, stamp: Date.now() },
      ]
    }

  },
  getConversations: function () {
    return [
      {
        id: 1,
        person: 'Razvan Stoenescu',
        avatar: 'https://cdn.quasar.dev/team/razvan_stoenescu.jpeg',
        caption: 'I\'m working on Quasar!',
        time: '15:00',
        sent: true
      },
      {
        id: 2,
        person: 'Dan Popescu',
        avatar: 'https://cdn.quasar.dev/team/dan_popescu.jpg',
        caption: 'I\'m working on Quasar!',
        time: '16:00',
        sent: true
      },
      {
        id: 3,
        person: 'Jeff Galbraith',
        avatar: 'https://cdn.quasar.dev/team/jeff_galbraith.jpg',
        caption: 'I\'m working on Quasar!',
        time: '18:00',
        sent: true
      },
      {
        id: 4,
        person: 'Allan Gaunt',
        avatar: 'https://cdn.quasar.dev/team/allan_gaunt.png',
        caption: 'I\'m working on Quasar!',
        time: '17:00',
        sent: true
      }
    ]
  }
}

export default mockData;