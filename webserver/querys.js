

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

//Query table 1
exports.query1 = function(req,res){


	params = {
		"TableName" : 'table1',
		"AttributeToGet" : [
			'towerId'
		],
		"KeyConditions" : {
			date : {
				"ComparisonOperator" : 'EQ',
				"AttributeValueList" : [
					S : req.date
				]
			}
		} 
	};
	dynamodb.query(params, function(err,data){
		if (err){
			console.log(err,err.stack);
			return res.status(404).end();
		}
		console.log(data);
		return res.status(200).send(data);


	});
}
//Query table 2
exports.query2 = function(req,res){


	params = {
		"TableName" : 'table2',
		"AttributeToGet" : [
			'weight'
		],
		"KeyConditions" : {
			date : {
				"ComparisonOperator" : 'EQ',
				"AttributeValueList" : [
					S : req.date
				]
			},
			towerId: {
				"ComparisonOperator" : 'EQ',
				"AttributeValueList" : [
					S : req.towerId
				]	
			}
		} 
	};
	dynamodb.query(params, function(err,data){
		if (err){
			console.log(err,err.stack);
			return res.status(404).end();
		}
		console.log(data);
		return res.status(200).send(data);


	});
}
//Query table 3
exports.query3 = function(req,res){

	//For date as string comparison to work, dates must be stored as YYYY/MM/DD


	params = {
		"TableName" : 'table3',
		"AttributeToGet" : [
			'birdId'
		],
		"KeyConditions" : {
			lastDate : {
				"ComparisonOperator" : 'LT',
				"AttributeValueList" : [
					S : weekBefore
				]
			},
		} 
	};
	dynamodb.query(params, function(err,data){
		if (err){
			console.log(err,err.stack);
			return res.status(404).end();
		}
		console.log(data);
		return res.status(200).send(data);


	});
}


//
// Returns the day one week before, in the same day of the week
// #returns (String) -> "YYYY/MM/DD"
//

var weekBefore = function(){
	

	var maxDays = [31,28,31,30,31,30,31,31,30,31,30,31];
	
	var d = new Date();
	var currYear=String('000'+d.getFullYear()).slice(-4);
	var currMonth=String('0'+(d.getMonth()+1)).slice(-2);
	var currDay = String('0'+d.getDate()).slice(-2);
	
	if (currDay-7 < 1){	//last week was last month
		if (currMonth-1 < 1){	//if is january
			currDay = String(maxDays[12-1]+(currDay-7)); //no need for padding (works only for 7)
			currMonth = String(12);
			currYear = String(currYear-1);

		}
		else{
			currDay = String(maxDays[currMonth-2]+(currDay-7));
			currMonth = String('0'+(currMonth-1)).slice(-2);
		}
	}

	return currYear+"/"+currMonth+"/"+currDay;

}