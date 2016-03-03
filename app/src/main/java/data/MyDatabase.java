package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by kmartin on 02/03/2016.
 */
public class MyDatabase {

    private int BDD_VERSION = 1;
    private String BDD_NAME = "user.db";

    private SQLiteDatabase bdd;

    private Database mybdd;

    public MyDatabase(Context context) {
        this.mybdd = new Database(context, BDD_NAME, null, BDD_VERSION);
    }

    public void open() {
        //On récupère un accès en écriture à la base de données
        this.bdd = this.mybdd.getWritableDatabase();
    }

    public void close() {
        this.bdd.close();
    }

    public SQLiteDatabase getBdd() {
        return this.bdd;
    }

    public long addUser(String login, String password, String prenom, String nom) {

        ContentValues cv_user = new ContentValues();
        cv_user.put("login",login);
        cv_user.put("password", password);
        cv_user.put("prenom",prenom);
        cv_user.put("nom",nom);
        return bdd.insert("USER", null, cv_user);
    }

    public long updateUser(String login, String password, String prenom, String nom) {

        ContentValues cv_user = new ContentValues();
        cv_user.put("login",login);
        cv_user.put("password", password);
        cv_user.put("prenom",prenom);
        cv_user.put("nom",nom);
        return bdd.update("USER", cv_user, "login = '" + login + "'", null);
    }

    public User getByName(String login) {
        User resultat = null;
        Cursor cur = bdd.rawQuery("SELECT login, password, prenom, nom FROM USER WHERE login = ?", new String[]{login});
        while (cur.moveToNext()) {

            resultat = new User();
            resultat.setLogin(cur.getString(cur.getColumnIndex("login")));
            resultat.setPassword(cur.getString(cur.getColumnIndex("password")));
            resultat.setPrenom(cur.getString(cur.getColumnIndex("prenom")));
            resultat.setNom(cur.getString(cur.getColumnIndex("nom")));

        }
        cur.close();
        return resultat;
    }

}
