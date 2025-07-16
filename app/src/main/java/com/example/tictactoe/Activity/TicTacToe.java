package com.example.tictactoe.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tictactoe.MainActivity;
import com.example.tictactoe.R;


public class TicTacToe extends AppCompatActivity implements View.OnClickListener {
    private boolean PlayerOneActive;
    private TextView playeronescore, playertwoscore, playerstatus;
    private Button[] buttons = new Button[9];
    ImageView back;
    ImageView setting;
    private  ImageView reset; // Removed playAgain button reference
    private int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    private final int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };
    private int PlayerOneScorecount, playertwoscorecount;
    private int rounds;
    private boolean gameActive;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tic_tac_toe);

        playeronescore = findViewById(R.id.score_Player1);
        playertwoscore = findViewById(R.id.score_Player2);
        playerstatus = findViewById(R.id.textstatus);
        reset = findViewById(R.id.resetButton);
        back = findViewById(R.id.backButton);

        buttons[0] = findViewById(R.id.btn1);
        buttons[1] = findViewById(R.id.btn2);
        buttons[2] = findViewById(R.id.btn3);
        buttons[3] = findViewById(R.id.btn4);
        buttons[4] = findViewById(R.id.btn5);
        buttons[5] = findViewById(R.id.btn6);
        buttons[6] = findViewById(R.id.btn7);
        buttons[7] = findViewById(R.id.btn8);
        buttons[8] = findViewById(R.id.btn9);

        for (Button button : buttons) {
            button.setOnClickListener(this);
        }

        PlayerOneScorecount = 0;
        playertwoscorecount = 0;
        PlayerOneActive = true;
        rounds = 0;
        gameActive = true;
        updatePlayerScore();
        playerstatus.setText("Player 1's turn");

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        back.setOnClickListener(v -> {
            Intent intent2 = new Intent(this, slphashscrrenActivity.class);
            startActivity(intent2);
            finish();
        });
    }

    @Override
    public void onClick(View v) {
        if (!gameActive) {
            return;
        }

        Button clickedButton = (Button) v;

        if (!clickedButton.getText().toString().equals("")) {
            return;
        }

        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1)) - 1;

        if (PlayerOneActive) {
            clickedButton.setText("X");
            clickedButton.setTextSize(40.0F);
            clickedButton.setTextColor(Color.parseColor("#FB1302"));
            clickedButton.setShadowLayer(5f, 2f, 2f, Color.parseColor("#02E0FD"));
            gameState[gameStatePointer] = 0;
        } else {
            clickedButton.setText("O");
            clickedButton.setTextSize(40.0F);
            clickedButton.setTextColor(Color.parseColor("#FFFFFFFF"));
            clickedButton.setShadowLayer(5f, 2f, 2f, Color.parseColor("#02E0FD"));
            gameState[gameStatePointer] = 1;
        }

        rounds++;

        if (checkWinner()) {
            gameActive = false;
            if (PlayerOneActive) {
                PlayerOneScorecount++;
                playerstatus.setText("Player 1 has won!");
            } else {
                playertwoscorecount++;
                playerstatus.setText("Player 2 has won!");
            }
            updatePlayerScore();
        } else if (rounds == 9) {
            gameActive = false;
            playerstatus.setText("It's a draw!");
        } else {
            PlayerOneActive = !PlayerOneActive;
            playerstatus.setText(PlayerOneActive ? "Player 1's turn" : "Player 2's turn");
        }
    }

    private boolean checkWinner() {
        for (int[] pos : winningPositions) {
            int a = pos[0];
            int b = pos[1];
            int c = pos[2];
            if (gameState[a] != 2 &&
                    gameState[a] == gameState[b] &&
                    gameState[b] == gameState[c]) {
                return true;
            }
        }
        return false;
    }

    private void updatePlayerScore() {
        playeronescore.setText(String.valueOf(PlayerOneScorecount));
        playertwoscore.setText(String.valueOf(playertwoscorecount));
    }

    private void resetGame() {
        rounds = 0;
        PlayerOneActive = true;
        gameActive = true;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
            buttons[i].setText("");
        }
        playerstatus.setText("Player 1 turn");
    }
}