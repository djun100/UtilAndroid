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


compile 'com.github.djun100:UtilAndroid:677e4bc39055e34a42da7f52424c0ae741c1b387'

should compile below:

    provided 'com.android.support:support-v4:+'

if you use UtilBase64_Codec compile below:

    provided 'commons-codec:commons-codec:1.8'