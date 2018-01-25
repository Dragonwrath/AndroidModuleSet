package com.joker.common.utils.widgets;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class LinearGradientTextView extends AppCompatTextView{
  private int mViewWidth;
  private final Paint mPaint;
  private LinearGradient mLinearGradient;
  private final Matrix mGradientMatrix;
  private int mTranslate;
  private final int[] COLORS=new int[]{Color.DKGRAY,Color.LTGRAY,Color.DKGRAY};
  private boolean isStarted;

  public LinearGradientTextView(Context context){
    this(context,null,0);
  }

  public LinearGradientTextView(Context context,AttributeSet attrs){
    this(context,attrs,0);
  }

  public LinearGradientTextView(Context context,AttributeSet attrs,int defStyleAttr){
    super(context,attrs,defStyleAttr);
    mPaint=getPaint();
    mGradientMatrix=new Matrix();
    mLinearGradient=new LinearGradient(0,0,mViewWidth,0,COLORS,null,Shader.TileMode.CLAMP);
    mPaint.setShader(mLinearGradient);
  }

  @Override
  protected void onSizeChanged(int w,int h,int oldw,int oldh){
    super.onSizeChanged(w,h,oldw,oldh);
    if(mViewWidth==0||mViewWidth!=getMeasuredWidth()){
      setLinearGradient();
    }
  }

  private void setLinearGradient(){
    mViewWidth=getMeasuredWidth();
    mLinearGradient=new LinearGradient(0,0,mViewWidth,0,COLORS,null,Shader.TileMode.CLAMP);
    mPaint.setShader(mLinearGradient);
  }

  @Override
  protected void onDraw(Canvas canvas){
    super.onDraw(canvas);
    if(mGradientMatrix!=null&&isStarted){
      mTranslate+=mViewWidth/8;
      mGradientMatrix.setTranslate(mTranslate,0);
      mLinearGradient.setLocalMatrix(mGradientMatrix);
      postInvalidateDelayed(80);
      if(mTranslate>=mViewWidth){
        mTranslate=-mViewWidth;
      }
    }
  }

  public void start(){
    isStarted=true;
    postInvalidate();
  }

  public void stop(){
    isStarted=false;
    postInvalidate();
  }

  public boolean isStarted(){
    return isStarted;
  }
}
