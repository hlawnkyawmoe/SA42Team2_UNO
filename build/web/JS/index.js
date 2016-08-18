$(function(){
   $("#btnStartGame").on("click",function(){
        var url = "Common.html";    
        $(location).attr('href',url);
   });
   $("#btnJoinGame").on("click",function(){
       var url = "Individual.html";
       //var url = "Login.html";
       $(location).attr('href',url);
   });
   $("#btnCreatePlayer").on("click",function(){
        var url = "Register.html";    
        $(location).attr('href',url);
   });
   $("#btnExit").on("click",function(){
        
   });
});