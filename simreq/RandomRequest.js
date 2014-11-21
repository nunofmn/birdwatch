
var request = require('request');


var RandomRequest = function (url){

	if (!url)
		return new Error ('No functions to be called provided')

	var _timeout = 1000;
	this.count = 0;
	this.url = url;

	this.setTimeToLeave = function (timeout) {
		if (_timeout > 10000)
			return new Error('max timeout is 10secs');
		_timeout = timeout;
	};
	this.getTimeToLeave = function(){
		return _timeout;
	};
}
RandomRequest.prototype = {

	doRequest: function (numReq,done) {




		var start = new Date().getTime();
		var runtime = 0;
		var count = 0;						

		var statistics = {
			cycles: [],
			runtime: 0,
			realRuntime: 0
		}
		var self = this;

		var intervalFun = function (fun) {
			
			var startMe = new Date().getTime();
			var end;

			if (self.count>=numReq)
				return;

			fun(function (err,res,body) {
				
				var endMe = new Date().getTime();

				statistics.cycles.push({
					start: startMe-start,	//start of cycle
					time: endMe - start,
					delay:  endMe-startMe,	//time to wait for req answer
					code: (err)?-1:res.statusCode,
				});


				if (statistics.cycles.length === numReq ){
					statistics.realRuntime= endMe-start;
					done(statistics);
				}
			});

			self.count += 1;

			end = new Date().getTime();
			statistics.runtime = end-start;

			
		}
		var i = 0;
		while(i++<numReq){
			setTimeout(intervalFun, 0, function (cb){
				return request({url:self.url,timeout:self.getTimeToLeave()},cb);
			});
		}
		self.count = 0;
	}
};

module.exports = RandomRequest;

