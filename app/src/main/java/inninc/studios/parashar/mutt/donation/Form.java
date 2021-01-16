package inninc.studios.parashar.mutt.donation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import java.util.UUID;

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

        //Checking for android version code...
//      and create a notification channel...

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Notification", "Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        //write file to external storage...
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putInDataBase();
                printPdf();
                Intent intent=new Intent(Form.this,MainActivity.class);
                startActivity(intent);

                // Notification Code

                NotificationCompat.Builder builder = new NotificationCompat.Builder(Form.this, "Notification");
                builder.setContentTitle("Pdf Generated");
                builder.setContentText("The PDF is created on" + "  " + getFilesDir());
                builder.setSmallIcon(R.drawable.ic_baseline_notifications_active_24);
                builder.setAutoCancel(false);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Form.this);
                managerCompat.notify(1,builder.build());
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

        Dataholder obj=new Dataholder(dName,dAmt,mNumer,dAddress,rName);

        String uniqueID = UUID.randomUUID().toString();
        myRef.child(uniqueID).setValue(obj);
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
        canvas.drawText("Amount: "+donationAmt.getText(),20,140,myPaint);
        canvas.drawLine(20,155,230,155,forLine);

        canvas.drawText("Receiver Name: "+ recieverName.getText(), 20,175,myPaint);

        canvas.drawLine(20,185,230,185,forLine);
        canvas.drawText("Date :"+mDateFormat.format(new Date().getTime()), 20,260,myPaint);

        myPaint.setTextAlign(Paint.Align.CENTER);
        myPaint.setTextSize(12f);
        myPaint.setColor(Color.RED);
        canvas.drawText("Thank You!", canvas.getWidth()/2,320,myPaint);
        myPdfDocument.finishPage(myPage1);

        String dName=donatorName.getText().toString().trim();
        File file = new File(this.getExternalFilesDir("/"),dName+mDateFormat.format(new Date().getTime())+".pdf");

        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
        }catch (IOException e){
            e.printStackTrace();
        }
        myPdfDocument.close();

    }
}