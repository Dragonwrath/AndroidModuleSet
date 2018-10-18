package com.joker.scan;
import com.joker.scan.core.OnScanResultListener;
import com.joker.scan.widget.FrameSurfaceView;
public interface QrScanManager {

 void onStart();

 void onStop();

 void onDestroy();

 void switchLight();



 interface Builder{

  Builder supportSensor(boolean shouldSupport);

  Builder surfaceView(FrameSurfaceView view);

  Builder addListener(OnScanResultListener listener);

  Builder requireSound();

  Builder requireVibrate();

  QrScanManager create();

 }
}
