<%
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />

<!-- libs -->
<link rel="stylesheet" href="//fonts.googleapis.com/css?family=RobotoDraft:300,400,500,700,400italic">
<link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/angular_material/0.10.0/angular-material.min.css">
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular-animate.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular-resource.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular-aria.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/angular_material/0.10.0/angular-material.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/angular-ui-router/0.2.15/angular-ui-router.min.js"></script>

<!-- 3rd party libs -->
<script src="//cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/0.13.0/ui-bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.1/toastr.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.8.3/underscore-min.js"></script>

<!-- directives -->
<script src="/my-first-portlet/app/core/directives/directives.module.js"></script>
<script src="/my-first-portlet/app/core/directives/messagePanel.directive.js"></script>
<script src="/my-first-portlet/app/core/directives/loader.directive.js"></script>

<!-- application files -->
<script src="/my-first-portlet/app/app.module.js"></script>
<script src="/my-first-portlet/app/app.routes.js"></script>

<!-- blocks files -->
<script src="/my-first-portlet/app/blocks/exception/exception.module.js"></script>
<script src="/my-first-portlet/app/blocks/exception/exception-handler.js"></script>
<script src="/my-first-portlet/app/blocks/exception/exception.js"></script>
<script src="/my-first-portlet/app/blocks/logger/logger.module.js"></script>
<script src="/my-first-portlet/app/blocks/logger/logger.js"></script>
<script src="/my-first-portlet/app/blocks/router/router.module.js"></script>
<script src="/my-first-portlet/app/blocks/router/routehelper.js"></script>

<!-- core files -->
<script src="/my-first-portlet/app/core/core.module.js"></script>
<script src="/my-first-portlet/app/core/constants.js"></script>
<script src="/my-first-portlet/app/core/config.js"></script>
<script src="/my-first-portlet/app/core/custom.js"></script>

<!-- services files -->
<script src="/my-first-portlet/app/core/services/services.module.js"></script>
<script src="/my-first-portlet/app/core/services/underscore.service.js"></script>

<script src="/my-first-portlet/app/core/services/myFirst.base.service.js"></script>
<script src="/my-first-portlet/app/core/services/landing.service.js"></script>

<!-- featureSets files -->
<script src="/my-first-portlet/app/featureSets/leftMenuBar/leftMenuBar.module.js"></script>
<script src="/my-first-portlet/app/featureSets/leftMenuBar/leftMenuBar.controller.js"></script>
<script src="/my-first-portlet/app/featureSets/home/home.module.js"></script>
<script src="/my-first-portlet/app/featureSets/home/home.controller.js"></script>


<div layout="column" ng-app="myFirstApp">
	<md-toolbar layout="row">
		<div class="md-toolbar-tools">
			<h1>My First Portal Header</h1>
		</div>
	</md-toolbar>
	
	<!-- CONTAINER -->
	<div layout="row" flex>
		<!-- NAVIGATION -->
		<div data-ng-include src="'/my-first-portlet/app/featureSets/leftMenuBar/leftMenuBar.html'"></div> 
		<!-- MAIN CONTENT -->
		<div layout="column" flex id="content">
			 <!-- THIS IS WHERE WE WILL INJECT OUR CONTENT ============================== -->
			<md-content layout="column" flex class="md-padding" ui-view>inject content here...</md-content>
		</div>
	</div>
</div>

<!-- INCLUDE USER INFO -->
<jsp:include page="userInfo.jsp"/>