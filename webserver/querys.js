var AWS = require('aws-sdk');
var dynamodb = new AWS.DynamoDB({region: 'us-west-2',  apiVersion: '2011-12-05'});
var moment = require('moment');

//Query table 1
exports.query1 = function(req,res){
	params = {
		"TableName" : 'BW-MAXWINGSPAN',
        Key: {
            "HashKeyElement": {
                "S": req.body.date
            }
        }
	};
	dynamodb.getItem(params, function(err,data){
		if (err){
			console.log(err,err.stack);
			return res.status(404).end();
		}
		console.log(data);
		return res.status(200).send(data.Item.towerid.S);
	});
}
//Query table 2
exports.query2 = function(req,res){
	params = {
		"TableName" : 'BW-SUMWEIGHT',
        Key: {
            "HashKeyElement": {
                "S": req.body.date + ":" + req.body.towerid
            }
        }
	};
	dynamodb.getItem(params, function(err,data){
		if (err){
			console.log(err,err.stack);
			return res.status(404).end();
		}
		console.log(data);
		return res.status(200).send(data.Item.weight.S);
	});
}
//Query table 3
exports.query3 = function(req,res){
    var date = moment().subtract(7, 'days').format('YYYY-MM-DD');
    console.log("Number of birds not seen since: ", date);

    params = {
        "TableName" : 'BW-LASTOBSERVATION',
        ScanFilter: {
            "lastseen": {
                ComparisonOperator: 'LE',
                AttributeValueList: [{
                    S: date
                },
                ]
            }
        }
    };
	dynamodb.scan(params, function(err,data){
		if (err){
			console.log(err,err.stack);
			return res.status(404).end();
		}

		console.log("Number of items", data.Items.length);

        var birdsid = data.Items.map(function(elem) {
            return elem.birdid.S;
        });

		return res.status(200).send(birdsid);
	});
}

//Simple and non-exhaustive date validator middleware
exports.validateDate = function(req,res,next){
	var date = req.date;

	if (!date) return res.status(404).end();

	var parts = date.split('/')
	var validDay = parts[0].match(/(^0[1-9])|(^[1-2][0-9])|(^3[0-1])/);
	var validMonth = parts[1].match(/(^0[1-9])|(^1[0-2])/);
	var validYear = parts[2].match(/(^1[900-999])|(^2[000-015])/);

	if (validDay && validMonth && validMonth)
		return next();
	return res.status(404).send("Malformed Date");
};

exports.validateTowerId = function() {
	if (!req.towerId)
		return res.status(404).end();
	return next();
};
