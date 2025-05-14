# Windows no Kafka Streams

Os Windows no Kafka Streams permitem dividir um fluxo contínuo de dados em intervalos de tempo 
(como "últimos 5 minutos") para agregar, analisar ou processar dados em blocos temporais. 
Eles são essenciais para análises em tempo real com semântica de tempo.


# Para que servem os Windows?

- Agregar dados em intervalos (ex: contagem de pedidos por minuto).
- Detectar padrões temporais (ex: picos de acesso a cada hora).
- Processar dados em "pedaços" em vez de um fluxo infinito.

## Tipos de Janelas (Windows)

- Tumbling Window	Janelas fixas e não sobrepostas (ex: de 5 em 5 minutos).
- Hopping Window	Janelas que "pulam" (ex: janela de 5 minutos atualizada a cada 1 minuto).
- Sliding Window	Janelas que deslizam com base em eventos (útil para junções).
- Session Window	Janelas baseadas em períodos de atividade/inatividade do usuário.

# Como Funciona na Prática?

## Produtor envia pedidos para o tópico pedidos-novos:

json
````
{"pedidoId": "123", "cliente": "João", "valor": 150.00}
````
Kafka Streams agrupa os pedidos em janelas de 1 minuto:

Se 10 pedidos chegarem entre 10:00:00 e 10:01:00, o resultado será:

Chave: "pedidos-minuto", Valor: 10
Resultado publicado no tópico pedidos-por-minuto:

json
````
{"key": "pedidos-minuto", "value": "10"}
````


# Casos de Uso Reais

## Monitoramento de Transações
- Janelas de 5 minutos para detectar fraudes (ex: >100 transações/minuto por cartão).

## Análise de Logs
- Contagem de erros por hora em sistemas distribuídos.

## IoT (Sensores)
- Média de temperatura a cada 10 minutos em uma fábrica.
  
 
# Resumo
  
- Windows dividem streams em intervalos temporais para análise.
- Spring Boot + Kafka Streams facilitam a implementação.
- Tumbling Window é o mais comum (janelas fixas).
- Interactive Queries permitem consultar o estado via API.

