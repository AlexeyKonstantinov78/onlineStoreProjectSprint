'use strict';

const ordersItem = document.querySelectorAll('.orders__item');

const toggle = (target) => {
    if (target.parentNode.classList.contains('orders__item')) target.parentNode.classList.toggle('orders__item_activ');

    if (target.classList.contains('orders__item')) target.classList.toggle('orders__item_activ');
}

ordersItem.forEach((item) => {
    item.addEventListener('click', (event) => {
        toggle(event.target);
    });
})
