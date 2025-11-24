# carteira-acoes-backend

O servidor estará disponível em `http://localhost:3000`

## API Endpoints

### Ações

- **GET** `/api/acoes` - Lista todas as ações da carteira
- **POST** `/api/acoes` - Adiciona uma nova ação
- **PUT** `/api/acoes/:id` - Atualiza uma ação
- **DELETE** `/api/acoes/:id` - Remove uma ação

### Cotações

- **GET** `/api/cotacoes/:ticker` - Obtém cotação atual de um ativo
- **GET** `/api/cotacoes/carteira` - Obtém cotações de todos os ativos da carteira

### Relatórios

- **GET** `/api/relatorios/rentabilidade` - Calcula rentabilidade da carteira
- **GET** `/api/relatorios/distribuicao` - Mostra distribuição dos ativos

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## Segurança

- Utilize variáveis de ambiente para informações sensíveis
- Mantenha o arquivo `.env` fora do controle de versão
- Configure CORS adequadamente para produção

## License

Copyright © 2025 FIXME
