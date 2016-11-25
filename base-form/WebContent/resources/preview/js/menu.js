// menu JS

$(document).ready(function(){
	$('.mainnav li').mouseover(function(){
		var ulChild = $(this).find('.mainnav_childnav');
		ulChild.show();
	});
	$('.mainnav li').mouseleave(function(){
		var ulChild = $(this).find('.mainnav_childnav');
		ulChild.hide();
	});		
});