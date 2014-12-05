siege -c100 -t5M -d10 http://birdwatch-lb-525863057.us-west-2.elb.amazonaws.com/test
sleep 30
siege -c200 -t5M -d10 http://birdwatch-lb-525863057.us-west-2.elb.amazonaws.com/test
sleep 30
siege -c300 -t5M -d10 http://birdwatch-lb-525863057.us-west-2.elb.amazonaws.com/test
sleep 30
siege -c500 -t5M -d10 http://birdwatch-lb-525863057.us-west-2.elb.amazonaws.com/test
sleep 30
siege -c600 -t5M -d10 http://birdwatch-lb-525863057.us-west-2.elb.amazonaws.com/test
sleep 30
siege -c1000 -t5M -d10 http://birdwatch-lb-525863057.us-west-2.elb.amazonaws.com/test
