package com.cy.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

/**
   @author cy <a href="https://github.com/djun100">https://github.com/djun100</a>
 */
public class Demo4Activity extends Activity{

	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.listview);
//		listView=(ListView)findViewById(R.id.listview);
		
		AdapterCommonSingleStyle<DemoBean> mAdapter;
/*		listView.setAdapter(mAdapter = new AdapterCommonSingleStyle<DemoBean>( getDemoBean(), R.layout.type1)
        {
            @Override  
            public void convert(ViewHolder helper, DemoBean item)  
            {  
//                helper.setText(R.id.tv_time, item.getTime());  
                  
//              helper.getView(R.id.tv_title).setOnClickListener(l)  
            }

			@Override
			public List onDealRawList(List list) {
				// TODO Auto-generated method stub
			    if(list==null){
			        list=new ArrayList<DemoBean>();
			    }
				return null;
			}
  
  
        });*/
		
		
		
	}
	
	
	private ArrayList<DemoBean>  getDemoBean(){
		ArrayList<DemoBean> list=new ArrayList<DemoBean>();
		for (int i = 0; i < 10; i++) {
			list.add(new DemoBean("1"));
			list.add(new DemoBean("1"));
			list.add(new DemoBean("1"));
			list.add(new DemoBean("1"));
			list.add(new DemoBean("1"));
			list.add(new DemoBean("1"));
			list.add(new DemoBean("2"));
			list.add(new DemoBean("2"));
			list.add(new DemoBean("2"));
			list.add(new DemoBean("2"));
			list.add(new DemoBean("2"));
			list.add(new DemoBean("2"));
		}
		
		return list;
	}
	
}
