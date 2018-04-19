onmessage = function(event) {
    var star=new Date();
    var before=star.getTime();
    while(true){
        var date=new Date();
        var now=date.getTime();
        if(now-before>3000){
            postMessage("done");
            before=now;
        }
    }
}
