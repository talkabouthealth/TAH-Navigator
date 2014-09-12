(function($){
	var addTooltipsToTable = function(divs) {
//		var maxHeight = parseInt($(divs[0]).css('maxHeight'),10);
//		var padding = parseInt($(divs[0]).css('padding'),10);
		var gradientHeight = 35; // this is the shadow that peeks out from the bottom of our div

		$(divs).each(function(i, div){
			div = $(div);

			var maxHeight = parseInt(div.css('maxHeight'),10);
			if(!$.isNumeric(maxHeight)) {
				maxHeight = 10;
			}
			var padding = parseInt(div.css('padding-top'),10);
			if(!$.isNumeric(padding)) {
				padding = 10;
			}
			var height = div.height();
			var heightCutoff = maxHeight - (padding * 2) - gradientHeight; 

			if ( !div.hasClass('tooltipstered') && height >= heightCutoff ) {
				div.tooltipster({
					content: div.html(),
					contentAsHTML: true,
					maxWidth: 800
				});
			} else if ( div.hasClass('tooltipstered') && height < heightCutoff ) {
				div.tooltipster('destroy');
			}
		});
	};

	$(document).ready(function(){
		var divs = $('table.patient-dashboard td div');
		if (divs.length) {
			addTooltipsToTable(divs);

			$(window).resize(function(){
				addTooltipsToTable(divs);
			});
		}

	});
})(jQuery);