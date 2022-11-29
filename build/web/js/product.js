function showNotEnought() {
    alert('Not Enought Product');
}

//Scroll to top
const mybutton = document.querySelector('.btn')
window.onscroll = function () {
    scrollFunction()
};

function scrollFunction() {
    if (document.body.scrollTop > 800 || document.documentElement.scrollTop > 800) {
        mybutton.style.display = "block";
    } else {
        mybutton.style.display = "none";
    }
}

//Show Edit Product Form
const editBtns = document.querySelectorAll('.edit-btn')
editBtns.forEach((btn) => {
    btn.addEventListener('click',(e) =>{
        console.log(e)
    })
})