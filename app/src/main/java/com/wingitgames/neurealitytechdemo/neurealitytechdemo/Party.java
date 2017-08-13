/**
 * Created by blittle on 8/6/2017.
 */

package com.wingitgames.neurealitytechdemo.neurealitytechdemo;

public class Party {

    private static final int PARTY_X = 0;
    private static final int PARTY_Y = 1;
    private static final int PARTY_LEVEL = 2;
    private static final int PARTY_SPEED = 3;

    public static int X;
    public static int Y;
    public static int Z;
    public static int speed;
    public static float locationX;
    public static float locationY;
    public static int mapX;
    public static int mapY;

    public Party() {

        X = 0;
        Y = 0;
        speed = 1;
        locationX = 0.0f;
        locationY = 0.0f;
    }

    public Party(int partyX, int partyY, int partyZ, int partySpeed, float partyLocationX, float partyLocationY) {

        X = partyX;
        Y = partyY;
        speed = partySpeed;
        locationX = partyLocationX;
        locationY = partyLocationY;
    }
}
