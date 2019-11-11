# cordova-kakao-ec
kakao api for cordova project (Android)

How to use:

Create Cordova project:
    cordova create <dest-dir> <android-package-info> <AppName>
    
    ex) cordova create myapp org.eggchicken.myapp MyApp


Install Plugin:

    plugman install --platform android --project /cordova-project-path/platforms/android --plugin https://github.com/eggchicken/cordova-kakao-ec/ --variable API_KEY=YOUR_KAKAO_API_KEY


Signing Keystore:

    open android studio
    
    choose-project-dir) /cordova-project-path/platforms/android
    
      (If possible, update the gradle plugin)
      
    The next step is here: https://developer.android.com/studio/publish/app-signing.html
    
      
Android Build


Uninstall:

    plugman uninstall --platform android --project .\platforms\android --plugin cordova-kakao-ec
