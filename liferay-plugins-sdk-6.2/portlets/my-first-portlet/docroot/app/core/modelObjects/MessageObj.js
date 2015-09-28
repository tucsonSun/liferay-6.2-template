(function() {
	'use strict';
	window.MODEL = window.MODEL || {};
}());

//MessageObj Object treated as a module
MODEL.MessageObj = (function() {
	'use strict';
	
	/**
	 * MessageObj constructor used to create a MessageObj instance.
	 * @param type
	 * @param title
	 * @param msg
	 **/
	function MessageObj(type, msg) {
		this.type = type;
		this.title = capitalizeFirstLetter(this.type);
		this.msg = msg;
	}
	
	function capitalizeFirstLetter(string) {
		if (string && string.length > 0)
			return string.charAt(0).toUpperCase() + string.slice(1);
		else
			return "";
	}

	/**
	 * Shared prototype object on every instance of MessageObj.
	 **/
	MessageObj.prototype = {
		//Resetting the prototype constructor property to point MessageObj not Object
		constructor : MessageObj,

		getType : function() {
			return this.type;
		},

		getTitle : function() {
			return this.title;
		},
		
		setTitle : function(title) {
			this.title = title;
			return this.title;
		},

		getMsg : function() {
			return this.msg;
		},
		
		getMessageObj : function() {
			return this;
		},

		/**
		 * Clear Message object
		 **/
		resetMessageObj : function() {
			this.type = null;
			this.title = null;
			this.msg = null;
		},

		
		/**
		 * Stringify the MessageObj object.
		 **/
		toString : function() {
			var output = 'type=' + this.getType() + "         title="+ this.getTitle() + "         msg="+ this.getMsg();
			return output;
		}
	};

	//Return reference to the MessageObj Object Constructor
	return MessageObj;
}());

/** Declare a window variable **/
angular.module('myFirstApp.modelObjects', [])
	.value('MessageObj', MODEL.MessageObj);
