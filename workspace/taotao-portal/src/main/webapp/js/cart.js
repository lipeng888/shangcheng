var TTCart = {
	load : function(){ // 加载购物车数据
		
	},
	itemNumChange : function(){
		//class选择器，其实获取的就是+号按钮，绑定点击事件
		$(".increment").click(function(){//＋
			//获取同级的input元素，其实就是获取的商品数量的input
			var _thisInput = $(this).siblings("input");
			//把商品数量加一并且赋值给自己
			_thisInput.val(eval(_thisInput.val()) + 1);
			//http://www.taotao.com/service/cart/update/num/1159198734/100
			$.post("/service/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val(),function(data){
				//刷新总价
				TTCart.refreshTotalPrice();
			});
		});
		$(".decrement").click(function(){//-
			var _thisInput = $(this).siblings("input");
			if(eval(_thisInput.val()) <= 1){
				return ;
			}
			_thisInput.val(eval(_thisInput.val()) - 1);
			$.post("/service/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val(),function(data){
				TTCart.refreshTotalPrice();
			});
		});
		$(".quantity-form .quantity-text").rnumber(1);//限制只能输入数字
		$(".quantity-form .quantity-text").change(function(){
			var _thisInput = $(this);
			$.post("/service/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val(),function(data){
				TTCart.refreshTotalPrice();
			});
		});
	},
	refreshTotalPrice : function(){ //重新计算总价
		var total = 0;
		$(".quantity-form .quantity-text").each(function(i,e){
			var _this = $(e);
			total += (eval(_this.attr("itemPrice")) * 10000 * eval(_this.val())) / 10000;
		});
		$(".totalSkuPrice").html(new Number(total/100).toFixed(2)).priceFormat({ //价格格式化插件
			 prefix: '￥',
			 thousandsSeparator: ',',
			 centsLimit: 2
		});
	}
};

$(function(){
	TTCart.load();
	TTCart.itemNumChange();
});