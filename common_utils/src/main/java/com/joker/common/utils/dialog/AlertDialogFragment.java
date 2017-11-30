package com.joker.common.utils.dialog;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.joker.common.utils.R;
import com.joker.common.utils.ResourcesUtils;

import java.util.Stack;


public class AlertDialogFragment extends DialogFragment implements View.OnClickListener{

  private TextView title;
  private TextView content;
  private Button positive;
  private Button negative;
  private Stack<Dialog.OnClickListener> posListeners;
  private Stack<Dialog.OnClickListener> negListeners;

  public AlertDialogFragment(){
    // Required empty public constructor
  }

  public static AlertDialogFragment newInstance(Bundle bundle){
    AlertDialogFragment fragment=new AlertDialogFragment();
    if(bundle!=null)
      fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater,ViewGroup container,
                           Bundle savedInstanceState){
    View view=inflater.inflate(R.layout.dialog_alert,container,false);
    positive=(Button)view.findViewById(R.id.btn_dialog_pos);
    negative=(Button)view.findViewById(R.id.btn_dialog_neg);
    title=(TextView)view.findViewById(R.id.tv_dialog_title);
    content=(TextView)view.findViewById(R.id.tv_dialog_content);
    return view;
  }

  @Override
  public void onViewCreated(View view,@Nullable Bundle savedInstanceState){
    super.onViewCreated(view,savedInstanceState);
    posListeners=new Stack<>();
    posListeners.push(new DialogInterface.OnClickListener(){
      @Override
      public void onClick(DialogInterface dialog,int which){
        dialog.dismiss();
      }
    });
    negListeners=new Stack<>();
    negListeners.push(new DialogInterface.OnClickListener(){
      @Override
      public void onClick(DialogInterface dialog,int which){
        dialog.dismiss();
      }
    });

    positive.setOnClickListener(this);
    negative.setOnClickListener(this);

    Bundle bundle=getArguments();
    String title, content, confirm, cancel;
    if(bundle!=null){
      title=bundle.getString("title",ResourcesUtils.getString(getContext(),R.string.title));
      content=bundle.getString("content",ResourcesUtils.getString(getContext(),R.string.content));
      confirm=bundle.getString("confirm",ResourcesUtils.getString(getContext(),R.string.confirm));
      cancel=bundle.getString("cancel");
      this.title.setText(title);
      this.content.setText(content);
      positive.setText(confirm);
      if(!TextUtils.isEmpty(cancel)){
        makeNegativeButtonVisible();
        negative.setText(cancel);
      }
    }
  }


  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState){
    //set the default style
    setStyle(STYLE_NO_TITLE,R.style.BaseDialog_DisableDim);
    return super.onCreateDialog(savedInstanceState);
  }

  public void setCancelable(boolean cancelable){
    Dialog dialog=getDialog();
    dialog.setCancelable(false);
    dialog.setCanceledOnTouchOutside(false);
  }

  public void addPositiveClickListener(Dialog.OnClickListener listener){
    if(listener!=null){
      posListeners.push(listener);
    }
  }

  public void addOnNegativeClickListener(Dialog.OnClickListener listener){
    if(listener!=null&&posListeners!=null){
      makeNegativeButtonVisible();
      negListeners.push(listener);
    }
  }

  private void makeNegativeButtonVisible(){
    if(negative.getVisibility()!=View.VISIBLE){
      negative.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void onClick(View v){
    int id=v.getId();
    if(id==R.id.btn_dialog_pos){
      if(posListeners!=null&&posListeners.size()>=0){
        for(Dialog.OnClickListener listener : posListeners) {
          listener.onClick(getDialog(),Dialog.BUTTON_POSITIVE);
        }
      }
    }else if(id==R.id.btn_dialog_neg){
      for(Dialog.OnClickListener listener : negListeners) {
        listener.onClick(getDialog(),Dialog.BUTTON_NEGATIVE);
      }
    }
  }
}
