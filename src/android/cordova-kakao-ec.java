package pe.eggchicken.cordova.plugin.kakao;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.eggchicken.cordova.plugin.kakao.KakaoServices;

/**
 * This class echoes a string called from JavaScript.
 */
public class cordova-kakao-ec extends CordovaPlugin {
    public KakaoService kakao;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        // your init code here
        kakao = new KakaoService();
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if ("sendTemplate".equals(action)) {
            kakao.sendTemplate();
            return true;
        }
        // if (action.equals("coolMethod")) {
        //     String message = args.getString(0);
        //     this.coolMethod(message, callbackContext);
        //     return true;
        // }
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}
