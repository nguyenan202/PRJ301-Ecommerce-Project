const urlOrder = 'http://localhost:8080/Project1/api-admin-order'
const urlTopCustomer = 'http://localhost:8080/Project1/api-admin-topcustomer'
const urlTopOrder = 'http://localhost:8080/Project1/api-admin-toporder'

function doChartOrder(labels, orderAccepted, orderRejected, orderPending) {
    const data = {
        labels: labels,
        datasets: [{
            label: 'Order Accepted',
            backgroundColor: 'rgb(38, 212, 32)',
            borderColor: 'rgb(38, 212, 32)',
            data: orderAccepted,
        }, {
            label: 'Order Rejected',
            backgroundColor: 'rgb(240, 14, 14)',
            borderColor: 'rgb(240, 14, 14)',
            data: orderRejected,
        }, {
            label: 'Order Pending',
            backgroundColor: 'rgb(7, 172, 222)',
            borderColor: 'rgb(7, 172, 222)',
            data: orderPending,
        }]
    };

    const config = {
        type: 'line',
        data: data,
        options: {
            //            animations: {
            //                tension: {
            //                    duration: 3000,
            //                    easing: 'linear',
            //                    from: 1,
            //                    to: 0,
            //                    loop: true
            //                }
            //            },
            scales: {
                y: {
                    min: 0,
                    max: Math.floor(Math.max(...orderAccepted.concat(orderPending).concat(orderRejected)) * 2)
                }
            }
        }
    };

    const ctx = document.getElementById('my-chart-order');
    const myChart = new Chart(ctx, config)
}

function doChartCustomer(users, totalOrderMoney) {
    const labels = users;
    const data = {
        labels: labels,
        datasets: [{
            label: 'Total Order $',
            data: totalOrderMoney,
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(255, 159, 64, 0.2)',
                'rgba(255, 205, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(201, 203, 207, 0.2)'
            ],
            borderColor: [
                'rgb(255, 99, 132)',
                'rgb(255, 159, 64)',
                'rgb(255, 205, 86)',
                'rgb(75, 192, 192)',
                'rgb(54, 162, 235)',
                'rgb(153, 102, 255)',
                'rgb(201, 203, 207)'
            ],
            borderWidth: 1
        }]
    };

    const config = {
        type: 'bar',
        data: data,
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        },
    };

    const ctx = document.getElementById('my-chart-customer');
    const myChart = new Chart(ctx, config)
}

function doChartProduct(names, units) {
    const data = {
        labels: names,
        datasets: [{
            label: 'My First Dataset',
            data: units,
            backgroundColor: [
                'rgb(255, 99, 132)',
                'rgb(75, 192, 192)',
                'rgb(255, 205, 86)',
                'rgb(201, 203, 207)',
                'rgb(54, 162, 235)'
            ]
        }]
    };

    const config = {
        type: 'polarArea',
        data: data,
        options: {
            
        }
    };

    const ctx = document.getElementById('my-chart-product');
    const myChart = new Chart(ctx, config)
}

function start(url1, url2, url3) {
    fetch(url1)
        .then(res => res.json())
        .then((arr) => {
            const months = arr.map(obj => obj["month"])
            const orderRejected = arr.map(obj => obj["order"]["Rejected"])
            const orderPrending = arr.map(obj => obj["order"]["Pending"])
            const orderAccepted = arr.map(obj => obj["order"]["Accepted"])

            doChartOrder(months, orderAccepted, orderRejected, orderPrending)
        })
        .catch(err => console.log(err))

    fetch(url2)
        .then(res => res.json())
        .then(arr => {
            const users = arr.map(obj => obj.username)
            const totalOrderMoney = arr.map(obj => obj.totalMoney)

            doChartCustomer(users, totalOrderMoney)
        })
        .catch(err => console.log(err))

    fetch(url3)
        .then(res => res.json())
        .then(arr => {
            const names = arr.map(obj => obj.name)
            const units = arr.map(obj => obj.unitOnOrder)
            doChartProduct(names, units)
        })
}

start(urlOrder, urlTopCustomer, urlTopOrder)