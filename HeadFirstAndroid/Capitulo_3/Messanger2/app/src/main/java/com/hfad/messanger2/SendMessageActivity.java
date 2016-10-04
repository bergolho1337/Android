package com.hfad.messanger2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class SendMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
    }

    public void onSendMessage (View view) {
        EditText messageView = (EditText) findViewById(R.id.message);
        String messageText = messageView.getText().toString();

        // Criar uma Intent que vai procurar uma Activity com uma acao de enviar mensagem
        Intent intent = new Intent(Intent.ACTION_SEND);

        // Especifica como eh o tipo de mensagem que vamos enviar
        intent.setType("text/plain");

        // Coloca o conteudo da mensagem na Intent para ser enviada para a Activity que vai enviar a mensagem
        intent.putExtra(Intent.EXTRA_TEXT,messageText);
        // Pode-se colocar o assunto da mensagem tambem com a linha abaixo
        intent.putExtra(Intent.EXTRA_SUBJECT,"Teste");

        // No caso eh o usuario que vai escolher qual Activity vai realizar essa tarefa
        // Exemplo: servico de Mensagem comum do smartphone, Gmail, Facebook, WhatsUp ...

        // Para evitar que as opcoes de selecionar o aplicativo padrao para executar a funcao apareca.
        // Isso eh bom quando o Android nao acha uma Activity para rodar a acao requerida na Intent implicita
        // Vamos criar um Chooser.
        String chooserTitle = getString(R.string.chooser);
        Intent chooseIntent = Intent.createChooser(intent,chooserTitle);

        // Inicializa a Activity selecionada no Chooser
        startActivity(chooseIntent);

    }
}
