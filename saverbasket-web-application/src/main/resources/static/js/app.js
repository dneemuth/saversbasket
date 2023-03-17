/*
 * Template: Foodsto - Grocery & Food Store Bootstrap 5 Theme
 * Author: iqonic.design
 * Design and Developed by: iqonic.design
 * NOTE: This file contains the initialize scripts.
 */

/*----------------------------------------------
Index Of Script
------------------------------------------------

:: Magnific Popup
:: Swiper
:: Smooth Scrollbar
:: Top to Bottom
:: Minus-plus
:: NoUiSlider
:: LoaderInit

------------------------------------------------
Index Of Script
----------------------------------------------*/
(function(jQuery) {
    "use strict";

    const header = document.querySelector('#iq-menu-container')
        if (header !== null) {
        const headerActiveItem = header.querySelectorAll('.current-menu-item')
        Array.from(headerActiveItem, (elem) => {
            if (elem.closest('.sub-menu') !== null){
                const childMenu = elem.closest('.current-menu-item').parentElement.parentElement
                const sections = header.querySelectorAll('.active');
                for (let i = 0; i < sections.length; i++){
                    sections[i].classList.remove('active');
                }
                childMenu.classList.add('active')
            }
        })
    }
    /*---------------------------------------------------------------------
                  Swiper
    -----------------------------------------------------------------------*/

    function headerHeight() {
        let height = document.querySelector(".header1").offsetHeight;
        document.querySelector('.iq-height').style.height = height + 'px';
    }

    let navbar =  document.querySelector(".header1"),
        yOffset = 0,
        triggerPoint = 80;
    headerHeight();

    window.addEventListener('resize',headerHeight);
    window.addEventListener('scroll', function() {

        yOffset = document.documentElement.scrollTop;

        if (yOffset >= triggerPoint) {
            navbar.classList.add("menu-sticky","animated","slideInDown");
        } else {
            navbar.classList.remove("menu-sticky","animated","slideInDown");
        }

    });

    if (document.querySelector('header').classList.contains('has-sticky')) {
    window.addEventListener('scroll', function() {

        var height = document.querySelector('.navbar').outerHeight;
        if (document.documentElement.scrollTop > height) {
            document.querySelector('.has-sticky .logo').classList.add('logo-display');
        } else if (document.documentElement.scrollTop <= height) {
            document.querySelector('.has-sticky .logo').classList.remove('logo-display');
        }
    });
}

    /*---------------------------------------------------------------------
                  Top to Bottom
    -----------------------------------------------------------------------*/
    const backToTop = document.querySelector('#back-to-top')

    backToTop.classList.add('animate__animated', 'animate__fadeOut')

    window.addEventListener('scroll', (e) => {
        if (document.documentElement.scrollTop > 250) {
            backToTop.classList.remove('animate__fadeOut')
            backToTop.classList.add('animate__fadeIn')

        } else {
            backToTop.classList.remove('animate__fadeIn')
            backToTop.classList.add('animate__fadeOut')
        }
    })

    // scroll body to 0px on click
    document.querySelector('#top').addEventListener('click', (e) => {
        e.preventDefault()
        window.scrollTo({
            top: 0,
            behavior: 'smooth'
        });
    })

    /*---------------------------------------------------------------------
                  Minus-plus
    -----------------------------------------------------------------------*/

    const plusBtns = document.querySelectorAll('.plus')

    const minusBtns = document.querySelectorAll('.minus')

    const updateQtyBtn = (elem, value) => {
        const oldValue = elem.closest('[data-qty="btn"]').querySelector('[data-qty="input"]').value
        const newValue = Number(oldValue) + Number(value)
        if (newValue >= 1) {
            elem.closest('[data-qty="btn"]').querySelector('[data-qty="input"]').value = newValue
        }
    }

    Array.from(plusBtns, (elem) => {
        elem.addEventListener('click', (e) => {
            updateQtyBtn(elem, 1)
        })
    })

    Array.from(minusBtns, (elem) => {
        elem.addEventListener('click', (e) => {
            updateQtyBtn(elem, -1)
        })
    })


    /*---------------------------------------------------------------------
                  LoaderInit
    -----------------------------------------------------------------------*/

    const loaderInit = () => {
        const loader = document.querySelector('.loader')
        loader.classList.add('animate__animated', 'animate__fadeOut')
        setTimeout(() => {
            loader.classList.add('d-none')
        }, 100)
    }
    /*----------------------------------------------------------------------
                  Sweetalerts
    ----------------------------------------------------------------------*/
    const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
            confirmButton: 'btn btn-primary ms-3 text-white fw-bold h6',
            cancelButton: 'btn btn-secondary text-white fw-bold h6'
        },
        buttonsStyling: false
    })
    const alertButton = document.querySelectorAll('[data-alert="sweetalert-product"]')
    Array.from(alertButton, (btn) => {
        btn.addEventListener('click', () => {
        swalWithBootstrapButtons.fire({
            title: 'Added to cart!',
            text: "",
            icon: 'success',
            showCancelButton: true,
            confirmButtonText: 'Continue shopping',
            cancelButtonText: 'Checkout',
            reverseButtons: true
        }).then((result) => {
            if (result.isConfirmed) {
                btn.querySelector('.btn').innerHTML = 'View Cart <i aria-hidden="true" class="fas fa-long-arrow-alt-right ms-2"></i>'
                return true
            } else if (
                /* Read more about handling dismissals below */
                result.dismiss === Swal.DismissReason.cancel
            ) {
                window.location.replace('../checkout.html')
            }
        })
        })
    })

    /*-------------------------------
        sweet alert for heart
        -----------------------*/

        const alertButton1 = document.querySelectorAll('[data-alert="sweetalert-heart"]')
        Array.from(alertButton1, (btn) => {
            btn.addEventListener('click', () => {
            swalWithBootstrapButtons.fire({
                title: 'Added to Wishlist!',
                text: "Loved",
                icon: 'success',
                showCancelButton: false,
                confirmButtonText: 'Ok',
                reverseButtons: true
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.replace('../wishlist.html')
                }
            })
        })
    })


    /*---------------------------------------------------------------------
                  DOMContentLoaded
    -----------------------------------------------------------------------*/
    document.addEventListener('DOMContentLoaded', (event) => {
        loaderInit()
    });


    /*---------------------------------------------------------
                        Custom Scripts
    ----------------------------------------------------------*/
  const btn = document.querySelector('#update-cart')
  if (btn !== null) {
    btn.addEventListener('click', (e) => {
        const products = document.querySelectorAll('.product')
        Array.from(products, (row) => {
            const price = row.querySelector('.price').innerText
            const qty = row.querySelector('.qty').value
            let subTotal = Number(price) * Number(qty)
            row.querySelector('.sub-total').innerText = subTotal
        })
    })
  }
    if (jQuery(window).width() < 1200) {
        jQuery('#top-menu .menu-item').on('click', function (e) {
            e.preventDefault();
            jQuery(this).next('.sub-menu').slideToggle();
        });
    }
    
    
    /*---------------------------------------------------------
                        Search Products
    ----------------------------------------------------------*/
    const btnSearch = document.querySelector('#btnSearchProduct')
    if (btnSearch !== null) {
    btnSearch.addEventListener('click', (e) => {  
		$('#pills-breakfast-cereals .row').empty();
        //$('#pills-breakfast-cereals .row').append('<div class="col"><div class="card card-product"><div class="card-header">'+value.businessName+'</div><div class="card-body"><div class="iq-product-image"><img src="images/product/04.png" class="hover_image" alt="img"></div><div class="iq-product-content mt-3"><p class="mb-0"><small>Breakfast Cereals, Snacks</small></p><a href="./product/new-product.html" class="iq-product-title-link h5">Bolthouse</a><div class="d-flex align-items-center justify-content-between my-2"><h6 class="m-0"><span class="text-decoration-line-through"></span><span class="text-primary"><b>$100</b></span></h6><div class="iq-ratings"><div class="d-flex align-items-center text-secondary list-inline p-0 m-0"><i class="fas fa-star fa-xs me-1"></i><i class="fas fa-star fa-xs me-1"></i><i class="fas fa-star fa-xs me-1"></i><i class="far fa-star fa-xs me-1"></i><i class="far fa-star fa-xs me-1"></i></div></div></div><div class="d-flex justify-content-between mb-3"><div class="iq-btn-container"><a class="" href="javascript:void(0)" data-alert="sweetalert-product"><div class="btn btn-secondary animation-btn">Add To Card<i aria-hidden="true" class="fas fa-long-arrow-alt-right ms-2"></i></div></a></div><div class="d-flex align-items-center"><div class="iq-product-icon"><a href="javascript:void(0)" data-alert="sweetalert-heart"><i class="far fa-heart"></i></a></div><div class="iq-product-icon ms-2"><a href="../viewProduct?pid=1"><i class=" fa fa-eye"></i></a></div></div></div></div></div></div></div>');
   		//search by keywords
   		var searchValue = $('#prodSearchKeywords').val();
		var pageNumber = 0;
		
		var globalProductCategory = $('#idProductType option:selected').val();
		
		var paginationSearchRequest = new PaginationSearchRequest();
		paginationSearchRequest.searchValue = searchValue;
		paginationSearchRequest.pageNumber = pageNumber;
		paginationSearchRequest.globalProductCategory = globalProductCategory;

		//search products
		searchProducts(paginationSearchRequest);	
   
    })
  }  
  
    
})(jQuery);
