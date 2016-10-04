package com.hfad.messenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ReceiveMessageActivity extends AppCompatActivity {

    // Constante para servir de marcacao para a chave da Intent
    public static final String EXTRA_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);

        // Descobrir qual a Intent que iniciou a Activity e capturar os dados contidos na mesma
        Intent intent = getIntent();
        String messageText = intent.getStringExtra(EXTRA_MESSAGE);

        // Atribuir no elemento TextView contido no .xml do layout o conteudo da mensagem passada pela Intent
        TextView messageView = (TextView) findViewById(R.id.receive);
        messageView.setText(messageText);

    }
}
