$(document).ready(function() {
	$('#loginForm').validator();

	var error = $('.alert-danger');

	if ( error.length ) {
		error.hide().slideDown();
	}

    setInterval(function() {
        //if ( ($('input[name="email"]').val() && $('input[name="password"]').val() ) || $("input:-webkit-autofill").length > 0 ) {
    	try {
	    	var messageLength = $("input:-webkit-autofill").length;
	        if ( messageLength > 0 ) {
	            $('#signin').removeAttr('disabled');
	        }
    	} catch(e){}
    }, 200);
});
