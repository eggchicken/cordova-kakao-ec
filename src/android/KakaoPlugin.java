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

/**
 * This class echoes a string called from JavaScript.
 */
public class KakaoPlugin extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("sendTemplate")) {
            this.sendTemplate(args, callbackContext);
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

    public void sendTemplate(JSONArray args, CallbackContext callbackContext) {
        Context context = this.cordova.getActivity().getApplicationContext();
        try {
            JSONObject convertArgs = args.getJSONObject(0);
            JSONObject content = convertArgs.getJSONObject("content");
            JSONObject link = content.getJSONObject("link");
            JSONObject social = convertArgs.getJSONObject("social");

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
            .setDescrption(description)
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
                    callbackContext.error("Expected one non-empty string argument.");
                }

                @Override
                public void onSuccess(KakaoLinkResponse result) {
                    callbackContext.success("KakaoPluginOK");
                }
            });
        } catch(JSONException e) {
            Log.d("kakao plugin error: ", + e);
        }

    }

}
