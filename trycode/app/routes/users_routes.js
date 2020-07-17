module.exports = function (app) {
    app.get('/data', (request, response) => {
        var result = [{
            "id": 1,
            "data": "ФИО: Аксанов Камиль Ниязович"
        },
            {
                "id": 2,
                "data": "Возраст: 19"
            },
            {
                "id": 3,
                "data": "Почта: aksanovkamil@gmail.com"
            },
            {
                "id":4,
                "data": "Номер телефона: 89191118822"
            },
            {
                "id": 5,
                "data": "Цель: получить должность программиста"
            },
            {
                "id": 6,
                "data": "Опыт: нет"
            },
            {
                "id": 7,
                "data": "Владение яп: java"
            }
        ];
        response.send(JSON.stringify(result));
    });

};
