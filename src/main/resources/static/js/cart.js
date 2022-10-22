loadCart()

function addCart(id) {
    const data = {productId: id}
    $.ajax({
        url: "/order/add",
        type: "POST",
        data: data,
        dataType: 'json',
        success: function (data) {
            console.log(data)
            updateCart(data)
            console.log('data')
        }
    })
}

function removeCart(id) {
    const data = {productId: id}
    $.ajax({
        url: "/order/del",
        type: "POST",
        data: data,
        dataType: 'json',
        success: function (data) {
            console.log(data)
            updateCart(data)
        }
    })
}

function loadCart() {
    $.ajax({
        url: "/order/load",
        type: "GET",
        data: '',
        dataType: 'json',
        success: function (data) {
            console.log(data)
            updateCart(data)
        }
    })
}

function buyCart() {
    $.ajax({
        url: "/order/buy",
        type: "GET",
        data: '',
        dataType: 'json',
        success: function (data) {
            console.log(data)
            updateCart(data)
        }
    })
}

function updateCart(data) {
    const cart = document.getElementById('cart')
    while (cart.firstChild) {
        cart.removeChild(cart.firstChild)
    }
    let tr
    let tdIm
    let tdName
    let tdPrice
    let tdQuantity
    let table = document.createElement('table')
    table.setAttribute('class', 'table')
    let tbody = document.createElement('tbody')
    table.appendChild(tbody)

    let img = document.createElement('img')
    let totalPrice = 0
    let cartItems = 0
    for (let product of data) {
        cartItems += product.quantity
        totalPrice += product.price * product.quantity
        console.log(product)
        tr = document.createElement('tr')
        tdIm = document.createElement('td')
        tdName = document.createElement('td')
        tdPrice = document.createElement('td')
        tdQuantity = document.createElement('td')
        img = document.createElement('img')
        if (product.image) {
            img.setAttribute('src', product.image)
        } else {
            img.setAttribute('src', 'https://i.ibb.co/nQKknXZ/image-not-found.jpg')
        }
        tdIm.appendChild(img)
        tdName.innerText = product.name
        if (product.price) {
            tdPrice.innerText = '$' + product.price
        }
        const div = document.createElement('div')
        div.setAttribute('class', 'product-links')
        let i = document.createElement('i')
        i.setAttribute('class', 'fa fa-plus')
        i.setAttribute('id', product.id)
        i.onclick = function () {
            addCart(product.id)
        }
        div.appendChild(i)
        const b = document.createElement('b')
        b.innerText = product.quantity
        div.appendChild(b)
        i = document.createElement('i')
        i.setAttribute('class', 'fa fa-minus')
        i.setAttribute('id', product.id)
        i.onclick = function () {
            removeCart(product.id)
        }
        div.appendChild(i)
        tdQuantity.appendChild(div)
        tr.appendChild(tdIm)
        tr.appendChild(tdName)
        tr.appendChild(tdPrice)
        tr.appendChild(tdQuantity)
        tbody.appendChild(tr)
        cart.appendChild(table)
    }
    updateCartIcon(cartItems)
    let h4 = document.createElement('h4')
    h4.setAttribute('align', 'right')
    h4.innerHTML = 'Total: $' + totalPrice.toFixed(2)
    cart.appendChild(h4)


    function updateCartIcon(count) {
        const iconCart = document.getElementById('lblCartCount')
        if (count === 0) {
            iconCart.innerText = ''
        } else {
            iconCart.innerText = count
        }
    }
}