package com.joker.scan.core;
import android.support.annotation.NonNull;

import com.joker.scan.exceptions.CameraOpenFailureException;

public interface CameraManager{

 void bindViewDelegate(@NonNull ViewDelegate view);

 void bindDecodeManager(DecodeManager manager);

 void openCamera() throws CameraOpenFailureException;

 void closeCamera();

 void switchFlashLight();

}
