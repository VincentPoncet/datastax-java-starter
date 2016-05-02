
function updateProduct(sku){

    $.ajax({
        url: '/datastax-starter/rest/getProductsSolrQuery/sku:' + sku,
        success: function(dataset) {
            var elementCount = 0;
            //Clear all HTML inside product table

            elementHtml = "";

            //$("#product-grid").append("<div class='col-sm-6 col-md-6 col-lg-4' id='product-rowb'>")

            $.each(dataset, function(index, element) {
                //increment row-element count
                $("#productImage").attr('src',  element.image);
                $("#productTitle").html( element.name );
                $("#productPrice").html( '$' + element.regular_price );
                $("#addPrice").html( '$' + element.regular_price );
                $("#description").html( element.short_description );
            });


        },
        cache: false
    });
}


function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&amp;");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable) {
            return pair[1];
        }
    }
    alert('Query Variable ' + variable + ' not found');
}


updateProduct(getQueryVariable("sku"));




