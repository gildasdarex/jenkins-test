  $(document).ready(function() {
		   // validate a field on "blur" event, a 'select' on 'change' event & a '.reuired' classed multifield on 'keyup':
			  $('.frmPej')
		        //.on('blur', 'input[required="true"], input.optional, select.required', alert("Leaving step "))
		      	.on('blur', 'input[required="required"], input.optional, select.required', validator.checkField)
		        .on('change', 'select.required', validator.checkField)
		        .on('keypress', 'input[required][pattern]', validator.keypress);
		
		     $('.multi.required').on('keyup blur', 'input', function() {
		        validator.checkField.apply($(this).siblings().last()[0]);
		      });
		
		      $('.frmPej').submit(function(e) {
		        console.log('Action detected');
		        e.preventDefault();
		        var submit = true;
		
		        // evaluate the form using generic validaing
		        if (!validator.checkAll($(this))) {
		          submit = false;
		          console.log('Cancel submit');
		        }
		
		        if (submit)
		          this.submit();
		         return false;
		      });

  });