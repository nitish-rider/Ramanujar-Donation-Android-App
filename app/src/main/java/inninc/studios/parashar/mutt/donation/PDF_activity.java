package inninc.studios.parashar.mutt.donation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PDF_activity extends AppCompatActivity {

    PDFView mPDFView;
    File mFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_activity);
        mFile=new File(String.valueOf(getFilesDir()));
        mPDFView.fromFile(mFile).load();


    }
}