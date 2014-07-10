package cz.Velda.cordova.plugin.devicefeedback;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioManager;
//import android.util.Log;
import android.view.SoundEffectConstants;
import android.provider.Settings;

/**
 * This class echoes a string called from JavaScript.
 */
public class DeviceFeedback extends CordovaPlugin {
	AudioManager audioManager;
	
	// input values
	final static int VIBRATE_TYPE_INDEX = 0;
	
	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    	audioManager = (AudioManager) cordova.getActivity().getSystemService(Context.AUDIO_SERVICE);
    	//Log.w("DF", "init");
    	super.initialize(cordova, webView);
    	// this disable default webView sound on touch and click events; does not influence dialogs or other activities
    	webView.setSoundEffectsEnabled(false);
	}

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("Sound")) {
        	Sound();
        } else if (action.equals("Vibrate")) {
        	Vibrate(args);
        } else if (action.equals("isFeedbackEnabled")) {
        	isFeedbackEnabled(callbackContext);
        } else {
            return false;
        }
        return true;
    }

	void Sound() {
        audioManager.playSoundEffect(SoundEffectConstants.CLICK);
	}
	
	void Vibrate(JSONArray args) {
		try {
			webView.performHapticFeedback(args.getInt(VIBRATE_TYPE_INDEX));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	void isFeedbackEnabled(CallbackContext callbackContext) {
		ContentResolver cr = cordova.getActivity().getContentResolver();

		// check if is feedback allowed by user, 1 mean allowed, 0 mean disabled and -1 mean unspecified
		int haptic = Settings.System.getInt(cr, Settings.System.HAPTIC_FEEDBACK_ENABLED, -1);
		int acoustic = Settings.System.getInt(cr, Settings.System.SOUND_EFFECTS_ENABLED, -1);
		
		// map 1 to true, 0 to false and -1 to null
		JSONObject respond = new JSONObject();
		try {
			respond.put("haptic", haptic == -1 ? null : haptic == 1);
			respond.put("acoustic", acoustic == -1 ? null : acoustic == 1);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		callbackContext.success(respond);
	}
}