<template>
    <div class="container">
      <div class="q-pa-md row justify-center chat-container">
        <div style="width: 100%; max-width: 400px">
          <q-chat-message label="Sunday, 19th" />
          <q-chat-message
            name="me"
            avatar="https://cdn.quasar.dev/img/avatar4.jpg"
            :text="['hey, how are you?']"
            sent
            stamp="7 minutes ago"
          />
          <q-chat-message
            name="Jane"
            avatar="https://cdn.quasar.dev/img/avatar3.jpg"
            :text="['doing fine, how r you?']"
            stamp="4 minutes ago"
          /><q-chat-message
            name="me"
            avatar="https://cdn.quasar.dev/img/avatar4.jpg"
            :text="['hey, how are you?']"
            sent
            stamp="7 minutes ago"
          />
          <q-chat-message
            name="Jane"
            avatar="https://cdn.quasar.dev/img/avatar3.jpg"
            :text="['doing fine, how r you?']"
            stamp="4 minutes ago"
          /><q-chat-message
            name="me"
            avatar="https://cdn.quasar.dev/img/avatar4.jpg"
            :text="['hey, how are you?']"
            sent
            stamp="7 minutes ago"
          />
          <q-chat-message
            name="Jane"
            avatar="https://cdn.quasar.dev/img/avatar3.jpg"
            :text="['doing fine, how r you?']"
            stamp="4 minutes ago"
          /><q-chat-message
            name="me"
            avatar="https://cdn.quasar.dev/img/avatar4.jpg"
            :text="['hey, how are you?']"
            sent
            stamp="7 minutes ago"
          />
          <q-chat-message
            name="Jane"
            avatar="https://cdn.quasar.dev/img/avatar3.jpg"
            :text="['doing fine, how r you?']"
            stamp="4 minutes ago"
          />
        </div>
      </div>
      <div class="bottom-bar">
        <div class="chat">
          <input type="text" placeholder="Type a message..." />
          <button type="submit"><i class="fas fa-paper-plane"></i></button>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {};
  </script>
  
  <style lang="scss" scoped>
  @import url("https://fonts.googleapis.com/css?family=Lato:400,700");
  $font: "Lato", sans-serif;
  $primary: #79c7c5;
  $secondary: #a1e2d9;
  $white: #f9fbff;
  $dark: #777777;
  
  .bottom-bar {
    position: absolute;
    width: 100%;
    height: 55px;
    bottom: 0;
    background: #f9fbff;
    border-radius: 0 0 10px 10px;
  }
  
  .container {
    position: relative;
    margin: 12% auto;
    width: 620px;
    height: 450px;
    overflow: auto;
    background: linear-gradient(to bottom left, #79c7c5 40%, #f9fbff 100%);
  }
  
  .messages {
    position: absolute;
    background: $white;
    opacity: 0.5;
    width: 30%;
    height: 70%;
    top: 2.5%;
    left: 5%;
    border-radius: 10px 0 0 10px;
    box-shadow: -5px 5px 10px rgba($dark, 0.5);
  }
  
  .focus {
    background: $white;
    margin-left: 1px;
  }
  
  .chatbox {
    position: absolute;
    left: 35%;
    height: 75%;
    width: 60%;
    border-radius: 10px;
    box-shadow: 5px 5px 15px rgba($dark, 0.5);
  }
  
  .top-bar {
    width: 100%;
    height: 60px;
    background: $white;
    border-radius: 10px 10px 0 0;
  }
  
  .left {
    left: 0px;
  }
  
  input {
    padding: 7px;
    width: 74%;
    left: 5%;
    position: absolute;
    border: 0;
    top: 13px;
    background: $white;
    color: $primary;
  }
  
  input::placeholder {
    color: $secondary;
  }
  
  input:focus {
    color: $dark;
    outline: 0;
  }
  
  button {
    position: absolute;
    border: 0;
    font-size: 1em;
    color: $secondary;
    top: 19px;
    opacity: 0.8;
    right: 17px;
    cursor: pointer;
    outline: 0;
  
    &:hover {
      transform: scale(1.1);
      transition: all 0.3s ease-in-out;
      color: $primary;
    }
  }
  
  .icons {
    position: absolute;
    color: $secondary;
    padding: 10px;
    top: 5px;
    right: 21px;
    cursor: pointer;
    .fas {
      padding: 5px;
      opacity: 0.8;
      &:hover {
        transform: scale(1.1);
        transition: all .3s ease-in;
      }
    }
  }
  </style>