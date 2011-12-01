package com.vkassin;

//import java.util.Date;
import java.util.Random;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

import android.util.Log;

public class Particle {

	private static final String TAG = "TR.Particle";

	   CCSprite spr;
	   CCLayer lay;
	   Random rand = new Random();
	   int speed = 10;
	   int goside;
	   
	   public Particle(CCLayer l) {
		
		   lay = l;
		   
		   spr = CCSprite.sprite("C_g_chastica.png");
    	   spr.setPosition(CGPoint.ccp(Common.CAPSULE_POSITION_X, -1 /*Common.CAPSULE_POSITION_Y*/));
    	   spr.setVisible(false);
    	   lay.addChild(spr, 50);

    	   speed = Common.RANGE_SPEED / 10 + rand.nextInt(Common.RANGE_SPEED);
    	   
    	   goside = 1 - rand.nextInt(3);
	   }
	   
	   public void fun0() {
		  
		   spr.setVisible(true);

	   }

	   public void fun00() {
			  
		   spr.setVisible(false);

	   }
	   
	   public void start() {
		
    	   int rY = Common.RANGE_Y / 10 + rand.nextInt(Common.RANGE_Y);
    	   int add = Common.RANGE_X / 4 * goside;
    	   int rX = add + Common.RANGE_X / 2 - rand.nextInt(Common.RANGE_X);

    	   float x = spr.getPosition().x;
    	   float y = spr.getPosition().y;

    	   CGPoint mp = Common.man.getPosition();
    	   if((Math.abs(y - Common.CATCH_Y) < 3) && (Math.abs(x - mp.x) < 50)) {
    		   
    			SoundEngine.sharedEngine().playEffect(Common.cont, R.raw.finger_cymbal01);
    			Common.score++;
    			Common.labelScore.setString(String.format("%04d", Common.score));
    			y = -1;
    	   }
    	   
    	   int d1 = 0;
    	   
    	   if(y < 0) {
    		   
        	   spr.setPosition(CGPoint.ccp(Common.CAPSULE_POSITION_X, Common.CAPSULE_POSITION_Y));
    		   x = Common.CAPSULE_POSITION_X;
    		   y = Common.CAPSULE_POSITION_Y;
    		   d1 = rand.nextInt(20);
    	   }
//    	   else {
//    		   
//    		   d1 = CCDelayTime.action(0);
//    	   }
    		   
    	   CCDelayTime dd1 = CCDelayTime.action(d1);
    	   
    	   float x1 = x + rX;
    	   float y1 = y - rY;
    	   
    	   if((y > Common.CATCH_Y) && (y1 < Common.CATCH_Y)) {
    		   
    		   x1 = x;
    		   y1 = Common.CATCH_Y;
    	   }

    	   if(y1 < Common.CATCH_Y) {
    		   
    		   x1 = x;
    	   }
    	   
    	   float dist = CGPoint.ccpDistance(CGPoint.ccp(x, y), CGPoint.ccp(x1, y1));
    	   float time = dist / speed;
		   
//    	   Log.i(TAG, "d = "+d1+"x = "+x+" y = "+y+" x1 = "+x1+" y1 = "+y1+" time = "+time+ " dist = "+dist);
		   CCMoveTo m1 = CCMoveTo.action(time, CGPoint.ccp(x1, y1));
		   CCCallFunc fun1 = CCCallFunc.action(this, "start");
		   CCCallFunc ffun0 = CCCallFunc.action(this, "fun0");
		   CCCallFunc ffun00 = CCCallFunc.action(this, "fun00");
		   CCSequence rep = CCSequence.actions(dd1, ffun0, m1, fun1);
//		   spr.setVisible(true);
		   spr.runAction(rep);

	   }
	   
}
