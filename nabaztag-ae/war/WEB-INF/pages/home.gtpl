<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Nabaztag on Google App Engine</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="http://twitter.github.com/bootstrap/assets/css/bootstrap-1.1.1.min.css">
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script>
    (function(\$) {
    \$.fn.poll = function(options){
       var \$this = \$(this);
       // extend our default options with those provided
       var opts = \$.extend({}, \$.fn.poll.defaults, options);
       setInterval(update, opts.interval);
 
       // method used to update element html
       function update(){
           \$.ajax({
               type: opts.type,
               url: opts.url,
               success: opts.success
           });
       };
    };
 
    // default options
    \$.fn.poll.defaults = {
       type: "POST",
       url: ".",
       success: '',
       interval: 2000
    };
})(jQuery);
		\$("#lastRfid").poll({
		    url: "/web/lastRfid",
		    interval: 1000,
		    type: "GET",
		    success: function(data){
		        \$("#lastRfid > p > span").text(data);
		        \$("#lastRfid").show();
		    }
		});
		\$(document).ready(function(){
			\$('#rfidDialog').hide();
			\$('#submitRfid').click(function() {
	  			\$('#formRfid').submit();
			});
			\$('.closeRfid').click(function() {
	  			\$('#rfidDialog').hide();
			});
			\$('.editRfid').click(function() {
				var rfidKey = \$(this).text()
				\$("#rfidHeader").text("RFID " + rfidKey);
	  			\$('#rfidDialog').show();
	  			\$('#rfidKey').val(\$(this).attr("href"));
			});
		});
    </script>
    <style type="text/css">
		.color:focus { 
    outline: none; 
}

	</style>
  </head>
<body>
	<div id="rfidDialog" class="modal" style="position: float; top: 25%; left: 25%; margin: 0 auto; z-index: 1">
          <div class="modal-header">
            <h3 id="rfidHeader"></h3>
            <a href="#" class="close closeRfid">&times;</a>
          </div>
          <div class="modal-body">
            <form id="formRfid" class="form-stacked" method="post" action="/web/saveRfid">
            <input id="rfidKey" name="key" type="hidden" />
            <fieldset>
	            <div class="clearfix">
		            <label>Name</label>
		            <div class="input">
              			<input class="xlarge" id="name" name="name" size="30" type="text" />
            		</div>
          		</div>
          		<div class="clearfix">
		            <label>Color</label>
		            <input id="colorPicked" name="color" type="hidden" />
		            <div id="colorPicker" class="input" style="font-size:30px">
		            	<a class="color" href="#" style="color:blue">&#9632;</a>
		            	<a class="color" href="#" style="color:orange">&#9632;</a>
		            	<a class="color" href="#" style="color:greenyellow">&#9632;</a>
		            	<a class="color" href="#" style="color:mediumvioletred">&#9632;</a>
		            	<a class="color" href="#" style="color:silver">&#9632;</a>
		            	<a class="color" href="#" style="color:yellow">&#9632;</a>
		            	<a class="color" href="#" style="color:red">&#9632;</a>  
            		</div>
            		<script>
					   \$("#colorPicker > a").click(function () {
					    	\$("#colorPicked").val(\$(this).css("color"));
					    	\$("#colorPicker > a").css("text-decoration","");
					    	\$(this).css("text-decoration","underline");
					    });
				    </script>
          		</div>
            </fieldset>
            </form>
          </div>
          <div class="modal-footer">
            <a id="submitRfid" href="#" class="btn primary">Save</a>
            <a href="#" class="closeRfid btn secondary">Cancel</a>
          </div>
	</div>

	<div class="container">
		<section>
			<div class="page-header">
				<small style="float:right"><a href="${users.createLogoutURL("/web")}">Sign out</a></small>
				<h1>${user.nickname}</h1>
			</div>
		<%
			request.rabbits.each {  rabbit ->
		%>
			<div class="row">
				<div class="span5 columns">
		            <h4>Rabbit</h4>
		        	<p>${rabbit.key}</p>
		        	
		        	<div id="lastRfid" class="alert-message info">
        				<a id="close" class="close" href="#">&times;</a>
				        <p><strong>Last RFID: </strong><span>${rabbit.lastRfid}<span></p>
      				</div>
      				<script>
					   \$("#close").click(function () {
					    	\$("#lastRfid").hide();
					    });
				    </script>
				</div>
		 		<div class="span5 columns">
		            <h4>RFIDs</h4>
		            <ul>
		            <%
		            	request.rfids[rabbit.key.name].each { rfid ->
		        	%>
		            		<li><a class="editRfid" style="color: ${rfid.color}" href="#${rfid.key.name}">
		            		<%
		            			print rfid.name == null ? rfid.mac : rfid.name
	            			%>
		            		</a></li>
		        	<%
		            	}
		            %>
			      	</ul>
		      	</div>
		  	</div><!-- /row -->
		<%
			}
		%>
		</section>
	</div>
	<br/><br/>
		
		
		<br/>
		<br/>
		<form action="/web/register" method="post">
		Rabbit mac: <input type="text" name="mac" /><br />
		<input type="submit" value="Register" />
		</form>
		<br/>
		  
	



  </body>
</html>
