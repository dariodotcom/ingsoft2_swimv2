$(function() {
	$("#addAbilityInput").autocomplete({
		source : "/SwimWeb/home/abilityList",
		autoFocus : true,
		delay : 500
	});
});