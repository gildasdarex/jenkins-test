$(document).ready(function() {


    var submitButton = $("#submit-candidat-filter");
    var nextLink = $("#candidat-next-page");
    var previousLink = $("#candidat-previous-page");

    $( "#page-select" ).change(function() {
        var page = this.value;
        var filterCriterias = [];
        var filterForm= $("#frmRecherche");
        var items = filterForm.find(".item-filter");

        var $page = filterForm.find("#page-request");
        $page.val(page);

        for (i = 0; i < items.length; i++) {
            var $item = $(items[i]);
            var operation = $item.find(".operation").val();
            var colonne = $item.find(".operation").attr("data-attribute");
            var value = $item.find(".item-value").val();

            if (operation === undefined || operation === null  || value === undefined || value === null || operation === "" || value === "") {
            }
            else{
                var criteriaObject = {
                    "operation" : operation,
                    "colonne" : colonne,
                    "value" : value
                };

                filterCriterias.push(criteriaObject);
            }
        }

        var data = JSON.stringify(filterCriterias);
        var criteriaField = filterForm.find("#criteria")
        criteriaField.val(data)
        filterForm.submit()
    });


    nextLink.click(function() {
        var filterCriterias = [];
        var filterForm= $("#frmRecherche");
        var items = filterForm.find(".item-filter");

        var $nextPage = filterForm.find("#page-request-next");
        var $page = filterForm.find("#page-request");
        var nextPageVal = $nextPage.val();
        $page.val(nextPageVal);

        for (i = 0; i < items.length; i++) {
            var $item = $(items[i]);
            var operation = $item.find(".operation").val();
            var colonne = $item.find(".operation").attr("data-attribute");
            var value = $item.find(".item-value").val();

            if (operation === undefined || operation === null  || value === undefined || value === null || operation === "" || value === "") {
            }
            else{
                var criteriaObject = {
                    "operation" : operation,
                    "colonne" : colonne,
                    "value" : value
                };

                filterCriterias.push(criteriaObject);
            }
        }

        var data = JSON.stringify(filterCriterias);
        var criteriaField = filterForm.find("#criteria")
        criteriaField.val(data)
        filterForm.submit()


    });


    previousLink.click(function() {
        var filterCriterias = [];
        var filterForm= $("#frmRecherche");
        var items = filterForm.find(".item-filter");

        var $previousPage = filterForm.find("#page-previous-next");
        var $page = filterForm.find("#page-request");
        var previousVal = $previousPage.val();
        $page.val(previousVal);

        for (i = 0; i < items.length; i++) {
            var $item = $(items[i]);
            var operation = $item.find(".operation").val();
            var colonne = $item.find(".operation").attr("data-attribute");
            var value = $item.find(".item-value").val();

            if (operation === undefined || operation === null  || value === undefined || value === null || operation === "" || value === "") {
            }
            else{
                var criteriaObject = {
                    "operation" : operation,
                    "colonne" : colonne,
                    "value" : value
                };

                filterCriterias.push(criteriaObject);
            }
        }

        var data = JSON.stringify(filterCriterias);
        var criteriaField = filterForm.find("#criteria")
        criteriaField.val(data)
        filterForm.submit()

    });

    submitButton.click(function() {
        var filterCriterias = [];
        var filterForm= $("#frmRecherche");
        var items = filterForm.find(".item-filter");

        for (i = 0; i < items.length; i++) {
            var $item = $(items[i]);
            var operation = $item.find(".operation").val();
            var colonne = $item.find(".operation").attr("data-attribute");
            var value = $item.find(".item-value").val();

            if (operation === undefined || operation === null  || value === undefined || value === null || operation === "" || value === "") {
            }
            else{
                var criteriaObject = {
                    "operation" : operation,
                    "colonne" : colonne,
                    "value" : value
                };

                filterCriterias.push(criteriaObject);
            }
        }

        var data = JSON.stringify(filterCriterias);
        var criteriaField = filterForm.find("#criteria")
        criteriaField.val(data)
        filterForm.submit()

    });



    var filterForm= $("#frmRecherche");
    var criteriaSendValue = filterForm.find("#criteria-send-value").val();
    var currentPageValue =  filterForm.find("#page-request-current-page").val();

    if(currentPageValue === null || currentPageValue === undefined || currentPageValue === ""){
    }else{
        $( "#page-select" ).val(currentPageValue);
    }

    if(criteriaSendValue === null || criteriaSendValue === undefined || criteriaSendValue === ""){
    }else{
        var criterias = JSON.parse(criteriaSendValue);

        console.log("criterias is ", criterias);

        for (i = 0; i <= criterias.length -1 ; i++) {
            var criteria = criterias[i];
            var colonne = criteria["colonne"]
            var operation = criteria["operation"]
            var value = criteria["value"];



            console.log("criteria send value is ", criteria);

            var items = filterForm.find(".item-filter");

            for (j = 0; i <= items.length -1; j++) {
                var $item = $(items[j]);
                var colonneAttribute = $item.find(".operation").attr("data-attribute");
                console.log("criteria send value is found ", colonneAttribute);
                if(colonne === colonneAttribute){
                    $item.find(".operation").val(operation);
                    $item.find(".item-value").val(value);
                    break;
                }
                else continue;
            }

        }
    }


});