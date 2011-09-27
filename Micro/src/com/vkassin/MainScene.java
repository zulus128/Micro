package com.vkassin;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import android.util.Log;

public class MainScene extends CCLayer {

	private static final String TAG = "TR.MainScene";
	private int centerXpix;
	private int centerYpix;

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

    	CGSize size = CCDirector.sharedDirector().winSize();
		centerXpix = (int)(size.width / 2);
		centerYpix = (int)(size.height / 2);
    	    
    	CCSprite bg = CCSprite.sprite("bg.png");
    	this.addChild(bg);
    	bg.setPosition(CGPoint.ccp(this.centerXpix, this.centerYpix));
    	
	}
	

}
