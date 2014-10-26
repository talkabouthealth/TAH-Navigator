$(document).ready(function() {

$(window).scroll(function(){
		if ($(this).scrollTop() > 5) {
			var withd = $('.run-fix').width();
			
			$('.fixed-right-div').css('width',withd).css('position','fixed').css('top','0');

		} else {

				$('.fixed-right-div').css('width','100%').css('position','static');
		}
	}); 




    $('.mobile-an').click(function() {
    $('.list-left-link').slideToggle();
});


  $('.green-title').click(function() {	
    $(this).next('div.show-div-list').slideToggle();
	$(this).toggleClass('ex-coll');
});

		$('#test4').jdPicker({date_format:"dd FF YYYY", month_names: ["Janvier", "F&eacute;vrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Ao&ucirc;t", "Septembre", "Octobre", "Novembre", "D&eacute;cembre"],short_day_names: ["D", "L", "M", "M", "J", "V", "S"]});


		var hauteur=0;
		$('.code').each(function(){
			if($(this).height()>hauteur) hauteur = $(this).height();
		});

		$('.code').each(function(){ $(this).height(hauteur); });


});
