
/*************** wow jQuery *****************/

jQuery(document).ready(function($) {
	$('.counter').counterUp({
		delay: 10,
		time: 1000
	});
});


/*************** wow jQuery *****************/

wow = new WOW( 
	{
		animateClass: 'animated',
		offset:      30
	}
);
wow.init();

/************* Bootstrap ToolTip ****************/

$(function () {
	$('[data-toggle="tooltip"]').tooltip();
});

/************* Preloader jQuery ****************/

$(document).ready(function() {
	
	setTimeout(function() {
		$('body').addClass('loaded');
	}, 3000);
	
});

/************* feature's mouse over and leave jQuery ****************/

$(document).ready(function(){

	var $iphoneOne = $(".screen-1");
	var $iphoneTow = $(".screen-2");
	var $iphoneThree = $(".screen-3");
			
	/* mouse leave */
	$(".screen-right").mouseenter(function() {					
		$iphoneOne.addClass("hidden");				
		$iphoneTow.removeClass("hidden");														
	});
	
	$(".screen-left").mouseenter(function() {
		
		$iphoneOne.addClass("hidden");				
		$iphoneThree.removeClass("hidden");				
	});
	
	/* mouse enter */
	$(".screen-left, .screen-right").mouseleave(function() {
		
		$iphoneOne.removeClass("hidden");				
		$iphoneTow.addClass("hidden");													
		$iphoneThree.addClass("hidden");													
	});
});	

/************* Smooth scroll ****************/
$(function() {	
	$('.tbg-home-slider a[href*=#]').click(function() {
		if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') || location.hostname == this.hostname) {

			var target = $(this.hash);
			target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
			if (target.length) {
				$('html,body').animate({
					scrollTop: target.offset().top
				}, 800);
				return false;
			}
		}
	});
});

/************* Scroll To Top ****************/
$(window).scroll(function() {
	if ($(this).scrollTop() > 100) {
		$('.scroll-up').fadeIn();
	} else {
		$('.scroll-up').fadeOut();
	}
});

/************* stellar ****************/
$.stellar({
	horizontalScrolling: false,
	responsive: true
});	

/************* Feature iphoone ****************/		
// bootstrap scroll spy
$('body').scrollspy({ target: '.sidebar' });

// Closes the sidebar menu
$("#menu-close, body ").click(function(e) {
	e.preventDefault();
	//$("#sidebar-wrapper").toggleClass("active");
	$("#sidebar-wrapper").removeClass("active");
	//$("#menu-toggle").removeClass("hide");
});

// Opens the sidebar menu
$("#menu-toggle").click(function(e) {
	e.preventDefault();
	$("#sidebar-wrapper").toggleClass("active");
	//$("#menu-toggle").addClass("hide");
	e.stopPropagation();				
});	

/************* fit vidds video id jQuery ****************/
$(".vendor").fitVids();

/************* custom class add in modal jQuery ****************/
(function($) {

$(document)
	.on( 'hidden.bs.modal', '.modal', function() {
		$(document.body).removeClass( 'modal-scrollbar' );
	})
	.on( 'show.bs.modal', '.modal', function() {
		// Bootstrap's .modal-open class hides any scrollbar on the body,
		// so if there IS a scrollbar on the body at modal open time, then
		// add a right margin to take its place.
		if ( $(window).height() < $(document).height() ) {
			$(document.body).addClass( 'modal-scrollbar' );
		}
	});

})(window.jQuery);
