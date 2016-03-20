# UtilAndroid
**usage:**

    repositories {
    // ...
    maven { url "https://jitpack.io" }
    }
    old
    compile （'com.github.djun100:UtilAndroid:eede0e2fb3a62db4738840fb6f1c5054537cdfc8'）{
    exclude module: 'support-v4'
    exclude module:'eventbus'
    }
now

compile 'com.github.djun100:UtilAndroid:77f7e320fd3a27be3daa2f8536dd3f45b9c9726f'

should compile

    provided 'com.android.support:support-v4:+'
    provided files('libs/commons-codec-1.8.jar')
    provided 'org.greenrobot:eventbus:3.0.0'