;
(function (){

	'use strict';
	var RandomRequest = require('./RandomRequest.js');

	if (process.argv.length<3){
		console.log('\nThis will do requests to http://localhost:3000/unseen');
		console.log('usage: node simreq.js <number of requests>\n\n');
		process.exit(1);
	}

	var numberOfReqs = parseInt(process.argv[2]);

	var url ='http://localhost:3000/unseen';

	var rd = new RandomRequest(url);

	rd.doRequest(numberOfReqs,function (stats) {
			console.log(stats);	
	});

	//var reqFunctions = [	function (cb){ return request.post(url+'/bigbirds',cb);} ,
	//						function (cb){ return request.post(url+'/totalweight',cb);},
	//						function (cb){ return request(url+'/unseen',cb);}];
	//var reqFunctions = [ function (cb) {return request({url:url,timeout:300},cb);}];


	/*var max=0;
			stats.cycles.forEach(function (ele){
				 max = (ele.delay > max && ele.code!= -1)?ele.delay:max;
			});*/

})();

