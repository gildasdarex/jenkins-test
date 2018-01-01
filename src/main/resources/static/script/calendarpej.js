$(window).load(function() {
    var initialLocaleCode = 'fr';
    var iddel, longitude,latitude;
if (navigator.geolocation)
{
  navigator.geolocation.getCurrentPosition(function(position)
  {
      longitude=position.coords.longitude;
      latitude=position.coords.latitude ;
       //alert("longitude="+longitude+" latitude= "+latitude);

  });
}
else
alert("Votre navigateur ne prend pas en compte la géolocalisation HTML5");
        var date = new Date(),
            d = date.getDate(),
            m = date.getMonth(),
            y = date.getFullYear(),
            started,
            categoryClass;
        var calendar = $('#calendar').fullCalendar({
          header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay,listMonth'
          },
          selectable: true,
          selectHelper: true,
          locale: 'fr',
          weekNumbers: true,
          navLinks: true,
          eventLimit: true,
          select: function(start, end, allDay) {

            $('#fc_create').click();

            //alert( moment(start).format('DD/MM/YY'));
            $('#datedebut').val(moment(start).format('DD/MM/YY'));
             $('#datefin').val(moment(end).format('DD/MM/YY'));
            started = start;
            ended = end;
             if($('#latitude').val()==latitude && $('#longitude').val()==longitude){
                 $('#statut').val('oui');
             }
             else{
                 $('#statut').val('non');
             }

            $(".antosubmit").on("click", function() {
              var title = $("#title").val();
               //alert($('#datedebut').val() );
              var idsuivie = $('#idsuivie').val();
              		var datedebut = $('#datedebut').val();
              		var datefin = $('#datedebut').val();
              		var statut = $('#statut').val();
              		var entreprise = $('#entreprise').val();
              		var formateur = $('#formateur').val();
              		var annees = $('#annees').val();
              		var periode = $('#periode').val();
              $.post("http://localhost:8080/pej/genererunsuivie", {
              			idsuivie : idsuivie,
              			datedebut : datedebut,
              			datefin : datefin,
              			statut : statut,
              			entreprise : entreprise,
              			formateur : formateur,
              			annees : annees,
              			periode : periode
              		}, function(data) {

              			var json = JSON.parse(data);




              		}).done(function() {
              		}).fail(function(xhr, textStatus, errorThrown) {
              		}).complete(function(data) {
                         $("#CalenderModalNew").modal('hide');
                        calendar.fullCalendar('refetchEvents');
                        new PNotify({
                          title: 'Programme Emploi Jeune',
                          text: 'Date de formation créer',
                          type: 'success',
                          styling: 'bootstrap3'
                      });
                      window.location.reload();
              		});
              /*if (end) {
                ended = end;
              }

              categoryClass = $("#event_type").val();*/

             // if (title) {
               // calendar.fullCalendar('renderEvent', chargerLesSuivies($('#listeSuivies').val()),
                 // true // make the event "stick"
               // );
             // }

              $('#title').val('');
              calendar.fullCalendar('unselect');

              $('.antoclose').click();

              return false;
            });
          },
          eventClick: function(calEvent, jsEvent, view) {
          if($('#latitude').val()==latitude && $('#longitude').val()==longitude){
           $('#statut').val('oui');
       }
       else{
           $('#statut').val('non');
       }
            $('#fc_create').click();
             $('#idsuivie').val(calEvent.id);
            $('#datedebut').val(moment(calEvent.start).format('DD/MM/YY'));

            $('#datefin').val(moment(calEvent.start).format('DD/MM/YY'));
           // $('#statut').val(calEvent.className);

            categoryClass = $("#event_type").val();

            $(".antosubmit").on("click", function() {
              calEvent.title = $("#title2").val();
 var idsuivie = $('#idsuivie').val();
              		var datedebut = $('#datedebut').val();
              		var datefin = $('#datefin').val();
              		var statut = $('#statut').val();
              		var entreprise = $('#entreprise').val();
              		var formateur = $('#formateur').val();
              		var annees = $('#annees').val();
              		var periode = $('#periode').val();
              $.post("http://localhost:8080/pej/genererunsuivie", {
              			idsuivie : idsuivie,
              			datedebut : datedebut,
              			datefin : datefin,
              			statut : statut,
              			entreprise : entreprise,
              			formateur : formateur,
              			annees : annees,
              			periode : periode
              		}, function(data) {

              			var json = JSON.parse(data);




              		}).done(function() {
              		}).fail(function(xhr, textStatus, errorThrown) {
              		}).complete(function(data) {
                         $("#CalenderModalNew").modal('hide');
                        calendar.fullCalendar('refetchEvents');
                        new PNotify({
                          title: 'Programme Emploi Jeune',
                          text: 'Date de formation modifié',
                          type: 'success',
                          styling: 'bootstrap3'
                      });
                      window.location.reload();
              		});
            });

            calendar.fullCalendar('unselect');
          },
          editable: true,
          events: chargerLesSuivies($('#listeSuivies').val()),
           eventAfterRender: function (event, element, view) {
                                       element.on('mousedown', function (e) {
                                                                           if (e.which === 3) { // clic droit de la sourie
                                                                               //alert(event.id);
                                                                               iddel=event.id;
                                                                               element.contextmenu({
                                                                                 target: '#context-menu',
                                                                                 before: function (e) {
                                                                                           // This function is optional.
                                                                                           // Here we use it to stop the event if the user clicks a span
                                                                                           e.preventDefault();
                                                                                           if (e.target.tagName == 'SPAN') {
                                                                                             e.preventDefault();
                                                                                             this.closemenu();
                                                                                             return false;
                                                                                           }
                                                                                           this.getMenu().find("li").eq(2).find('a').html("This was dynamically changed");
                                                                                           return true;
                                                                                         }
                                                                             });

                                                                           }
                                                                       });

                                      }

        });
         $("#supprimer").on("click", function() {
          //alert('bsr');
                $.post("http://localhost:8080/pej/supprimerunsuivie", {
                    idsuivie : iddel,
                }, function(data) {

                    var json = JSON.parse(data);

                  //  alert(json);


                }).done(function() {
                 window.location.reload();
                }).fail(function(xhr, textStatus, errorThrown) {
                 alert(errorThrown);
                }).complete(function(data) {
                    //alert(data);
                    window.location.reload();
                    new PNotify({
                      title: 'Programme Emploi Jeune',
                      text: 'Date de formation supprimer',
                      type: 'success',
                      styling: 'bootstrap3'
                  });


                });
          });

           $("#presence").on("click", function() {
                          $.post("http://localhost:8080/pej/changerstatutgeo", {
                              idsuivie : iddel,
                              longitudeGeo: longitude,
                              latitudeGeo: latitude
                          }, function(data) {
                              var json = JSON.parse(data);
                          }).done(function() {

                          }).fail(function(xhr, textStatus, errorThrown) {
                           alert(errorThrown);
                          }).complete(function(data) {
                              //alert(data);
                              window.location.reload();
                              new PNotify({
                                title: 'Programme Emploi Jeune',
                                text: 'Statut Date de formation changer(Verification géolocalisation)',
                                type: 'success',
                                styling: 'bootstrap3'
                            });


                          });
                    });
      });


                      //  initialiserContextMenu();
       /**
       * Renvoie un object Suivie Ã  afficher dans le calendrier.
       * @param {Suivie} suivie
       * @param {string} info Indique une info sur la suivie  affichée.
       * @returns {Event Object} Représente un suivie Ã  afficher dans le calendrier.
       */
      function chargerUnSuivie(suivie, info) {

          return {
              id: suivie.idsuivie,
              title: (info ? info : '')
                     + '\n' + moment(suivie.datedebut).format('YYYY-MM-DD')
                      + '\n' + suivie.statut
              ,
              start:  new Date(suivie.datedebut).toISOString().slice(0, 10),
              className: suivie.statut,
              end: new Date(suivie.datefin).toISOString().slice(0, 10)
          };
      }
      /**
       * Renvoie un tableau des object Suivies Ã  afficher dans le calendrier.
       * @param {type} listesuivies
       * @returns {Array} Tableau des objects suivies afficher dans le calendrier.
       **/
      function chargerLesSuivies(listesuivies) {
        // alert(listesuivies);
          var array = [];
         var data = $.parseJSON(listesuivies);
         // alert(listesuivies);
          $.each(data, function (i, suivie) {
              array.push(chargerUnSuivie(suivie));
          });


          return array;
      }
      function chargerLesSuiviesUpdate(listesuivies) {
              // alert(listesuivies);
                var array = [];
               // alert(listesuivies);
                $.each(listesuivies, function (i, suivie) {
                    array.push(chargerUnSuivie(suivie));
                });
                 alert(array);
                return array;
            }
      function creerUnSuivieModal(){
      var datat = {}
      			datat["idsuivie"] = $("#idsuivie").val();
      			data["datedebut"] = $("#datedebut").val();
      			data["datefin"] = $("#datefin").val();
      			data["statut"] = $("#statut").val();
      			data["entreprise"] = $("#entreprise").val();
      			data["formateur"] = $("#formateur").val();
      			data["annees"] = $("#annees").val();
                   $.ajax({
                    type: "post",
                    url: "http://localhost:8080/pej/genererunsuivie",
                    cache: false,
                    data: JSON.stringify(data),
                    success: function(response){
                    // $('#result').html("");
                     var obj = JSON.parse(response);
                     $('#CalenderModalNew').modal('hide');
                     console.log('Show success response: ', obj);
                      $('#listeSuivies').val(obj);
                    },
                    error: function(){
                     alert('Error while request..');
                    }
                   });
                  }
                   function initialiserContextMenu() {

                                      }
