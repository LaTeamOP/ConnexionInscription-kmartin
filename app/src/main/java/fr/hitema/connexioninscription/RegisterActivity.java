package fr.hitema.connexioninscription;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.MyDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText editNewLogin, editNewPwd, editNewPrenom, editNewNom;
    private Button btnNewRegister;
    private MyDatabase myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myDB = new MyDatabase(getApplicationContext());

        editNewLogin = (EditText) findViewById(R.id.editNewLogin);
        editNewPwd = (EditText) findViewById(R.id.editNewPwd);
        editNewPrenom = (EditText) findViewById(R.id.editNewPrenom);
        editNewNom = (EditText) findViewById(R.id.editNewNom);

        btnNewRegister = (Button) findViewById(R.id.btnNewRegister);

        btnNewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkForm() == true){
                    // Insertion d'un étudiant dans la BDD
                    myDB.open();
                    Long result = myDB.addUser(editNewLogin.getText().toString(), editNewPwd.getText().toString(), editNewPrenom.getText().toString(),editNewNom.getText().toString());
                    Log.d("Insertion utilisateur", result.toString());
                    myDB.close();

                    Toast.makeText(getApplicationContext(), editNewLogin.getText().toString() + " created!", Toast.LENGTH_SHORT).show();

                    Intent it = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(it);

                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Problème", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean checkForm(){
        if (editNewLogin.getText().toString().length() == 0){
            editNewLogin.setError("Entrer votre login");
        }

        if (editNewPwd.getText().toString().length() == 0){
            editNewPwd.setError("Entrer votre password");
        }

        if (editNewPrenom.getText().toString().length() == 0){
            editNewPrenom.setError("Entrer votre prénom");
        }

        if (editNewNom.getText().toString().length() == 0){
            editNewNom.setError("Entrer votre nom");
        }

        return true;
    }
}
