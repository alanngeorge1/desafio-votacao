API REST desenvolvida para gerenciar pautas, sessões de votação e votos de associados em assembleias de cooperativas.

Este projeto atende ao desafio proposto, com os seguintes requisitos:

- Cadastrar pautas
- Abrir sessões de votação com tempo configurável
- Registrar votos únicos por associado/pauta
- Retornar o resultado da votação
- Persistência com banco de dados
- Documentação com Swagger
- Testes automatizados e carga de performance (1000 votos testados)


Tecnologias utilizadas:

Tecnologia						Uso
Java 21	                        Linguagem principal
Spring Boot 3.2.5				Framework principal
Spring Web	                    API REST (controllers/endpoints)
Spring Data JPA	                Acesso a banco de dados relacional
H2 Database	                    Banco de dados em memória (testes e local)
Lombok	                        Redução de boilerplate (getters/setters/construtores/logs)
Swagger/Springdoc OpenAPI	    Documentação interativa da API
JUnit 5	                        Testes automatizados
Mockito	                        Mocks e testes unitários
AssertJ	                        Fluent Assertions
Logback + @Slf4j	            Logging centralizado e padronizado


Arquitetura:
API RESTful versionada: /api/v1/

Separação em camadas:
Controller -> entrada REST
Service -> regras de negócio
Repository -> persistência
DTOs -> comunicação entre camadas e com clientes
ExceptionHandler -> tratamento global de erros


A documentação interativa da API está disponível via Swagger:
http://localhost:8080/swagger-ui/index.html


Endpoints principais:
Recurso	                  		Método	                        Caminho
Cadastrar pauta	          		POST	          			    /api/v1/pautas
Listar pautas	          		GET	    		  				/api/v1/pautas
Abrir sessão de votação     	POST	 						/api/v1/sessoes
Registrar voto					POST	        				/api/v1/votos
Consultar resultado da votação	GET	           					/api/v1/pautas/{id}/resultado

Fluxo swagger:
Fluxo					Endpoint						Método					Body
Criar Pauta				/api/v1/pautas					POST					JSON
Abrir Sessão			/api/v1/sessoes					POST					JSON
Registrar Voto			/api/v1/votos					POST					JSON
Consultar Resultado		/api/v1/pautas/{id}/resultado						    GET	—


Testes automatizados:
Controllers
Services
Integração completa (MockMvc)
Simulação de 1000 votos realizada com sucesso para validação de performance e concorrência.


Tarefa Bônus 1 - Integração com sistema externo (validação de CPF)
Implementada Facade Fake que simula validação de CPF:
Responde randomicamente:
"ABLE_TO_VOTE"
"UNABLE_TO_VOTE"
ou 404 para CPFs inválidos


Tarefa Bônus 2 - Performance:
Teste de carga executado simulando 1000 votos com sucesso.
Observações: Nenhum problema de concorrência identificado a API manteve estabilidade com múltiplos registros.


Tarefa Bônus 3 - Versionamento da API
API versionada em /api/v1/
Estratégia: versionamento por URI (mais transparente e compatível com clientes mobile).


Como rodar o projeto localmente

Pré-requisitos
Java 21+

Maven 3.9+

Executando
bash
Copiar
Editar
git clone https://github.com/alanngeorge1/desafio-votacao.git
cd desafio-votacao
mvn clean install
mvn spring-boot:run
A aplicação estará disponível em: http://localhost:8080


Como testar a API
Via Swagger
Acesse: http://localhost:8080/swagger-ui/index.html

Via Postman
Coleção Postman disponível em:
src/main/postman/desafio-votacao.postman_collection.json
Basta importar no Postman e executar os testes.


Banco de Dados
Tipo: Banco de dados em memória — H2 Database
Console Web: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:file:./data/testdb
Usuário: sa
Senha: (em branco)

Observações sobre Reset do Banco de Dados
Caso seja necessário resetar totalmente o banco de dados (excluir todas as tabelas e recriar):

No arquivo: src/main/resources/application.properties
Localize a linha: spring.jpa.hibernate.ddl-auto=update
Altere temporariamente para: spring.jpa.hibernate.ddl-auto=create
Inicie a aplicação — o banco será recriado do zero.
Em seguida, retorne para update: spring.jpa.hibernate.ddl-auto=update

Observação importante: Modo update -> garante que os dados serão persistidos no arquivo ./data/testdb entre execuções da aplicação.
Os arquivos do H2 (testdb.mv.db e testdb.trace.db) são responsáveis por armazenar o conteúdo persistido.

Logs:
Log centralizado com @Slf4j
Criação de pauta
Abertura de sessão
Registro de voto
Cálculo de resultado


Commits com padrão:
refactor(tests): padroniza endpoints e correção testes de integração
feat: implementação de validação de CPF


Resumo das escolhas técnicas
Spring Boot pela maturidade e produtividade.
Lombok para reduzir boilerplate.
Swagger/OpenAPI para documentação viva da API.
Versionamento por URI (mais simples para clientes mobile).
Banco em memória H2 para simplicidade em desenvolvimento e testes.
Testes de carga realizados (1000 votos).
Facade fake para integração externa simulada (validação de CPF).

Considerações finais
A aplicação está estável, testada e atende todos os requisitos do desafio, incluindo os bônus.
Qualquer dúvida ou sugestão, estou à disposição!