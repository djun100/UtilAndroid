package com.cy.adapter;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**<b>重写onDealRawList方法时一定不能返回null，不处理应返回参数list</b><br>
 * onItemClick里面，取子view方式eg：item.getChildAt(1)
 * <p>
 * 改变其他item里面的子view：
 *             ViewGroup item;
            for (int i = 0; i < mTtf_profit_types_lv.getCount(); i++) {
                item = (ViewGroup) mTtf_profit_types_lv.getChildAt(i);
                if (position == i) {
                    item.getChildAt(1).setVisibility(View.VISIBLE);

                } else {
                    item.getChildAt(1).setVisibility(View.GONE);
                }
            }
            mTtf_profit_types_lv.invalidate();
            
   @author cy <a href="https://github.com/djun100">https://github.com/djun100</a>
 */
public abstract class AdapterCommon<T>  extends BaseAdapter {

	protected List<T> mDatas;
	protected LayoutInflater mInflater;  
    protected Context mContext; 
    protected int layoutId;
    
    public AdapterCommon(Context ctx,List<T> lists,int layoutId){
    	this.mDatas=lists;
    	mInflater=(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	this.mContext=ctx;
    	this.layoutId=layoutId;
    	//adapter内进行空值处理：
        if(mDatas==null){
        	mDatas=new ArrayList<T>();
        }
        mDatas=onDealRawList(mDatas);
    }

    /**更新数据
     * @param list
     */
    public void update(List<T> list){
    	mDatas=onDealRawList(list);
        notifyDataSetChanged();
    }
    /**Tips:<p>
     * 1、must not be null<br>
     * 2、使用非增强for循环来遍历增加删除list元素
   	     for(int i=0;i<list.size();i++){
	          if(!list.get(i).transStatus.equals("0")){
	              list.remove(list.get(i));
	          }
	     }
     * @param list
     * @return at lest raw list,must not be null
     */
    public abstract List<T> onDealRawList(List<T> list);

    @Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

/*	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh=ViewHolder.getViewHolder(convertView, mContext, parent, layoutId, position);
		convert(vh, mDatas.get(position));
		return vh.getConvertView();
	}
	
	abstract protected void convert(ViewHolder vh,T item);*/
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh=ViewHolder.getViewHolder(convertView, mContext, parent, layoutId, position);
		convert(vh, mDatas,position);
		return vh.getConvertView();
	}
	
	abstract protected void convert(ViewHolder vh,List<T> mDatas,int position);

}
