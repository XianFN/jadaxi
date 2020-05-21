$(document).on('click', '.flow .item', function(){
   var imageIndex = $(this).index();// the image index
   $(this).find('canvas').attr('src');// the image src
   // then you could call a remoteCommand from here passing the index
})

function clickFlow(item ,e) {  
   //prevents image opening...                                                                     
   if ($(item).parent().hasClass('active')) {
      e.stopImmediatePropagation();
      console.log("Se ha clicado la imagen");
   }
}
