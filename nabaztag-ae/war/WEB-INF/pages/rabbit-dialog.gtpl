<form id="rabbit-form" class="form-stacked" method="post" action="/web/saveRabbit">
    <input id="rabbit-key" name="key" type="hidden" value="${request.rabbit.key.name}"/>
    <fieldset>
        <div class="clearfix">
            <label>Name</label>
            <div class="input">
                <input id="rabbit-name" class="xlarge required" name="name" size="30" type="text" value="${request.rabbit.name}"/>
            </div>
        </div>
    </fieldset>
</form>
<script type="text/javascript">
    \$("#rabbit-dialog").dialog2("options", {
         buttons: {
            "Save": {
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
    
	\$("#rabbit-form").validate();
</script>