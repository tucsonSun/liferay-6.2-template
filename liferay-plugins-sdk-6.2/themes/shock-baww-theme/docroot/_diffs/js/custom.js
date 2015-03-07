jQuery(document).ready(function() {
	/// Add Custome javacript methods.....
	
	/**
	 *  The following will prevent all future AJAX requests from being cached,
	 *  regardless of which jQuery method you use ($.get, $.ajax, etc.)
	**/
	$.ajaxSetup({ cache: false });
	
	/** 
	 * Method modifies Liferay search field form submit.
	 * -intercept the form submit and redirect search results 
	**/
	$(document).on('submit', '#desktop-search-controls form', function(event) {
		event.preventDefault();
		var searchValue = $('#search-control-input').val();
		window.location = '/web/guest/welcome?p_p_id=3&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&_3_struts_action=%2Fsearch%2Fsearch&_3_redirect=%2Fweb%2Fguest%2F&_3_keywords='+searchValue+'&_3_groupId=0&x=15&y=15';
	});
	
	/** 
	 * Method modifies Liferay search field form submit.
	 * -intercept the form submit and redirect search results 
	**/
	$(document).on('submit', '#mobile-search-controls form', function(event) {
		event.preventDefault();
		var searchValue = $('#search-control-input').val();
		window.location = '/web/guest/welcome?p_p_id=3&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&_3_struts_action=%2Fsearch%2Fsearch&_3_redirect=%2Fweb%2Fguest%2F&_3_keywords='+searchValue+'&_3_groupId=0&x=15&y=15';
	});

	/**
	 *  Method for mobile responsive when the #nav-pop button is clicked 
	 *  the #moble-nav-menu is clicked with the slideUp/slideDown effect
	**/
	$(document).on('click', '#mobile-nav #nav-pop', function(event) {
		event.preventDefault();
	    if ($('#mobile-nav-menu').hasClass('selected')) {
            $('#mobile-nav-menu').removeClass('selected').slideUp();
        } else {
            $('#mobile-nav-menu').addClass('selected').slideDown();
        }			
	});
	/**
	 *  Method modifies #mobile-nav-menu to indicate which menu link was clicked
	**/
	$(document).on('click', '#mobile-nav-menu > ul > li', function(event) {
        if ($(this).has('ul')) {
            if ($(this).hasClass('selected')) {
                $(this).removeClass('selected')
                       .find('ul').slideUp();
            }
            else {
                $(this).addClass('selected')
                       .find('ul').slideDown();
            }
        }
    });
});

$(window).load(function() {
	/// Add Custome javacript methods.....

});

