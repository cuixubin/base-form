// JavaScript Document
$(document).ready(function(){
	$('.mainnav li').click(function(){
		$(this).parent().find('li.selected').removeClass('selected');
		$(this).addClass('selected');
	});
});