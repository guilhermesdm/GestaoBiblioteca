
# Gestão de Biblioteca com Recomendação de Livros

O projeto fornece uma API e aplicação front-end para gestão de uma biblioteca.

## Funcionalidades

- CRUD Usuários: Cadastro, atualização, deleção de usuários com validação de dados como e-mail, telefone e data de cadastro.
- CRUD Livros: Cadastro, atualização, deleção com informações essenciais, incluindo categoria.
- CRUD Empréstimos: Cadastro de empréstimos e devoluções com data de devolução e status.
- Recomendação de Livros: Sugestão de livros para o usuário com base nas categorias dos livros que ele já leu.
- Testes Unitários: Cobertura dos serviços principais da aplicação com JUnit e Mockito.
- Angular: Aplicação gráfica para a gestão dos itens ditos acima.
- Rotas para consumo da API do Google Books, é possível se redirecionar até a tela de livros, realizar uma busca digitável, e adicionar os livros a partir dali, assim como é possível, na aba de livros, mostrar informações adicionais quando o livro vem da API. Rotas estão documentadas no swagger.


## Tecnologias usadas

- Backend: Java, Spring Boot
- Banco de Dados: PostgreSQL
- Frontend: Angular 15 com Angular Material, TypeScript, Node
- Testes Unitários: JUnit, Mockito
- Gerenciador de dependências: Maven e NPM
## Configuração

### Pré-requisitos
- Java 17+
- Maven
- PostgreSQL
- Node.js

### Rodar o sistema
#### Copiar o projeto
```bash
git clone <url_do_repositorio>
cd <diretorio_do_projeto>
```

#### Testes unitários
Se redirecione para a pasta do backend e digite `mvn test`.

#### Configurar
##### Banco de dados
No arquivo application.yaml configure os caminhos abaixo para o banco de dados, será usado postgres. Uma configuração padrão será deixada
```yaml
datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password:
```

##### API Google Books
No arquivo application.yaml configure o caminho com a sua chave da [API do Google](https://developers.google.com/books)
```yaml
google:
  books:
    api:
      key: 
```

##### Inicie o sistema
- Entre na pasta do backend e digite `mvn spring-boot:run`, servidor irá rodar na porta padrão 8080
- Entre na pasta do frontend e digite `ng serve`, cliente irá rodar na porta padrão 4200

## Documentação

A documentação da API foi feita com swagger, lá pode ser conferido todas as rotas e descrições das mesmas, no link [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Será deixado um JSON com rotas do Insomnia para teste.