<form id="rfid-form" class="form-stacked" method="post" action="/web/saveRfid">
    <input id="rfidKey" name="key" type="hidden" value="${request.rfid.key.name}" />
    <div class="clearfix">
        <label>Name</label>
        <div class="input">
            <input id="rfid-name" class="xlarge required" name="name" size="30" type="text" value="${request.rfid.name}" />
        </div>
    </div>
    <div class="clearfix">
        <label>Color</label>
        <input id="colorPicked" name="color" type="hidden" />
        <div id="colorPicker" class="input" style="font-size: 30px">
            <a class="color" href="#" style="color: rgb(0, 0, 255)">&#9632;</a> <a class="color" href="#"
                style="color: rgb(255, 165, 0)"
            >&#9632;</a> <a class="color" href="#" style="color: rgb(173, 255, 47)">&#9632;</a> <a class="color" href="#"
                style="color: rgb(199, 21, 133)"
            >&#9632;</a> <a class="color" href="#" style="color: rgb(192, 192, 192)">&#9632;</a> <a class="color" href="#"
                style="color: rgb(255, 255, 0)"
            >&#9632;</a> <a class="color" href="#" style="color: rgb(255, 0, 0)">&#9632;</a>
        </div>
        <script>
			   \$("#colorPicker > a").click(function () {
			    	\$("#colorPicked").val(\$(this).css("color"));
			    	\$("#colorPicker > a").css("text-decoration","");
			    	\$(this).css("text-decoration","underline");
			    });
			    \$('#colorPicker > a[style*="${request.rfid.color}"]').css("text-decoration","underline");
			    \$("#colorPicked").val("${request.rfid.color}");
		    </script>
    </div>
    <div class="clearfix">
        <label>Action</label>
        <select id="action-select" name="rfidAction">
            <% 
            com.appspot.nabaztag.RfidAction.each { 
                println "<option value=" + it + " "+ (request.rfid.action==it.name() ? "selected=selected" : "") +">"+it.label()+"</option>"
            } 
            %>
        </select>
        <div id="rfid-action" class="clearfix">
            <br />
        </div>
    </div>
</form>
<script type="text/javascript">
    \$("#rfid-dialog").dialog2("options", {
         buttons: {
            "Save": {
                primary: true,
                click: function() {
                	\$("#rfid-form").submit();
                	if(\$("#rfid-form").valid()){
                    	\$(this).dialog2("close");
                	}
                }
            }
    	}
    });
    \$("#action-select").change(function() {
    	\$("#rfid-action").load("rfid_actions/"+\$(this).val()+".html");
    	\$("#rfid-action").show();
    });
    \$("#rfid-action").load("rfid_actions/${request.rfid.action}.html", function() {
	    \$("#rfid-action-param").val("${request.rfid.actionParam}"); 
	});
    
	\$("#rfid-form").validate();
</script>