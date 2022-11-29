const user = document.querySelector('.user')
const menu = document.querySelector('.user-menu')
const html = document.querySelector('html')

if (user !== null) {
    user.addEventListener('click', () => {
        menu.style.display === 'none' || menu.style.display === '' ? menu.style.display = 'block' : menu.style.display = 'none'
    })
}

const profileBtn = document.querySelector('.user-menu li:first-child')
const logoutBtn = document.querySelector('.user-menu li:last-child')

if (profileBtn !== null) {
    profileBtn.addEventListener('click', () => {
        window.location = 'profile.jsp'
    })
}

if (logoutBtn !== null) {
    logoutBtn.addEventListener('click', () => {
        window.location = 'logout'
    })
}

function goManagement() {
    window.location = 'manager.jsp';
}

function goOrder() {
    window.location = 'order.jsp';
}

function goChart() {
    window.location = 'managerChart.jsp'
}