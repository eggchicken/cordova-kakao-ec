package pe.eggchicken.cordova.plugin.kakao;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;


import android.util.Log;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.message.template.*;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.network.ErrorResult;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import java.security.MessageDigest;
import android.util.Base64;
import java.security.NoSuchAlgorithmException;
import android.content.pm.PackageManager.NameNotFoundException;
/**
 * This class echoes a string called from JavaScript.
 */
public class KakaoPlugin extends CordovaPlugin {
    public boolean execute(String action, JSONObject args, CallbackContext callbackContext) throws JSONException {

        if (action.equals("sendTemplate")) {
            this.sendTemplate(args);
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

    public void sendTemplate(JSONObject args) {
        Context context = this.cordova.getActivity().getApplicationContext();

        try {
            JSONObject content = args.getJSONObject("content");
            JSONObject link = content.getJSONObject("link");
            JSONObject social = args.getJSONObject("social");

            String title = content.getString("title");
            String description = content.getString("description");
            String imageSrc = content.getString("imageUrl");
            String webUrl = link.getString("webUrl");
            String mobileWebUrl = link.getString("mobileWebUrl");

            int likeCount = social.getInt("likeCount");
            int commentCount = social.getInt("commentCount");

            FeedTemplate params = FeedTemplate
            .newBuilder(
                ContentObject.newBuilder(
                    title,
                    imageSrc,
                    LinkObject.newBuilder().setWebUrl(webUrl)
                    .setMobileWebUrl(mobileWebUrl).build())
            .setDescrption(description.substring(0,25))
            .build())
            .setSocial(SocialObject.newBuilder().setLikeCount(likeCount).setCommentCount(commentCount)
                    .setSharedCount(1).setViewCount(2).build())
            .addButton(new ButtonObject("웹에서 보기", LinkObject.newBuilder().setWebUrl("'https://developers.kakao.com").setMobileWebUrl("'https://developers.kakao.com").build()))
            .addButton(new ButtonObject("앱에서 보기", LinkObject.newBuilder()
                    .setWebUrl("'https://developers.kakao.com")
                    .setMobileWebUrl("'https://developers.kakao.com")
                    .setAndroidExecutionParams("key1=value1")
                    .setIosExecutionParams("key1=value1")
                    .build()))
            .build();

            KakaoLinkService.getInstance().sendDefault(context, params, new ResponseCallback<KakaoLinkResponse>() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    Log.d("KakaoPlugin", errorResult.toString());
                }

                @Override
                public void onSuccess(KakaoLinkResponse result) {
                    Log.d("KakaoPlugin", "Success");
                }
            });
        } catch(JSONException e) {
            Log.d("KakaoPlugin", "json error" + e);
        }

    }

    public static String getKeyHash(final Context context) {
        PackageManager pm = context.getPackageManager();
        try{
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            if (packageInfo == null)
            return null;

            for (Signature signature : packageInfo.signatures) {
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
                } catch (NoSuchAlgorithmException e) {
                    Log.w("KakaoPlugin", "Unable to get MessageDigest. signature=" + signature, e);
                }
            }
        } catch(NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
