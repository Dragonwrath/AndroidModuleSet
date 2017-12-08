package com.joker.photoselector.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.joker.photoselector.R;
import com.joker.photoselector.bean.ImageBean;
import com.joker.photoselector.helper.OnLoadFinishedListener;
import com.joker.photoselector.listener.PhotoCaptureListener;

import java.util.ArrayList;

public class SelectorCursorAdapter extends RecyclerView.Adapter<SelectorCursorAdapter.ViewHolder> implements OnLoadFinishedListener{

  private Context context;
  private LayoutInflater mLayoutInflater;
  private final ArrayList<ImageBean> imageList;
  private String[] projections;
  private final ArrayList<ImageBean> selectedImages;
  private int maxLimit;
  private final ImageBean photoBean;
  private PhotoCaptureListener listener;

  public SelectorCursorAdapter(Context context,int limit){
    this.context=context;
    this.mLayoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    imageList=new ArrayList<>();
    selectedImages=new ArrayList<>();
    maxLimit=limit;
    photoBean = new ImageBean();
  }

  public void setProjections(String[] projections){
    this.projections=projections;
  }

  @Override
  public int getItemCount(){
    return imageList==null?0:imageList.size();
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
    return new ViewHolder(mLayoutInflater.inflate(R.layout.item_grid,parent,false));
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder,int position){
    if(position ==0) {
      holder.photo.setImageResource(R.drawable.default_photo);
      holder.itemView.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
          if(listener != null) listener.checkPhotoCapturePermission();
        }
      });
      return;
    }
    final ImageBean bean=imageList.get(position);
    Glide.with(context).load(bean.getUri()).into(holder.photo);
    holder.tag.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View v){
        if(v instanceof ImageView){
          if(!bean.isSelected()){
            if(inRange()) {
              bean.setSelected(!bean.isSelected());
              changeState(holder,bean);
            } else {
              Toast.makeText(context,"hahahhaha",Toast.LENGTH_SHORT).show();
            }
          }else{
            bean.setSelected(!bean.isSelected());
            changeState(holder,bean);
          }
        }
      }
    });
    changeState(holder,bean);
  }

  private void changeState(ViewHolder holder,ImageBean bean){
    if(bean.isSelected()){
      holder.tag.setImageResource(R.drawable.image_check_on);
      holder.frame.setVisibility(View.VISIBLE);
      if(!selectedImages.contains(bean)){
        selectedImages.add(bean);
      }
    }else{
      holder.tag.setImageResource(R.drawable.image_check_off);
      holder.frame.setVisibility(View.GONE);
      selectedImages.remove(bean);
    }
  }

  private boolean inRange(){
    return selectedImages.size()<maxLimit;
  }

  private void loadData(Cursor cursor,boolean reset){
    if(cursor!=null){
      ArrayList<ImageBean> cache=new ArrayList<>();
      try{
        int dataIndex=cursor.getColumnIndex(projections[0]);
        int idIndex=cursor.getColumnIndex(projections[1]);
        int sizeIndex = cursor.getColumnIndex(projections[2]);
        while(cursor.moveToNext()){
          if(cursor.getInt(sizeIndex)>1024){
            ImageBean bean=new ImageBean();
            String uri=cursor.getString(dataIndex);
            bean.setUri(uri);
            long id=cursor.getLong(idIndex);
            bean.setImageId(id);
            cache.add(bean);
          }
        }
      }finally{
        cursor.close();
        if(cache.size()>0){
          if(reset){
            imageList.add(photoBean);
            imageList.clear();
          }
          imageList.addAll(cache);
          notifyDataSetChanged();
        }
      }
    }
  }

  private ArrayList<ImageBean> getSelectedImages(){
    return selectedImages;
  }

  @Override
  public void onLoad(@Nullable Cursor cursor){
    loadData(cursor,true);
  }

  @Override
  public void onLoadMore(@Nullable Cursor cursor){
    loadData(cursor,false);
  }

  private boolean state;

  @Override
  public boolean getState(){
    return state;
  }

  @Override
  public void setState(boolean shouldLoadMore){
    state=shouldLoadMore;
  }

  static class ViewHolder extends RecyclerView.ViewHolder{
    private ImageView photo;
    private View frame;
    private ImageView tag;

    ViewHolder(View itemView){
      super(itemView);
      photo=(ImageView)itemView.findViewById(R.id.iv_photo);
      frame=itemView.findViewById(R.id.v_photo_frame);
      tag=(ImageView)itemView.findViewById(R.id.iv_photo_tag);
    }
  }

  public void setCaptureListener(PhotoCaptureListener listener){
    this.listener = listener;
  }
}
