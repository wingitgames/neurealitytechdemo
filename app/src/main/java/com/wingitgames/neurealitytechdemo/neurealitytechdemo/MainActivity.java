package com.wingitgames.neurealitytechdemo.neurealitytechdemo;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    GestureDetector gestureDetector;

    private static final int MAX_CLICK_DURATION = 100;
    private long startClickTime;

    private static int MAX_X = 10;
    private static int MAX_Y = 12;

    float x1, x2, y1, y2, diffx, diffy;

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

    private char oldFloorPiece = 'F'; // cat technique r54tttttttttre455555555555555ewrdddddddddddddddddt65555555555555555555555555555555555555555555555555555555555555555555555

    Button mapButton[][] = new Button[MAX_X][MAX_Y];
    Button walkButton[][] = new Button[MAX_X][MAX_Y];

    private int START_X = 5;
    private int START_Y = 5;

    private static Party party;

    String currentSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        gestureDetector = new GestureDetector(getApplicationContext(), new GestureListener());

        getWindow().getDecorView().setSystemUiVisibility(
                          View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        initializeGame();
    }

    public boolean onTouchEvent(MotionEvent touchevent) {

        switch(touchevent.getAction()) {

            case MotionEvent.ACTION_DOWN:
                x1 = touchevent.getX();
                y1 = touchevent.getY();

                startClickTime = java.util.Calendar.getInstance().getTimeInMillis();
                break;

            case MotionEvent.ACTION_UP:

                x2 = touchevent.getX();
                y2 = touchevent.getY();

                String xyVals = "x1 = " + x1 + " x2 = " + x2 + " y1 = " + y1 + " y2 = " + y2 + "\n";

                // Android Calendar is API 24 darnit
                long clickDuration = java.util.Calendar.getInstance().getTimeInMillis() - startClickTime;
                if (clickDuration < MAX_CLICK_DURATION) {

                    Toast.makeText(this, "Single tap has performed " + xyVals, Toast.LENGTH_SHORT).show();

                } else {

                    diffx = x2 - x1;
                    diffy = y2 - y1;

                    int moveX = 0;
                    int moveY = 0;

                    double rad = Math.atan2(y1 - y2, x2 - x1) + Math.PI;
/*
                    if (x1 < x2 && Math.abs(diffy) < Math.abs(diffx)) {

                        Toast.makeText(this, "Left to Right swipe Performed " + xyVals + " radians = " + rad, Toast.LENGTH_SHORT).show();

                        moveX = 1;
                    }

                    if (x1 > x2 && Math.abs(diffy) < Math.abs(diffx)) {

                        Toast.makeText(this, "Right to Left swipe Performed " + xyVals + " radians = " + rad, Toast.LENGTH_SHORT).show();

                        moveX = -1;
                    }

                    if (y1 < y2 && Math.abs(diffx) < Math.abs(diffy)) {

                        Toast.makeText(this, "Top to Bottom swipe Performed " + xyVals + " radians = " + rad, Toast.LENGTH_SHORT).show();

                        moveY = -1;
                    }

                    if (y1 > y2 && Math.abs(diffx) < Math.abs(diffy)) {

                        Toast.makeText(this, "Bottom to Top swipe Performed " + xyVals + " radians = " + rad, Toast.LENGTH_SHORT).show();

                        moveY = 1;
                    }
*/
                    int mapX = party.X + bigMapColOffset;
                    int mapY = party.Y + bigMapRowOffset;

                    boolean movePartyOK = false;
                    if(rad > 1.875 * Math.PI || rad <= 0.125 * Math.PI) {

                        if(mapX > 0) {

                            moveX = -1;
                            movePartyOK = true;
                        }

                    } else if(rad > 0.125 * Math.PI && rad <= 0.375 * Math.PI) {

                        if(mapX > 0 && mapY > 0) {

                            moveX = -1;
                            moveY = -1;
                            movePartyOK = true;
                        }


                    } else if(rad > 0.375 * Math.PI && rad <= 0.625 * Math.PI) {

                        if(mapY > 0) {

                            moveY = -1;
                            movePartyOK = true;
                        }

                    } else if(rad > 0.625 * Math.PI && rad <= 0.875 * Math.PI) {

                        if(mapX > 0 && mapY < bigMap.length - 1) {

                            moveX = 1;
                            moveY = -1;
                            movePartyOK = true;
                        }

                    } else if(rad > 0.875 * Math.PI && rad <= 1.125 * Math.PI) {

                        if(mapX < bigMap[0].length - 1) {

                            moveX = 1;
                            movePartyOK = true;
                        }

                    } else if(rad > 1.125 * Math.PI && rad <= 1.375 * Math.PI) {

                        if(mapX < bigMap[0].length - 1 && mapY < bigMap.length - 1 ) {

                            moveX = 1;
                            moveY = 1;
                            movePartyOK = true;
                        }

                    } else if(rad > 1.375 * Math.PI && rad <= 1.625 * Math.PI) {

                        if(mapY < bigMap.length - 1) {

                            moveY = 1;
                            movePartyOK = true;
                        }

                    } else if(rad > 1.625 * Math.PI && rad <= 1.875 * Math.PI) {

                        if(mapX < bigMap.length - 1 && mapY > 0) {

                            moveX = -1;
                            moveY = 1;
                            movePartyOK = true;
                        }
                    }

                    if(movePartyOK) {

                        moveParty(moveX, moveY);
                    }
                }

                break;
        }

        return false;
    }

    public boolean onSingleTapConfirmed(MotionEvent e) {

        Intent i = new Intent(getApplicationContext(), Second.class);
        getContext().startActivity(i);

        return true;
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

    private void initializeGame() {

        initializeParty();
        initializeVisibleMap();
        showParty();
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

                addListenerToMapButton(walkButton[x][y], x, y);
            }
        }
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
    
    private void addListenerToMapButton(final Button walkButton, final int mapX, final int mapY) {

        /*
        walkButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if(currentSound == "walk1") {

                    currentSound = "walk2";

                } else {

                    currentSound = "walk1";
                }

                int moveX = mapX > party.X  ? 1 : mapX < party.X  ? -1 : 0;
                int moveY = mapY > party.Y ? 1 : mapY < party.Y ? -1 : 0;

                int newX = party.X  + moveX;
                int newY = party.Y + moveY;

                int newMapX = newX;
                int newMapY = newY;

                boolean moveParty = false;

                int bigMapOldRowOffset = bigMapRowOffset;
                int bigMapOldColOffset = bigMapColOffset;

                if(newX > MAX_X - 3  && party.X  + bigMapColOffset < bigMap[0].length - 3) {

                    newMapX--;
                    bigMapColOffset++;
                    moveParty = true;

                } else if(newX < 2 && bigMapColOffset > 0) {

                    newMapX++;
                    bigMapColOffset--;
                    moveParty = true;
                }

                if(newY > MAX_Y - 3 && party.Y + bigMapRowOffset < bigMap.length - 3) {

                    newMapY--;
                    bigMapRowOffset++;
                    moveParty = true;


                } else if(newY < 2 && bigMapRowOffset > 0) {

                    newMapY++;
                    bigMapRowOffset--;
                    moveParty = true;
                }


                if(moveParty == true) {

                    if(movableSpace(visibleMap[newX][newY])) {

                        populateVisibleMap();

                    }  else {

                        bigMapRowOffset = bigMapOldRowOffset;
                        bigMapColOffset = bigMapOldColOffset;
                    }
                }

                if(movableSpace(visibleMap[newMapX][newMapY])) {

                    clearPreviousParty();

                    party.X  = newMapX;
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
        });
        */
    }

    private void showParty() {

        int x =  Party.X;
        int y =  Party.Y;

        String buttonId = "walkx" + x + "y" + y;
        int buttonResourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
        Button btn = (Button)findViewById(buttonResourceId);

        btn.setBackgroundResource(R.drawable.sage);
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

        int audioResource = getResources().getIdentifier(soundName, "raw", getPackageName());;

        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), audioResource);
        mediaPlayer.start();
    }

    public boolean onSingleTapConfirmed(MotionEvent e) {

        Toast.makeText(this, "Single Tap Confirmed", Toast.LENGTH_SHORT).show();

        return true;
    }
}
