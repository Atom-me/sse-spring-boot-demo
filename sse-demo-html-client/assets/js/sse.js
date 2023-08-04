'use strict';

const SERVER_URL = 'http://localhost:8080';
const orderContainer = document.getElementById('order-list');

document.addEventListener('DOMContentLoaded', init);

function init() {
    const evtSource = new EventSource(`${SERVER_URL}/orders-sse`);
    // 对应服务端定义的eventName
    evtSource.addEventListener("new-order", processNewOrderEvent);
}

function processNewOrderEvent(event) {
    const order = JSON.parse(event.data);
    const listItem = document.createElement('li');
    listItem.textContent = `${order.customerName}: ${order.mealName}`;
    orderContainer.appendChild(listItem);
}