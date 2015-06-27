(function() {
    'use strict';

    angular
        .module('myFirstApp.directives', ['ui.bootstrap'])
        .directive('messagePanel', messagePanel);

    function messagePanel() {
        var directive = {
            link: link,
            restrict: 'AE',
			scope : {
	            "message": "@message",
	            "messages": "@messages",
	            "flashMessage":"@flashMessage"
			},
	        template: '<div id="messagePanelId" class="messagePanel">'+
			        	'<alert ng-repeat="alert in alerts" type="alert.type" close="close($index)">'+
			        		'<strong ng-if="alert.title">{{alert.title}}&nbsp;&nbsp;</strong>{{alert.msg}}'+
			        	'</alert>'+
			          '</div>',
			replace : true, //element to which the directive declared should be replaced with template
			controller: controller
        };

        return directive;

        function link(scope, element, attrs) {
            /**
             * Method to close a message row
             */
        	scope.close = function(index) {
            	scope.alerts.splice(index, 1);
        	};
        	
        	scope.$watch(function() {return element.attr('message'); }, function(newValue){
        		var myMessage = element.attr('message');
        		if (myMessage && myMessage.length > 0) {
                    // Call Message Processing Actions 
                    scope.preProcessMessageAction();
                    scope.addToMsgList();
        		}
        	});
        	scope.$watch(function() {return element.attr('messages'); }, function(newValue){
        		var myMessages = element.attr('messages');
        		if (myMessages && myMessages.length > 0) {
                    // Call Message Processing Actions 
                    scope.preProcessMessagesAction();
                    scope.addToMsgList();
        		}
        	});
        	scope.$watch(function() {return element.attr('flash-message'); }, function(newValue){
        		var myFlashMessage = element.attr('flash-message');
        		if (myFlashMessage && myFlashMessage.length > 0) {
                    // Call Message Processing Actions 
                    scope.preProcessFlashMessageAction();
                    scope.addToMsgList();
        		}
        	});
        }
        
        function controller($scope) {
			// initialize
            $scope.alerts = [];
            //Sample messsages
//            $scope.alerts = [
//                 { type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.' },
//                 { type: 'success', title: "Success!", msg: 'Well done! You successfully read this important alert message.' },
//                 { type: 'warning', msg: 'Another alert!' },
//                 { type: 'info', msg: 'do you have any ideas, why ?' },
//                 { type: 'error', msg: 'this an error message....' }
//            ];
            
            $scope.preProcessMessageAction = function() {
            	if ($scope.message === "{}") {
            		$scope.message = "";
            		return;//do nothing
            	}
            
            	if ($scope.message && $scope.message.length > 0) {
            		var containsJsonSyntax = $scope.message.indexOf("{") >= 0 && $scope.message.indexOf("}") >= 0;
            		if (containsJsonSyntax) {
            			var jsonObj = JSON.parse($scope.message);
            			$scope.message = jsonObj;
            		}
            		if (!containsJsonSyntax && (typeof $scope.message == 'string' || $scope.message instanceof String)) {
		            	// msg it's a string
		            	var indexOfColon = $scope.message.indexOf(":");
		            	var indexOfColonNext = $scope.message.indexOf(":")+1;
		            	var typeOnly = $scope.message.substring(0, indexOfColon);
		            	var msgOnly = $scope.message.substring(indexOfColonNext, $scope.message.length);
		            	$scope.message =  { type: typeOnly, msg: msgOnly};
		            }
            	}
            	else $scope.message = "";//set to empty 
            };
            
            $scope.preProcessMessagesAction = function() {
            	if ($scope.messages === "[]") {
            		$scope.messages = "";
            		return;//do nothing
            	}
            	
            	if ($scope.messages && $scope.messages.length > 0) {
            		var containsJsonSyntax = $scope.messages.indexOf("{") >= 0 && $scope.messages.indexOf("}") >= 0;
	            	if (containsJsonSyntax) {
	            		var jsonArray = JSON.parse($scope.messages);
	            		$scope.messages = jsonArray;
	            	}
            	}
            	else $scope.messages = "";//set to empty 
            };
            
            $scope.preProcessFlashMessageAction = function() {
            	if ($scope.flashMessage === "{}") {
            		$scope.flashMessage = "";
            		return;//do nothing
            	}
            	
            	if ($scope.flashMessage && $scope.flashMessage.length > 0) {
            		var containsJsonSyntax = $scope.flashMessage.indexOf("{") >= 0 && $scope.flashMessage.indexOf("}") >= 0;
	            	if (containsJsonSyntax) {
	            		var jsonObj = JSON.parse($scope.flashMessage);
	            		jsonObj['msg'] = jsonObj.body;
	            		$scope.flashMessage = jsonObj;
	            	}
            	}
            	else $scope.flashMessage = "";//set to empty 
            };
            		
            $scope.addToMsgList = function() {
	            if ($scope.message) {
	            	$scope.alerts.push($scope.message);
	            } 
	            if ($scope.messages) {
	            	for (var i = 0; i < $scope.messages.length; i++) {
						var msgTemp = $scope.messages[i];
						$scope.alerts.push(msgTemp);
					}	
	            } 
	            if ($scope.flashMessage) {
	            	$scope.alerts.push($scope.flashMessage);
	            }
            };
            
//            // Call Message Processing Actions 
//            $scope.preProcessMessageAction();
//            $scope.preProcessMessagesAction();
//            $scope.preProcessFlashMessageAction();
//            $scope.addToMsgList();
		}
    }
})();
