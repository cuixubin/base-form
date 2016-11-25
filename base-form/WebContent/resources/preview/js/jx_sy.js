// JavaScript Document
$(document).ready(function() {
    $(".bannerbox").slide({ 
		mainCell:".banner", 
		titCell:".focusbtn li", 
		effect:"fade",
	    autoPlay:true,
		interTime:4000,
		delayTime:500
		});		
	$(".photofocus").slide({
		mainCell:".focusbigimg",
		titCell:".focusbigimg li", 
		prevCell:".btnl",
		nextCell:".btnr",
		effect:"leftLoop",
		autoPlay:false,
		trigger:"click",
		vis:5,
		scroll:1
	    });	
});