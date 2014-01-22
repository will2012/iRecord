package com.birdv5.ir.utils.view;

import android.app.Activity;
import android.os.Handler;

import com.birdv5.ir.ui.base.BaseActivity;
import com.birdv5.ir.utils.common.Params;
import com.birdv5.ir.utils.system.ActivityUtility;

/**
 * @author : 桥下一粒砂
 * @email  : chenyoca@gmail.com
 * @date   : 2012-12-19
 * @desc   : 延时一定时间后，自动跳转到其它Activity
 */
public abstract class DelaySwitchActivity extends BaseActivity {

	private Runnable switchCallback;
	private Handler switchHandler;
	private int splashDelay = 3 * 1000;
	private Class<? extends Activity> nextActivity;
	private Params params;
	
	{
		switchCallback = new Runnable(){
			@Override
			public void run() {
				switchToNextView();
			}
		};
		switchHandler = new Handler();
	}

    /**
     * 设置延时时间
     * @param delayMillis 延时时间，单位：ms
     */
	final protected void setSplashDelay(int delayMillis){
		splashDelay = delayMillis;
	}

    /**
     * 跳转目标的Activity
     * @param target
     */
	final protected void setNextActivity(Class<? extends Activity> target){
		nextActivity = target;
	}

    /**
     * 跳转时Intent带的参数
     * @param params
     */
	final protected void setParams(Params params){
		this.params = params;
	}
	
	final protected void cancelSwitchAction(){
		switchHandler.removeCallbacks(switchCallback);
	}
	
	final protected void switchToNextView(){
        if(nextActivity!=null){
            ActivityUtility.switchTo(this, nextActivity, params);
        }
		cancelSwitchAction();
		finish();
	}

	@Override
	public final void onResume() {
		super.onResume();
		cancelSwitchAction();
		switchHandler.postDelayed(switchCallback, splashDelay);
		onResumeEx();
	}
	
	protected void onResumeEx(){}
}