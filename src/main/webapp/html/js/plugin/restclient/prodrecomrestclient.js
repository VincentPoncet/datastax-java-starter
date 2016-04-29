/**
 * 
 */

$.fn.prodrecomview = function(searchstring) {
    
	 var recomData;
	 
	function searchCallback(data) {
       recomData = data;
		createRecommDOM(productData,data);

    }
	
	$.ajax(
			{
				url : "http://localhost:8080/datastax-starter/rest/getProductsSolrQuery/name: " + searchstring + "*"
			}).then(
					function(data) { 
						productData = data;
						for (var i = 0; i < data.length; i++){
							
							if (i<2){
							 $.ajax(
										{
											url : "http://localhost:8080/datastax-starter/rest/getRecommendedProductsBySku/" + data[i].sku,
											success: searchCallback
										})
							}
							
						}
						
					});
					
					
				function createRecommDOM(data, recomData){
										
					
					// if recomData empty 
					// call createProductDOM
					
					if (recomData == null || recomData.length === 0 || searchstring == "*"){
						createProductDOM(data);
					}else{
						createRecomDOM(data,recomData);
					}
					
					// else
					// call createRecomDOM
					
					
					


			};
			function createRecomDOM(data, recomData){
				
				createProductDOM(data);
				
				var oldDOM = document.getElementById('widget-grid');
			    newDOM = oldDOM.cloneNode(true);
				
				var newProductView = document.getElementById('widget-grid');

				
				createRecomDOMNEW(recomData,newDOM);
				
				oldDOM.parentNode.replaceChild(newDOM, oldDOM);

			}
			
			
				function createRecomDOMNEW(data, dom){
				
				newProductView = dom;
//				$(newProductView).empty();
						
						
				for (var i = 0; i < data.length; i++) {

					// first div
					div = document
							.createElement('div');
					div.className = 'col-sm-6 col-md-6 col-lg-4';
					div.id = 'container' + i;
					if (i < 2) {
						div.className = 'col-sm-6 col-md-6 col-lg-6';
					}


						// second div - product
						product = document
								.createElement('div');
						product.className = 'product-content product-wrap clearfix';
						product.id = 'product' + i;
						div.appendChild(product);

						// third div - row
						row = document
								.createElement('div');
						row.className = 'row';
						row.id = 'row' + i;
						product.appendChild(row);

						// forth div - rowContainer
						rowContainer = document
								.createElement('div');
						rowContainer.className = 'col-md-5 col-sm-12 col-xs-12';
						rowContainer.id = 'rowContainer'
								+ i;
						row.appendChild(rowContainer);

						// fifth div - product image
						productImg = document
								.createElement('div');
						productImg.className = 'product-image';
						productImg.id = 'productImg'
								+ i;
						rowContainer
								.appendChild(productImg);

						// append product thumb
						productThumb = document
								.createElement('img');
						
						productThumb.src = data[i].thumbnailImage;
						productThumb.alt = "100x228";
						productThumb.id = 'productThumb'
								+ i;
						productThumb.className = 'img-responsive';
						productImg
								.appendChild(productThumb);

						// append tag to thumb
						productTag = document
								.createElement('span');
						productTag.className = 'tag2 sale';
						productTag.id = 'tag' + i;
						$(productTag).append('TOP');
						productImg.appendChild(productTag);

						// sixth div -
						// productDetailContainer
						productDetailContainer = document
								.createElement('div');
						productDetailContainer.className = 'col-md-7 col-sm-12 col-xs-12';
						productDetailContainer.id = 'product'
								+ i;
						row.appendChild(productDetailContainer);

						// seventh div - productDetail
						productDetail = document
								.createElement('div');
						productDetail.className = 'product-deatil';
						productDetail.id = 'productDetail' + i;
						productDetailContainer.appendChild(productDetail);

						// h5 - productTitleH
						productTitleH = document
								.createElement('h5');
						productTitleH.className = 'name';
						productTitleH.id = 'nameh' + i;
						productDetail.appendChild(productTitleH);

						// a - productTitle
						productTitle = document
								.createElement('a');
						productTitle.id = 'name' + i;
						$(productTitle).append(
								data[i].productName);
						productTitleH
								.appendChild(productTitle);

						// a - shortDescription

						shortDesc = document
								.createElement('span');
						shortDesc.id = 'shortDesc' + i;
						$(shortDesc).append(
								data[i].name);
						productTitle
								.appendChild(shortDesc);

						// p - priceContainer
						priceContainer = document.createElement('p');
						priceContainer.className = 'price-container';
						priceContainer.id = 'priceContainer'+ i;
						productDetail.appendChild(priceContainer);

						// span - price
						price = document.createElement('span');
						price.id = 'price' + i;
						$(price).append('Price: ' + data[i].regularPrice + ' $');
						priceContainer
								.appendChild(price);

						// div descContainer
						descContainer = document
								.createElement('div');
						descContainer.id = 'descContainer'
								+ i;
						$(descContainer)
								.append(
										data[i].short_description);
						productDetailContainer
								.appendChild(descContainer);

						newProductView.appendChild(div);

				}
			
		}
			
			function createProductDOM(data){
				
				var newProductView = document.getElementById('widget-grid');
				$(newProductView).empty();
						
						
			for (var i = 0; i < data.length; i++) {

				// first div
				div = document
						.createElement('div');
				div.className = 'col-sm-6 col-md-6 col-lg-4';
				div.id = 'container' + i;
				if (i < 2) {
					div.className = 'col-sm-6 col-md-6 col-lg-6';
				}


					// second div - product
					product = document
							.createElement('div');
					product.className = 'product-content product-wrap clearfix';
					product.id = 'product' + i;
					div.appendChild(product);

					// third div - row
					row = document
							.createElement('div');
					row.className = 'row';
					row.id = 'row' + i;
					product.appendChild(row);

					// forth div - rowContainer
					rowContainer = document
							.createElement('div');
					rowContainer.className = 'col-md-5 col-sm-12 col-xs-12';
					rowContainer.id = 'rowContainer'
							+ i;
					row.appendChild(rowContainer);

					// fifth div - product image
					productImg = document
							.createElement('div');
					productImg.className = 'product-image';
					productImg.id = 'productImg'
							+ i;
					rowContainer
							.appendChild(productImg);

					// append product thumb
					productThumb = document
							.createElement('img');
					
					productThumb.src = data[i].image;
					productThumb.alt = "100x228";
					productThumb.id = 'productThumb'
							+ i;
					productThumb.className = 'img-responsive';
					productImg
							.appendChild(productThumb);

					// append tag to thumb
					productTag = document
							.createElement('span');
					productTag.className = 'tag2 sale';
					productTag.id = 'tag' + i;
					$(productTag).append('TOP');
					productImg
							.appendChild(productTag);

					// sixth div -
					// productDetailContainer
					productDetailContainer = document
							.createElement('div');
					productDetailContainer.className = 'col-md-7 col-sm-12 col-xs-12';
					productDetailContainer.id = 'product'
							+ i;
					row
							.appendChild(productDetailContainer);

					// seventh div - productDetail
					productDetail = document
							.createElement('div');
					productDetail.className = 'product-deatil';
					productDetail.id = 'productDetail'
							+ i;
					productDetailContainer
							.appendChild(productDetail);

					// h5 - productTitleH
					productTitleH = document
							.createElement('h5');
					productTitleH.className = 'name';
					productTitleH.id = 'nameh' + i;
					productDetail
							.appendChild(productTitleH);

					// a - productTitle
					productTitle = document
							.createElement('a');
					productTitle.id = 'name' + i;
					$(productTitle).append(
							data[i].manufacturer);
					productTitleH
							.appendChild(productTitle);

					// a - shortDescription

					shortDesc = document
							.createElement('span');
					shortDesc.id = 'shortDesc' + i;
					$(shortDesc).append(
							data[i].name);
					productTitle
							.appendChild(shortDesc);

					// p - priceContainer
					priceContainer = document
							.createElement('p');
					priceContainer.className = 'price-container';
					priceContainer.id = 'priceContainer'
							+ i;
					productDetail
							.appendChild(priceContainer);

					// span - price
					price = document
							.createElement('span');
					price.id = 'price' + i;
					$(price).append(
							data[i].regular_price
									+ ' $');
					priceContainer
							.appendChild(price);

					// div descContainer
					descContainer = document
							.createElement('div');
					descContainer.id = 'descContainer'
							+ i;
					$(descContainer)
							.append(
									data[i].short_description);
					productDetailContainer
							.appendChild(descContainer);

					newProductView.appendChild(div);

			}
		}
};
