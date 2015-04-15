function initScrollTop(){
    $(function() {
        $.scrollUp({
          scrollName: 'scrollUp',
          topDistance: '100',
          topSpeed: 300,
          animation: 'fade',
          animationInSpeed: 200,
          animationOutSpeed: 200,
          scrollText: ''
        });
    });
}

function showModalContent(text){
	var modal = $.scojs_modal({
		  title: text,
		  onClose: function() {
		    this.destroy();
		  }
	});
	modal.show();
}

function showModalContentTop(text, topStr){
	var modal = $.scojs_modal({
		  title: text,
		  top:topStr,
		  onClose: function() {
		    this.destroy();
		  }
	});
	modal.show();
}

