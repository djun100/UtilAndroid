# UtilAndroid
**usage:**

    repositories {
    // ...
    maven { url "https://jitpack.io" }
    }
    
    compile （'com.github.djun100:UtilAndroid:eede0e2fb3a62db4738840fb6f1c5054537cdfc8'）{
    exclude module: 'support-v4'
    exclude module:'eventbus'
    }
