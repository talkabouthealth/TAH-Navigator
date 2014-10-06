(function($){
	var steps;

	var Step = function(opts) {
		this.index = opts.index;
		this.step = opts.el;
		this.header = this.step.find('.header');
		this.content = this.step.find('.content');
		this.status = this.header.find('.status');
		this.chevron = this.status.find('.fa');

		this.state = 'closed';

		this.open = function() {
			this.content.slideDown();
			this.step.addClass('open');
			this.chevron.removeClass('fa-chevron-down');
			this.state = 'open';

			$(steps).each(function(i, s){
				var step = $(s).data('step');
				if ( this.index !== step.index && step.state === 'open' ) {
					step.close();
				}
			}.bind(this));
		};

		this.close = function() {
			this.content.slideUp();
			this.step.removeClass('open');
			this.chevron.addClass('fa-chevron-down');
			this.state = 'closed';
		};

		this.toggle = function() {
			if ( this.state === 'closed' ) {
				this.open();
			} else {
				this.close();
			}
		};

		this.header.click(function(){
			this.toggle();
		}.bind(this));

	};

	$(document).ready(function() {
		steps = $('.registration-steps .step');
		steps.each(function(i, el){
			$(this).data('step',new Step({
				el: $(el),
				index: i
			}));
		});
	});
})(jQuery);


