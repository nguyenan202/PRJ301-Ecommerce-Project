//function confirm delete
function doDelete(id) {
    const cf = confirm('Remove Product id ' + id + ' from Order?')
    if (cf)
        window.location = 'cartquantity?productId='+id
}

//function update quantity item
function updateQuantity(id,qua) {
    document.f.action = 'cartquantity?productId='+id+'&quantity='+qua
    document.f.submit()
}