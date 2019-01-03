<%/************************************************************************************************
* Description
* - 
************************************************************************************************/%>
<script type="text/javascript">
$(function() {
	$("#aDisclaimer").click(function(event) {
		window.open("/serviceresource/pages/disclaimer");
	});

	$("#aPrivacy").click(function(event) {
		window.open("/serviceresource/pages/privacy");
	});
});
</script>

<div id="divFooterLeft">
	<div class="clickablItems"><a id="aDisclaimer">Disclaimer</a></div>
	<div class="clickablItemsBreak"></div>
	<div class="clickablItems"><a id="aPrivacy">Privacy</a></div>
	<div class="clickablItemsBreak"></div>
	<div class="clickablItems"><a id="aContactUs">Contact Us</a></div>
</div>
<div id="divFooterCenter">
	<div style="width:100%;text-align:center;">&copy; Entity Solutions</div>
</div>
<div id="divFooterRight">
	<div class="clickablItems"><ui:icon className="fa-google-plus" status="display" script="window.open('https://plus.google.com/100110685160025735918')"/></div>
	<div class="horGap10"></div>
	<div class="clickablItems"><ui:icon className="fa-linkedin" status="display" script="window.open('https://www.linkedin.com/company/entity-solutions')"/></div>
	<div class="horGap10"></div>
	<div class="clickablItems"><ui:icon className="fa-twitter" status="display" script="window.open('https://twitter.com/entityworld')"/></div>
	<div class="horGap10"></div>
	<div class="clickablItems"><ui:icon className="fa-facebook" status="display" script="window.open('https://www.facebook.com/entitysolutions1')"/></div>
</div>