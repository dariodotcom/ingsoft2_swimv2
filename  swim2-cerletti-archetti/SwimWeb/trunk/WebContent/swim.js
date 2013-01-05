//Ability selector autocomplete
$(function() {
	$(".abilityInput").autocomplete({
		source : "/SwimWeb/home/abilityList",
		autoFocus : true,
		delay : 500
	});

	// Feedback mark selector
	const MIN_MARK = 1;
	const MAX_MARK = 5;

	function FeedbackMarker(markerNode, inputNode) {
		this.inputNode = inputNode;
		this.marker = markerNode;
		this.markExprList = marker.getElementsByTagName("a");

		this.marker.addEventListener("mouseleave", this.onMarkerLeave
				.bind(this), false);

		window.console.log(this.markExprList);
		for ( var i = 0; i < this.markExprList.length; i++) {
			console.log("bind mark " + i);
			var markValue = i + 1;
			var node = this.markExprList[i];
			node.addEventListener("mouseover", this.showMark.bind(this,
					markValue), false);
			node.addEventListener("click", this.setMarkValue.bind(this,
					markValue), false);
		}

		return;
	}

	FeedbackMarker.prototype = {
		inputNode : null,
		marker : null,
		markExprList : null,
		currentMark : 0,

		setInputValue : function(value) {
			this.inputNode.setAttribute("value", value);
		},

		showMark : function(value) {
			window.console.log("show mark " + value);
			for ( var i = 0; i < 5; i++) {

				var elem = $(this.markExprList[i]);

				if (i < value) {
					elem.addClass("full");
				} else {
					elem.removeClass("full");
				}
			}
		},

		onMarkerLeave : function() {
			this.showMark(this.currentMark);
		},

		setMarkValue : function(mark) {
			if (mark < MIN_MARK || mark > MAX_MARK) {
				return;
			}

			this.currentMark = mark;
			this.setInputValue(mark);
		}
	}

	var marker = document.querySelector("#marker");
	var input = document.querySelector("#feedbackReview");
	if (marker && input) {
		new FeedbackMarker(marker, input);
	}
});