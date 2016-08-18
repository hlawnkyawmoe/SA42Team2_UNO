var login = {};
var dataCollection;
$(function(){
    console.log("function")
    console.log(this);
    $("#btnLogin").on("click",function(){
        console.log("Inner Individual JS");
        var LogInpromise = $.get("Login",function(){
            console.log("Call Servlet");
        }).done(function(LogInresult){
            console.log("Login Done");
                console.log("Enter Join Game Servlet");
                login.userid = LogInresult.username;
                console.log(login.userid);
                //var gameid = $(this.sess
                console.log(this);
                var promise = $.getJSON("JoinGame",function(){
                    console.log("JoinGame enter Jquery");
                }).done(function(result){
                    console.log(result);
                    dataCollection = result;
                    showResult();
                });
        });
        console.log("END Individual JS");
    });
    $("#DropArea").hide();
});

$(function(){
    $("#btnClickMe").on("click",function(){
        console.log("Inner Individual JS");
        var url = "Login.html";    
        $(location).attr('href',url);
});
}
);


function showResult()
{
    var template = "{{#each item}} <tr><td>{{this.sessionID}}</td><td>{{this.starter}}</td><td><button name=\"btnJoin\" id=\"btnJoin\" value={{this.sessionID}} type=\"button\" class=\"btn btn-xs\">JOIN</button></td><tr> {{/each}}";
    var generateData = Handlebars.compile(template);
    $("#showData tr").remove();
    $("#showData").append("<tr style='text-align:center;'><td style='text-align:left;'> <u>GAME ID</u> </td><td colspan='2' style='text-align:left;'> <u>STARTER NAME</u> </td></tr>");
    $("#showData").append(generateData({item:dataCollection}));
    $("#showData button").on("click",function(){
        var gameid = $(this).prop("value");
        login.gameid = gameid;
        login.event = new EventSource("api/playerplay?playerName=" + login.userid+ "&gameID=" + login.gameid);
        console.log("------->> EVENT DONE <<------");
        
        $(login.event).on(login.gameid, handleGAME);

        console.log("------->> Individual Page <<------");
        $("#showData").empty().append($("<center><h2>").text("Game ID : " + login.gameid + ", " + login.userid + " is waiting for other players...."));
    });
}

/*
 * { cmd: "start_game", cards: [ 2, 3, 4, 5, 6, 7, 8 ]}
 */
function handleGAME(evt) {
    
    console.log("---->> Inside the handleGAME <<-----");
    var result = JSON.parse(evt.originalEvent.data);
    login.userid = result.playerName;
    login.gameid = result.gameId;
    switch (result.cmd) {
//>>>>>Game Start
        case "start_game":
//When Game is Started >> 7 Cards to each Player
                $("#TableDiv").empty();                
                var template = "<img src = 'Images/cards/{{card}}' class='draggable' id='{{card}}' value='{{card}}' data-cardID='{{card}}' style='display:inline-block; position: absolute;'>";
//                 
                var generateData = Handlebars.compile(template);

                for(var i in result.CardList)
                {
                    var style = $(generateData({card:result.CardList[i].Cards}));
                    style.css({left:(150+(i*60))+"px"});
                    $("#TableDiv").append(style);
                    $("#TableDiv").css("{height:600px}"); 
                }
//Drop Area and Draw Pile Button                
                $("#DropArea").show();
                $("#DropArea").append("<div id='DrawPileDiv'><button id='btnDrawPile' value='"+login.userid+"'><img src='Images/cards/back.png' id='draggable'></button></div>");
                $("#DropArea").append("<div class='DropBox' id='droppable'><h4>Drop Here</h4></div>");
                $("#DropArea").css({"margin-top":"300px"});
                
//Click on DrawPile >>>  
                $("#DropArea button").on("click",function(){
                    $.post("PlayServlet", {
                        gameID: login.gameid,
                        playerName: login.userid
                    }).done(function() {
                        console.log("I did it.");
                        //$("#message").val("");                   
                    });
                });
//Drag and Drop Part                
                
                $(".draggable").draggable({
                    revert:true,
                    drag: function(event,ui){
                        $("#droppable").find("h4").html("Drop Here");
                    }
                });
                $( "#droppable" ).droppable({
                    drop: function( event, ui ) {
                        var image = ui.draggable;
                        image.remove();
                        $( this ).find( "h4" ).html( "Dropped!" );
                        login.dropID = ui.draggable.attr("id");
                        console.log(login.dropID);
                        
                        $.get("PlayServlet", {
                        gameID: login.gameid,
                        playerName: login.userid,
                        dropCard: login.dropID
                    },function(){
                        console.log("Starting PlayServlet");
                    }).done(function() {
                        
                    });
                    }
                });
                
            break;
//>>>>>>>>>>>Draw Card
        case "playGame_DrawCard":
            console.log(" Inside a Event Funtion : Draw A Card");
            $("#TableDiv").empty();                
                var template = "<img src = 'Images/cards/{{card}}' class='draggable' id='{{card}}' value='{{card}}' data-cardID='{{card}}' style='display:inline-block; position: absolute;'>";
                var generateData = Handlebars.compile(template);

                for(var i in result.CardList)
                {
                    
                    var style = $(generateData({card:result.CardList[i].Cards}));
                    style.css({left:(150+(i*60))+"px"});
                    $("#TableDiv").append(style);
                    $("#TableDiv").css("{height:600px}");
                }

//Drag and Drop Part                
                $(".draggable").draggable({
                    revert:true,
                    drag: function(event,ui){
                        $("#droppable").find("h4").html("Drop Here");
                    }
                });
                $( "#droppable" ).droppable({
                    drop: function( event, ui ) {
                        var image = ui.draggable;
                        image.remove();
                        $( this ).find( "h4" ).html( "Dropped!" );
                        login.dropID = ui.draggable.attr("id");
                        console.log(login.dropID);
                        
                        $.get("PlayServlet", {
                        gameID: login.gameid,
                        playerName: login.userid,
                        dropCard: login.dropID
                    },function(){
                        console.log("Starting PlayServlet");
                    }).done(function() {
                        
                    });
                    }
                });
            break;
//>>>>>>>>>>>>Discard Pile
        case "playGame_RetakeCard":
            $("#TableDiv").empty();                
                var template = "<img src = 'Images/cards/{{card}}' class='draggable' id='{{card}}' value='{{card}}' data-cardID='{{card}}' style='display:inline-block; position: absolute;'>";
                var generateData = Handlebars.compile(template);

                for(var i in result.CardList)
                {
                    
                    var style = $(generateData({card:result.CardList[i].Cards}));
                    style.css({left:(150+(i*60))+"px"});
                    $("#TableDiv").append(style);
                    $("#TableDiv").css("{height:600px}");
                }
//Drag and Drop Part                
                $(".draggable").draggable({
                    revert:true,
                    drag: function(event,ui){
                        $("#droppable").find("h4").html("Drop Here");
                    }
                });
                $( "#droppable" ).droppable({
                    drop: function( event, ui ) {
                        var image = ui.draggable;
                        image.remove();
                        $( this ).find( "h4" ).html( "Dropped!" );
                        login.dropID = ui.draggable.attr("id");
                        console.log(login.dropID);
                        
                        $.get("PlayServlet", {
                        gameID: login.gameid,
                        playerName: login.userid,
                        dropCard: login.dropID
                    },function(){
                        console.log("Starting PlayServlet");
                    }).done(function() {
                        
                    });
                    }
                });
            break;
    }
}