
var express = require('express'),
	app = express();

app.use(express.static(__dirname+"/public"));


app.get('/',function(req,res) {
	res.render("./public/index.html");
});

app.get('/sampaio',function(req,res){
	res.status(200).send('Sampaio');
});

app.listen(3000);