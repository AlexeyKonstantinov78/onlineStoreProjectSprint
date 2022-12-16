'use strict';

const ordersItem = document.querySelectorAll('.orders__item');
const ordersSearchInput = document.querySelector('.orders__search_input');
const searchRezult =document.querySelector('.search__rezult');

const toggle = (target) => {
    if (target.parentNode.classList.contains('orders__item')) target.parentNode.classList.toggle('orders__item_activ');

    if (target.classList.contains('orders__item')) target.classList.toggle('orders__item_activ');
}

const render = (data) => {
  const div = document.createElement('div');
  div.classList.add('orders__number_item');
  div.innerHTML = `
    <a class="orders__number_item" href="/admin/orders/editStatus/${data.id}">
        <p >Номер заказа: ${data.number}</p>
        <div class="orders__product_name">
          <p>Наименование: + ${data.product.title}"</p>
          <p>Статус: ${data.status.status}</p>
        </div>
    </a>
  `;

  searchRezult.append(div);
};

const getSearchNumberOrders = async (search) => {

    searchRezult.textContent = '';
    const date = await fetch(`http://localhost:8080/api/orders/search?q=${search}`).then((res) => {
        if (res.ok) {
                return res.json();
        }
    });

    searchRezult.textContent = '';
    date.forEach(item => render(item));
}

ordersItem.forEach((item) => {
    item.addEventListener('click', (event) => {
        toggle(event.target);
    });
})

ordersSearchInput.addEventListener('change', (event) => {
    event.preventDefault();
    let inputValue = event.target.value;
    if (inputValue.length <4 ) {
        searchRezult.textContent = 'Введите больше 3 знаков';
    } else {
        getSearchNumberOrders(event.target.value);
    }
})
