package com.cy.app.main;

import com.cy.file.UtilSP;

public class MainData {

    public void saveData(String data) {
        UtilSP.set("test",data);
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
                onListener.onResult((String) UtilSP.get("test",""));
            }
        }).start();

    }

    public interface OnListener{
        void onResult(String result);
    }
}
