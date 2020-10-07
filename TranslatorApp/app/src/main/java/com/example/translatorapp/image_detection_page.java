package com.example.translatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;

import java.util.List;

public class image_detection_page extends AppCompatActivity {

    private Button captureimagebtn, detecttextbtn;
    private ImageView imageView;
    private TextView textView;
    private Bitmap imageBitmap;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detection_page);

        captureimagebtn = findViewById(R.id.captured_image_button);
        detecttextbtn = findViewById(R.id.detected_text_button);
        imageView = findViewById(R.id.captured_image);
        textView = findViewById(R.id.detected_text);



        captureimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
                textView.setText("No text detected. Try to detect again.");
            }
        });

        detecttextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detectTextFromImage();
            }
        });
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }


    private void detectTextFromImage() {
        FirebaseVisionImage Image = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextDetector TextDetector = FirebaseVision.getInstance().getVisionTextDetector();
        TextDetector.detectInImage(Image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                processTxt(firebaseVisionText);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(image_detection_page.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();

                Log.d("Error ", e.getMessage());
            }
        });
    }

    private void processTxt(FirebaseVisionText text) {
        List<FirebaseVisionText.Block> blocks = text.getBlocks();
        if (blocks.size()==0){
            Toast.makeText(image_detection_page.this, "No text :(", Toast.LENGTH_LONG);
            return;
        }
        for(FirebaseVisionText.Block block: text.getBlocks()){
            String txt = block.getText();
           // textView.setTextSize(24);
            textView.setText(txt);
        }
    }

}
