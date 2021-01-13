package inninc.studios.parashar.mutt.donation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Form extends AppCompatActivity {

    Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);


        EditText donatorName = (EditText)findViewById(R.id.donatorName);
        EditText donationAmt = (EditText)findViewById(R.id.donationAmt);
        EditText mobileNum = (EditText)findViewById(R.id.mobileNum);
        EditText address = (EditText)findViewById(R.id.address);
        EditText recieverName = (EditText)findViewById(R.id.recieverName);//
        createButton = findViewById(R.id.submit);

        //write file to external storage...
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        createPDF();
    }

    private void createPDF() {
        createButton.setOnClickListener((view) ->{

            PdfDocument myPdfDocument = new PdfDocument();
            Paint myPaint = new Paint();

            PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(1200,2010,1).create();
            PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
            Canvas canvas = myPage1.getCanvas();
        });
    }
}