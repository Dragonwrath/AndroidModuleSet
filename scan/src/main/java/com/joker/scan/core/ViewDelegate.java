package com.joker.scan.core;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.SurfaceHolder;

public interface ViewDelegate{

 Context getViewContext();

 @NonNull SurfaceHolder getSurfaceHolder();

 void onPreviewFrameChanged(Rect rect);
}
