package com.pitercapistrano.playmusic;

import android.media.MediaPlayer;  // Importa a classe MediaPlayer para lidar com a reprodução de áudio
import android.os.Bundle;  // Importa a classe Bundle para salvar e restaurar o estado da atividade
import android.view.View;  // Importa a classe View para lidar com interações da interface
import android.widget.ImageButton;  // Importa a classe ImageButton para os botões de controle de música

import androidx.activity.EdgeToEdge;  // Importa EdgeToEdge para otimizar o uso da tela
import androidx.appcompat.app.AppCompatActivity;  // Importa a classe AppCompatActivity, base para atividades
import androidx.core.graphics.Insets;  // Importa Insets para manipular os espaços em torno da tela
import androidx.core.view.ViewCompat;  // Importa ViewCompat para compatibilidade de versões anteriores do Android
import androidx.core.view.WindowInsetsCompat;  // Importa WindowInsetsCompat para gerenciar as margens do sistema

public class MainActivity extends AppCompatActivity {

    // Declara os botões de controle de música
    private ImageButton pause, play, stop;

    // Declara um objeto MediaPlayer para reproduzir o arquivo de áudio
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ativa o modo EdgeToEdge para otimizar a tela, permitindo que a atividade use toda a área da tela
        EdgeToEdge.enable(this);

        // Define o layout da atividade com base no arquivo XML activity_main
        setContentView(R.layout.activity_main);

        // Define um listener para ajustar as margens da visualização de acordo com as barras do sistema (status bar, nav bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Ajusta o padding da tela para evitar sobreposição com barras do sistema
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referencia os botões de pausa, play e stop, vinculando-os aos seus respectivos elementos do layout
        pause = findViewById(R.id.btPause);
        play = findViewById(R.id.btPlay);
        stop = findViewById(R.id.btStop);

        // Inicializa o MediaPlayer com o arquivo de música armazenado em res/raw
        mediaPlayer = MediaPlayer.create(this, R.raw.fortunaimperatrixmundi);

        // Configura o evento de clique do botão de pausa
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verifica se o áudio está tocando e, se estiver, o pausa
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            }
        });

        // Configura o evento de clique do botão de play
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verifica se o MediaPlayer foi inicializado e, se sim, inicia a reprodução
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }
            }
        });

        // Configura o evento de clique do botão de stop
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verifica se o áudio está tocando e, se estiver, para a reprodução
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    // Reinicializa o MediaPlayer para permitir uma nova reprodução após o stop
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.fortunaimperatrixmundi);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Quando a atividade é parada, pausa a reprodução se o áudio estiver tocando
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Quando a atividade é destruída, para e libera os recursos do MediaPlayer, se ele estiver tocando
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            // Libera todos os recursos da memória e remove o arquivo de música da memória do dispositivo
            mediaPlayer.release();
            mediaPlayer = null;  // Define o MediaPlayer como null para evitar reutilização
        }
    }
}
