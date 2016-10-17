# UtilAndroid
**usage:**

    allprojects {
    	repositories {
    		jcenter()
    		maven { url "https://jitpack.io" }
    	}
    }


compile 'com.github.djun100:UtilAndroid:3b6bf356a9a187210b020d40d036b3ccdeb418fd'

should compile below:

    provided 'com.android.support:support-v4:+'

if you use UtilBase64_Codec compile below:

    provided 'commons-codec:commons-codec:1.8'

增加测试数据生成功能