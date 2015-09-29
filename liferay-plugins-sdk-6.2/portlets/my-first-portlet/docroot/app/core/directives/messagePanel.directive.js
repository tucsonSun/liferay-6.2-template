/**
 * Directive used to display messages.
 * 
 * Sample messages
 *             scope.alerts = [
 *             		{ type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.' },
 *              	{ type: 'success', title: "Success!", msg: 'Well done! You successfully read this important alert message.' },
 *            		{ type: 'warning', msg: 'Another alert!' },
 *              	{ type: 'info', msg: 'do you have any ideas, why ?' },
 *              	{ type: 'error', msg: 'this an error message....' }
 *              ];
 * 
 */
(function() {
    'use strict';

    angular
        .module('myFirstApp.directives', ['ui.bootstrap'])
        .directive('messagePanel', messagePanel);

    function messagePanel() {
        var directive = {
            restrict: 'AE',
			"scope" : {
	            "message": "=",
	            "messages": "=",
	            "nextMessage":"="
			},
	        template: '<div id="messagePanelId" class="clear messagePanel">'+
			        	'<alert ng-repeat="alert in alerts" type="{{alert.type}}" close="close($index)">'+
			        		'<strong ng-if="alert.title">{{alert.title}}:&nbsp;&nbsp;</strong>{{alert.msg}}'+
			        	'</alert>'+
			          '</div>',
			replace : true, //element to which the directive declared should be replaced with template
			link: link
        };

        return directive;

        function link(scope, element, attrs) {
			// initialize
            scope.alerts = [];
        	
        	/**
             * Method to close a message row
             */
        	scope.close = function(index) {
            	scope.alerts.splice(index, 1);
        	};
        	
        	
        	scope.$watch('message', function(newValue, oldValue){
        		if (scope.message && scope.message.hasMsg()) {
                    // Push into alerts array
                    scope.alerts.push(scope.message);
        		}
        	});
        	
        	scope.$watch('messages', function(newValue, oldValue){
        		if (scope.message && scope.messages.length > 0) {
        			for (var i = 0; i < scope.messages.length; i++) {
						var msgTemp = scope.messages[i];
						// Push into alerts array
						scope.alerts.push(msgTemp);
					}
        		}
        	});
        	
        	scope.$watch('nextMessage', function(newValue, oldValue){
        		if (scope.nextMessage && scope.nextMessage.hasMsg()) {
                    // Push into alerts array
                    scope.alerts.push(scope.nextMessage);
        		}
        	});
        	
        }
    }
})();
