package com.cy.app.main;

import com.cy.File.UtilSP;

public class MainData {

    public void saveData(String data) {
        UtilSP.setParam("test",data);
    }

    public void readData(final OnListener onListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                onListener.onResult((String) UtilSP.getParam("test",""));
            }
        }).start();

    }

    public interface OnListener{
        void onResult(String result);
    }
}
