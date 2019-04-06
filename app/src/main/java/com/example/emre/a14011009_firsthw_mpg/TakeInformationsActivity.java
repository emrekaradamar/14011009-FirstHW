package com.example.emre.a14011009_firsthw_mpg;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class TakeInformationsActivity extends AppCompatActivity {

    private static final int GALLERY = 1;

    int i, j;
    private String[] days = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20",
            "21","22","23","24","25","26","27","28","29","30","31"};
    private String[] months = {"00","01","02","03","04","05","06","07","08","09","10","11","12"};
    private String[] years ;

    private ArrayAdapter<String> dataAdapterForDays;
    private ArrayAdapter<String> dataAdapterForMonths;
    private ArrayAdapter<String> dataAdapterForYears;

    ImageView imageView;
    Button choosePhoto_Button, save_Button, reset_Button;
    EditText name_ET, surname_ET, email_ET, TC_ET, telephone_ET;
    Spinner day_SP, month_SP, year_SP;

    Intent showInfo;
    Uri photoURI;

    ArrayList<String> infoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitializeAllContent();

        choosePhoto_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhotoFromGallary();

            }
        });

        save_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isFilled = AreTheyAllFilled();
                if(isFilled){
                    Toast.makeText(TakeInformationsActivity.this,"You should fill all the fields!!!!",Toast.LENGTH_SHORT).show();
                }else{
                    PutInformationsToArrayList();
                    showInfo.putExtra("allInformations", infoList);
                    startActivity(showInfo);
                }
            }
        });

        reset_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetALL();
            }
        });


    }

    public void ResetALL(){
        name_ET.setText("");
        surname_ET.setText("");
        TC_ET.setText("");
        telephone_ET.setText("");
        email_ET.setText("");
        day_SP.setSelection(0);
        month_SP.setSelection(0);
        year_SP.setSelection(0);
        imageView.setImageResource(0);

    }

    public void choosePhotoFromGallary() {
        try {
            if (ActivityCompat.checkSelfPermission(TakeInformationsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(TakeInformationsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY);
            } else {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, GALLERY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                //photoURI = data.getData();
                try {
                    final Uri imageUri = data.getData();
                    //photoURI = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    //bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    Toast.makeText(TakeInformationsActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imageView.setImageBitmap(selectedImage);
                   photoURI = imageUri;

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(TakeInformationsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, GALLERY);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                    Toast.makeText(TakeInformationsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void  PutInformationsToArrayList(){
        infoList.add(photoURI.toString());
        infoList.add(name_ET.getText().toString());
        infoList.add(surname_ET.getText().toString());
        infoList.add(TC_ET.getText().toString());
        infoList.add(telephone_ET.getText().toString());
        infoList.add(email_ET.getText().toString());
        infoList.add(day_SP.getSelectedItem().toString());
        infoList.add(month_SP.getSelectedItem().toString());
        infoList.add(year_SP.getSelectedItem().toString());
    }

    public boolean AreTheyAllFilled(){
        return name_ET.getText().toString().equals("") || surname_ET.getText().toString().equals("") ||
                day_SP.getSelectedItem().toString().equals("00") || month_SP.getSelectedItem().toString().equals("00") ||
                year_SP.getSelectedItem().toString().equals("0000") || email_ET.getText().toString().equals("") ||
                TC_ET.getText().toString().equals("") || telephone_ET.getText().toString().equals("") || photoURI == null;
    }

    public void InitializeAllContent(){

        imageView = (ImageView)findViewById(R.id.imageView);
        choosePhoto_Button = (Button)findViewById(R.id.choosePhoto_Button);
        name_ET = (EditText) findViewById(R.id.name_ET);
        surname_ET = (EditText) findViewById(R.id.surname_ET);
        day_SP=(Spinner)findViewById(R.id.day_SP);
        month_SP=(Spinner)findViewById(R.id.month_SP);
        year_SP=(Spinner)findViewById(R.id.year_SP);
        email_ET = (EditText) findViewById(R.id.email_ET);
        TC_ET = (EditText) findViewById(R.id.TC_ET);
        telephone_ET = (EditText) findViewById(R.id.telephone_ET);
        save_Button = (Button)findViewById(R.id.save_Button);
        reset_Button = (Button)findViewById(R.id.reset_Button);
        showInfo= new Intent(TakeInformationsActivity.this,ShowInformationsActivity.class);
        infoList = new ArrayList<String>();

        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        j = 1;
        years = new String[thisYear-1899];
        years[0] = "0000";
        for(i = 1900; i < thisYear; i++){
            years[j] = Integer.toString(i);
            j++;
        }

        dataAdapterForDays = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);
        dataAdapterForMonths = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,months);
        dataAdapterForYears = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,years);

        dataAdapterForDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterForMonths.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterForYears.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        day_SP.setAdapter(dataAdapterForDays);
        month_SP.setAdapter(dataAdapterForMonths);
        year_SP.setAdapter(dataAdapterForYears);
    }

}
