<h1>Lectura texto de imágenes</h1>

<h2>Idea inicial</h2>
<p>La primera idea fue el reconocimiento de caracteres con una red neuronal, luego ir añadiendo distintas fuentes y cuando esto funcionara, buscar más de un caracter en una imágen. El problema surge que la situación del espacio blanco alrededor de una letra puede confudir totalmente a la red neuronal.</p>

<h2>Solución</h2>
<p>Encontramos una libreria llamada pytesser que ya está entrenada y aunque tenga fallos realiza este proceso de conversión de imágenes a texto. Se puede encontrar la libreria en el <a href="http://code.google.com/p/pytesser/">enlace</a>, pero tiene muchos fallos que pulir, así que nosotros la descargamos y tratamos para que nos fuera funcional por eso está incluida en el proyecto.</p>

<h2>¿Cómo funciona?</h2>
<p>Es lo más fácil posible, sólo tenemos que importar la libreria y utilizar el método image_file_to_string con la dirección de la imagen. Este método devuelve el texto reconocido.</p>

<h2>Un ejemplo</h2>
<p>Para la imagen de prueba <a href="texto.jpg">texto.jpg</a> hicimos la prueba y el texto resultante fue:<br/>
16 pts:<br/>
Arial: Prueba de texto con letra Arial<br/>
Centuryi Letras djstintas para mensajes djstintos<br/>
36 pts:<br/>
Arialz Prueba distinta de Arial<br/>
Centuryi Esta letra no es igua]</p>
