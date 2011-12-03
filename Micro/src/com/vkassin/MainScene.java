package com.vkassin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.cocos2d.actions.CCTimer;
import org.cocos2d.actions.UpdateCallback;
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
import org.cocos2d.nodes.CCLabelAtlas;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.particlesystem.CCParticleFireworks;
import org.cocos2d.particlesystem.CCParticleSystem;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.*;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.CountDownTimer;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainScene extends CCLayer implements UpdateCallback{

//	private RefreshHandler mRedrawHandler = new RefreshHandler();  
	  	  
	private static final String TAG = "TR.MainScene";
	private int centerXpix;
	private int centerYpix;
	private CGSize size;

//	private CCSprite man;
	private CCAction run_man;
//	private static final int man_y = 120;
	private boolean touchFlag = false;
	
	private CCLabel lbl;
	private CCMenu menu;

	private CCSprite caps_close;
	private CCSprite caps_open;
	
//	private CCSprite fireSprite;
//	private CCAction fireAction;
	
	private ArrayList<Particle> ptcls = new ArrayList<Particle>();

	private CCSprite bg, bg1;
	
//	private CCTimer timer;
	
//	  class RefreshHandler extends Handler {  
//		    @Override  
//		    public void handleMessage(Message msg) {  
//		      MainScene.this.updateUI();  
//		    }  
//		  
//		    public void sleep(long delayMillis) {  
//		      this.removeMessages(0);  
//		      sendMessageDelayed(obtainMessage(0), delayMillis);  
//		    }  
//		  };  
//		  
//	private void updateUI() {  
//	
////		Log.i(TAG, "Tick");
//		
//		Common.time--;
////		Common.labelTime.setString(String.format("00.%02d", Common.time));
//
//		this.removeChild(Common.labelTime, true);
//		
//		Common.labelTime = CCLabelAtlas.label(String.format("00:%02d", Common.time), "creon_game_numbers.png", 26, 34, '0');
//		Common.labelTime.setPosition(CGPoint.ccp(30, 540));
//		this.addChild(Common.labelTime);
//
//		if(Common.time <= 0) {
//			
////	        CCLabel lbl = CCLabel.makeLabel("Вы набрали", "DroidSans", 36);
////	        lbl.setPosition(CGPoint.ccp(345, 240));
////	        Common.layer.addChild(lbl);
//
//		}
//		else
//			mRedrawHandler.sleep(1000);  
//	}  
	
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

		Common.layer = this;
		
    	size = CCDirector.sharedDirector().winSize();
		centerXpix = (int)(size.width / 2);
		centerYpix = (int)(size.height / 2);
    	    
    	bg = CCSprite.sprite("creon_game_background.png");
    	this.addChild(bg);
    	bg.setPosition(CGPoint.ccp(this.centerXpix, this.centerYpix));

    	bg1 = CCSprite.sprite("creon_game_background1.png");
    	this.addChild(bg1);
    	bg1.setPosition(CGPoint.ccp(this.centerXpix, this.centerYpix));
    	bg1.setVisible(false);
    	
    	
//    	fireSprite = CCSprite.sprite("fireWorks0001.png");
//    	this.addChild(fireSprite, 75);
//    	fireSprite.setPosition(CGPoint.ccp(this.centerXpix, this.centerYpix));
//    	fireSprite.setVisible(false);
    	
//    	CCAnimation fanim0 = CCAnimation.animation("fire_anim0", 0.08f);
//    	for(int i = 1; i < 9; i++)
//    		fanim0.addFrame(String.format("fireWorks%04d.png",i));
//
//    	CCAnimation fanim1 = CCAnimation.animation("fire_anim0", 0.08f);
//    	for(int i = 9; i < 18; i++)
//    		fanim1.addFrame(String.format("fireWorks%04d.png",i));
//    	
//    	fireAction = CCRepeatForever.action(CCAnimate.action(fanim0));

    	Common.man = CCSprite.sprite("walk0001.png");
    	this.addChild(Common.man, 55);
    	Common.man.setPosition(CGPoint.ccp(this.centerXpix, Common.MAN_Y));
    	
    	CCAnimation anim = CCAnimation.animation("man_run", 0.08f);
    	for(int i = 1; i < 17; i++)
    		anim.addFrame(String.format("walk%04d.png",i));
    	
    	run_man = CCRepeatForever.action(CCAnimate.action(anim));
    	
//        lbl = CCLabel.makeLabel("Вы набрали", "DroidSans", 36);
//        this.addChild(lbl);
//        lbl.setPosition(CGPoint.ccp(345, 240));
        
        CCMenuItem start = CCMenuItemImage.item("C_g_start_off.png", "C_g_start_on.png",
                this, "startTouched"); 
        CCMenuItem exit = CCMenuItemImage.item("C_g_exit_off.png", "C_g_exit_on.png",
                this, "exitTouched"); 
       // Create array of CCMenuItem object to add to CCMenu
       CCMenuItem[] items = { start, exit };
       // Add menu items to menu
       menu = CCMenu.menu(items);
       // Align items with 150px adding
//       menu.alignItemsVertically(150);
       // Add menu to the scene
       this.addChild(menu, 100);
       menu.setPosition(CGPoint.ccp(centerXpix, centerYpix / 2 * 3 - 40 ));
       exit.setPosition(CGPoint.ccp(370, 50));
       
       
       for(int i = 0; i < Common.PARTICLES_CNT; i++) {

    	   Particle spr = new Particle(this);
    	   ptcls.add(spr);
       }
       
   	caps_close = CCSprite.sprite("c_g_kapsula_close.png");
	this.addChild(caps_close);
	caps_close.setPosition(CGPoint.ccp(Common.CAPSULE_POSITION_X, Common.CAPSULE_POSITION_Y));

   	caps_open = CCSprite.sprite("c_g_kapsula_open.png");
	this.addChild(caps_open);
	caps_open.setPosition(CGPoint.ccp(Common.CAPSULE_POSITION_X, Common.CAPSULE_POSITION_Y));
	caps_open.setVisible(false);

	Common.labelTime = CCLabelAtlas.label("00:50", "creon_game_numbers.png", 26, 34, '0');
	Common.labelTime.setPosition(CGPoint.ccp(30, 540));
	this.addChild(Common.labelTime);

	Common.labelScore = CCLabelAtlas.label("0000", "creon_game_numbers.png", 26, 34, '0');
	Common.labelScore.setPosition(CGPoint.ccp(670, 540));
	this.addChild(Common.labelScore);
		
	CCLabel lbl1 = CCLabel.makeLabel("Демо!!!", "DroidSans", 24);
	lbl1.setColor(ccColor3B.ccRED);
	lbl1.setPosition(CGPoint.ccp(700, 80));
	Common.layer.addChild(lbl1,500);

	  
	}

	public void update(float arg0) {

		Log.i(TAG, "update");
//		timer.update(1f);
	}
	public void rtick(float dt){

//		Log.i(TAG, "Tick");
		
		Common.time--;
		Common.labelTime.setString(String.format("00:%02d", Common.time));

		if(Common.time <= 0) {
			
			
			this.unscheduleAllSelectors();
			caps_close.setVisible(true);
			caps_open.setVisible(false);

			   Iterator<Particle> e = ptcls.iterator();
			    while (e.hasNext()) {

			    	Particle s = (Particle) e.next();
			    	s.reset();
			    }

			
			//if(Common.level == 1) {
			if(Common.score > Common.cnt / 2) {
//			if(true) {

				lbl = CCLabel.makeLabel("Поздравляем! Вы прошли "+Common.level+"-й этап!", "DroidSans", 36);
				lbl.setColor(ccColor3B.ccBLUE);
		        lbl.setPosition(CGPoint.ccp(400, 340));
		        this.addChild(lbl);
		        
//		        fireSprite.setVisible(true);
//		        fireSprite.runAction(fireAction);

		        
		        if(Common.level == 1)
		        	this.schedule("goSecond", 5.0f);
		        else
		        	this.schedule("goVideo", 5.0f);

			}
			else {
	        
				lbl = CCLabel.makeLabel(String.format("Вы набрали %04d баллов!", Common.score), "DroidSans", 36);
				lbl.setColor(ccColor3B.ccBLUE);
				lbl.setPosition(CGPoint.ccp(400, 340));
		        this.addChild(lbl);
		        this.schedule("goVideo", 5.0f);
			}
			//}

		}

//        CCLabel lbl = CCLabel.makeLabel("Вы набрали", "DroidSans", 36);
//        lbl.setPosition(CGPoint.ccp(345, 240));
//        Common.layer.addChild(lbl);

	}

	public void goVideo(float dt){

		this.unscheduleAllSelectors();
		this.removeChild(lbl, false);

//		fireSprite.stopAllActions();
//		fireSprite.setVisible(false);
		
		menu.setVisible(true);
		Log.i(TAG, "goVideo !!!");
				
		SoundEngine.sharedEngine().playEffect(Common.cont, R.raw.triangel02);

//		File = new File();
//		listFiles();
		
		File s = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);

