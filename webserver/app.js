
var express = require('express'),
	app = express(),
	AWS,
	dynamoDB;
var bodyParser = require('body-parser');
app.use(express.static(__dirname+"/public"));
app.use(bodyParser.json());

//
//	Allow access to aws and dynamoDB when ready to deploy
//	Also allows creation and deletion of tables when
// (in a real case scenario this would be really dangerous)
//

if (process.env.NODE_ENV==='deploy'){

	AWS = require('aws-sdk');
	AWS.config.loadFromPath('./config.json');
	dynamoDB = new AWS.DynamoDB();

	var TABLE1 = require('./table1.json'),
		TABLE2 = require('./table2.json'),
		TABLE3 = require('./table3.json');


	app.get('/CREATE',function(req,res){
		dynamoDB.createTable(TABLE1, function (err) {
			if (err) console.log(err);
		});
		dynamoDB.createTable(TABLE2, function (err) {
			if (err) console.log(err);
		});
		dynamoDB.createTable(TABLE3,function (err) {
			if (err) console.log(err);
		});
	});

	app.get('/DESTROY',function(req,res) {
		dynamoDB.deleteTable({TableName : Table1.TableName }, function(err) {
	 		if (err) console.log(err);
		});
		dynamoDB.deleteTable({TableName : Table2.TableName }, function(err) {
	 		if (err) console.log(err);
		});
		dynamoDB.deleteTable({TableName : Table3.TableName }, function(err) {
	 		if (err) console.log(err);
		});

	});

}


app.get('/', function (req,res) {
	res.render("./public/index.html");
});

app.post('/bigbirds', function (req,res){
	//var date = req.body.date;
	console.log(req.body);
	res.status(200).send("tower-10");
});
app.post('/totalweight', function (req,res){
	 console.log(req.body);
	res.status(200).send("135907");
});

app.get('/unseen', function (req,res) {
	console.log(req.body);
	res.status(200).send(["hawk-49","sparrow-201","parrot-307","hawk-19"]);
});

app.listen( process.env.PORT || 3000 );

console.log("--- Birdwatch app webserver ---");
console.log("Running in http://localhost:" + (process.env.PORT||3000));
