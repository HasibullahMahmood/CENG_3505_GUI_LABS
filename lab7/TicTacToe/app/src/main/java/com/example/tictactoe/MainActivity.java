package com.example.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    // 0 = yellow, 1 = red
    int activePlayer = 0;
    boolean gameIsActive = true;

    // 2 means unplayed
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (gameState[tappedCounter] == 2 && gameIsActive) {
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1000f);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);
            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2) {
                    // Someone has won!
                    gameIsActive = false;
                    String winner = "Red";
                    if (gameState[winningPosition[0]] == 0) {
                        winner = "Yellow";
                    }
                    TextView winnerMessage = findViewById(R.id.winnerMessage);
                    winnerMessage.setText(winner + " has won!");
                    LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                } else {
                    boolean gameIsOver = true;
                    for (int counterState : gameState) {
                        if (counterState == 2) gameIsOver = false;
                    }
                    if (gameIsOver) {
                        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                        winnerMessage.setText("It's a draw");
                        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view) {
        gameIsActive = true;
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);
        activePlayer = 0;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("gameState", gameState);
        outState.putInt("activePlayer", activePlayer);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadBoard(savedInstanceState);

    }

    public void loadBoard(Bundle savedInstanceState){
        if (savedInstanceState != null) {
            HashMap<Integer, View> hashMap = new HashMap<>();
            hashMap.put(0, findViewById(R.id.imageView));
            hashMap.put(1, findViewById(R.id.imageView2));
            hashMap.put(2, findViewById(R.id.imageView3));
            hashMap.put(3, findViewById(R.id.imageView4));
            hashMap.put(4, findViewById(R.id.imageView5));
            hashMap.put(5, findViewById(R.id.imageView6));
            hashMap.put(6, findViewById(R.id.imageView7));
            hashMap.put(7, findViewById(R.id.imageView8));
            hashMap.put(8, findViewById(R.id.imageView9));

            gameState = savedInstanceState.getIntArray("gameState");
            activePlayer = savedInstanceState.getInt("activePlayer");
            for (int i = 0; i < gameState.length; i++) {
                if (gameState[i] == 0) {
                    ((ImageView)hashMap.get(i)).setImageResource(R.drawable.yellow);
                } else if (gameState[i] == 1) {
                    ((ImageView)hashMap.get(i)).setImageResource(R.drawable.red);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean newGame(MenuItem item){
        playAgain(null);
        return true;
    }

    public boolean saveGame(MenuItem item){
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("activePlayer",activePlayer);
        editor.putString("gameState",Arrays.toString(gameState));
        editor.commit();
        return true;
    }

    public boolean loadGame(MenuItem item){

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        activePlayer = preferences.getInt("activePlayer", activePlayer);

        String board = preferences.getString("gameState", "");
        board = board.replace("[","");
        board = board.replace("]","");
        String[] arr = board.split(", ");
        for (int i = 0; i < arr.length; i++){
            gameState[i] = Integer.parseInt(arr[i]);
        }
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }

        Bundle bundle = new Bundle();
        bundle.putInt("activePlayer", activePlayer);
        bundle.putIntArray("gameState", gameState);
        loadBoard(bundle);
        return true;
    }
}
