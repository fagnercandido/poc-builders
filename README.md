# Instruções para execução

## Seguem os passos para execução com Docker:

1. É necessã́rio ter o Docker instalado localmente. 
2. Execute o seguinte comando na raiz do projeto:
   ```shell
        docker-compose up --build
   ```
3. Documentação do Swagger http://localhost:8080/swagger-ui/index.html 

## Seguem os passos para execução sem o Docker e usando H2:

1. Execute a classe Application. 
2. Adicione o profile "test" na IDE(testado no Eclipse 2021-06. Para o IntelliJ e VSCode o profile de test não funcionou conforme esperado).
3. Documentação do Swagger http://localhost:8080/swagger-ui/index.html 


# Decisões de Design e Pontos de Melhoria

1. Poderia ter sido usado o lombok, opção que não foi usada. Numa possível migração para o Java 14, opção essa que poderia ser facilmente substituída pelos "records"
2. Nos serviços optou-se por nã́o usar interface, visto que não acrescentavam valor, eram anêmicas.
3. Optou-se por usar o construtor ao Autowired.
