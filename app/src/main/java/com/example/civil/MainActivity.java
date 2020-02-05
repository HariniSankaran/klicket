package com.example.civil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageButton submit_btn;
    private EditText username_et,password_et;
    String user,pswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit_btn = (ImageButton) findViewById(R.id.btn_Submit);
        username_et= (EditText)findViewById(R.id.et_UserName);
        password_et=(EditText)findViewById(R.id.et_Password);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user=username_et.getText().toString();
                pswd=password_et.getText().toString();
                if (user.equals("admin")&&pswd.equals("admin"))
                {
                    Intent intent = new Intent(MainActivity.this,newpage.class);
                    MainActivity.this.startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}