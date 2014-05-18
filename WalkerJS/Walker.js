// to run you must install "node js" http://nodejs.org
// then from the terminal run :node Walker.js
 
var fs = require('fs');
//enter in an ast file in this string below
// in my example I ran the walker AllWordAST.txt
var file = __dirname + '/AstToWalkHere'; // ex /ast.txt or /ast.json 
var content;

// reads in the file then processes(walks) it 
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
    // example of why the walker is useful
    // searches the ast for all variable and function names
    function process(key,value) {
    if (key == "name")
        name.push(value);
    return name
    }
	// logic to do the actual walking 
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