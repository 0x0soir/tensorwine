
$("#idForm").submit(function(e) {
    e.preventDefault();
    $('.image-preview').popover('hide');
    var formData = new FormData($(this)[0]);
    $.ajax({
        crossOrigin: true,
        url: "http://ns3261968.ip-5-39-77.eu:8000/wine_api/recognize_photo",
				dataType: 'text',
        type: 'post',
        contentType: 'application/x-www-form-urlencoded',
        data: formData,
        async: false,
        success: function (msg) {
            var json = $.parseJSON(msg);
            if(json["status"]=="error"){
              var porcentaje = json["predicted_score"] +" ";
              porcentaje = porcentaje.substring(0,2);
              document.getElementById('error-message').innerHTML ="Porcentaje de acierto: " + porcentaje + "% para el vino "+json["predicted_wine"];
              $("#myModal-Error").modal();
            }else{
              var porcentaje = json["wine_info"]["predicted_score"] +" ";
              porcentaje = porcentaje.substring(0,2);
              document.getElementById('porcentaje-acierto').innerHTML ="Porcentaje de acierto: " + porcentaje + "%";
              document.getElementById('wine-name').innerHTML =json["wine_info"]["predicted_wine"];
              try{
                var owner =json["wine_info"]["wine_info"]["owner"]+"";
                if(owner=="undefined"){
                  document.getElementById('wine-owner').innerHTML ="-";
                }else{
                  document.getElementById('wine-owner').innerHTML =owner;
                }
              }catch(err){
                document.getElementById('wine-owner').innerHTML ="-";
              }
              try{
                var info_do =json["wine_info"]["wine_info"]["detailed"]["info_do"]+"";
                if(info_do=="undefined"){
                  document.getElementById('wine-do').innerHTML ="-";
                }else{
                  document.getElementById('wine-do').innerHTML =info_do;
                }
              }catch(err){
                document.getElementById('wine-do').innerHTML ="-";
              }
              try{
                var valoraciones =json["wine_info"]["wine_info"]["points"]+"";
                if(valoraciones=="undefined"){
                  document.getElementById('wine-valoraciones').innerHTML ="Valoraciones: -";
                }else{
                  document.getElementById('wine-valoraciones').innerHTML ="Valoraciones: "+valoraciones;
                }
              }catch(err){
                document.getElementById('wine-valoraciones').innerHTML ="Valoraciones: -";
              }
              try{
                var tipo =json["wine_info"]["wine_info"]["type"]+"";
                if(tipo=="undefined"){
                  document.getElementById('wine-tipo').innerHTML ="Tipo: -";
                }else{
                  document.getElementById('wine-tipo').innerHTML ="Tipo: "+tipo;
                }
              }catch(err){
                document.getElementById('wine-tipo').innerHTML= "Tipo: -";
              }
              try{
                var uva =json["wine_info"]["wine_info"]["grape"]+"";
                if(uva=="undefined"){
                  document.getElementById('wine-uva').innerHTML ="Uva: -";
                }else{
                  document.getElementById('wine-uva').innerHTML ="Uva: "+uva;
                }
              }catch(err){
                document.getElementById('wine-uva').innerHTML= "Uva: -";
              }
              try{
                var bottle =json["wine_info"]["wine_info"]["detailed"]["info_bottle"]+"";
                if(bottle=="undefined"){
                  document.getElementById('wine-bottle').innerHTML ="Botella: -";
                }else{
                  document.getElementById('wine-bottle').innerHTML ="Botella: "+bottle;
                }
              }catch(err){
                document.getElementById('wine-bottle').innerHTML ="Botella: -";
              }
              try{
                var elaboration =json["wine_info"]["wine_info"]["detailed"]["info_elaboration"]+"";
                if(elaboration=="undefined"){
                  document.getElementById('wine-elaboration').innerHTML ="Elaboración: -";
                }else{
                  document.getElementById('wine-elaboration').innerHTML ="Elaboración: "+elaboration;
                }
              }catch(err){
                document.getElementById('wine-elaboration').innerHTML ="Elaboración: -";
              }
              try{
                var recommendation =json["wine_info"]["wine_info"]["detailed"]["info_recommendations"]+"";
                if(recommendation=="undefined"){
                  document.getElementById('wine-recommendation').innerHTML ="Recomendaciones: -";
                }else{
                  document.getElementById('wine-recommendation').innerHTML ="Recomendaciones: "+recommendation;
                }
              }catch(err){
                document.getElementById('wine-recommendation').innerHTML ="Recomendaciones: -";
              }
              try{
                var pairing =json["wine_info"]["wine_info"]["detailed"]["info_pairing"]+"";
                if(pairing=="undefined"){
                  document.getElementById('wine-recommendation').innerHTML ="Maridaje: -";
                }else{
                  document.getElementById('wine-recommendation').innerHTML ="Maridaje: "+pairing;
                }
              }catch(err){
                document.getElementById('wine-recommendation').innerHTML ="Maridaje: -";
              }
              try{
                var info =json["wine_info"]["wine_info"]["detailed"]["info_text"]+"";
                if(info=="undefined"){
                  document.getElementById('wine-informacion').innerHTML ="Información: -";
                }else{
                  document.getElementById('wine-informacion').innerHTML ="Información: "+info;
                }
              }catch(err){
                document.getElementById('wine-informacion').innerHTML ="Información: -";
              }

              document.getElementById('wine-photo').src =json["wine_info"]["wine_info"]["image"];
              $("#myModal-Acierto").modal();
            }
        },
        cache: false,
        contentType: false,
        processData: false,
        crossDomain: true,
        credentials: false
    });
});

$(document).on('click', '#close-preview', function(){
    $('.image-preview').popover('hide');
    // Hover befor close the preview
    $('.image-preview').hover(
        function () {
           $('.image-preview').popover('show');
        },
         function () {
           $('.image-preview').popover('hide');
        }
    );
});

$(function() {
    // Create the close button
    var closebtn = $('<button/>', {
        type:"button",
        text: 'x',
        id: 'close-preview',
        style: 'font-size: initial;',
    });
    closebtn.attr("class","close pull-right");
    // Set the popover default content
    $('.image-preview').popover({
        trigger:'manual',
        html:true,
        title: "<strong>Vista previa</strong>"+$(closebtn)[0].outerHTML,
        content: "There's no image",
        placement:'bottom'
    });
    // Clear event
    $('.image-preview-clear').click(function(){
        $('.image-preview').attr("data-content","").popover('hide');
        $('.image-preview-filename').val("");
        $('.image-preview-clear').hide();
        $('.image-preview-input input:file').val("");
        $(".image-preview-input-title").text("Elegir");
    });
    // Create the preview image
    $(".image-preview-input input:file").change(function (){
        var img = $('<img/>', {
            id: 'dynamic',
            width:250,
            height:200
        });
        var file = this.files[0];
        var reader = new FileReader();
        // Set preview image into the popover data-content
        reader.onload = function (e) {
            $(".image-preview-input-title").text("Cambiar");
            $(".image-preview-clear").show();
            $(".image-preview-filename").val(file.name);
            img.attr('src', e.target.result);
            $(".image-preview").attr("data-content",$(img)[0].outerHTML).popover("show");
        }
        reader.readAsDataURL(file);
    });
});
