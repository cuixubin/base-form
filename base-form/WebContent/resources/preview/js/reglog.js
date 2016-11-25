$(document).ready(function(e) {
   $('.select').click(function(e){
	$(this).find('ul').hide();
	$(this).find('ul').show();
	e.stopPropagation();
  });
  $('.select').mouseleave(function(e){
  	$(this).find('ul').hide();
  });
  $('.select li').click(function(e){
	var val = $(this).text();
	$(this).parents('.select').find('input').val(val);
	$('.select ul').hide();
	e.stopPropagation();
  }); 
  $('.inputbox input').hover(
  function(){
	  $(this).css('border','1px solid #2598D6');})
  $('.select input').hover(
  function(){
	  $(this).css('border','0px');})
  $('.inputbox input').mousedown(
  function(){
	  $(this).css('border','1px solid #2598D6');})
  $('.inputbox input').mouseleave(
  function(){
	  $(this).css('border','1px solid #A8A8A8');})
  $('.select input').mousedown(
  function(){
	  $(this).css('border','0px');})
  $('.select input').mouseleave(
  function(){
	  $(this).css('border','0px');})
 
	  
});

