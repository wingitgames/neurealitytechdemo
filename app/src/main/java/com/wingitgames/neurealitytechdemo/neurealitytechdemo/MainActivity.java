package com.wingitgames.neurealitytechdemo.neurealitytechdemo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Context context;

    GestureDetector gestureDetector;

    private static int MAX_X = 10;
    private static int MAX_Y = 12;

    float diffx, diffy;

    private char readableBigMap[][] =
/*
            {{'F', 'F', 'F', 'F', 'F'},
            { 'W', 'U', 'W', 'W', 'W'},
            { 'W', 'F', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'W'},
            {'F', 'F', 'W', 'F', 'F'}};
*/
/*
            {{'F', 'F', 'W', 'F', 'F', 'F', 'W', 'W', 'W', 'F', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'U', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'W', 'W', 'W', 'W', 'L', 'W', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'd', 'W', 'W', 'F', 'F'},
            {'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'W', 'U', 'W', 'W', 'W', 'W', 'F', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'L', 'W', 'F', 'F', 'F', 'F', 'W', 'F', 'W', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'W', 'F', 'W', 'F', 'F', 'W', 'F', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'F', 'F', 'F', 'W', 'W', 'W', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'W', 'W', 'W', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'W', 'F', 'U', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'F', 'F', 'W', 'W', 'W', 'F', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'U', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'W', 'W', 'W', 'W', 'L', 'W', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'd', 'W', 'W', 'F', 'F'},
            {'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F'},
            {'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F'}};
*/
            {{'F', 'F', 'W', 'F', 'F', 'F', 'W', 'W', 'W', 'F', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F', 'W', 'W', 'W', 'F', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'U', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'U', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'W', 'W', 'W', 'W', 'L', 'W', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'W', 'W', 'W', 'W', 'L', 'W', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'd', 'W', 'W', 'F', 'F', 'F', 'F', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'd', 'W', 'W', 'F', 'F'},
            {'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'W', 'U', 'W', 'W', 'W', 'W', 'F', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'W', 'U', 'W', 'W', 'W', 'W', 'F', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'L', 'W', 'F', 'F', 'F', 'F', 'W', 'F', 'W', 'F', 'F', 'F', 'F', 'W', 'L', 'W', 'F', 'F', 'F', 'F', 'W', 'F', 'W', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'W', 'F', 'W', 'F', 'F', 'W', 'F', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'W', 'F', 'W', 'F', 'F', 'W', 'F', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'F', 'F', 'F', 'W', 'W', 'W', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F', 'F', 'W', 'W', 'W', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'W', 'W', 'W', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'W', 'W', 'W', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'W', 'F', 'U', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'W', 'F', 'U', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'F', 'F', 'W', 'W', 'W', 'F', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F', 'W', 'W', 'W', 'F', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'U', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'U', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'W', 'W', 'W', 'W', 'L', 'W', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'W', 'W', 'W', 'W', 'L', 'W', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'd', 'W', 'W', 'F', 'F', 'F', 'F', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'd', 'W', 'W', 'F', 'F'},
            {'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'U', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'U', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'W', 'W', 'W', 'W', 'L', 'W', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'W', 'W', 'W', 'W', 'L', 'W', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'd', 'W', 'W', 'F', 'F', 'F', 'F', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'd', 'W', 'W', 'F', 'F'},
            {'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'W', 'U', 'W', 'W', 'W', 'W', 'F', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'W', 'U', 'W', 'W', 'W', 'W', 'F', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'L', 'W', 'F', 'F', 'F', 'F', 'W', 'F', 'W', 'F', 'F', 'F', 'F', 'W', 'L', 'W', 'F', 'F', 'F', 'F', 'W', 'F', 'W', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'W', 'F', 'W', 'F', 'F', 'W', 'F', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'W', 'F', 'W', 'F', 'F', 'W', 'F', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'F', 'F', 'F', 'W', 'W', 'W', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F', 'F', 'W', 'W', 'W', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'W', 'W', 'W', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'W', 'W', 'W', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'W', 'F', 'U', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'W', 'F', 'U', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'F', 'F', 'W', 'W', 'W', 'F', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F', 'W', 'W', 'W', 'F', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'U', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'U', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'W', 'W', 'W', 'W', 'L', 'W', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'W', 'W', 'W', 'W', 'L', 'W', 'W', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'W', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F'},
            {'F', 'F', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'd', 'W', 'W', 'F', 'F', 'F', 'F', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'd', 'W', 'W', 'F', 'F'},
            {'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F'},
            {'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F'}};

    char bigMap[][] = new char[readableBigMap.length][readableBigMap[0].length];
    
    char visibleMap[][] = new char[MAX_X][MAX_Y];

    int bigMapRowOffset = 2;
    int bigMapColOffset = 2;

    Button mapButton[][] = new Button[MAX_X][MAX_Y];
    Button walkButton[][] = new Button[MAX_X][MAX_Y];

    private int START_X = 5;
    private int START_Y = 5;

    private static Party party;

    private boolean mIsBound = false;
    private MusicService mServ;

    String currentSound;
    AudioAttributes audioAttribs;
    SoundPool soundPool;

    int soundIds[] = new int[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        gestureDetector = new GestureDetector(this, new GestureListener());
        doBindService();

        getWindow().getDecorView().setSystemUiVisibility(
                          View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        initializeGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
    }

    private void initializeGame() {

        initializeBackgroundMusic();
        initializeSoundpool();
        initializeParty();
        initializeVisibleMap();
        showParty();
    }

    private void initializeBackgroundMusic() {

        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        startService(music);
    }

    private void initializeSoundpool() {

        audioAttribs = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(10)
                .setAudioAttributes(audioAttribs)
                .build();

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        soundIds[0] = soundPool.load(getApplicationContext(), R.raw.walk1, 1);
        soundIds[1] = soundPool.load(getApplicationContext(), R.raw.walk2, 1);
        soundIds[2] = soundPool.load(getApplicationContext(), R.raw.knock, 1);
    }

    private void initializeParty() {

        party.X = START_X;
        party.Y = START_Y;
    }

    private void initializeVisibleMap() {

        invertMap();
        populateVisibleMap();
        initializeMapButtons();
    }

    private void populateVisibleMap() {

        for(int x = 0; x < MAX_X; x++) {
            for(int y = 0; y < MAX_Y; y++) {

                int newX = x;
                int newY = y;


                int newRow = y + bigMapRowOffset;
                int newCol = x + bigMapColOffset;

                if(bigMap[0].length > newCol && bigMap.length > newRow) {

                    visibleMap[newX][newY] = bigMap[newRow][newCol];

                } else {

                    visibleMap[x][y] = ' ';
                }

                String backId = "backx" + x + "y" + y;
                int backResourceId = getResources().getIdentifier(backId, "id", getPackageName());
                mapButton[x][y] = (Button)findViewById(backResourceId);
                mapButton[x][y].setClickable(false);

                int mapPiece = getMapPiece(x, y);

                mapButton[x][y].setBackgroundResource(mapPiece);

            }
        }
    }

    private void invertMap() {

        int newRow = 0;
        for(int row = readableBigMap.length - 1; row >=0; row--) {
            for(int col = 0; col < readableBigMap[0].length; col++) {

                bigMap[newRow][col] = readableBigMap[row][col];
            }

            newRow++;
        }
    }

    private void initializeMapButtons() {

        for(int x = 0; x < MAX_X; x++) {
            for(int y = 0; y < MAX_Y; y++) {

                String walkId = "walkx" + x + "y" + y;
                int walkResourceId = getResources().getIdentifier(walkId, "id", getPackageName());
                walkButton[x][y] = (Button)findViewById(walkResourceId);
                walkButton[x][y].setSoundEffectsEnabled(false);
                walkButton[x][y].setClickable(false);
            }
        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {

            float x = e.getX();
            float y = e.getY();

            String xyVals = "x = " + x + " y = " + y + "\n";

            Toast.makeText(getApplicationContext(), "Single tap has occured x = " + xyVals, Toast.LENGTH_SHORT).show();

            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            float x = e.getX();
            float y = e.getY();

            String xyVals = "x = " + x + " y = " + y + "\n";

            Toast.makeText(getApplicationContext(), "Double tap has occured x = " + xyVals, Toast.LENGTH_SHORT).show();

            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {

            float x1 = event1.getX();
            float y1 = event1.getY();
            float x2 = event2.getX();
            float y2 = event2.getY();

            diffx = x2 - x1;
            diffy = y2 - y1;

            int moveX = 0;
            int moveY = 0;

            double rad = Math.atan2(y1 - y2, x2 - x1) + Math.PI;

            int mapX = party.X + bigMapColOffset;
            int mapY = party.Y + bigMapRowOffset;

            boolean movePartyOK = false;
            if(rad > 1.875 * Math.PI || rad <= 0.125 * Math.PI) {

                if (mapX < bigMap[0].length - 1) {

                    moveX = 1;
                    movePartyOK = true;
                }

            } else if(rad > 0.125 * Math.PI && rad <= 0.375 * Math.PI) {

                if(mapX < bigMap[0].length - 1 && mapY < bigMap.length - 1) {

                    moveX = 1;
                    moveY = 1;
                    movePartyOK = true;
                }

            } else if(rad > 0.375 * Math.PI && rad <= 0.625 * Math.PI) {

                if(mapY < bigMap.length - 1) {

                    moveY = 1;
                    movePartyOK = true;
                }

            } else if(rad > 0.625 * Math.PI && rad <= 0.875 * Math.PI) {

                if(mapX > 0 && mapY < bigMap.length - 1) {

                    moveX = -1;
                    moveY = 1;
                    movePartyOK = true;
                }

            } else if(rad > 0.875 * Math.PI && rad <= 1.125 * Math.PI) {

                if(mapX > 0) {

                    moveX = -1;
                    movePartyOK = true;
                }

            } else if(rad > 1.125 * Math.PI && rad <= 1.375 * Math.PI) {

                if(mapX > 0 && mapY > 0) {

                    moveX = -1;
                    moveY = -1;
                    movePartyOK = true;
                }

            } else if(rad > 1.375 * Math.PI && rad <= 1.625 * Math.PI) {

                if(mapY > 0) {

                    moveY = -1;
                    movePartyOK = true;
                }

            } else if(rad > 1.625 * Math.PI && rad <= 1.875 * Math.PI) {

                if(mapX < bigMap.length - 1 && mapY > 0) {

                    moveX = 1;
                    moveY = -1;
                    movePartyOK = true;
                }
            }

            if(movePartyOK) {

                moveParty(moveX, moveY);
            }

            return true;
        }
        /*
        @Override
        public void onLongPress(MotionEvent event) {
            Toast.makeText(getApplicationContext(), "onLongPress: " + event.toString(), Toast.LENGTH_SHORT).show();
        }
        */
        /*
        @Override
        public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                                float distanceY) {

            Toast.makeText(getApplicationContext(), "onScroll: " + event1.toString() + event2.toString(), Toast.LENGTH_SHORT).show();
            return true;
        }

        */
        /*
        @Override
        public void onShowPress(MotionEvent event) {
            Toast.makeText(getApplicationContext(), "onShowPress: " + event.toString(), Toast.LENGTH_SHORT).show();
        }
        */
    }

    private void moveParty(int moveX, int moveY) {

        if(currentSound == "walk1") {

            currentSound = "walk2";

        } else {

            currentSound = "walk1";
        }

        int newX = party.X  + moveX;
        int newY = party.Y + moveY;

        int newMapX = newX;
        int newMapY = newY;

        boolean moveParty = false;

        int bigMapOldRowOffset = bigMapRowOffset;
        int bigMapOldColOffset = bigMapColOffset;

        if (newX > MAX_X - 3 && party.X + bigMapColOffset < bigMap[0].length - 3) {

            newMapX--;
            bigMapColOffset++;
            moveParty = true;

        } else if (newX < 2 && bigMapColOffset > 0) {

            newMapX++;
            bigMapColOffset--;
            moveParty = true;
        }

        if (newY > MAX_Y - 3 && party.Y + bigMapRowOffset < bigMap.length - 3) {

            newMapY--;
            bigMapRowOffset++;
            moveParty = true;


        } else if (newY < 2 && bigMapRowOffset > 0) {

            newMapY++;
            bigMapRowOffset--;
            moveParty = true;
        }


        if (moveParty == true) {

            if (movableSpace(visibleMap[newX][newY])) {

                populateVisibleMap();

            } else {

                bigMapRowOffset = bigMapOldRowOffset;
                bigMapColOffset = bigMapOldColOffset;
            }
        }

        if (movableSpace(visibleMap[newMapX][newMapY])) {

            clearPreviousParty();

            party.X = newMapX;
            party.Y = newMapY;

            showParty();
        }

        playSound(currentSound);

    }
    
    private boolean movableSpace(char newSpace) {

        boolean retres;

        switch(newSpace) {

            case ' ':
                retres = false;
                break;
            case 'F':
                retres = true;
                break;
            case 'W':
                retres = false;
                break;
            case 'U':
                retres = true;
                break;
            case 'L':

                Toast toast = Toast.makeText(getApplicationContext(), "Locked!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 100);
                toast.show();
                currentSound = "knock";
                retres = false;
                break;
            case 'u':
                retres = true;
                break;
            case 'd':
                retres = true;
                break;
            default:
                retres = false;
                break;
        }

        return retres;
    }    

    private void showParty() {

        int x =  Party.X;
        int y =  Party.Y;

        String buttonId = "walkx" + x + "y" + y;
        int buttonResourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
        Button button = (Button)findViewById(buttonResourceId);

        button.setBackgroundResource(R.drawable.sage);

        int[] buttonLocation = new int[2];
        button.getLocationInWindow(buttonLocation);
        party.locationX = buttonLocation[0];
        party.locationY = buttonLocation[1];
    }

    private void clearPreviousParty() {

        int x =  Party.X;
        int y =  Party.Y;

        String walkId = "walkx" + x + "y" + y;
        int buttonResourceId = getResources().getIdentifier(walkId, "id", getPackageName());
        Button btn = (Button)findViewById(buttonResourceId);

        btn.setBackgroundResource(android.R.color.transparent);
    }

    private int getPieceFromTile(char tile) {

        int piece;

        switch(tile) {

            case ' ':
                piece = R.drawable.blank;
                break;
            case 'F':
                piece = R.drawable.floor1;
                break;
            case 'W':
                piece = R.drawable.wall1;
                break;
            case 'U':
                piece = R.drawable.door1;
                break;
            case 'L':
                piece = R.drawable.door1;
                break;
            case 'u':
                piece = R.drawable.up1;
                break;
            case 'd':
                piece = R.drawable.down1;
                break;
            default:
                piece = R.drawable.floor1;
                break;
        }

        return piece;
    }

    private int getMapPiece(int x, int y) {

        char mapTile = visibleMap[x][y];

        int piece = getPieceFromTile(mapTile);

        return piece;
    }


    private void playSound(String soundName) {

        int effectId = getSoundEffectId(soundName);
        soundPool.play(soundIds[effectId], 1, 1, 1, 0, 1.0f);
    }

    private int getSoundEffectId(String effectName) {

        int effectId = 0;
        switch(effectName) {

            case "walk1":
                effectId = 0;
                break;
            case "walk2":
                effectId = 1;
                break;
            case "knock":
                effectId = 2;
                break;
        }
        return effectId;
    }

    private ServiceConnection Scon = new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,MusicService.class),
                Scon,Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }
}
