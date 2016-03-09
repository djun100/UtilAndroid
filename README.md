# UtilAndroid
**usage:**

    repositories {
    // ...
    maven { url "https://jitpack.io" }
    }
    
    compile （'com.github.djun100:UtilAndroid:e7f36de312b1e80c24b5435293c1c005d62f5a2e'）{
    exclude module: 'support-v4'
    exclude module:'eventbus'
    }
