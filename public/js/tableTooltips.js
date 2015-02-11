(function($){
	$.fn.addTooltipsToTable = function() {
		var gradientHeight = 35; // this is the shadow that peeks out from
									// the bottom of our div
		return $(this).each(function(i) {
			var div = $(this);
			var maxHeight = parseInt(div.css('maxHeight'), 10);
			if (!$.isNumeric(maxHeight)) {
				maxHeight = 10;
			}
			
			var padding = parseInt(div.css('padding-top'), 10);
			if (!$.isNumeric(padding)) {
				padding = 10;
			}
			
			var height = div.height();
			var heightCutoff = maxHeight - (padding * 2) - gradientHeight;
								
				if (!div.hasClass('tooltipstered') ) {

					var child1 = div.children('span')[0];
					var child2 = div.children('span')[1];
					if($(child1).hasClass("lastNoteTitle")&&$(child2).hasClass("lastNoteDesc"))
						{							
							contents="<strong style='font-weight:bolder;font-size:18px;text-align:left'>"+$(child1).html()+"</strong>";
							contents+="<hr style='margin:8px 0;'/><p style='font-size:14px;color:#fff;text-align:justify'>"+$(child2).html()+"</p>";
							div.tooltipster({
								content : contents,
								contentAsHTML : true,
								maxWidth : 800
							});
						}
					else if(height >= heightCutoff)
						{							
							div.tooltipster({
								content : div.html(),
								contentAsHTML : true,
								maxWidth : 800
							});
						}					
				
			} else if (div.hasClass('tooltipstered') && height < heightCutoff) {
				div.tooltipster('destroy');
			}
		});
	};
})(jQuery);
