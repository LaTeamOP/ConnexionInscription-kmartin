package fr.hitema.connexioninscription;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import data.MyDatabase;
import data.User;

public class ProfilActivity extends AppCompatActivity {

    private TextView userLoginView;
    private EditText editUserPwd, editUserFirstName, editUserLastName;
    private Button btnUpdateData, btnDisconnect;
    private MyDatabase myDB;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        setTitle("Mes informations");

        myDB = new MyDatabase(getApplicationContext());

        userLoginView = (TextView) findViewById(R.id.userLoginView);
        editUserPwd = (EditText) findViewById(R.id.editUserPwd);
        editUserFirstName = (EditText) findViewById(R.id.editUserFirstName);
        editUserLastName = (EditText) findViewById(R.id.editUserLastName);
        btnUpdateData = (Button) findViewById(R.id.btnUpdateData);
        btnDisconnect = (Button) findViewById(R.id.btnDisconnect);

        settings = getSharedPreferences("userData", Context.MODE_PRIVATE);

        userLoginView.setText(settings.getString("login", null));

        myDB.open();
        User userSelect = myDB.getByName(userLoginView.getText().toString());
        myDB.close();

        editUserPwd.setText(userSelect.getPassword().toString());
        editUserFirstName.setText(userSelect.getPrenom().toString());
        editUserLastName.setText(userSelect.getNom().toString());

        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkForm() == true) {
                    // Insertion d'un étudiant dans la BDD
                    myDB.open();
                    Long result = myDB.updateUser(userLoginView.getText().toString(), editUserPwd.getText().toString(), editUserFirstName.getText().toString(), editUserLastName.getText().toString());
                    Log.d("Update utilisateur", result.toString());
                    myDB.close();

                    Toast.makeText(getApplicationContext(), "Account updated!", Toast.LENGTH_SHORT).show();

                    finish();
                    startActivity(getIntent());
                } else {
                    Toast.makeText(getApplicationContext(), "Update Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = settings.edit();
                editor.clear();
                editor.commit();

                Intent it = new Intent(ProfilActivity.this, LoginActivity.class);
                startActivity(it);
                finish();
            }
        });

    }

    public boolean checkForm(){

        if (editUserPwd.getText().toString().length() == 0){
            editUserPwd.setError("Entrez un mot de passe");
        }

        if (editUserFirstName.getText().toString().length() == 0){
            editUserFirstName.setError("Entrez un prénom");
        }

        if (editUserLastName.getText().toString().length() == 0){
            editUserLastName.setError("Entrez un nom");
        }

        return true;
    }
}
