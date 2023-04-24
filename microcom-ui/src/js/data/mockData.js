let mockData = {
  getChatMessage: function (id) {
    if (id == 1) {
      return [
        {
          "from": {
            "id": 2,
            "name": "余詩涵",
            "head": "https://cdn.quasar.dev/img/avatar4.jpg"
          },
          "message": "hello world",
          "to": {
            "id": 1,
            "name": "张三",
            "head": "https://cdn.quasar.dev/img/avatar3.jpg"
          },
          "time": "2023-04-23 22:04:02"
        },
        {
          "from": {
            "id": 2,
            "name": "余詩涵",
            "head": "https://cdn.quasar.dev/img/avatar4.jpg"
          },
          "message": "hello world",
          "to": {
            "id": 1,
            "name": "张三",
            "head": "https://cdn.quasar.dev/img/avatar3.jpg"
          },
          "time": "2023-04-23 22:08:14"
        },
        {
          "from": {
            "id": 2,
            "name": "余詩涵",
            "head": "https://cdn.quasar.dev/img/avatar4.jpg"
          },
          "message": "hello world",
          "to": {
            "id": 1,
            "name": "张三",
            "head": "https://cdn.quasar.dev/img/avatar3.jpg"
          },
          "time": "2023-04-23 22:08:14"
        },
        {
          "from": {
            "id": 2,
            "name": "余詩涵",
            "head": "https://cdn.quasar.dev/img/avatar4.jpg"
          },
          "message": "hello world",
          "to": {
            "id": 1,
            "name": "张三",
            "head": "https://cdn.quasar.dev/img/avatar3.jpg"
          },
          "time": "2023-04-23 22:08:48"
        },
        {
          "from": {
            "id": 2,
            "name": "余詩涵",
            "head": "https://cdn.quasar.dev/img/avatar4.jpg"
          },
          "message": "hello world",
          "to": {
            "id": 1,
            "name": "张三",
            "head": "https://cdn.quasar.dev/img/avatar3.jpg"
          },
          "time": "2023-04-23 22:08:48"
        },
        {
          "from": {
            "id": 1,
            "name": "张三",
            "head": "https://cdn.quasar.dev/img/avatar3.jpg"
          },
          "message": "hello",
          "to": {
            "id": 2,
            "name": "余詩涵",
            "head": "https://cdn.quasar.dev/img/avatar4.jpg"
          },
          "time": "2023-04-23 22:45:25"
        },
        {
          "from": {
            "id": 1,
            "name": "张三",
            "head": "https://cdn.quasar.dev/img/avatar3.jpg"
          },
          "message": "hello",
          "to": {
            "id": 2,
            "name": "余詩涵",
            "head": "https://cdn.quasar.dev/img/avatar4.jpg"
          },
          "time": "2023-04-23 22:45:54"
        },
        {
          "from": {
            "id": 2,
            "name": "余詩涵",
            "head": "https://cdn.quasar.dev/img/avatar4.jpg"
          },
          "message": "hello",
          "to": {
            "id": 1,
            "name": "张三",
            "head": "https://cdn.quasar.dev/img/avatar3.jpg"
          },
          "time": "2023-04-23 22:46:02"
        },
        {
          "from": {
            "id": 1,
            "name": "张三",
            "head": "https://cdn.quasar.dev/img/avatar3.jpg"
          },
          "message": "heello",
          "to": {
            "id": 2,
            "name": "余詩涵",
            "head": "https://cdn.quasar.dev/img/avatar4.jpg"
          },
          "time": "2023-04-23 22:47:31"
        },
        {
          "from": {
            "id": 2,
            "name": "余詩涵",
            "head": "https://cdn.quasar.dev/img/avatar4.jpg"
          },
          "message": "hello",
          "to": {
            "id": 1,
            "name": "张三",
            "head": "https://cdn.quasar.dev/img/avatar3.jpg"
          },
          "time": "2023-04-23 22:47:36"
        },
        {
          "from": {
            "id": 2,
            "name": "余詩涵",
            "head": "https://cdn.quasar.dev/img/avatar4.jpg"
          },
          "message": "hello",
          "to": {
            "id": 1,
            "name": "张三",
            "head": "https://cdn.quasar.dev/img/avatar3.jpg"
          },
          "time": "2023-04-23 22:48:07"
        },
        {
          "from": {
            "id": 1,
            "name": "张三",
            "head": "https://cdn.quasar.dev/img/avatar3.jpg"
          },
          "message": "sada",
          "to": {
            "id": 2,
            "name": "余詩涵",
            "head": "https://cdn.quasar.dev/img/avatar4.jpg"
          },
          "time": "2023-04-23 22:50:00"
        },
        {
          "from": {
            "id": 2,
            "name": "余詩涵",
            "head": "https://cdn.quasar.dev/img/avatar4.jpg"
          },
          "message": "hello",
          "to": {
            "id": 1,
            "name": "张三",
            "head": "https://cdn.quasar.dev/img/avatar3.jpg"
          },
          "time": "2023-04-23 22:50:13"
        },
        {
          "from": {
            "id": 1,
            "name": "张三",
            "head": "https://cdn.quasar.dev/img/avatar3.jpg"
          },
          "message": "asd",
          "to": {
            "id": 2,
            "name": "余詩涵",
            "head": "https://cdn.quasar.dev/img/avatar4.jpg"
          },
          "time": "2023-04-24 00:12:00"
        },
        {
          "from": {
            "id": 2,
            "name": "余詩涵",
            "head": "https://cdn.quasar.dev/img/avatar4.jpg"
          },
          "message": "asd",
          "to": {
            "id": 1,
            "name": "张三",
            "head": "https://cdn.quasar.dev/img/avatar3.jpg"
          },
          "time": "2023-04-24 00:12:11"
        },
        {
          "from": {
            "id": 2,
            "name": "余詩涵",
            "head": "https://cdn.quasar.dev/img/avatar4.jpg"
          },
          "message": "asd",
          "to": {
            "id": 1,
            "name": "张三",
            "head": "https://cdn.quasar.dev/img/avatar3.jpg"
          },
          "time": "2023-04-24 00:13:46"
        },
        {
          "from": {
            "id": 2,
            "name": "余詩涵",
            "head": "https://cdn.quasar.dev/img/avatar4.jpg"
          },
          "message": "sd",
          "to": {
            "id": 1,
            "name": "张三",
            "head": "https://cdn.quasar.dev/img/avatar3.jpg"
          },
          "time": "2023-04-24 00:14:21"
        },
        {
          "from": {
            "id": 2,
            "name": "余詩涵",
            "head": "https://cdn.quasar.dev/img/avatar4.jpg"
          },
          "message": "gh",
          "to": {
            "id": 1,
            "name": "张三",
            "head": "https://cdn.quasar.dev/img/avatar3.jpg"
          },
          "time": "2023-04-24 00:18:29"
        },
        {
          "from": {
            "id": 1,
            "name": "张三",
            "head": "https://cdn.quasar.dev/img/avatar3.jpg"
          },
          "message": "gh",
          "to": {
            "id": 2,
            "name": "余詩涵",
            "head": "https://cdn.quasar.dev/img/avatar4.jpg"
          },
          "time": "2023-04-24 00:18:33"
        },
        {
          "from": {
            "id": 1,
            "name": "张三",
            "head": "https://cdn.quasar.dev/img/avatar3.jpg"
          },
          "message": "ghgh",
          "to": {
            "id": 2,
            "name": "余詩涵",
            "head": "https://cdn.quasar.dev/img/avatar4.jpg"
          },
          "time": "2023-04-24 00:18:36"
        },
        {
          "from": {
            "id": 1,
            "name": "张三",
            "head": "https://cdn.quasar.dev/img/avatar3.jpg"
          },
          "message": "ghghgh",
          "to": {
            "id": 2,
            "name": "余詩涵",
            "head": "https://cdn.quasar.dev/img/avatar4.jpg"
          },
          "time": "2023-04-24 00:18:38"
        }
      ]
    } else if (id == 2) {
      return [{"from":{"head":"https://cdn.quasar.dev/img/avatar4.jpg","id":2,"name":"余詩涵"},"message":"hello world","time":"2023-04-23 22:04:02","to":{"head":"https://cdn.quasar.dev/img/avatar3.jpg","id":1,"name":"张三"}},{"from":{"head":"https://cdn.quasar.dev/img/avatar4.jpg","id":2,"name":"余詩涵"},"message":"hello world","time":"2023-04-23 22:08:14","to":{"head":"https://cdn.quasar.dev/img/avatar3.jpg","id":1,"name":"张三"}},{"from":{"head":"https://cdn.quasar.dev/img/avatar4.jpg","id":2,"name":"余詩涵"},"message":"hello world","time":"2023-04-23 22:08:14","to":{"head":"https://cdn.quasar.dev/img/avatar3.jpg","id":1,"name":"张三"}},{"from":{"head":"https://cdn.quasar.dev/img/avatar4.jpg","id":2,"name":"余詩涵"},"message":"hello world","time":"2023-04-23 22:08:48","to":{"head":"https://cdn.quasar.dev/img/avatar3.jpg","id":1,"name":"张三"}},{"from":{"head":"https://cdn.quasar.dev/img/avatar4.jpg","id":2,"name":"余詩涵"},"message":"hello world","time":"2023-04-23 22:08:48","to":{"head":"https://cdn.quasar.dev/img/avatar3.jpg","id":1,"name":"张三"}},{"from":{"head":"https://cdn.quasar.dev/img/avatar4.jpg","id":2,"name":"余詩涵"},"message":"hello","time":"2023-04-23 22:46:02","to":{"head":"https://cdn.quasar.dev/img/avatar3.jpg","id":1,"name":"张三"}},{"from":{"head":"https://cdn.quasar.dev/img/avatar4.jpg","id":2,"name":"余詩涵"},"message":"hello","time":"2023-04-23 22:47:36","to":{"head":"https://cdn.quasar.dev/img/avatar3.jpg","id":1,"name":"张三"}},{"from":{"head":"https://cdn.quasar.dev/img/avatar4.jpg","id":2,"name":"余詩涵"},"message":"hello","time":"2023-04-23 22:48:07","to":{"head":"https://cdn.quasar.dev/img/avatar3.jpg","id":1,"name":"张三"}},{"from":{"head":"https://cdn.quasar.dev/img/avatar4.jpg","id":2,"name":"余詩涵"},"message":"hello","time":"2023-04-23 22:50:13","to":{"head":"https://cdn.quasar.dev/img/avatar3.jpg","id":1,"name":"张三"}},{"from":{"head":"https://cdn.quasar.dev/img/avatar4.jpg","id":2,"name":"余詩涵"},"message":"asd","time":"2023-04-24 00:12:11","to":{"head":"https://cdn.quasar.dev/img/avatar3.jpg","id":1,"name":"张三"}},{"from":{"head":"https://cdn.quasar.dev/img/avatar4.jpg","id":2,"name":"余詩涵"},"message":"asd","time":"2023-04-24 00:13:46","to":{"head":"https://cdn.quasar.dev/img/avatar3.jpg","id":1,"name":"张三"}},{"from":{"head":"https://cdn.quasar.dev/img/avatar4.jpg","id":2,"name":"余詩涵"},"message":"sd","time":"2023-04-24 00:14:21","to":{"head":"https://cdn.quasar.dev/img/avatar3.jpg","id":1,"name":"张三"}},{"from":{"head":"https://cdn.quasar.dev/img/avatar4.jpg","id":2,"name":"余詩涵"},"message":"gh","time":"2023-04-24 00:18:29","to":{"head":"https://cdn.quasar.dev/img/avatar3.jpg","id":1,"name":"张三"}}]
    } else if (id == 3) {
      return [
        { name: "3", avatar: "https://cdn.quasar.dev/img/avatar4.jpg", text: ['hey, how are you?'], sent: true, stamp: 1618447113314 },
        { name: "3", avatar: "https://cdn.quasar.dev/img/avatar3.jpg", text: ['doing fine, how r you?'], sent: false, stamp: 1618447113314 },
        { name: "3", avatar: "https://cdn.quasar.dev/img/avatar4.jpg", text: ['hey, how are you?'], sent: true, stamp: 1618447113314 },
        { name: "3", avatar: "https://cdn.quasar.dev/img/avatar3.jpg", text: ['doing fine, how r you?'], sent: false, stamp: 1618447113314 },
        { name: "3", avatar: "https://cdn.quasar.dev/img/avatar3.jpg", text: ['doing fine, how r you?doing fine, how r you?doing fine, how r you?doing fine, how r you?doing fine, how r you?doing fine, how r you?'], sent: false, stamp: 1619449113314 },
        { name: "3", avatar: "https://cdn.quasar.dev/img/avatar3.jpg", text: ['doing fine, how r you?'], sent: false, stamp: Date.now() },
      ]
    } else {
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