
var stompClient = null;
connect();
function connect() {
  

 

      

      var socket = new SockJS("http://localhost:8022/greeting");
      stompClient = Stomp.over(socket);

      stompClient.connect({}, onConnected, onError);

}


function onConnected() {
  stompClient.subscribe('/topic/reply', onMessageReceived);

}
function onMessageReceived(payload){console.log(payload.body);}
function onError(error) {

}

function disconnect() {
    stompClient.close90;
}

function sendName() {
    ws.send("ds");
}

function showGreeting(message) {
    $("#greetings").append(" " + message + "");
}