//onchange profile and display save btn
const firstName = document.querySelector('.fname')
const lastName = document.querySelector('.lname')
const email = document.querySelector('.email')
const phone = document.querySelector('.phone')
const saveBtn = document.querySelector('.save-change')

firstName.addEventListener('change', () => {
    saveBtn.style.display = 'inline-block'
})
lastName.addEventListener('change', () => {
    saveBtn.style.display = 'inline-block'
})
email.addEventListener('change', () => {
    saveBtn.style.display = 'inline-block'
})
phone.addEventListener('change', () => {
    saveBtn.style.display = 'inline-block'
})

//change password
const changePassBtn = document.querySelector('.change-pass-btn button')

changePassBtn.addEventListener('click', () => {
    window.location = 'changepassword.jsp'
})

//Process Address (add, dele)
const addAddressBtn = document.querySelector('.add-address-btn')
const inputAddress = document.querySelector('.input-address')

addAddressBtn.addEventListener('click', () => {
    if (inputAddress.style.display === '' || inputAddress.style.display === 'none') {
        inputAddress.style.display = 'block'
        addAddressBtn.querySelector('input').value = 'Cancel'
    } else {
        inputAddress.style.display = 'none'
        addAddressBtn.querySelector('input').value = 'Add new Address'
    }
})

function doRemoveAddress(id, num) {
    let cf = confirm('Remove Address ' + num + '?')
    if (cf)
        window.location = 'address?id=' + id
}