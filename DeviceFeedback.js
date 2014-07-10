/**
 * DeviceFeedback cordova plugin
 * @author Jan Velecký aka Velda
 * 
 * Obejct bellow is exported into window.plugins.deviceFeedback
 */
var self = module.exports = {
	/**
	 * There are constants for different kinds of haptic feedback.
	 * You should respect their purpose (which you can see in their names) to provide the best UX to user.
	 * @const
	 */
	LONG_PRESS: 0, 
	VIRTUAL_KEY: 1,
	KEYBOARD_TAP: 3,
	/**
	 * Provide sound feedback to user, nevertheless respect user's settings and current active device profile as native feedback do.
	 */
	acoustic: function() {
		cordova.exec(
			function() {},
			function() {}, 
			'DeviceFeedback', 
			'Sound', 
			[]
		)
	},
	/**
	 * Provide vibrate feedback to user, nevertheless respect user's tactile feedback setting as native feedback do.
	 *
	 * @arg {VIBRATE_TYPE_CONSTANT} Specify type of vibration feedback.
	 *		One of LONG_PRESS, VIRTUAL_KEY and KEYBOARD_TAP values.
	 *		Default value is VIRTUAL_KEY.
	 */
	haptic: function(type) {
		// set default value when argument is omitted
		if(type == undefined) type = self.VIRTUAL_KEY
		// validate type argument
		else if(type !== self.VIRTUAL_KEY && type !== self.LONG_PRESS && type !== self.KEYBOARD_TAP)
			throw "Argument must be equal to one of these constants: LONG_PRESS, VIRTUAL_KEY, KEYBOARD_TAP."
		
		cordova.exec(
			function() {},
			function() {}, 
			'DeviceFeedback', 
			'Vibrate', 
			[type]
		)
	},
	/**
	 * Check if haptic and acoustic feedback is enabled by user settings.
	 *
	 * @arg {function} @callback
	 *						@arg feedback {object}
	 *							@member haptic {boolean|null} - Specify if haptic feedback is enabled by user.
	 *							@member acoustic {boolean|null} - Specify if acoustic feedback is enabled by user.
	 *							null mean, that option is missing in settings (not occured yet)
	 */
	isFeedbackEnabled: function(success) {
		// verify, that argument is function
		if(typeof success != "function") {
			throw "Argument must be callback function."
		}
	
		cordova.exec(
			success,
			function() {}, 
			'DeviceFeedback', 
			'isFeedbackEnabled', 
			[]
		)
	}
}