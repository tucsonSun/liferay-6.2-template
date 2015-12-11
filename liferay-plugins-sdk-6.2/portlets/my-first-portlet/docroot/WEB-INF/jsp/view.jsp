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
		<div layout="column" flex id="portal-content">
			 <!-- THIS IS WHERE WE WILL INJECT OUR CONTENT ============================== -->
			<md-content layout="column" flex class="md-padding" ui-view>inject content here...</md-content>
		</div>
	</div>
</div>

<!-- Angular Libraries -->
<jsp:include page="js-libraries.jsp"/>
<!-- Angular Application -->
<jsp:include page="js-app.jsp"/>
<!-- Permissions UserInfo Object -->
<jsp:include page="js-userInfo.jsp"/>