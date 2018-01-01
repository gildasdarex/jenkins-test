$(document).ready(function() {
    console.log('Exec wizard');
    $(":input").inputmask();
    $('#wizard').smartWizard({ transitionEffect: 'slide',
        keyNavigation: true,
        labelNext:'Suivant',
        labelPrevious:'Précédent',
        labelFinish:'Enregistrer',
        reverseButtonsOrder: false,
        onLeaveStep:leaveAStepCallback,
        onFinish: onFinishCallback});
    function leaveAStepCallback(obj, context){
        //alert("Leaving step " + context.fromStep + " to go to step " + context.toStep);
        return validator.checkAll( $('#frmCandidat'));
    }

    $('.buttonNext').addClass('btn btn-success');
    $('.buttonPrevious').addClass('btn btn-primary');
    $('.buttonFinish').addClass('btn btn-default');

    validator.message.date = 'Pas une date valide.';
    function onFinishCallback(){
        console.log('Asking for save candidat',$('form'));
        $('#frmCandidat').submit();

    }

    $( "#departement" ).change(function() {
        console.log('Trigger change event: ',$( this ).val());
        //Effacer toutes les options du champ commune
        $('#commune').empty();
        var url='/pej/candidats/commune/'+$( this ).val();

        var setCommune=function(jqXHR){
            console.log('START SETCOMMUNE OK');

            try {
                var communes = jQuery.parseJSON( jqXHR.responseText );
                console.log('Ajax response error:',communes);

                //if(communes=='undefined') return;
                var options='<option value=""></option>';
                for (i = 0; i < communes.length; i++)
                {
                    options=options+'<option value="'+communes[i].codecommune+'">'+communes[i].libcommune+'</option>';
                }
                console.log('Options appending:', options)
                $('#commune').append(options);
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

    $( "#commune" ).change(function() {
        console.log('Trigger change event: ',$( this ).val());
        //Effacer toutes les options du champ commune
        $('#arrondissement').empty();
        var url='/pej/candidats/arrondissement/'+$( this ).val();

        var setArrondissement=function(jqXHR){
            try {
                console.log('START SETARRONDISSEMENT OK');
                var arrondissements = jQuery.parseJSON( jqXHR.responseText );
                console.log('Ajax response error:',arrondissements);

                //if(arrondissements=='undefined') return;
                var options='<option value=""></option>';
                for (i = 0; i < arrondissements.length; i++)
                {
                    options=options+'<option value="'+arrondissements[i].codearrondissement+'">'+arrondissements[i].libarrondissement+'</option>';
                }
                console.log('Options appending:', options)
                $('#arrondissement').append(options);
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

    $( "#arrondissement" ).change(function() {
        console.log('Trigger change event: ',$( this ).val());
        //Effacer toutes les options du champ commune
        $('#quartier').empty();
        var url='/pej/candidats/quartier/'+$( this ).val();

        var setQuartier=function(jqXHR){
            try {
                console.log('START SETQUARTIER OK');
                var quartiers = jQuery.parseJSON( jqXHR.responseText );
                console.log('Ajax response error:',quartiers);
                var options='<option value=""></option>';
                for (i = 0; i < quartiers.length; i++)
                {
                    options=options+'<option value="'+quartiers[i].idquartier+'">'+quartiers[i].libquartier+'</option>';
                }
                console.log('Options appending:', options)
                $('#quartier').append(options);
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
    $('#numeroagent').blur(function(){
        console.log('DEBUT RECHERCHE AGENT OK');
        $('#agent').empty();
        var url='/pej/candidats/agent/'+$( this ).val();
        var setAgent=function(jqXHR){
            $('#agent').val('');
            try {
                var agent = jQuery.parseJSON( jqXHR.responseText );
                console.log('Ajax response error:',agent);
                $('#agent').val(agent.nom+ ' '+agent.prenom);
            }
            catch(err) {
                $('#numeroagent').val('');
                $('#numeroagent').val('');
                new PNotify({
                    title: 'Programme Emploi Jeune',
                    text: 'Le code agent '+$('#numeroagent').val()+'n\'existe pas dans le système.',
                    type: 'error',
                    styling: 'bootstrap3'
                });
            }


        };
        var agent=doGet(url, setAgent);
    })
    //validate a field on "blur" event, a 'select' on 'change' event & a '.reuired' classed multifield on 'keyup':
    $('#frmCandidat')
    //.on('blur', 'input[required="true"], input.optional, select.required', alert("Leaving step "))
        .on('blur', 'input[required="required"], input.optional, select.required', validator.checkField)
        .on('change', 'select.required', validator.checkField)
        .on('keypress', 'input[required][pattern]', validator.keypress);

    $('.multi.required').on('keyup blur', 'input', function() {
        validator.checkField.apply($(this).siblings().last()[0]);
    });

    $('#frmCandidat').submit(function(e) {
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
        console.log(window.location.origin + url);
        var data;
        $.ajax({
            url : window.location.origin  + url,
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

    function upgradeCandidat(url){
        var validerCandidat=function(jqXHR){
            var quartiers = jQuery.parseJSON( jqXHR.responseText );
            console.log('Ajax response error:',quartiers);
        }
        doGet(url, quartiers);
    }

	/*Recuperer les date de formation d'une formation selectionne pour une cooperative*/
    $( "#formation" ).change(function() {
        console.log('Trigger change event: ',$( this ).val());
        //Effacer toutes les options du champ commune
        $('#dateformation').empty();
        var url='/pej/pej/formations/dateformation/'+$( this ).val();

        var setDateformation=function(jqXHR){
            try {
                console.log('START SET DATE FORMATION OK');
                var dateFormations = jQuery.parseJSON( jqXHR.responseText );
                console.log('Ajax response error:',dateFormations);

                //if(arrondissements=='undefined') return;
                var options='<option value=""></option>';
                for (i = 0; i < dateFormations.length; i++)
                {
                    var myDate = moment(dateFormations[i]).format("DD/MM/YYYY");
                    options=options+'<option value="'+myDate+'">'+myDate+'</option>';
                }
                console.log('Options appending:', options)
                $('#dateformation').append(options);
            }
            catch(err) {

                new PNotify({
                    title: 'Programme Emploi Jeune',
                    text: 'Erreur lors de la recuperation des dates de formation',
                    type: 'error',
                    styling: 'bootstrap3'
                });
            }

        };
        var dateformation=doGet(url, setDateformation);

    });
	/*$('#datatable-departements').DataTable( {
	 dom: 'Bfrtip',
	 buttons: [
	 'copyHtml5',
	 'excelHtml5',
	 'csvHtml5',
	 'pdfHtml5'
	 ]
	 } );
	 */
	/*Impression des tables*/
	/* var handleDataTableButtons = function()z {
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
	 */
});