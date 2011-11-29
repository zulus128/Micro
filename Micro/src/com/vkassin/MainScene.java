package com.vkassin;

import java.util.ArrayList;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
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
	private CCAction run_man;
	private static final int man_y = 120;
	private boolean touchFlag = false;
	
	private CCLabel lbl;
	private CCMenu menu;

	private CCSprite caps_close;
	private CCSprite caps_open;
	private ArrayList<CCSprite> ptcls = new ArrayList<CCSprite>();

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
    	    
    	CCSprite bg = CCSprite.sprite("creon_game_background.png");
    	this.addChild(bg);
    	bg.setPosition(CGPoint.ccp(this.centerXpix, this.centerYpix));
    	
    	man = CCSprite.sprite("walk0001.png");
    	this.addChild(man);
    	man.setPosition(CGPoint.ccp(this.centerXpix, man_y));
    	
    	CCAnimation anim = CCAnimation.animation("man_run", 0.07f);
    	for(int i = 1; i < 17; i++)
    		anim.addFrame(String.format("walk%04d.png",i));
    	
    	run_man = CCRepeatForever.action(CCAnimate.action(anim));
    	
//        lbl = CCLabel.makeLabel("Hello World!", "DroidSans", 24);
//        this.addChild(lbl);
//        lbl.setPosition(CGPoint.ccp(345, 240));
        
        CCMenuItem start = CCMenuItemImage.item("C_g_start_off.png", "C_g_start_on.png",
                this, "startTouched"); 
       // Create array of CCMenuItem object to add to CCMenu
       CCMenuItem[] items = { start };
       // Add menu items to menu
       menu = CCMenu.menu(items);
       // Align items with 150px adding
//       menu.alignItemsVertically(150);
       // Add menu to the scene
       this.addChild(menu, 100);
       menu.setPosition(CGPoint.ccp(centerXpix, centerYpix / 2 * 3 ));
       
       for(int i = 0; i < Common.PARTICLES_CNT; i++) {
    	   
    	   CCSprite spr = CCSprite.sprite("C_g_chastica.png");
    	   ptcls.add(spr);
    	   spr.setPosition(CGPoint.ccp(Common.CAPSULE_POSITION_X, Common.CAPSULE_POSITION_Y));
    	   spr.setVisible(false);
    	   this.addChild(spr);
       }
       
   	caps_close = CCSprite.sprite("c_g_kapsula_close.png");
	this.addChild(caps_close);
	caps_close.setPosition(CGPoint.ccp(Common.CAPSULE_POSITION_X, Common.CAPSULE_POSITION_Y));

   	caps_open = CCSprite.sprite("c_g_kapsula_open.png");
	this.addChild(caps_open);
	caps_open.setPosition(CGPoint.ccp(Common.CAPSULE_POSITION_X, Common.CAPSULE_POSITION_Y));
	caps_open.setVisible(false);

	}
	
	/**This method is called when the start menu item is touched**/
	public void startTouched(Object sender) {
		
		if(!menu.getVisible())
			return;
		
		Log.i(TAG, "startTouched");
		
		menu.setVisible(false);
		caps_close.setVisible(false);
		caps_open.setVisible(true);
		

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
	
		man.flipX_ = false;
		man.runAction(CCMoveTo.action(5f, CGPoint.ccp(man.getPosition().x + size.width, man_y)));
		man.runAction(run_man);
	}

	private void manGoLeft() {
		
		man.flipX_ = true;
		man.runAction(CCMoveTo.action(5f, CGPoint.ccp(man.getPosition().x - size.width, man_y)));
		man.runAction(run_man);
	}
	
	private void manStop() {
	
		man.stopAllActions();
	}

}
