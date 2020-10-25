package com.example.evento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.database.EventoDAO;
import com.example.evento.modelo.Evento;

import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;

public class CadastroEvento extends AppCompatActivity {

    private boolean edicao = false;
    private boolean excluir = false;
    private int id = 0;
    private DateFormat data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);
        setTitle("Cadastro de Eventos");
        carregarEvento();
    }

    private void carregarEvento() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null && intent.getExtras().get("eventotoSelecionado") != null) {
            Evento evento = (Evento) intent.getExtras().get("eventotoSelecionado");
            EditText editTextNome = findViewById(R.id.editTextNome);
            EditText editTextData = findViewById(R.id.editTextData);
            EditText editTextLocal = findViewById(R.id.editTextLocal);
            editTextNome.setText(evento.getNome());
            editTextData.setText(evento.getData().toString());
            editTextLocal.setText(evento.getLocal());
            id = evento.getId();
        }
    }

    public void onClickBack(View v) {
        finish();
    }

    public void onClickSave(View v) {
        processar();

    }

    public void onClickRemove(View v) {
        excluir = true;
        processar();

    }

    private void processar() {

        EditText editTextNome = findViewById(R.id.editTextNome);
        EditText editTextData = findViewById(R.id.editTextData);
        EditText editTextLocal = findViewById(R.id.editTextLocal);

        String nome = editTextNome.getText().toString();
        String data = editTextData.getText().toString();
        String local = editTextLocal.getText().toString();


        if ((nome.isEmpty() || data.isEmpty() || local.isEmpty()) && !excluir) {
            erroMensagem();
            return;

        } else {

            Evento evento = new Evento(id, nome, data, local);
            EventoDAO eventoDAO = new EventoDAO(getBaseContext());


            if (excluir) {
                boolean excluir = eventoDAO.excluir(evento);
            } else {
                boolean salvou = eventoDAO.salvar(evento);
                if (!salvou) {
                    Toast.makeText(CadastroEvento.this,
                            "Erro ao salvar",
                            Toast.LENGTH_LONG).show();
                }
            }
            finish();
        }
    }

    private void erroMensagem() {
        Toast.makeText(CadastroEvento.this, "É obrigatório preencher todos os campos", Toast.LENGTH_LONG).show();
    }


}