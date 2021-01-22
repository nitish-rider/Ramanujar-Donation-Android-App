package inninc.studios.parashar.mutt.donation;

import androidx.annotation.NonNull;
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
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Form extends AppCompatActivity {
    static String fName;
    Button createButton;
    EditText donatorName;
    EditText donationAmt;
    EditText mobileNum;
    SimpleDateFormat mDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        donatorName = findViewById(R.id.donatorName);
        donationAmt = findViewById(R.id.donationAmt);
        mobileNum = findViewById(R.id.mobileNum);
        ;
        createButton = findViewById(R.id.submit);

//Checking for android version code...
//      and create a notification channel...

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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

                Intent intent = new Intent(Form.this, MainActivity.class);
                startActivity(intent);

// Notification Code

                NotificationCompat.Builder builder = new NotificationCompat.Builder(Form.this, "Notification");
                builder.setContentTitle("Pdf Generated");
                builder.setContentText("The PDF is created on" + "  " + getFilesDir());
                builder.setSmallIcon(R.drawable.ic_baseline_notifications_active_24);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Form.this);
                managerCompat.notify(1, builder.build());

                Toast.makeText(Form.this, "The PDF is created on" + "  " + getFilesDir(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void putInDataBase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Reciver");
        DatabaseReference getRef = FirebaseDatabase.getInstance().getReference().child("Reciver");

        String dName = donatorName.getText().toString().trim();
        String dAmt = donationAmt.getText().toString().trim();
        String mNumer = mobileNum.getText().toString().trim();

        Dataholder obj = new Dataholder(dName, dAmt, mNumer);

        String uniqueID = UUID.randomUUID().toString();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long invoiceID = snapshot.getChildrenCount();
                printPdf(invoiceID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myRef.child(uniqueID).setValue(obj);

    }


    private void printPdf(long invoiceNumber) {
        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();
        Paint forLine = new Paint();

        PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(350, 350, 1).create();
        PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
        Canvas canvas = myPage1.getCanvas();

        myPaint.setColor(Color.BLUE);
        myPaint.setTextSize(5f);
        myPaint.setTextAlign(Paint.Align.RIGHT);

        canvas.drawText("PH:0431-2431109", 330, 20, myPaint);
        canvas.drawText("Cell:9150403470", 330, 30, myPaint);


        myPaint.setTextSize(10f);
        myPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Sri:", canvas.getWidth() / 2, 40, myPaint);


        myPaint.setTextSize(14f);
        myPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("SRIMAAN TRUST", canvas.getWidth() / 2, 55, myPaint);


        myPaint.setTextSize(11f);
        myPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("No. 231,South Uthra Street,Srirangam,Trichy-6", canvas.getWidth() / 2, 70, myPaint);


        myPaint.setTextSize(11f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Email: srimaantrust@gmail.com  /  Web: srimaantrust.com", 20, 85, myPaint);

        myPaint.setTextSize(10f);
        myPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("RECEIPT", canvas.getWidth() / 2, 100, myPaint);

        myPaint.setTextSize(10f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("RECEIPT No.: " + invoiceNumber, 20, 100, myPaint);


        myPaint.setTextSize(10f);
        myPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Tax Exemption u/s 80G(5) VI of the Income Tax Act,1961.", canvas.getWidth() / 2, 115, myPaint);

        myPaint.setTextSize(10f);
        myPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("C.No. 6162E(168)/2005-068CIT-I/TRY.Dt 10-10-2007", canvas.getWidth() / 2, 125, myPaint);


        myPaint.setTextSize(10f);
        myPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Permanent Exemption from 01-04-2007,Pan no. AAFTS6887C", canvas.getWidth() / 2, 135, myPaint);

        forLine.setStyle(Paint.Style.STROKE);
        forLine.setPathEffect(new DashPathEffect(new float[]{5, 5}, 0));
        forLine.setStrokeWidth(2);
        canvas.drawLine(20, 145, 320, 145, forLine);


        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(10f);
        myPaint.setColor(Color.BLACK);
        canvas.drawText("Donator Name: " + donatorName.getText(), 20, 160, myPaint);
        canvas.drawText("Phone Number: " + mobileNum.getText(), 20, 175, myPaint);
        canvas.drawText("Amount: " + donationAmt.getText(), 20, 190, myPaint);
        canvas.drawLine(20, 205, 330, 205, forLine);

        canvas.drawText("Date :" + mDateFormat.format(new Date().getTime()), 20, 250, myPaint);

        myPaint.setTextAlign(Paint.Align.CENTER);
        myPaint.setTextSize(12f);
        myPaint.setColor(Color.RED);
        canvas.drawText("Thank You!", canvas.getWidth() / 2, 320, myPaint);
        myPdfDocument.finishPage(myPage1);


        fName = donatorName.getText().toString().trim() + mDateFormat.format(new Date().getTime()) + ".pdf";
        File file = new File(this.getExternalFilesDir("/"), fName);

        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        myPdfDocument.close();

    }
}