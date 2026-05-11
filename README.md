O necessário pra rodar e fazer os testes: Java 17+, Maven(sem banco externo, usa H2 embutido).

Como rodar: clonar o repositório, mudar pro branch api-rest, abrir no IntelliJ, configurar spring.profiles.active=dev no application.properties, rodar RestauranteApplication
Primeiro acesso: registrar em POST /api/auth/registrar, fazer login em POST /api/auth/login, usar o token retornado no header Authorization
Swagger: http://localhost:8080/swagger-ui/index.html
Coleção Postman: arquivo Raizes do Nordeste.postman_collection.json na raiz do repositório
Link do repositório: https://github.com/Icaro-Bruno/coffee_ikro/tree/api-rest