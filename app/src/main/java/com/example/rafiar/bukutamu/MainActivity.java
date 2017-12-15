package com.example.rafiar.bukutamu;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase myDB;
    private SQLiteOpenHelper openHelperDB;

    TextView dataTamu;
    EditText namaTamu;
    EditText institusiTamu;
    EditText tujuanTamu;

    String namaTamuValue;
    String institusiTamuValue;
    String tujuanTamuValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataTamu = (TextView) findViewById(R.id.dataTamu);

        namaTamu = (EditText) findViewById(R.id.namaTamu);
        institusiTamu = (EditText) findViewById(R.id.institusiTamu);
        tujuanTamu = (EditText) findViewById(R.id.tujuanTamu);

        openHelperDB = new SQLiteOpenHelper(getBaseContext(),"dbbaru.sql",null,1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
        myDB = openHelperDB.getWritableDatabase();
        myDB.execSQL("create table if not exists tamu(nama TEXT, institusi TEXT, tujuan TEXT);");
        tampilkanData();
    }

    public void hideSoftInput(View view)
    {
        InputMethodManager a = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        a.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    public void getAllText(){
        namaTamuValue = namaTamu.getText().toString();
        institusiTamuValue = institusiTamu.getText().toString();
        tujuanTamuValue = tujuanTamu.getText().toString();
    }

    public void clearAllText(){
        namaTamuValue = "";
        namaTamu.setText(namaTamuValue);
        institusiTamuValue = "";
        institusiTamu.setText(institusiTamuValue);
        tujuanTamuValue = "";
        tujuanTamu.setText(tujuanTamuValue);
    }

    private String listMaker(Cursor cur){
        String parser = "";
        cur.moveToFirst();
        while (!cur.isLast())
        {
            parser += "Nama : ";
            parser += cur.getString(cur.getColumnIndex("nama"));
            parser += "\n";

            parser += "Institusi : ";
            parser += cur.getString(cur.getColumnIndex("institusi"));
            parser += "\n";

            parser += "Tujuan : ";
            parser += cur.getString(cur.getColumnIndex("tujuan"));
            parser += "\n\n";
            cur.moveToNext();
        }
        parser += "Nama : ";
        parser += cur.getString(cur.getColumnIndex("nama"));
        parser += "\n";

        parser += "Institusi : ";
        parser += cur.getString(cur.getColumnIndex("institusi"));
        parser += "\n";

        parser += "Tujuan : ";
        parser += cur.getString(cur.getColumnIndex("tujuan"));
        parser += "\n";
        return parser;
    }

    public void tutup(View view){
        finish();
    }

    public void simpan(View view){
        hideSoftInput(view);
        getAllText();

        ContentValues myData = new ContentValues();
        myData.put("nama",namaTamuValue);
        myData.put("institusi",institusiTamuValue);
        myData.put("tujuan",tujuanTamuValue);

        myDB.insert("tamu",null,myData);

        Toast.makeText(getBaseContext(),"Data Tersimpan",Toast.LENGTH_SHORT).show();
        clearAllText();
        tampilkanData();
    }

    public void tampilkanData()
    {
        Cursor cur = myDB.rawQuery("select * from tamu",null);

        if(cur.getCount() > 0) {
            String parser = listMaker(cur);
            cur.moveToFirst();
            Toast.makeText(getBaseContext(),"Isi :"+cur.getString(cur.getColumnIndex("nama")),Toast.LENGTH_LONG).show();
            dataTamu.setText(parser);
        }
        else {
            dataTamu.setText("Data tamu kosong");
        }
    }
}
