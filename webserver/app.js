var express = require('express'),
	app = express();

var query = require('./querys');
var bodyParser = require('body-parser');
app.use(express.static(__dirname+"/public"));
app.use(bodyParser.json());

//
//	Allow access to aws and dynamoDB when ready to deploy
//	Also allows creation and deletion of tables when
// (in a real case scenario this would be really dangerous)
//

if (process.env.NODE_ENV==='deploy'){

}

app.get('/', function (req,res) {
	res.render("./public/index.html");
});

app.post('/bigbirds', function (req,res){
    query.query1(req,res);
});

app.post('/totalweight', function (req,res){
    query.query2(req,res);
});

app.get('/unseen', function (req, res) {
    query.query3(req,res);
});

app.get('/test', function (req, res) {
    for(var i=0; i<1000000; i++) {
        Math.tan(Math.random());
    }
    res.status(200).send("OK");
});

app.listen(8080);

console.log("--- Birdwatch app webserver ---");
console.log("Running in http://localhost:" + (process.env.PORT||3000));
