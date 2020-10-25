package com.example.evento;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.database.EventoDAO;
import com.example.evento.modelo.Evento;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ListView listViewEventos;
    private ArrayAdapter<Evento> adapterEventos;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Eventos");
        listViewEventos = findViewById(R.id.listView_eventos);
        ArrayList<Evento> eventos = new ArrayList<Evento>();
        onClickListaEventos();
    }

    @Override
    protected void onResume() {
        super.onResume();

        EventoDAO eventoDAO = new EventoDAO(getApplicationContext());
        adapterEventos = new ArrayAdapter<Evento>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                eventoDAO.Listar());
        listViewEventos.setAdapter(adapterEventos);
    }

    private void onClickListaEventos() {
        listViewEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Evento eventoSelecionado = adapterEventos.getItem(position);
                Intent intent = new Intent(MainActivity.this, CadastroEvento.class);
                intent.putExtra("eventotoSelecionado", eventoSelecionado);
                startActivity(intent);
            }
        });
    }


    public void onClickNew(View v) {
        Intent intent = new Intent(MainActivity.this,
                CadastroEvento.class);
        startActivity(intent);
    }

}