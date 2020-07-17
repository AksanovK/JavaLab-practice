module.exports = function (app) {
    app.get('/profile.html', (request, response) => {
        var result = [{
            "id": 1,
            "data1": "ФИО: Аксанов Камиль Ниязович"
        },
            {
                "id": 2,
                "data1": "Возраст: 19"
            },
            {
                "id": 3,
                "data1": "Почта: aksanovkamil@gmail.com"
            },
            {
                "id": 4,
                "data1": "Номер телефона: 89191118822"
            },
            {
                "id": 5,
                "data1": "Цель: получить должность программиста"
            },
            {
                "id": 6,
                "data1": "Опыт: нет"
            },
            {
                "id": 7,
                "data1": "Владение яп: java"
            }
        ];
        response.send(JSON.stringify(result));
    });
}