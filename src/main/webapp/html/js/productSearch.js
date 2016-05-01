
function updateProductList(searchValue){

    $.ajax({
        url: '/datastax-starter/rest/getProductsSolrQuery/name:' + searchValue,
        success: function(dataset) {
            var elementCount = 0;
            //Clear all HTML inside product table

            $("#product-grid").empty();

            elementHtml = "";

            //$("#product-grid").append("<div class='col-sm-6 col-md-6 col-lg-4' id='product-rowb'>")

            $.each(dataset, function(index, element) {
                //increment row-element count

                elementCount = elementCount + 1;
               // build each product block
                elementHtml += "<div class='col-sm-6 col-md-6 col-lg-4' id='product-row" + index +"'>";
                elementHtml += "<div class='product-content product-wrap clearfix'>";
                elementHtml += "<div class='row'>";
                elementHtml += "<div class='col-md-5 col-sm-12 col-xs-12'>";
                elementHtml += "<div class='product-image'>";
                elementHtml += "<img src='" + element.image +"'  alt='194x228' class='img-responsive'>";
                elementHtml += "</div>";
                elementHtml += "</div>";

                elementHtml += "<div class='col-md-7 col-sm-12 col-xs-12'>";
                elementHtml += "<div class='product-deatil'>";

                elementHtml += "<h5 class='name'><a href='index.html#ajax/product.html?"+ element.sku+ "'>"+ element.manufacturer + " : " + element.model_number  + "</a></h5>";
                elementHtml += "<p class='price-container'> <span>" + element.regular_price +"</span> </p> <span class='tag1'></span> ";


                elementHtml += "</div>";

                elementHtml += "<div class='description'>";
                elementHtml += "<p>" + element.short_description.substring(0,80) + "</p>";
                elementHtml += "</div>";
                elementHtml += "</div>";
                elementHtml += "</div>";
                elementHtml += "</div>";
                elementHtml += "</div>";

                if (elementCount == 3) {
                    elementCount = 0;
                   // elementHtml += "</div><div class='col-sm-6 col-md-6 col-lg-4' id='product-row" + index +"'>";
                }
            });
            $("#product-grid").append(elementHtml);

        },
        cache: false
    });
}

function runSearch() {



    if ($("#searchValue").val().trim().length > 0) {

        updateProductList($("#searchValue").val());
    }

};


updateProductList('*');



