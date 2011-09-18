<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Nabaztag on Google App Engine</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="http://twitter.github.com/bootstrap/assets/css/bootstrap-1.1.1.min.css">
    <link rel="stylesheet" href="jquery.dialog2.css">
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="jquery.dialog2.js"></script>
  	<script src="jquery.validate.js"></script>
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
		    interval: 10000,
		    type: "GET",
		    success: function(data){
		    	if(data && data != "null") {
		        	\$("#lastRfid > p > span").text(data);
		        	\$("#lastRfid").show();
		        }
		    }
		});
		
		\$(document).ready(function(){
			\$("#register-dialog").dialog2({
	            showCloseHandle: false,
	            removeOnClose: false,
	            autoOpen: false,
	            buttons: {
	                Register: {
	                    primary: true,
	                    click: function() {
	                    	\$("#register-form").submit();
	                    	if(\$("#register-form").valid()){
	                        	\$(this).dialog2("close");
                        	}
	                    }
	                }
            	}
	        });

	        \$("#open-register-dialog").click(function(event) {
	            event.preventDefault();
	            \$("#register-dialog").dialog2("open");
	        });
        	\$("#register-form").validate();

	        \$(".open-rfid-dialog").click(function(event) {
	       		var rfidId = \$(this).attr("href").split('#')[1]
				\$('<div/>').dialog2({
					title: "Edit RFID",
					removeOnClose: true,
	                content: "/web/editRfid?rfidKey="+rfidId,
	                id: "rfid-dialog"
	            });
	            event.preventDefault();
	        });
	        
        	\$("#rabbit-dialog").dialog2({
	            showCloseHandle: false,
	            removeOnClose: false,
	            autoOpen: false,
	            buttons: {
	                Save: {
	                    primary: true,
	                    click: function() {
	                    	\$("#rabbit-form").submit();
	                    	if(\$("#rabbit-form").valid()){
	                        	\$(this).dialog2("close");
                        	}
	                    }
	                }
            	}
	        });

	        \$(".open-rabbit-dialog").click(function(event) {
	       		var args = \$(this).attr("href").split("#")
	       		var rabbitKey = args[1]
	       		var name = args[2]
	  			\$('#rabbit-key').val(rabbitKey);
	  			\$('#rabbit-name').val(name);
	            event.preventDefault();
	            \$("#rabbit-dialog").dialog2("open");
	        });
        	\$("#rabbit-form").validate();
        	
		});
    </script>
    <style type="text/css">
		html, body {
			height: 100%;
		}
		.wrapper {
			min-height: 90%;
		}
		.xfooter, .push {
			height: 5%;
		}
		.color:focus { 
    		outline: none; 
		}
	</style>
  </head>
<body>
	<div id="register-dialog">
        <h1>Register a rabbit</h1>
        <form id="register-form" class="form-stacked" method="post" action="/web/register">
            <fieldset>
	            <div class="clearfix">
		            <label>Rabbit's MAC address</label>
		            <div class="input">
              			<input class="xlarge required" name="mac" size="30" type="text" />
            		</div>
          		</div>
            </fieldset>
        </form>
	</div>
	
	<div id="rabbit-dialog">
        <h1>Edit Rabbit</h1>
        <form id="rabbit-form" class="form-stacked" method="post" action="/web/saveRabbit">
        <input id="rabbit-key" name="key" type="hidden" />
        <fieldset>
            <div class="clearfix">
	            <label>Name</label>
	            <div class="input">
          			<input id="rabbit-name" class="xlarge required" name="name" size="30" type="text" />
        		</div>
      		</div>
        </fieldset>
        </form>
	</div>
	
	<div class="container wrapper">
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
		        	<p><a class="open-rabbit-dialog" href="#${rabbit.key.name}#${rabbit.name}">${rabbit.name}</a></p>
		        	
		        	<div id="lastRfid" class="alert-message info">
        				<a class="close" href="#">&times;</a>
				        <p><strong>Last RFID: </strong><span>${rabbit.lastRfid}<span></p>
      				</div>
      				<script>
					   \$(".close").click(function () {
					    	\$(this).parent().hide();
					    });
				    </script>
				</div>
		 		<div class="span5 columns">
		            <h4>RFIDs</h4>
		            <ul>
		            <%
		            	request.rfids[rabbit.key.name].each { rfid ->
		        	%>
		            		<li><a class="open-rfid-dialog" style="color: ${rfid.color}" href="#${rfid.key.name}">
		            		<%
		            			print rfid.name
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
		<button id="open-register-dialog" class="info btn"/>+ Rabbit</button>
		</section>
		<div class="push"></div>
	</div>
	
    <footer class="footer xfooter">
      <div class="container">
        <p>
       		Thanks to ... <br/>
          	<a href="https://github.com/youribonnaffe/nabaztag-ae/issues/new" target="_blank">Issues ?</a>
        </p>
      </div>
     </footer
     
  </body>
</html>
