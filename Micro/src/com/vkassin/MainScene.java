package com.vkassin;

import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import android.util.Log;
import android.view.MotionEvent;

public class MainScene extends CCLayer {

	private static final String TAG = "TR.MainScene";
	private int centerXpix;
	private int centerYpix;
	private CGSize size;

	private CCSprite man;
	private static final int man_y = 150;
	private boolean touchFlag = false;
	
	public static CCScene scene() {
		
		CCScene scene = CCScene.node();

		try{
			CCLayer layer = new MainScene();
			scene.addChild(layer);
			
			//scene.addChild(new ButtonsLayer());
		}
		catch(Exception e){
			
			Log.e(TAG, "MainScene problem", e);
		}

		return scene;
	}
	
	MainScene() throws Exception {
		
		//Common.mainActivity.setContentView(R.layout.mapscene);
		
		this.setIsTouchEnabled(true);
		this.setIsAccelerometerEnabled(false);

    	size = CCDirector.sharedDirector().winSize();
		centerXpix = (int)(size.width / 2);
		centerYpix = (int)(size.height / 2);
    	    
    	CCSprite bg = CCSprite.sprite("bg.png");
    	this.addChild(bg);
    	bg.setPosition(CGPoint.ccp(this.centerXpix, this.centerYpix));
    	
    	man = CCSprite.sprite("man.png");
    	this.addChild(man);
    	man.setPosition(CGPoint.ccp(this.centerXpix, man_y));
	}
	
	public boolean ccTouchesBegan(MotionEvent event) {
		 
		Log.i(TAG, "ccBegin");
		
		touchFlag = true;

		if(event.getX() > centerXpix)
			manGoRight();
		else
			manGoLeft();
		
		return CCTouchDispatcher.kEventHandled;
	}
	
	public boolean ccTouchesEnded(MotionEvent event) {
    	
		//Log.i(TAG, "ccEnded");
		
		touchFlag = false;
		
	    manStop();
	   
		return CCTouchDispatcher.kEventHandled;
	}
	
	private void manGoRight() {
	
		man.runAction(CCMoveTo.action(5f, CGPoint.ccp(man.getPosition().x + size.width, man_y)));
	}

	private void manGoLeft() {
		
		man.runAction(CCMoveTo.action(5f, CGPoint.ccp(man.getPosition().x - size.width, man_y)));
	}
	
	private void manStop() {
	
		man.stopAllActions();
	}

}