//		CCLabel lbl = CCLabel.makeLabel(s.toString(), "DroidSans", 24);
//		lbl.setPosition(CGPoint.ccp(345, 240));
//		Common.layer.addChild(lbl,600);
//		
//		Log.i(TAG, "root dir = " + s.toString());
		
//		String movieurl = "/sdcard/Data/krion.mp4";
//		String movieurl = s.toString() + "/krion.mp4";
		String movieurl = "/data/local/krion.mp4";
//		String movieurl = "android.resource://com.vkassin/raw/krion.mp4";
		Intent intentToPlayVideo = new Intent(Intent.ACTION_VIEW);
		intentToPlayVideo.setDataAndType(Uri.parse(movieurl), "video/*");
		Common.cont.startActivity(intentToPlayVideo);
	}

	public void goSecond(float dt){
		
		this.unscheduleAllSelectors();
		this.removeChild(lbl, false);
		
//		fireSprite.stopAllActions();
//		fireSprite.setVisible(false);

		caps_close.setVisible(false);
		caps_open.setVisible(true);
		
		   Iterator<Particle> e = ptcls.iterator();
		    while (e.hasNext()) {

		    	Particle s = (Particle) e.next();
		    	s.start();
		    }
		    
		Common.time = 50;
		Common.cnt = 0;
		Common.score = 0;
		Common.level = 2;
		
		Common.labelScore.setString(String.format("%04d", Common.score));

    	
		bg.setVisible(false);
    	bg1.setVisible(true);

		this.schedule("rtick", 1.0f);

	}

