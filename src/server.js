const express = require('express');
require('dotenv').config();

const estoqueRoutes = require('./routes/estoque.routes');

const app = express();

app.use(express.json());
app.use(estoqueRoutes);

const porta = process.env.PORT || 8088;

app.listen(porta, () => {
  console.log('Servidor rodando na porta ' + porta);
});
