package com.example.emre.a14011009_firsthw_mpg;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;



public class ShowInformationsActivity extends AppCompatActivity {


    private static final int REQUEST_CALL = 1;
    ImageView imageView2, call_IV, email_IV;
    TextView name_TV, TC_TV, telephone_TV, email_TV, birthDate_TV;
    Button listLesson_Button;
    String newString;
    Uri photoURI;
    ArrayList<String> infoList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_informations);

        InitializeAllContent();
        SetInformations();

        call_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        });
        email_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
        listLesson_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListLessons();
            }
        });


    }

    private void makePhoneCall() {
        String number = infoList.get(4);
        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(ShowInformationsActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ShowInformationsActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(ShowInformationsActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendEmail(){
        Intent mailIntent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:?subject=" + ""+ "&body=" + "" + "&to=" + infoList.get(5));
        mailIntent.setData(data);
        startActivity(Intent.createChooser(mailIntent, "Send mail..."));
    }

    public void ListLessons(){
        Intent listLess = new Intent(ShowInformationsActivity.this, ListLessonsActivity.class);
        startActivity(listLess);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void InitializeAllContent(){
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        name_TV = (TextView)findViewById(R.id.name_TV);
        TC_TV = (TextView)findViewById(R.id.TC_TV);
        telephone_TV = (TextView)findViewById(R.id.telephone_TV);
        email_TV = (TextView)findViewById(R.id.email_TV);
        birthDate_TV = (TextView)findViewById(R.id.birthDate_TV);
        call_IV = (ImageView)findViewById(R.id.call_IV);
        email_IV = (ImageView)findViewById(R.id.email_IV);
        listLesson_Button = (Button)findViewById(R.id.listLesson_Button);

    }

    public void SetInformations(){
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            newString= null;
        } else {
            infoList = extras.getStringArrayList("allInformations");
            photoURI = Uri.parse(infoList.get(0));
            try{
                final InputStream imageStream = getContentResolver().openInputStream(photoURI);
                final Bitmap photo = BitmapFactory.decodeStream(imageStream);
                imageView2.setImageBitmap(photo);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
            name_TV.setText("Name: "+infoList.get(1)+" "+infoList.get(2));
            TC_TV.setText("TC No: "+infoList.get(3));
            telephone_TV.setText("Telephone: "+infoList.get(4));
            email_TV.setText("E-Mail: "+infoList.get(5));
            birthDate_TV.setText("Birth Date: "+infoList.get(6)+"."+infoList.get(7)+"."+infoList.get(8));
        }
    }


}
