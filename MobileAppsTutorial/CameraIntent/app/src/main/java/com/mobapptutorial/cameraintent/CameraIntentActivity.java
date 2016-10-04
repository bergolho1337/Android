package com.mobapptutorial.cameraintent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.Random;


public class CameraIntentActivity extends Activity {

    private static final int ACTIVITY_START_CAMERA_APP = 0;     // Codigo da aplicacao de tirou a foto
    private ImageView imgView;                                  // Guarda uam referencia para a ImageView do layout
    private String imageFileLocation;                           // Localizacao do arquivo de imagem da foto no celular

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_intent);

        imgView = (ImageView)findViewById(R.id.capturePhoto);

    }

    public void takePhoto (View view) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        // Vamos adicionar a imagem da foto tirada
        File photoFile = null;
        try {
            photoFile = createImageFile();
            // A partir desse ponto 'photoFile' contem o arquivo da foto
        }catch (IOException e) {
            e.printStackTrace();
        }
        // Logo colocamos a foto como dado da intent a ser passada, com a tag expecificada como o caminho do arquivo
        // Isto salva o arquivo
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(intent,ACTIVITY_START_CAMERA_APP);
    }

    // Quando a camera acabar de tirar a foto chama-se essa funcao
    // A Intent 'data' contem os dados da foto
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {

            try {
                rotateImage(setReduceImageSize());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    File createImageFile () throws IOException {
        // Cria o nome do arquivo de imagem da foto tirada.
        // Este nome deve ser unico para cada foto.
        Random randomGenerator = new Random();
        Integer number = randomGenerator.nextInt(10000);
        String timeStamp = number.toString();
        String imgFileName = "IMAGE_" + timeStamp + "_";
        Log.i("createImageFile","imgFileName = "+imgFileName);
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        // Descobre o caminho aonde a iamgem sera salva
        File image = File.createTempFile(imgFileName,".jpg",storageDirectory);
        imageFileLocation = image.getAbsolutePath();
        return image;
    }

    // Funcao captura as dimensoes da foto tirada e da ImageView e tenta criar uma escala para mostrar somente
    // a dimensao visivel da foto no aparelho
    private Bitmap setReduceImageSize () throws IOException {

        int targetImageViewWidth = imgView.getWidth();
        int targetImageViewHeight = imgView.getHeight();

        BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
        bmpOptions.inJustDecodeBounds = true;

        // Ler as informacoes da foto, no caso somente as dimensoes largura e altura
        BitmapFactory.decodeFile(imageFileLocation,bmpOptions);

        int cameraWidth = bmpOptions.outWidth;
        int cameraHeight = bmpOptions.outHeight;

        // Calcular o fator de diferenca entre o tamanho da imageView e o tamanho da foto
        int scaleFactor = Math.min(cameraHeight/targetImageViewHeight,cameraWidth/targetImageViewWidth);
        bmpOptions.inSampleSize = scaleFactor;

        // Retorna o valor da flag de carregar a imagem somente nos detalhes para 'false', pq agora queremos carregar
        // toda a imagem, visto que agora ela sera carregada com o 'scaleFactor' setado, logo consumira menos memoria
        // que a imagem original
        bmpOptions.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(imageFileLocation,bmpOptions);

    }

    private void rotateImage (Bitmap bitmap) {
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(imageFileLocation);
        }catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_UNDEFINED);
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            default:
        }
        Bitmap rotateImage = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        imgView.setImageBitmap(rotateImage);
    }
}
