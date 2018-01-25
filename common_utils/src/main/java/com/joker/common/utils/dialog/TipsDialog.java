package com.joker.common.utils.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.TextView;

import com.joker.common.utils.R;

public class TipsDialog extends AppCompatDialog implements View.OnClickListener{

  private DialogInterface.OnClickListener mPosListener;
  private DialogInterface.OnClickListener mNegListener;

  public TipsDialog(@NonNull Context context){
    this(context,R.style.BaseDialog,R.layout.dialog_tips);
  }

  public TipsDialog(@NonNull Context context,@LayoutRes int resId){
    this(context,R.style.BaseDialog,resId);
  }

  public TipsDialog(@NonNull Context context,@StyleRes int themeResId,@LayoutRes int resId){
    super(context,themeResId);
    setContentView(resId);
    setCancelable(false);
    setCanceledOnTouchOutside(false);
  }

  public void setPositiveButton(
      @StringRes int resId,@Nullable DialogInterface.OnClickListener action,boolean isWaring){
    TextView confirm=(TextView)findViewById(R.id.confirm);
    if(confirm!=null){
      confirm.setText(resId);
      Context context=confirm.getContext();
      int color;
      if(isWaring){
        color=ContextCompat.getColor(context,R.color.colorAccent);
      }else{
        color=ContextCompat.getColor(context,R.color.colorPrimary);
      }
      confirm.setTextColor(color);
      confirm.setOnClickListener(this);
      mPosListener=action;
    }
  }

  public void setNegativeButton(
      @StringRes int resId,@Nullable DialogInterface.OnClickListener action){
    TextView cancel=(TextView)findViewById(R.id.cancel);
    if(cancel!=null){
      cancel.setText(resId);
      cancel.setVisibility(View.VISIBLE);
      cancel.setOnClickListener(this);
      mNegListener=action;
    }
  }

  @Override
  public void setTitle(CharSequence title){
    TextView tvTitle=(TextView)findViewById(R.id.title);
    if(tvTitle!=null)
      tvTitle.setText(title);
  }

  @Override
  public void setTitle(@StringRes int resId){
    TextView title=(TextView)findViewById(R.id.title);
    if(title!=null)
      title.setText(resId);
  }

  public void setMessage(@StringRes int resId){
    TextView message=(TextView)findViewById(R.id.message);
    if(message!=null){
      message.setText(resId);
    }
  }

  public void setMessage(CharSequence message){
    TextView tvMessage=(TextView)findViewById(R.id.message);
    if(tvMessage!=null){
      tvMessage.setText(message);
    }
  }

  @Override
  public void onClick(View v){
    int id=v.getId();
    if(id==R.id.confirm){
      if(mPosListener!=null)
        mPosListener.onClick(this,DialogInterface.BUTTON_POSITIVE);
    }else if(id==R.id.cancel){
      if(mNegListener!=null)
        mNegListener.onClick(this,DialogInterface.BUTTON_NEGATIVE);
    }
    dismiss();
  }
}
