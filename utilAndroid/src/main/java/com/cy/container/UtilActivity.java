package com.cy.container;

import android.content.Intent;

import com.cy.app.UtilContext;

public class UtilActivity {
    /**intent是否可以成功跳转，是否有目标组件接收
     * @param intent
     * @return
     */
    public boolean isIntentAvailable(Intent intent) {
        if (intent.resolveActivity(UtilContext.getContext().getPackageManager()) != null) {
            //存在
            return true;
        } else {
            //不存在
            return false;
        }
    }
}
