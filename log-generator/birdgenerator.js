var Chance = require('chance');
var fs = require('fs');
var moment = require('moment');
var chance = new Chance();
var watchtowers = ['Sintra', 'Lisboa', 'Setúbal', 'Coimbra', 'Viseu', 'Leiria', 'Porto'];
var birds = ['eagle', 'falcon', 'pigeon', 'seagull', 'pelican'];

var toTwoDigits = function(n) {
    return n > 9 ? '' + n : '0' + n;
}

var log = fs.createWriteStream('log.txt', {'flags': 'a'});

for(var i=0; i<process.argv[2]; i++) {
    var date = moment(chance.date({year: '2014', string: true, american: false}), 'DD/MM/YY').format('YYYY-MM-DD');
    var time = toTwoDigits(chance.hour()) + ':' + toTwoDigits(chance.minute()) + ':' + toTwoDigits(chance.second());
    var tower = watchtowers[chance.integer({min:0, max:watchtowers.length-1})] + '-' + chance.integer({min:1, max:50});
    var bird = birds[chance.integer({min:0, max:birds.length-1})] + '-' + chance.integer({min:1, max:100});
    var weight = chance.integer({min: 2, max: 20});
    var span = chance.integer({min: 10, max: 200});
    var weather = chance.integer({min: 0, max: 3});

    var line = [];
    line.push(tower,date,time,bird,weight,span,weather);

    log.write(line.join(', ') + '\n');
}


