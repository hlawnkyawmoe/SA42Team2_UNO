var UNOApp = { };
var playerlist = []; 

$(function(){
    $("#btnStart").on("click",function(){
        $.getJSON("StartNewGame",function(){
        }).done(function(result){
            UNOApp.gameID = result.UNOID;
            UNOApp.pCount = document.getElementById("noOfPlayers").value;
            UNOApp.pStarter = document.getElementById("starterid").value;//$("#starterid").val();
            console.log(UNOApp);
            $("#TableDiv").empty().append($("<center><h2>").text("Game ID = " + UNOApp.gameID))
                    .append($("<center><h1>").text("Waiting for another players.TableDiv..."));
            
            UNOApp.event = new EventSource("api/play/"+ UNOApp.gameID +"?playerName=UNOtable" 
                    + "&pStarter=" + UNOApp.pStarter + "&pCount=" + UNOApp.pCount);
            $(UNOApp.event).on(UNOApp.gameID, handleUNO);
            
    });
   }) ;
   
    $("#btnDropCard").on("click",function(){
        console.log("Common Page Button Double Click");
        $.post("RetakeServlet", {
            gameID: UNOApp.gameID
            },function(){
                console.log("Starting RetakeServlet");
                }).done(function() {        
                    });
                });
});

function handleUNO(evt) {
    
    var UNOMsg = JSON.parse(evt.originalEvent.data); 
    
    console.log("UNO First card : " + UNOMsg.first_card);
    
     switch (UNOMsg.cmd) {
        case "start_game":
            console.log(UNOMsg);
            console.log(UNOApp);
            console.log("UNO First card : " + UNOMsg.first_card);
            $("#TableDiv").empty().append("Players: ");
                for (var i = 0, l = playerlist.length; i < l; ++i) {
                    $("#TableDiv").append("        "+playerlist[i] +"<br>");
                }
            $("#TableDiv").append("<button id='btnDropCard' class='btnDropCard'><img src='Images/cards/" + UNOMsg.first_card +"' alt='UNO'></button>")
                .append($("<center><h5>").text("UNO Card"));
                console.log("UNO Card for Common View");
                
            $("#TableDiv button").on("dblclick",function(){
                console.log("Common Page Button Double Click");
                $.post("RetakeServlet", {
                        gameID: UNOApp.gameID
                    },function(){
                        console.log("Starting RetakeServlet");
                    }).done(function() {
                        
                    });
            });
            break;
            
        case "player_Join" : console.log("player_Join");console.log(UNOMsg);
            playerlist.push(UNOMsg.playerName);
            $("#TableDiv").append(UNOMsg.playerName +"<br>");
            break;
            
        case "playGame_DropCard" :
            UNOApp.drop_card = UNOMsg.DropCard;
            console.log("Drop a card" + UNOApp.drop_card);
            $("#TableDiv").empty().append("<button class='btnDropCard'><img src='Images/cards/" + UNOApp.drop_card +"' alt='UNO'></button>")
                .append($("<center><h5>").text("UNO Card"));
                console.log("UNO Card for Common View");
            
            $("#TableDiv button").on("dblclick",function(){
                console.log("Common Page Button Double Click");
                $.post("RetakeServlet", {
                        gameID: UNOApp.gameID
                    },function(){
                        console.log("Starting RetakeServlet");
                    }).done(function() {
                        
                    });
                });
            break;
        case "playGame_RetakeCard":
            UNOApp.Card = UNOMsg.Card;
            console.log("Drop a card" + UNOApp.drop_card);
            $("#TableDiv").empty().append("<button><img src='Images/cards/" + UNOApp.Card +"' alt='UNO'></button>")
                .append($("<center><h5>").text("UNO Card"));
                console.log("UNO Card for Common View");
            
            $("#TableDiv button").on("dblclick",function(){
                console.log("Common Page Button Double Click");
                $.post("RetakeServlet", {
                        gameID: UNOApp.gameID
                    },function(){
                        console.log("Starting RetakeServlet");
                    }).done(function() {
                        
                    });
                });
            break;
     }
}