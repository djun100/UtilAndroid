# UtilAndroid
**usage:**

    allprojects {
    	repositories {
    		jcenter()
    		maven { url "https://jitpack.io" }
    	}
    }


compile 'com.github.djun100:UtilAndroid:1686a04e34e1175e61d0545b846d59126faf14d3'

should compile below:

    provided 'com.android.support:support-v4:+'

if you use UtilBase64_Codec compile below:

    provided 'commons-codec:commons-codec:1.8'

增加测试数据生成功能