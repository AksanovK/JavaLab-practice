// используем библиотеку express
const express = require('express');
// создаем объект экспресс
const app = express();
//говорим, что раздаем папку public (статика)
app.use(express.static('public'));
// запускаем на порту 80
app.listen(80);
console.log("Server started at 80");

var path = require('path');
app.use(express.static(path.join(__dirname, 'public')));
