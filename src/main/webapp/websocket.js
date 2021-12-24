var ws;

function handleKeyPress(e){
 var key=e.keyCode || e.which;
  if (key === 13){ // Клавиша Enter
send();
  }
}



function connect() {
    var username = (document.getElementById("username").value).replace("#", "X");


    var host = document.location.host;
    var pathname = document.location.pathname;


    
    ws = new WebSocket("wss://" +host  + pathname + "chat/" + username);

    ws.onmessage = function(event) {
    var log = document.getElementById("log");
    var sys = document.getElementById("system");

        console.log(event.data);
        var message = JSON.parse(event.data);

       // log.innerHTML += message.from + " : " + message.content + "\n";
        log.innerHTML =message.content;
        sys.innerHTML ="NOTHING";

    };



}


function send() {
    var content = document.getElementById("msg").value;



   var json = JSON.stringify({
        "content":content ,

    });

    document.getElementById("msg").value ="";
    ws.send(json);
}




