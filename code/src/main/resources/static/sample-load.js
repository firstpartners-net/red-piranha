
fetch("sample.json")
.then(function (response) {
  //json also returns a function
  return response.json();
})
.then(function (sampleData) {

  // The JSON data will arrive here

  //handle to main document
  var mainContainer = document.getElementById("sampleDataDiv");

  // Loop through 
  for(var i = 0; i < sampleData.length; i++) {
    console.log("json:"+sampleData[i].xlsFile)

    var div = document.createElement("div");
    var href ="<a href=''>Run</a>"
    div.innerHTML = href+' date: ' + sampleData[i].xlsFile + ' ' + sampleData[i].ruleFile + ' ' + sampleData[i].explanation;
    mainContainer.appendChild(div);
  }

})
.catch(function (err) {
  // If an error occured, you will catch it here
  console.log(err)
});





