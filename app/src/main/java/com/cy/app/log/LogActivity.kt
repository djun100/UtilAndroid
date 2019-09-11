package com.cy.app.log
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.cy.app.R
import com.cy.app.UtilContext
import com.cy.io.KLog

import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler

import cz.msebera.android.httpclient.Header

class LogActivity : FragmentActivity() {
    private var httpClient: AsyncHttpClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        initView()
        initData()
        handler.sendEmptyMessageDelayed(0, 3000)
    }

    private fun initView() {
        val toolbar = findViewById<View>(R.id.toolBar) as Toolbar
        if (toolbar != null) {
            toolbar.setTitleTextColor(Color.WHITE)
//            setSupportActionBar(toolbar)
        }
    }

    private fun initData() {
        httpClient = AsyncHttpClient()
        JSON_LONG = resources.getString(R.string.json_long)
        JSON = resources.getString(R.string.json)
        STRING_LONG = getString(R.string.string_long)
    }

    fun logTraceStack(view: View) {
        TestTraceUtil.testTrace()
    }

    fun logDebug(view: View) {
        KLog.debug()
        KLog.debug("This is a debug message")
        KLog.debug("DEBUG", "params1", "params2", this)
    }

    fun log(view: View) {
        KLog.v()
        KLog.d()
        KLog.i()
        KLog.w()
        KLog.e()
        KLog.a()
    }

    fun logWithNull(view: View) {
        KLog.v(null)
        KLog.d(null)
        KLog.i(null)
        KLog.w(null)
        KLog.e(null)
        KLog.a(null)
    }

    fun logWithMsg(view: View) {
        KLog.v(LOG_MSG)
        KLog.d(LOG_MSG)
        KLog.i(LOG_MSG)
        KLog.w(LOG_MSG)
        KLog.e(LOG_MSG)
        KLog.a(LOG_MSG)
    }

    fun logWithTag(view: View) {
        KLog.v(TAG, LOG_MSG)
        KLog.d(TAG, LOG_MSG)
        KLog.i(TAG, LOG_MSG)
        KLog.w(TAG, LOG_MSG)
        KLog.e(TAG, LOG_MSG)
        KLog.a(TAG, LOG_MSG)
    }

    fun logWithLong(view: View) {
        KLog.d(TAG, STRING_LONG)
    }

    fun logWithParams(view: View) {
        KLog.v(TAG, LOG_MSG, "params1", "params2", this)
        KLog.d(TAG, LOG_MSG, "params1", "params2", this)
        KLog.i(TAG, LOG_MSG, "params1", "params2", this)
        KLog.w(TAG, LOG_MSG, "params1", "params2", this)
        KLog.e(TAG, LOG_MSG, "params1", "params2", this)
        KLog.a(TAG, LOG_MSG, "params1", "params2", this)
    }


    fun logWithJson(view: View) {
        KLog.json("12345")
        KLog.json(null)
        KLog.json(JSON)
    }

    fun logWithLongJson(view: View) {
        KLog.json(JSON_LONG)
    }

    fun logWithJsonTag(view: View) {
        KLog.json(TAG, JSON)
    }

    fun logWithFile(view: View) {
        KLog.file(Environment.getExternalStorageDirectory(), JSON_LONG)
        KLog.file(TAG, Environment.getExternalStorageDirectory(), JSON_LONG)
        KLog.file(TAG, Environment.getExternalStorageDirectory(), "test.txt", JSON_LONG)
    }

    fun logWithXml(view: View) {
        KLog.xml("12345")
        KLog.xml(null)
        KLog.xml(XML)
    }

    fun logWithXmlFromNet(view: View) {
        httpClient!!.get(this, URL_XML, object : TextHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseString: String, throwable: Throwable) {
                KLog.e(responseString)
            }

            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseString: String) {
                KLog.xml(responseString)
            }
        })
    }

    ///////////////////////////////////////////////////////////////////////////
    // MENU
    ///////////////////////////////////////////////////////////////////////////

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_about, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_github -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ZhaoKaiQiang/KLog")))
            R.id.action_blog -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://blog.csdn.net/zhaokaiqiang1992")))
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {

        private val LOG_MSG = "KLog is a so cool Log Tool!"
        private val TAG = "KLog"
        private val URL_XML = "https://raw.githubusercontent.com/ZhaoKaiQiang/KLog/master/app/src/main/AndroidManifest.xml"
        private val XML = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><!--  Copyright w3school.com.cn --><note><to>George</to><from>John</from><heading>Reminder</heading><body>Don't forget the meeting!</body></note>"
        private var JSON: String? = null
        private var JSON_LONG: String? = null
        private var STRING_LONG: String? = null

        private val handler = object : Handler() {

            override fun handleMessage(msg: Message) {
                KLog.d("Inner Class Test")

                KLog.file("mytag",UtilContext.getContext().filesDir,"lala","content")
            }
        }
    }

}
