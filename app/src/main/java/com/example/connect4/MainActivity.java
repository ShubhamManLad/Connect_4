package com.example.connect4;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Player 1 -- Even Moves -- Yellow
    // Player 2 -- Odd Moves -- Orange
    int winner;

    int [] rowCounter = {5,5,5,5,5,5,5};
    Button [] rowButtons;
    ImageView [][] images;

    int [][] Grid = new int[6][7];

    TextView playerTurn;
    Button reset;

    int moves = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        playerTurn = findViewById(R.id.textView);

        images = new ImageView[6][7];

        rowButtons = new Button[7];

        reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reset();
            }
        });




        Reset();

        for (int i = 0; i < 7; i++) {
            int col = i;
            int id = getResources().getIdentifier("button" + (i), "id",getPackageName());
            rowButtons[i] = findViewById(id);
            rowButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Tap(col);

                }
            });
        }

    }

    void Tap(int col){
        int row = rowCounter[col];
        if (row>=0){
            ImageView img = images[row][col];
            img.setTranslationY(-1000f);
            if (moves%2 == 0){
                // Player 1 is Playing
                images[row][col].setImageResource(R.drawable.yellow);
                Grid[row][col]=1;
                playerTurn.setText("Player 2's Chance");
            }
            else{
                // Player 2 is Playing
                images[row][col].setImageResource(R.drawable.orange);
                Grid[row][col]=2;
                playerTurn.setText("Player 1's Chance");
            }
            moves++;
            rowCounter[col]--;
            img.animate().translationYBy(1000f).setDuration(500);
            playSound();
        }
        checkDraw();
        checkWin();
    }

    void Reset(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j<7; j++){
                int position = i*10 + j;
                int id = getResources().getIdentifier("imageView" + (position),"id",getPackageName());
                images[i][j] = findViewById(id);
                images[i][j].setImageResource(0);
                Grid[i][j]=0;
            }
        }
        moves = 0;
        for (int i = 0; i < 7; i++) {
            rowCounter[i] = 5;
        }
        playerTurn.setText("Player 1's Chance");
        reset.setVisibility(View.INVISIBLE);
    }

    boolean horizontalCheck(){
        for (int i = 0; i < 6 ; i++){
            for (int j = 0; j < 4;j++){
                if (Grid[i][j]!=0 &&
                        Grid[i][j]== Grid[i][j+1] &&
                        Grid[i][j+1]== Grid[i][j+2] &&
                        Grid[i][j+2]== Grid[i][j+3]){
                    winner = Grid[i][j];
                    return true;

                }
            }
        }
        return false;
    }

    boolean verticalCheck(){
        for (int j = 0; j < 7 ; j++){
            for (int i = 0; i < 3;i++){
                if (Grid[i][j]!=0 &&
                        Grid[i][j]== Grid[i+1][j] &&
                        Grid[i+1][j]== Grid[i+2][j] &&
                        Grid[i+2][j]== Grid[i+3][j]){
                    winner = Grid[i][j];
                    return true;

                }
            }
        }
        return false;
    }

    boolean ascendingDiagonalCheck(){
        for (int i = 3; i < 6 ; i++){
            for (int j = 0; j < 4;j++){
                if (Grid[i][j]!=0 &&
                        Grid[i][j]== Grid[i-1][j+1] &&
                        Grid[i-1][j+1]== Grid[i-2][j+2] &&
                        Grid[i-2][j+2]== Grid[i-3][j+3]){
                    winner = Grid[i][j];
                    return true;

                }
            }
        }
        return false;
    }

    boolean descendingDiagonalCheck(){
        for (int i = 3; i < 6 ; i++){
            for (int j = 3; j < 7;j++){
                if (Grid[i][j]!=0 &&
                        Grid[i][j]== Grid[i-1][j-1] &&
                        Grid[i-1][j-1]== Grid[i-2][j-2] &&
                        Grid[i-2][j-2]== Grid[i-3][j-3]){
                    winner = Grid[i][j];
                    return true;
                }
            }
        }
        return false;

    }

    void checkWin(){
        if(horizontalCheck() || verticalCheck() || ascendingDiagonalCheck() || descendingDiagonalCheck()){
            playerTurn.setText("Player "+ winner + " has won!");
            reset.setVisibility(View.VISIBLE);

        }
    }

    void checkDraw(){
        if (moves==42){
            playerTurn.setText("Match Draw");
        }
    }

    // Play Sound
    private void playSound() {
        //function that play sound according to sound ID
        int audioRes = R.raw.coin2;

        MediaPlayer p = MediaPlayer.create(this, audioRes);
        p.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        p.start();
    }

}