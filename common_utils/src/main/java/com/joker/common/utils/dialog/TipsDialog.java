package com.joker.common.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.TextView;

import com.joker.common.utils.R;


public class TipsDialog extends Dialog{


  public TipsDialog(Context context,int themeResId){
    super(context,themeResId);
  }

  public static class Builder{
    private TipsDialog mDialog;
    private @StringRes int title;
    private @DrawableRes int drawable;
    private @StringRes int message;
    private @StringRes int posTxt;
    private View.OnClickListener posAct;
    private @StringRes int negTxt;
    private View.OnClickListener negAct;

    public Builder(Context context){
      this(context,R.style.BaseDialog);
    }

    public Builder(Context context,@StyleRes int resId){
      mDialog=new TipsDialog(context,resId);
      mDialog.setContentView(R.layout.tips_dialog);
    }

    public Builder setTitle(@StringRes int resId){
      title=resId;
      return this;
    }

    public Builder setTitleDrawable(@DrawableRes int resId){
      drawable=resId;
      return this;
    }

    public Builder setMessage(@StringRes int resId){
      message=resId;
      return this;
    }

    public Builder setPositiveBtnTxt(@StringRes int resId){
      posTxt=resId;
      return this;
    }

    public Builder setPositiveButtonListener(View.OnClickListener action){
      posAct=action;
      return this;
    }


    public Builder setNegativeBtnTxt(@StringRes int resId){
      negTxt=resId;
      return this;
    }

    public Builder setNegativeButtonListener(View.OnClickListener action){
      negAct=action;
      return this;
    }

    public TipsDialog create(){
      TextView title=(TextView)mDialog.findViewById(R.id.title);
      title.setText(this.title);
      if(drawable>0)
        title.setCompoundDrawables(mDialog.getContext().getResources().getDrawable(drawable),null,null,null);
      TextView message=(TextView)mDialog.findViewById(R.id.message);
      message.setText(this.message);
      if(negTxt>0){
        TextView negBtn=(TextView)mDialog.findViewById(R.id.cancel);
        negBtn.setText(negTxt);
        negBtn.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View v){
            mDialog.dismiss();
            if(negAct!=null) negAct.onClick(v);
          }
        });
        negBtn.setVisibility(View.VISIBLE);
      }
      TextView posBtn=(TextView)mDialog.findViewById(R.id.confirm);
      posBtn.setText(posTxt);
      posBtn.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
          mDialog.dismiss();
          if(posAct!=null) posAct.onClick(v);
        }
      });

      return mDialog;
    }

    public TipsDialog show(){
      create();
      mDialog.show();
      return mDialog;
    }

  }
}
