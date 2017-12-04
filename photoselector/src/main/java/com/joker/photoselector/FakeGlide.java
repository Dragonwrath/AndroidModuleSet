package com.joker.photoselector;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;

@RequiresApi(26)
public class FakeGlide{
  public String url;
  public int width,height;
  public Context context;
  public void fakeLoad() {
    FutureTarget<Bitmap> futureTarget =
        Glide.with(context)
            .asBitmap()
            .load(url)
            .submit(width, height);

    Bitmap bitmap = futureTarget.get();

// Do something with the Bitmap and then when you're done with it:
    Glide.with(context).clear(futureTarget);
  }
}
