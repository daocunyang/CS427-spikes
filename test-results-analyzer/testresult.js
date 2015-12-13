function renderEmailForm() {
       var form = '<form id="submitIdAndEmail" method="post">' + 'netID:' + '<br/>';
       form += '<input type="text" id="input_netid"  name="netid" placeholder="Enter your netID here">';
       form += '<br/><br/>' +  'Email:' + '<br/>' ;
       form += '<input type="text" id="input_email"  name="email" placeholder="Enter your email here">';
       form += '<br/><br/>' + '<input type="submit" value="Submit" onclick="submitIdEmail()"></form><br/>';

       return form;
}


function renderQueryEmailForm() {
    var queryForm = '<form method="post">' + 'Please enter a netID:' + '<br/>' + '<input type="text" name="inputnetID" id="getEmailByNetID">' + '<br/><br/>';
    queryForm += '<input type="submit" value="Submit" id="get_email" onclick="getIdEmail()"></form><br/>';

    return queryForm;
}


function getIdEmail() {

   var netid = $j("input#getEmailByNetID").val();
    if (!netid) {
    	confirm("Please enter a netID to start your query.");
    	return;
    }

 var emailAddr;

   remoteAction.getEmail(netid, $j.proxy(function(t) {
         emailAddr = t.responseObject();
      if (emailAddr) {
        alert(emailAddr);
        return;
      }
    }, this));

}

function submitIdEmail() {

  var netID = $j("input#input_netid").val();
  var emailAddr = $j("input#input_email").val();

  if (!netID) {
     confirm("Please fill in netID");
     return;
  }

  if (!emailAddr) {
     confirm("Please fill in email address");
     return;
}

   var jsonData = {
   "netid": netID,
   "email": emailAddr
  };

remoteAction.submitEmail(JSON.stringify(jsonData), $j.proxy(function(res) {
            var response = res.responseObject();
            if (response !== "success") {
              alert(response);
              //  alert("Something went wrong. We were unable to save your netID and email address.");
            }

            else {
           //  Clear inputs that were used to submit form
                 $j("input#input_netid").empty();
                  $j("input#input_email").empty();
           }
       }, this));
 }


