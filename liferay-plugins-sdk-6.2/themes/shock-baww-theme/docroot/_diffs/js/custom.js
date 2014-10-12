jQuery(document).ready(function() {

	$.ajaxSetup({ cache: false });
	
	$(document).on('submit', '#header-search form', function(event) {
		event.preventDefault();
		window.location = '/web/guest/home?p_p_id=3&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&_3_struts_action=%2Fsearch%2Fsearch&_3_redirect=%2Fweb%2Fguest%2F&_3_keywords='+$(this).children('input').val()+'&_3_groupId=0&x=15&y=15';
	});

	$('.sign-in a').text("My Account");

	$('.result').appendTo('.search-layout');

	if ($('#cpmVersion:contains("CP-PROD")').length > 0) {
	    $("#cpmVersion").hide();
	}

	


	/**
	 * Method used to disable breadcrumb top parent link for the bread crumbs
	 * Method executes when HTML-Document is loaded and DOM is ready
	 * 1) Find the breadcrumb top parent element
	 * 2) If topParent exists and not equal to 'My Account' disable href
	 */

	//Find the breadcrumb top parent element
	//If it exists and not equal to 'My Account' disable
	var liElement = $( "ul.breadcrumb li.first" );
	//If exists then disable link
	if (liElement.length) {
		var aElement=liElement.children(":first");
		var value = $(aElement).text();
		if(value !="My Account") {
			//disable link
			$(aElement).attr('href','javascript:void(0)');
			//disable css style on link
			$(aElement).attr('style','cursor:default; color: #00325b;');
		}
	}

	/* Responsive Navbar */

	$(document).on('click', '#mobile-nav #nav-pop', function(event) {
		event.preventDefault();
		
	    if ($('#mobile-nav-menu').hasClass('selected')) {
            $('#mobile-nav-menu').removeClass('selected').slideUp();
        } else {
            $('#mobile-nav-menu').addClass('selected').slideDown();
        }			
	});

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

	$.fn.setAllToMaxHeight = function(){
		return this.height( Math.max.apply(this, $.map( this , function(e){ return $(e).height() }) ) );
	}

	$('#footer-wrapper footer .row-fluid ul').setAllToMaxHeight();

});

