# UtilAndroid
**usage:**

    repositories {
    	// ...
    	maven { url "https://jitpack.io" }
    }

    allprojects {
    	repositories {
    		jcenter()
    		maven { url "https://jitpack.io" }
    	}
    }


compile 'com.github.djun100:UtilAndroid:a349dddb825f4f889fe2a8e719b6bf8f45244873'

should compile below:

    provided 'com.android.support:support-v4:+'

if you use UtilBase64_Codec compile below:

    provided 'commons-codec:commons-codec:1.8'