module.exports = {
	checkWaivers: function(req, res, next){
		var cart = req.session.cart;
		// Express不期望中间件返回值，也不会用返回值做任何事，此处只是简写的next(); return;
		if(!cart) return next();

		if(cart.some(function(i) { 
			return i.product.requiresWaiver; 
		})){
			if(!cart.warnings) cart.warnings = [];
			cart.warnings.push('One or more of your selected ' + 'tours requires a waiver.');
		}
		next();
	},
	checkGuestCounts: function(req, res, next){
		var cart = req.session.cart;
		if(!cart) return next();

		if(cart.some(function(item){ return item.guests >
			item.product.maximumGuests; })){
				if(!cart.errors) 
					cart.errors = [];
				cart.errors.push('One or more of your selected tours ' +
				'cannot accommodate the number of guests you ' +
				'have selected.');
		}
		next();
	}
}