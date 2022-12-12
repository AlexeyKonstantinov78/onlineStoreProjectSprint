'use strict';

const openSearch = document.querySelector('.openSearch');
const searchProductForm = document.querySelector('.search__product_form');

const toogle = () => {
     searchProductForm.classList.toggle('search__product_form_activ');
     if (searchProductForm.classList.contains('search__product_form_activ')) {
          openSearch.textContent = 'Закрыть поиск';
     } else {
          openSearch.textContent = 'Найти';
     }
}

openSearch.addEventListener('click', (event) => {
     toogle();
});

document.addEventListener('DOMContentLoaded', () => {
     if (document.location.pathname.includes('/product/search')) toogle();
});

