package com.hfad.messenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class CreateMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);
    }

    // Metodo usado para tratar o evento do botao Send Message
    public void onSendMessage (View view) {
        // Captura o elemento EditText do .xml
        EditText messageView = (EditText) findViewById(R.id.message);
        String messageText = messageView.getText().toString();

        // Criar uma Intent para ao pressionar o botao SendMessage() trocar para a Activity ReceiveMessageActivity
        Intent intent = new Intent(this,ReceiveMessageActivity.class);

        // Colocar a mensagem do EditText na Intent
        // ** Evitar colocar String literais como identificador da mensagem ** Cria-se uma cosntante na classe ReceiveMessageActivity
        // que contem essa chave.
        intent.putExtra(ReceiveMessageActivity.EXTRA_MESSAGE,messageText);

        // Chama a nova activity passando a Intent
        startActivity(intent);
    }
}
