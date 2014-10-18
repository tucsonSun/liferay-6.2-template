jQuery(document).ready(function() {
	/// Add Custome javacript methods.....
	
	/** The following will prevent all future AJAX requests from being cached, 
		regardless of which jQuery method you use ($.get, $.ajax, etc.)
	**/
	$.ajaxSetup({ cache: false });
	
	/** Liferay search field modify submit.
	 -intercept the form submit and redirect search results 
	**/
	$(document).on('submit', '#header-search form', function(event) {
		event.preventDefault();
		var searchValue = $(this).children('input').val();
		window.location = '/web/guest/home-page?p_p_id=3&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&_3_struts_action=%2Fsearch%2Fsearch&_3_redirect=%2Fweb%2Fguest%2F&_3_keywords='+searchValue+'&_3_groupId=0&x=15&y=15';
	});
});

$(window).load(function() {
	/// Add Custome javacript methods.....

});

