<nav class="navbar navbar-expand-lg navbar-dark py-lg-4" id="mainNav">
  <div class="container">
    <a class="navbar-brand text-uppercase text-expanded font-weight-bold d-lg-none" href="index.php">Qué visitar</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarResponsive">
      <ul class="navbar-nav mx-auto">
        <li class="nav-item <?php if ($pagina == 0){ echo("active");} ?> px-lg-4">
          <a class="nav-link text-uppercase text-expanded" href="index.php">El proyecto

          </a>
        </li>
        <li class="nav-item <?php if ($pagina == 1){ echo("active");} ?> px-lg-4">
          <a class="nav-link text-uppercase text-expanded" href="foto.php">Sube tu foto
          </a>
        </li>
        <li class="nav-item <?php if ($pagina == 2){ echo("active");} ?> px-lg-4">
          <a class="nav-link text-uppercase text-expanded" href="vinos.php">Más vinos
          </a>
        </li>
      </ul>
    </div>
  </div>
</nav>
