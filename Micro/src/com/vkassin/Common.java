package com.vkassin;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCLabelAtlas;
import org.cocos2d.nodes.CCSprite;

import android.content.Context;

// https://github.com/ZhouWeikuan/cocos2d#readme

public class Common {

	public static final float SUPPOSED_WIN_WIDTH  = 800; 
	public static final float SUPPOSED_WIN_HEIGHT = 600;

	public static final float PARTICLES_CNT = 10;
	
	public static final float CAPSULE_POSITION_X = 400;
	public static final float CAPSULE_POSITION_Y = 550;

	public static final int RANGE_Y = 100;
	public static final int RANGE_X = 150;
	public static final int RANGE_SPEED = 170;

	public static final int MAN_Y = 120;
	public static final int CATCH_Y = 200;
	
	public static Context cont;
	public static CCLayer layer;
	
	public static CCSprite man;

	public static CCLabelAtlas labelTime;
	public static CCLabelAtlas labelScore;
	
	public static int level = 1;

	public static int score = 0;
	public static int cnt = 0;
	public static int time = 0;
}
