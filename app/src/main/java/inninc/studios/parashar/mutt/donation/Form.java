package inninc.studios.parashar.mutt.donation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.icu.text.DateFormat;
import android.icu.text.DateTimePatternGenerator;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Form extends AppCompatActivity {

    Button createButton;
    EditText donatorName;
    EditText donationAmt;
    EditText mobileNum;
    EditText address;
    EditText recieverName;
    Date dateobj;
    SimpleDateFormat mDateFormat=new SimpleDateFormat("dd-MM-yyyy hh:mm a");
    //DateFormat dateFormat;

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

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putInDataBase();
                Intent intent=new Intent(Form.this,MainActivity.class);
                startActivity(intent);
                printPdf();
            }
        });
    }

    private void putInDataBase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Reciver");

        String dName=donatorName.getText().toString().trim();
        String dAmt=donationAmt.getText().toString().trim();
        String mNumer=mobileNum.getText().toString().trim();
        String dAddress=address.getText().toString().trim();
        String rName=recieverName.getText().toString().trim();

        Dataholder obj=new Dataholder(dName,dAmt,mNumer,dAddress);
        myRef.child(rName).setValue(obj);
    }

    private void printPdf(){
        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();
        Paint forLine=new Paint();

        PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(250 , 350, 1).create();
        PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
        Canvas canvas = myPage1.getCanvas();


        myPaint.setTextSize(5f);
        myPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("call - +91-832724018" , 230,20,myPaint);


        myPaint.setTextSize(9f);
        myPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("INVOICE",canvas.getWidth()/2,40,myPaint);
        forLine.setStyle(Paint.Style.STROKE);
        forLine.setPathEffect(new DashPathEffect(new float[]{5,5},0));
        forLine.setStrokeWidth(2);
        canvas.drawLine(20,65,230,65,forLine);

        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(10f);
        myPaint.setColor(Color.BLACK);
        canvas.drawText("Donator Name: "+ donatorName.getText(), 20,80,myPaint);
        canvas.drawText("Phone Number: "+ mobileNum.getText(), 20,100,myPaint);
        canvas.drawText("Address: "+ address.getText(), 20,120,myPaint);
        canvas.drawText("Amount"+donationAmt.getText(),20,140,myPaint);
        canvas.drawLine(20,155,230,155,forLine);

        canvas.drawText("Receiver Name: "+ recieverName.getText(), 20,165,myPaint);

        canvas.drawLine(20,170,230,170,forLine);
        canvas.drawText("Date :"+mDateFormat.format(new Date().getTime()), 20,260,myPaint);

        myPaint.setTextAlign(Paint.Align.CENTER);
        myPaint.setTextSize(12f);
        canvas.drawText("Thank You!", canvas.getWidth()/2,320,myPaint);
        myPdfDocument.finishPage(myPage1);

        String dName=donatorName.getText().toString().trim();
        File file = new File(this.getExternalFilesDir("/"),dName+donationAmt.getText().toString().trim()+".pdf");

        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
        }catch (IOException e){
            e.printStackTrace();
        }
        myPdfDocument.close();

    }

    /*private void createPDF() {
        createButton.setOnClickListener((view) ->{

            //initializing the date
//            dateobj = new Date();



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

                myPaint.setTextSize(70);
                myPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("INVOICE",500,500,myPaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(35);
                myPaint.setColor(Color.BLACK);
                canvas.drawText("Donator Name: "+ donatorName.getText(), 20,590,myPaint);
                canvas.drawText("Receiver Name: "+ recieverName.getText(), 20,640,myPaint);
                canvas.drawText("Phone Number: "+ mobileNum.getText(), 20,690,myPaint);


                myPaint.setTextAlign(Paint.Align.RIGHT);
                myPaint.setTextSize(35);
                myPaint.setColor(Color.BLACK);
                canvas.drawText("Address: "+ address.getText(), 1160,590,myPaint);

//                dateFormat = new SimpleDateFormat("DD-MM-YY");
//                canvas.drawText("Date :"+dateFormat.format(dateobj), 1160,690,myPaint);

                myPaint.setStyle(Paint.Style.STROKE);
                myPaint.setStrokeWidth(2);
                canvas.drawRect(20,700, 1160 , 860 , myPaint);

                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setStyle(Paint.Style.FILL);
                canvas.drawText("Sl. No",40,830,myPaint);
                canvas.drawText("Donator Name ",200,830,myPaint);
                canvas.drawText("Amount",700,830,myPaint);
                canvas.drawText("Mobile Number",800,830,myPaint);

                canvas.drawLine(180,790,180,840,myPaint);
                canvas.drawLine(680,790,680,840,myPaint);
                canvas.drawLine(880,790,880,840,myPaint);
                canvas.drawLine(1030,790,1030,840,myPaint);






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
    }*/
}