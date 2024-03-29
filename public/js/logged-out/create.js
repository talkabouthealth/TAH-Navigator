/*
(function($){
	var steps;

	var Step = function(opts) {
		this.index = opts.index;
		this.step = opts.el;
		this.header = this.step.find('.headerc');
		this.content = this.step.find('.content');
		this.status = this.header.find('.status');
		this.statusSpan = this.status.find('span');
		this.chevron = this.status.find('.fa');
		this.inputs = this.content.find('input');

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

			this.content.find('input:visible:first').focus();
		};

		this.close = function() {
			this.content.slideUp();
			this.step.removeClass('open');
			this.chevron.addClass('fa-chevron-down');
			this.state = 'closed';
			checkInputs();
		};

		this.toggle = function() {
			if ( this.state === 'closed' ) {
				this.open();
			} else {
				this.close();
			}
		};

		var complete = function(done) {
			if ( done ) {
				this.statusSpan.attr('class', 'completed').html('Completed <i class="fa fa-check-circle-o fa-3x"></i>');
			} else {
				this.statusSpan.attr('class', 'not-yet-completed').html('Not Yet Completed');
			}

		}.bind(this);

		var checkInputs = function() {
			var missing = false;

			if ( this.content.find('div.has-error').length ) {
				missing = true;
			} else {
				this.inputs.each(function(){
					var input = $(this);
					if ( input.attr('type') === 'checkbox' ) {
						if ( !input.prop('checked')) {
							missing = true;
						}
					} else {
						if (! input.val()) {
							missing = true;
						}
					}
				});
			}

			complete( !missing );
		}.bind(this);

		this.inputs.change(checkInputs);

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

			$(this).find('.content .form-group:last-child input').keydown(function(e){
				if ( e.keyCode === 9 && e.shiftKey === false ) {
					e.preventDefault();
					if ( i < steps.length-1 ) {
						$(steps[i]).data('step').close();
						$(steps[i+1]).data('step').open();
					}
				}
			});

			var checkStatus = function() {
				var errors = false;
				var content = $(this).parents('.content');
				content.find('div.form-group').each(function(){
					if ( $(this).hasClass('has-error') || $(this).find('.with-errors').html() || !$(this).find('input').val() ) {
						errors = true;
					}
				});
				if ( errors === false ) {
					content.find('.complete').stop().animate({opacity: 1});
				} else {
					content.find('.complete').stop().animate({opacity: 0});
				}
			};

			$(this).find('.content input').keyup(checkStatus).blur(checkStatus).focus(checkStatus);
		});

		$(steps[0]).data('step').open();

	});
})(jQuery);



*/