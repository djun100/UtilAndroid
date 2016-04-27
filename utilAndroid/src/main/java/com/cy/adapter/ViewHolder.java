package com.cy.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cy.app.UtilContext;

/**
   @author cy <a href="https://github.com/djun100">https://github.com/djun100</a>
 */
public class ViewHolder {

   private final SparseArray<View> mView;	
   private View mConvertView;
   
   public ViewHolder( ViewGroup parent, int layoutId, int position){
	   this.mView=new SparseArray<View>();
       mConvertView = LayoutInflater.from(UtilContext.getContext()).inflate(layoutId, parent, false);
       mConvertView.setTag(this);
   };
   
   /**
    * 获得viewholder
    * @param convertView
    * @param parent
    * @param layoutId
    * @param position
    * @return
    */
   public static ViewHolder getViewHolder(View convertView,ViewGroup parent, int layoutId, int position){
	   if(null==convertView){
		   return new ViewHolder(parent, layoutId, position);
	   }
	   return (ViewHolder) convertView.getTag();
   }
   
   /**
    * @param id
    * @return
    */
   public  <T extends View> T getView(int id){
	   View view=mView.get(id);
	   if(null==view){
		   view=mConvertView.findViewById(id);
		   mView.put(id,view);
	   }
	   return (T)view;
   }
   
   public View getConvertView(){
	   return mConvertView;
   }
}
