(function($){
	$(document).ready(function(){
		var divs = $('table.patient-dashboard td div');
		if (divs.length) {
			divs.addTooltipsToTable();

			$(window).resize(function(){
				divs.addTooltipsToTable();
			});
		}

	});
})(jQuery);