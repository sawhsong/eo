/**
 * ContactUs.js
 */
var popup;

$(function() {
	$("button").click(function() {
		popup = commonJs.openPopup({
			popupId:"contactus",
			url:"/index/contact/contactuspop",
			paramData:{},
			header:"Contact Us",
			width:900,
			height:600
		});
	});

	$(window).load(function() {
	});
});