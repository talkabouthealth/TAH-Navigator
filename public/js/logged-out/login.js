$(document).ready(function() {
	$('#loginForm').validator();

	var error = $('.alert-danger');

	if ( error.length ) {
		error.hide().slideDown();
	}
});
