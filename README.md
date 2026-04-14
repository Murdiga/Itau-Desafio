# Itaú Unibanco - Desafio de Programação
API desenvolvida para processar transações e gerar estatísticas em tempo real, conforme desafio técnico proposto pelo Itaú.
## 🚀 Tecnologias utilizadas
- Java
- Spring Boot
- Maven
- JUnit / Mockito
- Spring Boot Actuator
- SLF4J (com Log4j2)
## 📌 Objetivo
Receber transações e calcular estatísticas (count, sum, avg, min, max) com base em um intervalo de tempo definido (por padrão, últimos 60 segundos).
## ⚙️ Funcionalidades
- ✅ Receber transações via API
- ✅ Validar regras de negócio (valores negativos, timestamps futuros, etc.)
- ✅ Calcular estatísticas em tempo real
- ✅ Suporte a intervalo customizável (ex: minutos definidos pelo usuário)
- ✅ Remover todas as transações (reset da base em memória)
## 📡 Endpoints
### POST /transacao
Recebe uma nova transação

Body:
{
  "valor": 100.0,
  "dataHora": "2026-04-11T12:00:00Z"
}

Respostas:
- 201 Created
- 422 Unprocessable Entity
- 400 Bad Request
### DELETE /transacao
Remove todas as transições armazenadas.

---
### GET /estatistica

Retorna estatísticas das transações com base em um intervalo de tempo.

#### Parâmetros (query)

- `minutes` (opcional): intervalo em minutos para cálculo das estatísticas  
  - Default: `1`
#### Exemplo de requisição

GET /estatistica?minutes=2

#### Resposta (200 OK)

{
  "sum": 100.0,
  "avg": 50.0,
  "max": 70.0,
  "min": 30.0,
  "count": 2
}

---
### GET /metrics/statistics
Retorna métricas de desempenho relacionadas ao tempo de execução para geração das estatísticas.

#### Resposta (200 OK)
{
  "max": 5.79,
  "count": 3,
  "averageTime": 2.03,
  "totalTime": 6.10
}

## 🧠 Regras de negócio
- Transações com timestamp no futuro ou valores negativos são rejeitadas
- Transações fora do intervalo informado para estatísticas são ignoradas
- O cálculo considera apenas transações dentro do intervalo especificado
- Caso não existam transações válidas, os valores retornados serão zerados
- O tempo médio de geração das estatísticas é calculado e exposto por meio de um endpoint específico
## 🏗️ Arquitetura
O projeto segue princípios de Clean Code e separação de responsabilidades:
- Controller → entrada da API
- Use Case → regras de negócio
- Repository → armazenamento em memória
- Monitoramento com Spring Actuator