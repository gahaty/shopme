$(document).ready(function() {
	$("#logoutLink").on("click", function(e) {
		e.preventDefault();
		document.logoutForm.submit()
	});

	customizeDropDownMenu();
});

function customizeDropDownMenu(){
	$(".navbar .dropdown").hover(
		function() {
			$(this).find('.dropdown-menu').first().stop(true, true).delay(10).slideDown();
		},
		function() {
			$(this).find('.dropdown-menu').first().stop(true, true).delay(10).slideUp();
		}
	);

	$(".dropdown > a").click(function() {
		location.href = this.href;
	});
}