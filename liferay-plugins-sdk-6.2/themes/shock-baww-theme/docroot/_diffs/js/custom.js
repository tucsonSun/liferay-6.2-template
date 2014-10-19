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
	$(document).on('submit', '#header-search form', function(event) {
		event.preventDefault();
		var searchValue = $(this).children('input').val();
		window.location = '/web/guest/home-page?p_p_id=3&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&_3_struts_action=%2Fsearch%2Fsearch&_3_redirect=%2Fweb%2Fguest%2F&_3_keywords='+searchValue+'&_3_groupId=0&x=15&y=15';
	});

	/**
	 *  Method modifies Navigation Bar to be responsive for mobile
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
	 *  Method modifies Liferay Navigation menu to be click-able
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

