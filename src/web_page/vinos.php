<!DOCTYPE html>
<html lang="es">

  <?php
    require_once("head.php");
  ?>

  <body>

    <h1 class="site-heading text-center text-white d-none d-lg-block">
      <span class="site-heading-upper text-primary mb-3">Todos tus vinos aquí</span>
      <span class="site-heading-lower">TensorWine</span>
    </h1>

    <!-- Navigation -->
    <?php
    $pagina = 2;
    require_once("navigation.php");
    $url ="http://ns3261968.ip-5-39-77.eu:8000/wine_api/recommendations";
    //Use file_get_contents to GET the URL in question.
    $contents = file_get_contents($url);
    $correct= false;
    if($contents !== false){
        $json_file = json_decode($contents, true);
        $correct= true;
    }
    ?>

    <?php
    if($correct){
      $numero = sizeof($json_file);
      for ($i = 0; $i <$numero; $i++) {
        $seleccionado = $i;
      ?>
    <!-- Modal -->
    <div class="modal fade" id="myModal-<?php echo($seleccionado); ?>" role="dialog">
      <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
          <div class="modal-header">
            <h4 class="modal-title">Información vino</h4>
          </div>
          <div class="modal-body">
            <div style="display:flow-root;">
              <div class="float-left">
                <img class="img-fluid rounded about-heading-img mb-3 mb-lg-0" src="<?php echo($json_file[$seleccionado]["wine_info"]["wine_info"]["image"]); ?>" alt="">
              </div>
              <div class="float-right">
                <p class="text-right"><?php echo($json_file[$seleccionado]["wine_info"]["wine_info"]["name"]); ?></p>
                <p class="text-right"><?php echo($json_file[$seleccionado]["wine_info"]["wine_info"]["owner"]); ?></p>
                <p class="text-right"><?php echo($json_file[$seleccionado]["wine_info"]["wine_info"]["detailed"]["info_do"]);?></p>
              </div>
            </div>
            <div>
              <p>Valoraciones: <?php echo($json_file[$seleccionado]["wine_info"]["wine_info"]["points"]); ?></p>
              <p>Tipo: <?php echo($json_file[$seleccionado]["wine_info"]["wine_info"]["type"]); ?></p>
              <p>Uva: <?php echo($json_file[$seleccionado]["wine_info"]["wine_info"]["grape"]); ?></p>
              <p>Información: <?php echo($json_file[$seleccionado]["wine_info"]["wine_info"]["detailed"]["info_text"]); ?></p>
              <p>Botella: <?php echo($json_file[$seleccionado]["wine_info"]["wine_info"]["detailed"]["info_bottle"]); ?></p>
              <p>Elaboración: <?php echo($json_file[$seleccionado]["wine_info"]["wine_info"]["detailed"]["info_elaboration"]); ?></p>
              <p>Recomendaciones: <?php echo($json_file[$seleccionado]["wine_info"]["wine_info"]["detailed"]["info_recommendations"]); ?></p>
              <p>Maridaje: <?php echo($json_file[$seleccionado]["wine_info"]["wine_info"]["detailed"]["info_pairing"]); ?></p>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
          </div>
        </div>

      </div>
    </div>
    <?php
      }
    } ?>


    <section class="page-section about-heading">
      <div class="container">
        <img class="img-fluid rounded about-heading-img mb-3 mb-lg-0" src="img/products-01.jpg" alt="">
        <div class="about-heading-content">
          <div class="row">
            <div class="col-xl-9 col-lg-10 mx-auto">
              <div class="bg-faded rounded p-5">
                <h2 class="section-heading mb-4">
                  <span class="section-heading-upper">Descubre datos de</span>
                  <span class="section-heading-lower">Más vinos</span>
                </h2>
                <p>Haz click en cualquier vino y te mostraremos los datos que hemos encontrado.</p>
                <ul class="list-unstyled list-hours mb-5 text-left mx-auto" style="width:100%;">
                  <?php
                  if($correct){
                    $numero = sizeof($json_file);
                    for ($i = 0; $i <$numero; $i++) {
                    ?>
                    <li class="list-unstyled-item list-hours-item d-flex">
                      <button type="button" class="btn" data-toggle="modal" data-target="#myModal-<?php echo($i); ?>">
                        <i class="fa fa-search"></i>
                      </button>
                      <span class="span-center"><?php echo($json_file[$i]["wine_info"]["wine_info"]["name"]);?></span>
                      <span class="ml-auto span-center"><?php echo($json_file[$i]["wine_info"]["wine_info"]["detailed"]["info_do"]);?></span>
                    </li>
                    <?php
                    }

                  }
                  ?>
                </ul>
              </div>
            </div>
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

  </body>

</html>
