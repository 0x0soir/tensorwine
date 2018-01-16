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
    $pagina = 0;
    require_once("navigation.php");
    ?>

    <section class="page-section about-heading">
      <div class="container">
        <img class="img-fluid rounded about-heading-img mb-3 mb-lg-0" src="img/about.jpg" alt="">
        <div class="about-heading-content">
          <div class="row">
            <div class="col-xl-9 col-lg-10 mx-auto">
              <div class="bg-faded rounded p-5">
                <h2 class="section-heading mb-4">
                  <span class="section-heading-upper">Los vinos y la programación</span>
                  <span class="section-heading-lower">Nuestro proyecto</span>
                </h2>
                <p>Nuestro grupo ha intentado desarrollar este proyecto a partir de una idea aportada por el profesor, en la cual hemos utilizado distintas herramientas actuales, así como la búsqueda de bases de datos en Internet.</p>
                <p class="mb-0">Hemos conseguido desarrollar una API a la que se conecta la aplicación y la página web para ejecutar los algoritmos de TensorFlow para el reconocimiento de etiquetas de vinos.</p>
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
