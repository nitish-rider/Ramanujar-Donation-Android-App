package inninc.studios.parashar.mutt.donation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Form extends AppCompatActivity {

    Button createButton;
    EditText donatorName;
    EditText donationAmt;
    EditText mobileNum;
    EditText address;
    EditText recieverName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        donatorName = findViewById(R.id.donatorName);
        donationAmt = findViewById(R.id.donationAmt);
        mobileNum = findViewById(R.id.mobileNum);
        address = findViewById(R.id.address);
        recieverName = findViewById(R.id.recieverName);
        createButton = findViewById(R.id.submit);

        //write file to external storage...
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        createPDF();
    }

    private void createPDF() {
        createButton.setOnClickListener((view) ->{

            if (donatorName.getText().toString().length()==0 ||
            donationAmt.getText().toString().length()==0 ||
            mobileNum.getText().toString().length()==0 ||
            address.getText().toString().length()==0 ||
            recieverName.getText().toString().length()==0){
                Toast.makeText(Form.this , "Some Feilds are empty , Please fill them to generate the pdf", Toast.LENGTH_LONG).show();
            }else {


                PdfDocument myPdfDocument = new PdfDocument();
                Paint myPaint = new Paint();

                PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
                Canvas canvas = myPage1.getCanvas();

                myPaint.setColor(Color.rgb(0,0,0));
                myPaint.setTextSize(30);
                myPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("call - +91-832724018" , 1160,40,myPaint);


                myPdfDocument.finishPage(myPage1);


                File file = new File(Environment.getExternalStorageDirectory(), "/Hello.pdf");

                try {
                    myPdfDocument.writeTo(new FileOutputStream(file));
                }catch (IOException e){
                    e.printStackTrace();
                }

                myPdfDocument.close();

            }


        });
    }
}