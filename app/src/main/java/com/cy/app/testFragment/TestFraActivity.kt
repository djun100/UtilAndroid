package com.cy.app.testFragment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.cy.app.R
import com.cy.container.util.UtilFragment
import com.cy.io.Log

/**
 * 显示未add过的fragment，只需要add，commit
 * 显示add过的fragment，必须要1、hide不显示的；2、show显示的
 */
class TestFraActivity : AppCompatActivity() {
    var fra1:Fragment1?=null
    var fra2:Fragment2?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_fra)

        fra1= Fragment1()
        fra2=Fragment2()

        showFra1()
    }

    fun showFra1() {
        UtilFragment.showFragment(R.id.rootView,fra1!!,this)
    }

    fun showFra2() {
        UtilFragment.showFragment(R.id.rootView,fra2!!,this)
    }

}
