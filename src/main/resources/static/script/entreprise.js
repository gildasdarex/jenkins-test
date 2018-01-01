  $(document).ready(function() {

     $( "#modifier" ).click(function() {
          //alert($('#idcontenu').val());
         // editionModalEntreprise($('#idcontenu').val());


     });
     $( "#prendre" ).click(function() {
    	 if (navigator.geolocation)
    	 {
    	   navigator.geolocation.getCurrentPosition(function(position)
    	   {
    		   $("#longitude").val(position.coords.longitude);
    		   $("#latitude").val(position.coords.latitude);
    	     
    	        //alert("longitude="+longitude+" latitude= "+latitude);

    	   });
    	 }
    	 else
    	 alert("Votre navigateur ne prend pas en compte la géolocalisation HTML5");


    });


 $( "#entreprisesformateurEdition" ).change(function() {
			  console.log('change event: ',$( this ).val());
			  //Effacer toutes les options du champ commune
			  $('#formateurentrepriseEdition').empty();
			  var url='/pej/editionentreprisesformateurs/formateur/'+$( this ).val();

			   var setEditionEntrepriseFormateurs=function(jqXHR){
						  console.log('START ENTREPRISES FORMATEURS OK');

						  try {
							  var formateurs = jQuery.parseJSON( jqXHR.responseText );
			  				  console.log('Ajax response error:',formateurs);

							  //if(communes=='undefined') return;
							  var options='<option value="">Sélectionner un formateur</option>';
							  for (i = 0; i < formateurs.length; i++)
							  {
								  options=options+'<option value="'+formateurs[i].idformateur+'">'+formateurs[i].nom+' '+formateurs[i].prenom+'</option>';
							  }
							  console.log('Options appending:', options)
							  $('#formateurentrepriseEdition').append(options);
						  }
						  catch(err) {

							  new PNotify({
	                              title: 'Programme Emploi Jeune',
	                              text: 'Erreur lors de la récupération des formateurs',
	                              type: 'error',
	                              styling: 'bootstrap3'
	                          });
						  }
			  };
			  var formentr=doGet(url, setEditionEntrepriseFormateurs);

			});



		
		  $( "#departementEntreprise" ).change(function() {
			  console.log('change event: ',$( this ).val());
			  //Effacer toutes les options du champ commune
			  $('#communeEntreprise').empty();
			  var url='/pej/entreprises/commune/'+$( this ).val();
			  
			   var setCommune=function(jqXHR){
						  console.log('START SETCOMMUNE OK');	  
  
						  try {
							  var communes = jQuery.parseJSON( jqXHR.responseText );
			  				  console.log('Ajax response error:',communes);
			  				  
							  //if(communes=='undefined') return;
							  var options='<option value="">Sélectionner une commune</option>';
							  for (i = 0; i < communes.length; i++)
							  { 
								  options=options+'<option value="'+communes[i].codecommune+'">'+communes[i].libcommune+'</option>';		       
							  }
							  console.log('Options appending:', options)
							  $('#communeEntreprise').append(options);
						  }
						  catch(err) {
							 
							  new PNotify({
	                              title: 'Programme Emploi Jeune',
	                              text: 'Erreur lors de la récupération des communes de ce département',
	                              type: 'error',
	                              styling: 'bootstrap3'
	                          });
						  }
			  };
			  var communes=doGet(url, setCommune);
		  
			});
		  	
		  $( "#communeEntreprise" ).change(function() {
			  console.log('Trigger change event: ',$( this ).val());
			  //Effacer toutes les options du champ commune
			  $('#arrondissementEntreprise').empty();
			  var url='/pej/entreprises/arrondissement/'+$( this ).val();
			  
			   var setArrondissement=function(jqXHR){
				   try {
						  console.log('START SETARRONDISSEMENT OK');	  
						  var arrondissements = jQuery.parseJSON( jqXHR.responseText );
		  				  console.log('Ajax response error:',arrondissements);
		  				  
						  //if(arrondissements=='undefined') return;
						  var options='<option value="">Sélectionner un arrondissement</option>';
						  for (i = 0; i < arrondissements.length; i++)
						  { 
							  options=options+'<option value="'+arrondissements[i].codearrondissement+'">'+arrondissements[i].libarrondissement+'</option>';		       
						  }
						  console.log('Options appending:', options)
						  $('#arrondissementEntreprise').append(options);
					  }
					  catch(err) {
						 
						  new PNotify({
                           title: 'Programme Emploi Jeune',
                           text: 'Erreur lors de la récupération des arrondissements de cette commune',
                           type: 'error',
                           styling: 'bootstrap3'
                       });
					  }

			  };
			  var arrondissements=doGet(url, setArrondissement);
		  
			});
		
		  $( "#arrondissementEntreprise" ).change(function() {
			  console.log('Trigger change event: ',$( this ).val());
			  //Effacer toutes les options du champ commune
			  $('#quartierEntreprise').empty();
			  var url='/pej/entreprises/quartier/'+$( this ).val();
			  
			   var setQuartier=function(jqXHR){
				   try {
					   console.log('START SETQUARTIER OK');	  
						  var quartiers = jQuery.parseJSON( jqXHR.responseText );
		  				  console.log('Ajax response error:',quartiers);
						  var options='<option value="">Sélectionner un quartier</option>';
						  for (i = 0; i < quartiers.length; i++)
						  { 
							  options=options+'<option value="'+quartiers[i].idquartier+'">'+quartiers[i].libquartier+'</option>';		       
						  }
						  console.log('Options appending:', options)
						  $('#quartierEntreprise').append(options);
					  }
					  catch(err) {
						 
						  new PNotify({
	                        title: 'Programme Emploi Jeune',
	                        text: 'Erreur lors de la récupération des quartiers/villages de cet arrondissement.',
	                        type: 'error',
	                        styling: 'bootstrap3'
						  	});
					  }	 
			  };
			  var quartiers=doGet(url, setQuartier);
		  
			});

		   //validate a field on "blur" event, a 'select' on 'change' event & a '.reuired' classed multifield on 'keyup':
		  $('#frmEntreprise')
		        //.on('blur', 'input[required="true"], input.optional, select.required', alert("Leaving step "))
		      	.on('blur', 'input[required="required"], input.optional, select.required', validator.checkField)
		        .on('change', 'select.required', validator.checkField)
		        .on('keypress', 'input[required][pattern]', validator.keypress);
		
		     $('.multi.required').on('keyup blur', 'input', function() {
		        validator.checkField.apply($(this).siblings().last()[0]);
		      });
		
		      $('#frmEntreprise').submit(function(e) {
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
		      
		      
		      
		      function doGet(url, setCommune) {
		    		 var data;
		    		 $.ajax({
		    			 url : 'http://localhost:8080'  + url,
		    			 type : 'GET' ,
		    			 dataType : 'application/json' ,
		    			 beforeSend : function() {
		    			 },
		    			 success : function(data) {
		    				console.log('Show success response: ', data);
		    			 },
		    			 complete : function() {
		    				console.log('Ajax response complete');
		    			 },
		    			 error : function(jqXHR) {
		    				 setCommune(jqXHR);
		    			 }
		    		 })
		    		 
		    		 return data;
		    	}



		      function upgradeEntreprise(url){
		    	  var validerCandidat=function(jqXHR){
		    		  var quartiers = jQuery.parseJSON( jqXHR.responseText );
	  				  console.log('Ajax response error:',quartiers);
		    	  }
		    	 doGet(url, quartiers);
		      }
		      
		      $('#datatable-departements').DataTable( {
			        dom: 'Bfrtip',
			        buttons: [
			            'copyHtml5',
			            'excelHtml5',
			            'csvHtml5',
			            'pdfHtml5'
			        ]
			    } );

		      /*Impression des tables*/
		      var handleDataTableButtons = function() {
		          if ($("#tablepej").length) {
		            $("#tablepej").DataTable({
		              dom: "Bfrtip",
		              buttons: [
		                {
		                  extend: "copy",
		                  className: "btn-sm"
		                },
		                {
		                  extend: "csv",
		                  className: "btn-sm"
		                },
		                {
		                  extend: "excel",
		                  className: "btn-sm"
		                },
		                {
		                  extend: "pdfHtml5",
		                  className: "btn-sm"
		                },
		                {
		                  extend: "print",
		                  className: "btn-sm"
		                },
		              ],
		              responsive: true
		            });
		          }
		        };

		        TableManageButtons = function() {
		          "use strict";
		          return {
		            init: function() {
		              handleDataTableButtons();
		            }
		          };
		        }();
  });