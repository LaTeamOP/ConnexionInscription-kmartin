package fr.hitema.connexioninscription;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.MyDatabase;
import data.User;

public class LoginActivity extends AppCompatActivity {

    private Button btnConnect, btnRegister;
    private EditText editLogin, editPassword;
    private MyDatabase myDB;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Bienvenue");

        myDB = new MyDatabase(getApplicationContext());

        btnConnect = (Button) findViewById(R.id.btnConnect);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        editLogin = (EditText) findViewById(R.id.editLogin);
        editPassword = (EditText) findViewById(R.id.editPwd);

        settings = getSharedPreferences("userData", Context.MODE_PRIVATE);


        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkForm() == true){
                    myDB.open();
                    User userSelect = myDB.getByName(editLogin.getText().toString());
                    myDB.close();

                    if (userSelect != null) {
                        if (checkLogin(userSelect.getLogin().toString(), userSelect.getPassword().toString()) == true){

                            // Mise en cache des informations user
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("login", userSelect.getLogin().toString());

                            editor.apply();

                            Toast.makeText(getApplicationContext(), userSelect.getNom() + "/" + userSelect.getPrenom(), Toast.LENGTH_SHORT).show();
                            Intent it = new Intent(LoginActivity.this, ProfilActivity.class);
                            startActivity(it);
                            finish();
                        }
                    }
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "S'inscrire", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(LoginActivity.this, RegisterActivity.class);

                startActivity(it);

            }
        });

    }

    public boolean checkForm(){
        if (editLogin.getText().toString().length() == 0){
            editLogin.setError("Entrer votre login");
        }

        if (editPassword.getText().toString().length() == 0){
            editPassword.setError("Entrer votre password");
        }

        return true;
    }

    public boolean checkLogin(String loginSaved, String passwordSaved){
        if (!editLogin.getText().toString().equals(loginSaved)) {
            editLogin.setError("Wrong login");
        }

        if (!editPassword.getText().toString().equals(passwordSaved)) {
            editPassword.setError("Wrong password");
        }

        if (editLogin.getText().toString().equals(loginSaved) && editPassword.getText().toString().equals(passwordSaved)){
            return true;
        }

        return false;
    }
}
