<!DOCTYPE html>
<html lang="es">
  <?php
    require_once("head.php");
  ?>

  <body>
    <script type="text/javascript" src="js/jquery.ajax-cross-origin.min.js"></script>

    <h1 class="site-heading text-center text-white d-none d-lg-block">
      <span class="site-heading-upper text-primary mb-3">Todos tus vinos aquí</span>
      <span class="site-heading-lower">TensorWine</span>
    </h1>

    <!-- Navigation -->
    <?php
    $pagina = 1;
    require_once("navigation.php");
    ?>

    <!-- Modal -->
    <div class="modal fade" id="myModal-Error" role="dialog">
      <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
          <div class="modal-header">
            <h4 class="modal-title">Vino no encontrado</h4>
          </div>
          <div class="modal-body">
            <p>Lo sentimos pero la foto que ha subido no coincide con ninguno de nuestros vinos</p>
            <p id="error-message"></p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="myModal-Acierto" role="dialog">
      <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
          <div class="modal-header">
            <h4 class="modal-title">Información vino</h4>
          </div>
          <div class="modal-header">
            <h5 id="porcentaje-acierto"></h5>
          </div>
          <div class="modal-body">
            <div style="display:flow-root;">
              <div class="float-left">
                <img class="img-fluid rounded about-heading-img mb-3 mb-lg-0" id="wine-photo">
              </div>
              <div class="float-right">
                <p class="text-right" id="wine-name"></p>
                <p class="text-right" id="wine-owner"></p>
                <p class="text-right" id="wine-do"></p>
              </div>
            </div>
            <div>
              <p id="wine-valoraciones"></p>
              <p id="wine-tipo"></p>
              <p id="wine-uva"></p>
              <p id="wine-bottle"></p>
              <p id="wine-elaboration"></p>
              <p id="wine-recommendation"></p>
              <p id="wine-pairing"></p>
              <p id="wine-informacion"></p>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
          </div>
        </div>

      </div>
    </div>

    <section class="page-section clearfix">
      <div class="container">
        <div class="intro">
          <img class="intro-img img-fluid mb-3 mb-lg-0 rounded" src="img/intro.jpg" alt="">
          <div class="intro-text left-0 text-center bg-faded p-5 rounded">
            <h2 class="section-heading mb-4">
              <span class="section-heading-upper">Prueba la red neuronal</span>
              <span class="section-heading-lower">Sube tu foto</span>
            </h2>
            <p class="mb-3">
              Sube la etiqueta de un vino, puedes buscarla en Internet o puedes hacer tu mismo la foto.
            </p>
            <p>
              Nosotros intentaremos darte la información de tu vino
            </p>
            <form method="POST" enctype="multipart/form-data" id="idForm">

            	<!-- COMPONENT START-->
            	<div class="form-group">
            		<!--input type="file" name="wine_photo"/-->
                <div class="input-group image-preview">
                    <input type="text" class="form-control image-preview-filename" disabled="disabled"> <!-- don't give a name === doesn't send on POST/GET -->
                    <span class="input-group-btn">
                        <!-- image-preview-clear button -->
                        <button type="button" class="btn btn-default image-preview-clear" style="display:none;">
                            <span class="glyphicon glyphicon-remove"></span> Borrar
                        </button>
                        <!-- image-preview-input -->
                        <div class="btn btn-default image-preview-input">
                            <span class="glyphicon glyphicon-folder-open"></span>
                            <span class="image-preview-input-title">Elegir</span>
                            <input type="file" accept="image/png, image/jpeg, image/gif" name="wine_photo"/> <!-- rename it -->
                        </div>
                    </span>
                </div><!-- /input-group image-preview [TO HERE]-->
            	</div>
              <div class="demo">

            	</div>
            	<!-- COMPONENT END -->
            	<div class="intro-button mx-auto">
            		<button type="submit" class="btn btn-primary btn-xl">Subir foto</button>
            	</div>
            </form>
          </div>
        </div>
      </div>
    </section>


    <?php
      require_once("footer.php");
    ?>

    <!-- Bootstrap core JavaScript -->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="js/tensorwine.js"></script>

  </body>

</html>
