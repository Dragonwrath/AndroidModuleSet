package com.joker.photoselector.adapter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joker.photoselector.R;
import com.joker.photoselector.bean.ImageBean;

import java.util.ArrayList;


public class CustomCursorAdapter extends RecyclerView.Adapter<CustomCursorAdapter.ViewHolder>{

  private Context context;
  private LayoutInflater mLayoutInflater;
  private final ArrayList<ImageBean> imageList;
  private String[] projections;
  private final ArrayList<ImageBean> selectedImages;

  public CustomCursorAdapter(Context context){
    this.context=context;
    this.mLayoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    imageList=new ArrayList<>();
    selectedImages = new ArrayList<>();
  }

  public void setProjections(String[] projections){
    this.projections=projections;
  }

  @Override
  public int getItemCount(){
    return imageList == null?0:imageList.size();
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
    return new ViewHolder(mLayoutInflater.inflate(R.layout.item_grid,parent,false));
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder,int position){
    final ImageBean bean=imageList.get(position);
    String[] strings=bean.getUri().split("/");
    int length = strings.length;
    holder.path.setText(strings[length-2] +"--->"+ strings[length-1]);
    Glide.with(context).load(Uri.parse(bean.getUri()).getPath()).into(holder.photo);


    holder.photo.setTag(position);
    holder.tag.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View v){
        if(v instanceof ImageView){
          bean.setSelected(!bean.isSelected());
          changeState(holder,bean);
        }
      }
    });
    changeState(holder,bean);

  }

  private boolean isImagePosition(ImageView photo,int position){
    return photo.getTag() == position;
  }

  private void changeState(ViewHolder holder,ImageBean bean){
    if(bean.isSelected()) {
      holder.tag.setImageResource(R.drawable.image_check_on);
      holder.frame.setVisibility(View.VISIBLE);
      selectedImages.add(bean);
    } else{
      holder.tag.setImageResource(R.drawable.image_check_off);
      holder.frame.setVisibility(View.GONE);
      selectedImages.remove(bean);
    }
  }

  public void notifyCursorChanged(Cursor cursor){
    long time=System.currentTimeMillis();
    Log.w(context.getApplicationInfo().getClass().getSimpleName(),"working on "+Thread.currentThread().getName());
    if(cursor!=null){
      ArrayList<ImageBean> cache=new ArrayList<>();
      try{
        int dataIndex=cursor.getColumnIndex(projections[0]);
        int idIndex=cursor.getColumnIndex(projections[1]);
        while(cursor.moveToNext()){
          ImageBean bean=new ImageBean();
          String uri=cursor.getString(dataIndex);

          bean.setUri(uri);
          long id=cursor.getLong(idIndex);
          bean.setImageId(id);
          cache.add(bean);
        }
      }finally{
        cursor.close();
        if(cache.size()>0){
          imageList.clear();
          imageList.addAll(cache);
          notifyDataSetChanged();
        }
        Log.w(context.getApplicationInfo().getClass().getSimpleName(),"working cost "+(System.currentTimeMillis() - time));
      }
    }
  }

  static class ViewHolder extends RecyclerView.ViewHolder{
    private ImageView photo;
    private View frame;
    private ImageView tag;
    private TextView path;

    ViewHolder(View itemView){
      super(itemView);
      photo=(ImageView)itemView.findViewById(R.id.iv_photo);
      frame=itemView.findViewById(R.id.v_photo_frame);
      tag=(ImageView)itemView.findViewById(R.id.iv_photo_tag);
      path =(TextView)itemView.findViewById(R.id.tv_path);
    }
  }
}
