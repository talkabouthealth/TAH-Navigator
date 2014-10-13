(function($){
	$.fn.addTooltipsToTable = function() {
		var gradientHeight = 35; // this is the shadow that peeks out from the bottom of our div
		return $(this).each(function(i) {
			var div = $(this);

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

})(jQuery);
