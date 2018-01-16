<?php
$dir_subida = '';
$fichero_subido = $dir_subida . basename($_FILES['photoInput']['name']);
$uploadOk = 0;
echo '<pre>';
if (move_uploaded_file($_FILES['photoInput']['tmp_name'], $fichero_subido)) {
    $check = getimagesize($_FILES["photoInput"]["name"]);
    if($check !== false) {
        echo "El fichero es válido y se subió con éxito.\n";
        $uploadOk = 1;
    } else {
        echo "File is not an image.";
        $uploadOk = 0;
    }
} else {
    echo "¡Posible ataque de subida de ficheros!\n";
}

echo ('Más información de depuración:');
print_r($_FILES);

print "</pre>";

?>
