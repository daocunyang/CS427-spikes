
var temporary = $("img").css("width");
var convert1 = temporary.substring(0,temporary.length-2);
var convert2 = Number(convert1);
var flag = 0;

$(document).ready(function(){

  $("#Zoomin").click(function(){

  	 var temp = $("img").css("width");
  	 var width = temp.substring(0,temp.length-2);
  	 var width = Number(width);
      width = width + 30 + "px";

     $("img#image").css("width", width);

 });

    $("#Zoomout").click(function(){

  	 var temp = $("img").css("width");
  	 var width = temp.substring(0,temp.length-2);
  	 var width = Number(width);
      width = width - 30 + "px";

     $("img#image").css("width", width);

 });

    $("#use_slider").click(function(){
      if (flag ==0) {
       $("div#slider").show();
       $("#Zoomin").hide();
       $("#Zoomout").hide();
       $("#use_slider").text("Use buttons");
       flag = 1;
       return;
     }
      if (flag == 1) {
       $("div#slider").hide();
       $("#Zoomin").show();
       $("#Zoomout").show();
       $("#use_slider").text("Use Slider");
       flag = 0;
      }

    });


$( "#slider" ).slider({
  value: 100,
  step: 30,
  min: convert2,
  max: 600,
   slide: function(event,ui) {
      
         var width = ui.value + "px";       
         $("img#image").css("width",width);       
        // $( "#amount" ).val( ui.value );
      }

});

});