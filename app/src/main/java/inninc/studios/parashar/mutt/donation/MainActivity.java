package inninc.studios.parashar.mutt.donation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button reciptMaker=(Button)findViewById(R.id.recipt);
        TextView Hello = findViewById(R.id.hello);

        Hello.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Form.class);
            startActivity(intent);
        });

    }
}