//	public void rtick(Object sender){
//	    this.rtick(0.0f);
//	}
//	public void rtick() {
//		
//		Log.i(TAG, "Tick");
//	}

	public void exitTouched(Object sender) {
	
		SoundEngine.sharedEngine().playEffect(Common.cont, R.raw.triangel02);

		System.exit(0);
		
	}
//	public void onEnter() {
//		super.onEnter();
//		CCParticleSystem  emitter;		
//		emitter = CCParticleFireworks.node();
//        addChild(emitter, 10, 2);
////        emitter.setTexture(CCTextureCache.sharedTextureCache().addImage("stars.png"));
////        emitter.setTexture(CCTextureCache.sharedTextureCache().addImage("C_g_chastica.png"));
//        emitter.setPosition(200, 70);
//		}
	
	/**This method is called when the start menu item is touched**/
	public void startTouched(Object sender) {
		
		SoundEngine.sharedEngine().playEffect(Common.cont, R.raw.triangel02);
		
		if(!menu.getVisible())
			return;
		
		Log.i(TAG, "startTouched");
		
		menu.setVisible(false);
		caps_close.setVisible(false);
		caps_open.setVisible(true);
		
		   Iterator<Particle> e = ptcls.iterator();
		    while (e.hasNext()) {

		    	Particle s = (Particle) e.next();
		    	s.start();
		    }
		    
		Common.time = 50;
		Common.cnt = 0;
		Common.score = 0;
		Common.level = 1;
		
		Common.labelScore.setString(String.format("%04d", Common.score));

		bg1.setVisible(false);
    	bg.setVisible(true);

//		updateUI();

		this.schedule("rtick", 1.0f);

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
	
		manStop();
		Common.man.flipX_ = false;
		Common.man.runAction(CCMoveTo.action(2f, CGPoint.ccp(/*Common.man.getPosition().x + */size.width, Common.MAN_Y)));
		Common.man.runAction(run_man);
	}

	private void manGoLeft() {
		
		manStop();
		Common.man.flipX_ = true;
		Common.man.runAction(CCMoveTo.action(2f, CGPoint.ccp(0/*Common.man.getPosition().x - size.width*/, Common.MAN_Y)));
		Common.man.runAction(run_man);
	}
	
	private void manStop() {
	
		Common.man.stopAllActions();
	}


}
