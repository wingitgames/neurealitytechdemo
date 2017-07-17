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

    private static int MAX_X = 10;
    private static int MAX_Y = 12;

    private char fullMap[]= {'W', 'u', 'W', 'F', 'F', 'F', 'W', 'W', 'W', 'W',
                             'W', 'F', 'W', 'U', 'W', 'W', 'W', 'W', 'F', 'F',
                             'W', 'L', 'W', 'F', 'F', 'F', 'F', 'W', 'F', 'W',
                             'W', 'F', 'W', 'F', 'W', 'F', 'F', 'W', 'F', 'F',
                             'W', 'F', 'F', 'F', 'F', 'W', 'W', 'W', 'W', 'F',
                             'W', 'W', 'W', 'W', 'F', 'F', 'F', 'F', 'W', 'F',
                             'W', 'F', 'F', 'W', 'F', 'F', 'W', 'F', 'U', 'F',
                             'W', 'F', 'F', 'F', 'W', 'W', 'W', 'F', 'W', 'F',
                             'W', 'U', 'W', 'F', 'F', 'F', 'F', 'F', 'W', 'F',
                             'W', 'F', 'W', 'W', 'W', 'W', 'L', 'W', 'W', 'F',
                             'W', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F',
                             'W', 'W', 'W', 'W', 'W', 'W', 'W', 'd', 'W', 'W'};
    char visibleMap[][] = new char[MAX_X][MAX_Y];

    private static final int PARTY_X = 0;
    private static final int PARTY_Y = 1;
    private static final int PARTY_LEVEL = 2;
    private static final int PARTY_SPEED = 3;
    private int partyLocation[] = {7, 0, 0, 1};
    private char oldFloorPiece = 'F';

    Button mapButton[][] = new Button[MAX_X][MAX_Y];
    Button walkButton[][] = new Button[MAX_X][MAX_Y];

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

        int mapLocation = 0;
        for(int y = MAX_Y - 1; y >= 0 ; y--) {
            for(int x = 0; x < MAX_X; x++) {

                int newY = MAX_Y - y - 1;
                visibleMap[x][y] = fullMap[mapLocation];
                mapLocation++;
            }
        }
    }

    private void initializeMapButtons() {

        for(int x = 0; x < MAX_X; x++) {
            for(int y = 0; y < MAX_Y; y++) {

                String backId = "backx" + x + "y" + y;
                String walkId = "walkx" + x + "y" + y;
                int backResourceId = getResources().getIdentifier(backId, "id", getPackageName());
                int walkResourceId = getResources().getIdentifier(walkId, "id", getPackageName());
                mapButton[x][y] = (Button)findViewById(backResourceId);
                walkButton[x][y] = (Button)findViewById(walkResourceId);

                int mapPiece = getMapPiece(x, y);

                mapButton[x][y].setBackgroundResource(mapPiece);

                addListenerToMapButton(walkButton[x][y], x, y);
            }
        }
    }

    private void addListenerToMapButton(final Button walkButton, final int mapX, final int mapY) {

        walkButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                for(int m = 0; m < partyLocation[PARTY_SPEED]; m++) {

                    int moveX = mapX > partyLocation[PARTY_X] ? 1 : mapX < partyLocation[PARTY_X] ? -1 : 0;
                    int moveY = mapY > partyLocation[PARTY_Y] ? 1 : mapY < partyLocation[PARTY_Y] ? -1 : 0;

                    int newX = partyLocation[PARTY_X] + moveX;
                    int newY = partyLocation[PARTY_Y] + moveY;

                    if (movableSpace(visibleMap[newX][newY])) {

                        restoreOldFloor();

                        partyLocation[PARTY_X] = newX;
                        partyLocation[PARTY_Y] = newY;

                        showParty();

                    } else {

                        break;
                    }
                }
            }

            private boolean movableSpace(char newSpace) {

                boolean retres;

                switch(newSpace) {

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
    }

    private void showParty() {

        setOldFloorPiece();

        int x =  partyLocation[PARTY_X];
        int y =  partyLocation[PARTY_Y];

        String buttonId = "walkx" + x + "y" + y;
        int buttonResourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
        Button btn = (Button)findViewById(buttonResourceId);

        btn.setBackgroundResource(R.drawable.sage);
    }

    private void setOldFloorPiece() {

        int x =  partyLocation[PARTY_X];
        int y =  partyLocation[PARTY_Y];

        oldFloorPiece = visibleMap[x][y];
    }

    private void restoreOldFloor() {

        int x =  partyLocation[PARTY_X];
        int y =  partyLocation[PARTY_Y];

        String buttonId = "walkx" + x + "y" + y;
        int buttonResourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
        Button btn = (Button)findViewById(buttonResourceId);

        //int oldFloorResource = getOldFloorPiece();
        btn.setBackgroundResource(android.R.color.transparent);

    }

    private int getPieceFromTile(char tile) {

        int piece;

        switch(tile) {
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


    private int getOldFloorPiece() {

        int oldPiece = getPieceFromTile(oldFloorPiece);

        return oldPiece;
    }
}
