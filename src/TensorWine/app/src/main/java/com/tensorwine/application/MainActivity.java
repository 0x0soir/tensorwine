package com.tensorwine.application;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.vision.text.TextRecognizer;
import com.tensorwine.application.almacenamiento.Almacenamiento;
import com.tensorwine.application.vistas.DatosVino;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "Text API";
    private static final int PHOTO_REQUEST = 10;
    private TextView scanResults;
    private Uri imageUri;
    private TextRecognizer detector;
    private static final int REQUEST_WRITE_PERMISSION = 20;
    private static final String SAVED_INSTANCE_URI = "uri";
    private static final String SAVED_INSTANCE_RESULT = "result";
    private static int CAMERA_OPTION = 1;/*1 para cargar y 2 para hacer*/
    private static int RESULT_LOAD_IMG = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    String imgDecodableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int dato_crear = Data.createInstance(this.getResources().openRawResource(R.raw.listado_vinos));
        Button b_buscar = (Button) findViewById(R.id.btn_buscar);
        Button b_recomendar = (Button) findViewById(R.id.btn_recomendar);
        if (savedInstanceState != null) {
            imageUri = Uri.parse(savedInstanceState.getString(SAVED_INSTANCE_URI));
            scanResults.setText(savedInstanceState.getString(SAVED_INSTANCE_RESULT));
        }
        detector = new TextRecognizer.Builder(getApplicationContext()).build();
        b_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFind(findViewById(android.R.id.content));
            }
        });
        b_recomendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRecommend(findViewById(android.R.id.content));

            }
        });
        verifyStoragePermissions(MainActivity.this);
    }

    public void openVino(View view){
        Intent intent = new Intent(this, DatosVino.class);
        startActivity(intent);
    }

    public void openRecommend(View view){
        Intent intent = new Intent(this, Recommend.class);
        startActivity(intent);
    }
    public void openImage(View view){
        Intent intent = new Intent(this, EnviarImagen.class);
        startActivity(intent);
    }

    public void openFind(View view){
        Intent intent = new Intent(this, Find.class);
        startActivity(intent);
    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "picture.jpg");
        imageUri = FileProvider.getUriForFile(MainActivity.this,
                BuildConfig.APPLICATION_ID + ".provider", photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, PHOTO_REQUEST);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (imageUri != null) {
            outState.putString(SAVED_INSTANCE_URI, imageUri.toString());
            outState.putString(SAVED_INSTANCE_RESULT, scanResults.getText().toString());
        }
        super.onSaveInstanceState(outState);
    }

    public void loadImagefromGallery(View view) {
        Log.i("IMAGE", "LOAD IMAGE FROM GALLERY");
        CAMERA_OPTION = 1;
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    public void takePicture(View view) {
        try{
            CAMERA_OPTION = 2;
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        } catch (Exception e) {
            Toast.makeText(this, "Ha habido un error en takePicture", Toast.LENGTH_LONG).show();
        }
    }

    public String getRealPathFromURI(Uri contentURI, Activity context) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = context.managedQuery(contentURI, projection, null,
                null, null);
        if (cursor == null)
            return null;
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        if (cursor.moveToFirst()) {
            String s = cursor.getString(column_index);
            // cursor.close();
            return s;
        }
        // cursor.close();
        return null;
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            Log.i("PERMISOS", "no tiene PERMISOS");
        }
        Log.i("PERMISOS", "FIN PERMISOS");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if(CAMERA_OPTION ==1){
                if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
                    Uri selectedImage = data.getData();
                    Log.i("SELECIONANDO IMAGEN", selectedImage.toString());
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    String picturePath = getRealPathFromURI(selectedImage,
                            this);
                    Data.getInstance().ruta_imagen = picturePath;
                    Data.getInstance().bitmap_imagen = null;
                    openImage(findViewById(android.R.id.content));
                } else {
                    Toast.makeText(this, "No has elegido ninguna imagen",Toast.LENGTH_LONG).show();
                }
            }else{

                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                    Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                    Data.getInstance().bitmap_imagen = imageBitmap;
                    Data.getInstance().ruta_imagen = null;
                    openImage(findViewById(android.R.id.content));
                } else {
                    Toast.makeText(this, "No has hecho ninguna foto",Toast.LENGTH_LONG).show();
                }
            }

        } catch (Exception e) {
            Toast.makeText(this, "Ha habido un error", Toast.LENGTH_LONG).show();
        }

    }


}
