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


compile 'com.github.djun100:UtilAndroid:a349dddb82'

should compile below:

    provided 'com.android.support:support-v4:+'

if you use UtilBase64_Codec compile below:

    provided 'commons-codec:commons-codec:1.8'