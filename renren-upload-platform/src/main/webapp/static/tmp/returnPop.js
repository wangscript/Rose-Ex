var returnPop = null;
$.widget("dialog.returnGoods", $.ui.dialog, {
	_createWidget: function(msg) {
		this._super({
				buttons: [{
					text: '确认',
					"class": 'ui-button-confirm',
					click: function() { //btn 
						var objPop = $(this); //obj 弹窗本身
						returnPop.submitReturnPrice(objPop);
					}
				}, {
					text: '取消',
					"class": 'ui-button-cancel',
					click: function() { //btn 
						var objPop = $(this); //obj 弹窗本身
						objPop.dialog("instance").destroy();
					}
				}],
				title: "",
				dialogClass: 'ui-dialog-confirm',
				width: 400,
				height: 240	
			}, '<div>' +
					'<div class="tip-txt-wrap">' +
						'确定已经拿到货或者收到退货' +
						'<div class="tip-txt">' +
							'退货耗损成本 <input value="0" id="return_price" class="pop-txt" type="text">' +
						'</div>' +
						'<p id="tip_price" class="tip-price">请输入正确的退货价格成本！</p>' +
					'</div>' +
				'</div>');
	}
});
(function() {
	$(function() {
		returnPop = {
			domain: 'http://fenqi.renren.com',
			api: {
				getAddress: '/order/getAddress'
			},
			init: function() {
				var self = this,
				//dongyu.cai+{给退货按钮绑弹窗事件
				goodsReturn = $('#returnGoods_btn');
				goodsReturn.bind('click', function() {
					self.returnGoods()
				});
				//}
			},

			// 调用退货成本弹窗
			returnGoods: function() {
				var self = this;

				$.dialog.returnGoods();
			},

			// 点击“确定”按钮的事件处理函数
			submitReturnPrice: function(objPop) {
				var self = this,
					refundLoss_ = $('#return_price').val(),
					tip_price = $('#tip_price'),
					regPrice = /^\d+(\.\d+)?$/;
					regReuslt = regPrice.test(refundLoss_);
				if(regReuslt){
					//dongyu.cai+{
					returnGoods(refundLoss_);
					//}
				} else {
					tip_price.show();
				}
			}

		};
		returnPop.init();
	});
})();