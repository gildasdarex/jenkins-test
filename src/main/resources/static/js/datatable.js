      $(document).ready(function() {
    	  $('#dateformation').daterangepicker({
              singleDatePicker: true,
              calender_style: "picker_1"
              // ,
              // locale: {
              //     format: 'YYYY-MM-DD'
              // }
            }, function(start, end, label) {
            	
              console.log(start, end.toISOString(), label);
            });
          $('#dateDebut').daterangepicker({
              singleDatePicker: true,
              calender_style: "picker_1"
              // ,
              // locale: {
              //     format: 'YYYY-MM-DD'
              // }
          }, function(start, end, label) {

              console.log(start, end.toISOString(), label);
          });
    	  $('#datefin').daterangepicker({
              singleDatePicker: true,
              calender_style: "picker_1"
              // ,
              // locale: {
              //     format: 'YYYY-MM-DD'
              // }
            }, function(start, end, label) {
            	
              console.log(start, end.toISOString(), label);
            });
    	  $('#dateenregistrement').daterangepicker({
              singleDatePicker: true,
              calender_style: "picker_1"
              // ,
              // locale: {
              //     format: 'dd/MM/yyyy'
              // }
            }, function(start, end, label) {
            	
              console.log(start, end.toISOString(), label);
            });
    
    	// Setup - add a text input to each footer cell
    	  
    	    /*$('#datatable-buttons tfoot th').each( function () {
    	        var title = $(this).text();
    	        $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
    	    } );
    	 
    	    // DataTable
    	    var table = $('#datatable-buttons').DataTable(
    	    {
    	    	 dom: 'Bfrtip',
    	         buttons: [
    	             {
    	                 extend:    'copyHtml5',
    	                 text:      '<i class="fa fa-files-o"></i>',
    	                 titleAttr: 'Copy'
    	             },
    	             {
    	                 extend:    'excelHtml5',
    	                 text:      '<i class="fa fa-file-excel-o"></i>',
    	                 titleAttr: 'Excel'
    	             },
    	             {
    	                 extend:    'csvHtml5',
    	                 text:      '<i class="fa fa-file-text-o"></i>',
    	                 titleAttr: 'CSV'
    	             },
    	             {
    	                 extend:    'pdfHtml5',
    	                 text:      '<i class="fa fa-file-pdf-o"></i>',
    	                 titleAttr: 'PDF'
    	             }
    	         ],
                responsive: false,
                scrollX: true,
                orderCellsTop: true
                /*scrollY: 380,
                scrollCollapse: false,
                scroller: true,
    
    	    });
    	 
    	    // Apply the search
    	    table.columns().every( function () {
    	        var that = this;
    	 
    	        $( 'input', this.footer() ).on( 'keyup change', function () {
    	            if ( that.search() !== this.value ) {
    	                that
    	                    .search( this.value )
    	                    .draw();
    	            }
    	        } );
    	    } );
    	    
    	    */

    	    
    	    
    	    
    	    
    	    
    	    //--NEW SCRIPT----
    	
    	    $('#datatable-buttons thead tr#filterrow th').each( function () {
    	        var title = $('#example thead th').eq( $(this).index() ).text();
    	        $(this).html( '<input type="text" onclick="stopPropagation(event);" class="form-control" placeholder="Filtrer '+title+'" />' );
    	    } );

          $('#datatable-buttons-calendar thead tr#filterrow th').each( function () {
              var title = $('#example thead th').eq( $(this).index() ).text();
              $(this).html( '<input type="text" onclick="stopPropagation(event);" class="form-control" placeholder="Filtrer '+title+'" />' );
          } );
    	    // Apply the filter
    	    $("#datatable-buttons thead input").on( 'keyup change', function () {
    	        table
    	            .column( $(this).parent().index()+':visible' )
    	            .search( this.value )
    	            .draw();
    	    } );
    	 
    	    // DataTable
    	var table = $('#datatable-buttons').DataTable( {
    		 dom: 'Bfrtip',
	         buttons: [
	             {
	                 extend:    'copyHtml5',
	                 text:      '<i class="fa fa-files-o"></i>',
	                 titleAttr: 'Copy'
	             },
	             {
	                 extend:    'excelHtml5',
	                 text:      '<i class="fa fa-file-excel-o"></i>',
	                 titleAttr: 'Excel'
	             },
	             {
	                 extend:    'csvHtml5',
	                 text:      '<i class="fa fa-file-text-o"></i>',
	                 titleAttr: 'CSV'
	             },
	             {
	                 extend:    'pdfHtml5',
	                 text:      '<i class="fa fa-file-pdf-o"></i>',
	                 titleAttr: 'PDF'
	             }
	         ],
            responsive: false,
            //scrollX: true,
            //orderCellsTop: true
    		orderCellsTop: true,
    	    "scrollX": true
    	} );
    	     
    	var table1 = $('#datatable-buttons1').DataTable( {
   		 dom: 'Bfrtip',
	         buttons: [
	             {
	                 extend:    'copyHtml5',
	                 text:      '<i class="fa fa-files-o"></i>',
	                 titleAttr: 'Copy'
	             },
	             {
	                 extend:    'excelHtml5',
	                 text:      '<i class="fa fa-file-excel-o"></i>',
	                 titleAttr: 'Excel'
	             },
	             {
	                 extend:    'csvHtml5',
	                 text:      '<i class="fa fa-file-text-o"></i>',
	                 titleAttr: 'CSV'
	             },
	             {
	                 extend:    'pdfHtml5',
	                 text:      '<i class="fa fa-file-pdf-o"></i>',
	                 titleAttr: 'PDF'
	             }
	         ],
           responsive: false,
           //scrollX: true,
           //orderCellsTop: true
   		orderCellsTop: true,
   	    "scrollX": true
   	} );

    	  function stopPropagation(evt) {
    			if (evt.stopPropagation !== undefined) {
    				evt.stopPropagation();
    			} else {
    				evt.cancelBubble = true;
    			}
    		}


      });