package com.ash.hidenosimicon;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class HookClass implements IXposedHookLoadPackage {

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if (!lpparam.packageName.equals("com.android.systemui"))
		{
			//XposedBridge.log("sysui not found");
			return;
		}
		
		XC_MethodHook hook = new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
					try {
						ImageView[] view = (ImageView[]) XposedHelpers.getObjectField(param.thisObject, "mNoSimSlot");
						TextView[] tview = (TextView[]) XposedHelpers.getObjectField(param.thisObject, "mMobileSlot");
						
						try {view[0].setVisibility(View.GONE);}catch(Throwable t){}
						try {view[1].setVisibility(View.GONE);}catch(Throwable t){}
						try {view[2].setVisibility(View.GONE);}catch(Throwable t){}
						try {view[3].setVisibility(View.GONE);}catch(Throwable t){}
						
						try {tview[0].setVisibility(View.GONE);}catch(Throwable t){}
						try {tview[1].setVisibility(View.GONE);}catch(Throwable t){}
						try {tview[2].setVisibility(View.GONE);}catch(Throwable t){}
						try {tview[3].setVisibility(View.GONE);}catch(Throwable t){}
						
					} catch (NoSuchFieldError e) {
						
						//XposedBridge.log("image view not found");
					}
				
			}
		};

		try {
			Class<?> MSimSignalClusterView = XposedHelpers.findClass("com.android.systemui.statusbar.MSimSignalClusterView",
					lpparam.classLoader);
			findAndHookMethod(MSimSignalClusterView, "onAttachedToWindow", hook);
			findAndHookMethod(MSimSignalClusterView, "applySubscription", int.class, hook);
		} catch (Throwable t) {
			//XposedBridge.log("no class MSimSignalClusterView");
		}
	}
}
