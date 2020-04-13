package com.example.segnalazionistrade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Toolbar mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Informazioni");




        /*
        titoloQT.setText(R.string.Titolo);
        intro.setText(R.string.Incipit);
        titoloPassi.setText(R.string.Titolostep);
        passi.setText(R.string.step);
        titoloVantaggi.setText(R.string.Titolovantaggi);
        vantaggi.setText(R.string.vantaggi);
        titoloSvantaggi.setText(R.string.Titolosvantaggi);
        svantaggi.setText(R.string.svantaggi);

         */


        //TextView info = findViewById(R.id.TestoInfo);
        //info.setText(getString(R.string.Titolo));
        //Unisce le stringhe per creare un unico testo
        /*Appendable sampleString = new StringBuilder();
        try {
            sampleString.append(getString(R.string.Titolo)).append("\n");
            sampleString.append(getString(R.string.Incipit)).append("\n");
            sampleString.append(getString(R.string.Titolostep)).append("\n");
            sampleString.append(getString(R.string.step)).append("\n");
            sampleString.append(getString(R.string.Titolovantaggi)).append("\n");
            sampleString.append(getString(R.string.vantaggi)).append("\n");
            sampleString.append(getString(R.string.Titolosvantaggi)).append("\n");
            sampleString.append(getString(R.string.svantaggi)).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        info.setMovementMethod(new ScrollingMovementMethod());
        info.setText(sampleString.toString());
        }

        private void initUI() {
        titoloQT = findViewById(R.id.tTitolo);
        intro = findViewById(R.id.intro);
        titoloPassi = findViewById(R.id.tPassi);
        passi = findViewById(R.id.passi);
        titoloVantaggi = findViewById(R.id.tVantaggi);
        vantaggi = findViewById(R.id.vantaggi);
        titoloSvantaggi = findViewById(R.id.tSvantaggi);
        svantaggi = findViewById(R.id.svantaggi);

    }

         */

    }
}
