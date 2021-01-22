package inninc.studios.parashar.mutt.donation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button log = findViewById(R.id.login);


        log.setOnClickListener(view -> {
            if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                Intent intent = new Intent(login.this, MainActivity.class);
                startActivity(intent);
            } else {
                AlertDialog dil = new AlertDialog.Builder(login.this).setTitle("Error").setMessage("Login Faild")
                        .setPositiveButton("OK", (dialog, i) -> dialog.dismiss()).create();
                dil.show();
            }
        });

    }
}