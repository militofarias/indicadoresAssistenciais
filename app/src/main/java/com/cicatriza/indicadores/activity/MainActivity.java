package com.cicatriza.indicadores.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.cicatriza.indicadores.R;
import com.cicatriza.indicadores.fragment.PatientFragment;
import com.cicatriza.indicadores.helper.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private PatientFragment patientFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Indicadores Assistenciais");
        setSupportActionBar(toolbar);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        patientFragment = new PatientFragment();
        FragmentTransaction  transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrameLayout, patientFragment);
        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_sair :
                deslogarUsuario();
                startActivity(new Intent(getApplicationContext(), loginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deslogarUsuario() {
        try {
            autenticacao.signOut();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
