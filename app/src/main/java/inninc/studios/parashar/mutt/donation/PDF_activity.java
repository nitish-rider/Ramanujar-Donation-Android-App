package inninc.studios.parashar.mutt.donation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PDF_activity extends AppCompatActivity {

    PDFView mPDFView;
    File mFile;
    String fName;
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_activity);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
             fName=Form.getfName();
        }
        String location="/storage/emulated/0/Android/data/inninc.studios.parashar.mutt.donation/files/"+fName;
        mFile=new File(location);
        mPDFView.fromFile(mFile).load();
    }
}