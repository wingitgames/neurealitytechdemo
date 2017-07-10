package com.wingitgames.neurealitytechdemo.neurealitytechdemo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private static int maxX = 10;
    private static int maxY = 12;

    private char humanReadableMap[]= {'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F',
                                      'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F',
                                      'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F',
                                      'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F',
                                      'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F',
                                      'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F',
                                      'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F',
                                      'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F',
                                      'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F',
                                      'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F',
                                      'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F',
                                      'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F'};
    char visibleMap[][] = new char[maxX][maxY];

    private static final int partyX = 0;
    private static final int partyY = 1;
    private static final int partyLevel = 2;
    private int partyLocation[] = {7, 5, 0};
    private char oldFloorPiece = 'F';

    Button mapButton[][] = new Button[maxX][maxY];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

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

    private void initializeGame() {

        initializeVisibleMap();
        showParty();
    }

    private void initializeVisibleMap() {

        convertRowColMapToXY();
        initializeMapButtons();
    }

    private void convertRowColMapToXY() {

        for(int x = 0; x < maxX; x++) {
            for(int y = 0; y < maxY; y++) {

                int newY = maxY - y - 1;
                int humanMapLocation = (x * maxY) + y;
                visibleMap[x][newY] = humanReadableMap[humanMapLocation];
            }
        }
    }

    private void initializeMapButtons() {

        for(int x = 0; x < maxX; x++) {
            for(int y = 0; y < maxY; y++) {

                String buttonId = "x" + x + "y" + y;
                int buttonResourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
                mapButton[x][y] = (Button)findViewById(buttonResourceId);

                mapButton[x][y].setBackgroundResource(R.drawable.floor);

                addListenerToMapButton(mapButton[x][y], x, y);
            }
        }
    }

    private void addListenerToMapButton(final Button mapButton, final int mapX, final int mapY) {

        mapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int moveX = mapX > partyLocation[partyX] ?  1 : mapX < partyLocation[partyX] ? -1 : 0;
                int moveY = mapY > partyLocation[partyY] ?  1 : mapY < partyLocation[partyY] ? -1 : 0;

                restoreOldFloor(mapButton);

                partyLocation[partyX] = partyLocation[partyX] + moveX;
                partyLocation[partyY] = partyLocation[partyY] + moveY;

                showParty();
            }


        });
    }

    private void showParty() {

        setOldFloorPiece();

        int x =  partyLocation[partyX];
        int y =  partyLocation[partyY];

        String buttonId = "x" + x + "y" + y;
        int buttonResourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
        Button btn = (Button)findViewById(buttonResourceId);

        btn.setBackgroundResource(R.drawable.sage);
    }

    private void setOldFloorPiece() {

        int x =  partyLocation[partyX];
        int y =  partyLocation[partyY];

        oldFloorPiece = visibleMap[x][y];
    }

    private void restoreOldFloor(Button mapButton) {

        int x =  partyLocation[partyX];
        int y =  partyLocation[partyY];

        String buttonId = "x" + x + "y" + y;
        int buttonResourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
        Button btn = (Button)findViewById(buttonResourceId);

        int oldFloorResource = getOldFloorPiece();
        btn.setBackgroundResource(oldFloorResource);

    }

    private int getOldFloorPiece() {

        int oldPiece;

        switch(oldFloorPiece) {
            case 'F':
                oldPiece = R.drawable.floor;
                break;
            default:
                oldPiece = R.drawable.floor;
                break;
        }

        return oldPiece;
    }
}
