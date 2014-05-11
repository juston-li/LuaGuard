var fs = require('fs');
var file = __dirname + '/tst.txt';
var content;
 
fs.readFile(file, 'utf8', function (err, data) {
  if (err) {
    console.log('Error: ' + err);
    return;
  }
 
  data = JSON.parse(data);
  content = data;
  processFile();
});
function processFile(){
    var name = [];
    function process(key,value) {
    if (key == "name")
        name.push(value);
    return name
    }

    function traverse(content,func) {
    for (var i in content) {
      func.apply(this,[i,content[i]]);  
      if (content[i] !== null && typeof(content[i])=="object") {
          traverse(content[i],func);

      }
    }
    }
    traverse(content,process);
    console.log(name);
}