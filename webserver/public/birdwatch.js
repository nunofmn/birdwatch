
  var bigbirds = function(){
    var req = new XMLHttpRequest();

    req.onreadystatechange = function (){ 
     if ( req.readyState == 4 && req.status == 200 ) {
      document.getElementById('response').innerHTML = req.responseText;
      }
    };

    var val = document.getElementById("date_big").value;
    console.log(val);

    req.open("POST","/bigbirds");
    req.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	req.send(JSON.stringify({date:val}));
  }
  
  var totalweight = function(){
    var req = new XMLHttpRequest();

    req.onreadystatechange = function (){ 
     if ( req.readyState == 4 && req.status == 200 ) {
      document.getElementById('response').innerHTML = req.responseText
      }
    };

    var val = document.getElementById("date_fat").value,
    	val2 = document.getElementById("tower").value;

    req.open("POST","/totalweight");
    req.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	req.send(JSON.stringify({date:val,towerId:val2}));
  }

  var unseen = function(){
    var req = new XMLHttpRequest();

    req.onreadystatechange = function (){ 
     if ( req.readyState == 4 && req.status == 200 ) {
      document.getElementById('response').innerHTML = req.responseText;
      }
    };

    req.open("GET","/unseen");
	  req.send();
  }
 