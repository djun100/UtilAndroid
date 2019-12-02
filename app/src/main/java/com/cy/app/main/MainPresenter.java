package com.cy.app.main;

import com.cy.container.mvp.BasePresenter;
import com.cy.container.mvp.UtilType;
import com.cy.io.Log;

public class MainPresenter extends BasePresenter<IMainView,MainData> {
    @Override
    protected MainData newModel() {
        return UtilType.getTypeInstance(this,1);
    }

    public void saveData(String data) {
        baseGetModule().saveData(data);
    }

    public void readData() {
        baseGetModule().readData(new MainData.OnListener() {
            @Override
            public void onResult(String result) {
                baseGetMvpView().showToast(result);
                baseGetModule().saveData(result+"1");
            }
        });
    }

    public void testThread(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i();
            }
        });
        thread.start();
//        thread.start();
    }
}
