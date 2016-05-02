
function updateProduct(sku){

    $.ajax({
        url: '/datastax-starter/rest/getProductsSolrQuery/sku:' + sku,
        success: function(dataset) {
            var elementCount = 0;

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

function updateRecommendations(sku){

    $.ajax({
        url: '/datastax-starter/rest/getRecommendedProductsBySku/' + sku,
        success: function(dataset) {
            elementHtml = "";
            //$("#product-grid").append("<div class='col-sm-6 col-md-6 col-lg-4' id='product-rowb'>")


                $.each(dataset, function (index, element) {
                    //increment row-element count
                    elementHtml += "<div class='col-sm-4 col-md-4 col-lg-3' id='product-row'>";
                    elementHtml += "<div class='product-content product-wrap clearfix'>";
                    elementHtml += element.productName;
                    elementHtml += "<div><div class='col-lg-1'>$" + element.regularPrice;
                    elementHtml += "<img class='img-responsive' src='" + element.thumbnailImage + "'></div>";
                    elementHtml += "</div>";
                    elementHtml += "</div>";
                    elementHtml += "</div>";

                    return (index != 7);

                });

            $("#recommendations").append(elementHtml);
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
updateRecommendations(getQueryVariable("sku"));
