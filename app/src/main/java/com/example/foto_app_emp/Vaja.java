package com.example.foto_app_emp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Vaja extends AppCompatActivity {


    ImageButton next, previous, instructions, exifbutton, addphoto;
    ImageSwitcher imageSwitcher;
    CardView cardView, exifHidden;

    TextView exifData;

    RelativeLayout first_thing;


    //store images
    private ArrayList<Uri> imageUris;

    //request code for onActivityResult
    private static final int PICK_IMAGE_CODE = 0;

    //position of selected image
    int position = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vaja);

        imageUris = new ArrayList<>();

        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);

        instructions = findViewById(R.id.instructions);
        exifbutton = findViewById(R.id.exif);
        addphoto = findViewById(R.id.addPhoto);

        imageSwitcher = findViewById(R.id.imageSwitcher);

        first_thing = findViewById(R.id.first_thing);

        cardView = findViewById(R.id.instructionsHidden);
        exifHidden = findViewById(R.id.exifHidden);

        exifData = findViewById(R.id.exifData);


        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                return imageView;
            }
        });

        //choose image
        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check for permission of INTERNAL STORAGE
                //if (ActivityCompat.checkSelfPermission(Vaja.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //    ActivityCompat.requestPermissions(Vaja.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                //    Toast.makeText(Vaja.this,"Reopen the app", Toast.LENGTH_SHORT);
                //    return;
                //}

                //when button is pressed old photos are removed
                imageUris.clear();

                //if permission is granted ImageChooser is lunched
                pickImagesIntent();

            }
        });

        //previous image
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position > 0){
                    position--;
                    imageSwitcher.setImageURI(imageUris.get(position));
                    exifData.setText("");
                } else {
                    Toast.makeText(Vaja.this, "No previous images", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //next image
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < imageUris.size()-1){
                    position++;
                    imageSwitcher.setImageURI(imageUris.get(position));
                    exifData.setText("");
                } else {
                    Toast.makeText(Vaja.this, "No more images...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //show EXIF
        exifbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                InputStream in = null;
                Uri uri = imageUris.get(position);

                try {
                    in = getContentResolver().openInputStream(uri);
                    ExifInterface exifInterface = new ExifInterface(in);
                    exifData.setText("Goriščna razdalja: " + exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH));
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (in != null){
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        });

        instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInstructionsOpen()){
                    //open
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    cardView.setVisibility(View.INVISIBLE);
                } else {
                    //closed
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    cardView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private boolean isInstructionsOpen(){
        return cardView.getVisibility() == View.VISIBLE; //TRUE if visible ; FALSE if invisible
    }

    private void pickImagesIntent(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image(s)"), PICK_IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_CODE && resultCode == RESULT_OK){

            if (data.getClipData() != null){
                //picked multiple images
                int count = data.getClipData().getItemCount(); //number of selected images

                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri(); //get image URI at specific position
                    imageUris.add(imageUri);
                }

                imageSwitcher.setImageURI(imageUris.get(0)); //set first image to ImageSwitcher
                position = 0;

            } else {
                //picked single image
                Uri imageUri = data.getData();
                imageUris.add(imageUri);


                imageSwitcher.setImageURI(imageUris.get(0)); //set ONLY image to ImageSwitcher
                position = 0;
            }
            if (imageUris.size() != 0){
                first_thing.setVisibility(View.INVISIBLE);
            }
        }
    }

}
