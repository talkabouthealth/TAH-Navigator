(function($){
	$(document).ready(function(){
		var divs = $('table.table td div');
		if (divs.length) {
			divs.addTooltipsToTable();

			$(window).resize(function(){
				divs.addTooltipsToTable();
			});
		}

	});
})(jQuery);