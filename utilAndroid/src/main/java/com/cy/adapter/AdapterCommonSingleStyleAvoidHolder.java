package com.cy.adapter;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**只适用于单layout布局文件情况
 * <b>重写onDealRawList方法时一定不能返回null，不处理应返回参数list</b><br>
 * item样式完全一样，不需要position信息加以区别更改指定position的item的样式的AdapterCommon子类
 * 不需要针对不同item进行处理
 * 
 *   @author cy <a href="https://github.com/djun100">https://github.com/djun100</a>
 * @param <T>
 */
public abstract class AdapterCommonSingleStyleAvoidHolder<T> extends AdapterCommon {

	public AdapterCommonSingleStyleAvoidHolder(List<?> lists, int layoutId) {
		super(lists, layoutId);
	}

	@Override
	protected void convert(ViewHolder vh, List mDatas, int position) {
		// TODO Auto-generated method stub
		convert(vh, (T)mDatas.get(position));
	}
	abstract protected void convert(ViewHolder vh, T item);

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh= new ViewHolder(parent, layoutId, position);
		convert(vh, mDatas,position);
		return vh.getConvertView();
	}
}
