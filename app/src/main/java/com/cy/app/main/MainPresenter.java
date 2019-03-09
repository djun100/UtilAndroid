package com.cy.app.main;

import com.cy.host.mvp.BasePresenter;
import com.cy.host.mvp.UtilType;

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
}
