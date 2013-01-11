//Ability selector autocomplete
$(function() {
	$(".abilityInput").autocomplete({
		source : "/SwimWeb/home/abilityList",
		autoFocus : true,
		delay : 250
	});

	//Fake image input
	var realInput = $('#realImageInput');
	var fakeInput = $('#fakeImageInput');
	var control = $('#fakeImageSubmit');

	control.click(function(){
	    realInput.click();
	});

	fakeInput.click(function(){
	    realInput.click();
	});

	realInput.change(function(){
	    text = $(this).val();
	    fakeInput.val(text);
	});
	
	
	var currentForm = null;

	// review options
	window.createReviewForm = function(reqId, accept, node) {
		var target = node.parentNode.parentNode;

		if (currentForm) {
			currentForm.remove();
		}

		// Create HTML
		var messageBox = $('<div class="messageContainer reviewMessage"></div>')
				.hide();
		var message = $('<div class="message"></div>');
		var arrow = $('<div class="arrow">&nbsp;</div>');
		messageBox.append(arrow);
		messageBox.append(message);

		var form = $('<form action="/SwimWeb/admin/respond" method="post"></form>');
		form.append($('<input type="hidden" name="accept" value="' + accept
				+ '"/>'));
		form.append($('<input type="hidden" name="request" value="' + reqId
				+ '"/>'));
		form.append($('<textarea class="inputtext" name="review"></textarea>'));
		form
				.append($('<div class="submitLine"></div>')
						.append(
								$('<input type="submit" class="inputsubmit" value="Rispondi"/>')));
		message.append(form);

		// Append form
		currentForm = messageBox;
		$(target).append(messageBox);

		// Compute element position
		var ground = $(target).offset().left;

		var nodeOffset = $(node).offset().left - ground;
		var arrowOffset = parseInt($(arrow).css("marginLeft").replace("px", ""));

		messageBox.css("marginLeft", nodeOffset - arrowOffset + 7);
		$(messageBox).show();
	};

	// Feedback mark selector
	var MIN_MARK = 1;
	var MAX_MARK = 5;

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