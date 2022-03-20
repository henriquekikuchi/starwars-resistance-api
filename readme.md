# Starwars Resistance Social Network API
Projeto desenvolvido para auxiliar os rebeldes envolvidos na luta contra o império.
## Descrição do problema
O império continua na luta incessante de dominar a galáxia, tentando com todas suas forças acabar com os rebeldes.
E você, soldado da resistência foi convocado para desenvolver um sistema para compartilha recursos entre os rebeldes.
## Requisitos funcionais
### Adicionar rebeldes
O rebelde deve ter nome, idade, genero, localização (latitude, longitude, nome) e também um inventário contendo os recursos em sua posse.
### Atualizar localização do rebelde
Um rebelde deve ter a capacidade de reportar sua última localização, armazenando latitude/longitude/nome (não é necessário rastrear as localizações, apenas sobrescrever a última é suficiente)
### Reportar o rebelde como um traidor
Eventualmente algum rebelde irá trair a resistência e se aliar ao império. Quando isso acontecer, nós precisamos informar que o rebelde é um traidor.
Um traidor não pode negociar os recursos com os demais rebeldes, não pode manipular seu inventário, nem exibido em relatórios.
Um rebelde é marcado como traidor quando, ao menos, três outros rebeldes reportarem a traição.
Uma vez marcado como traidor, os itens do inventário se tornam inacessíveis (eles não podem ser negociados com os demais).
### Rebeldes não podem Adicionar/Remover itens do seu invetário
Seus pertences devem ser declarados quando eles são registrados no sistema. Após isso eles só poderão mudar seu inventário através de negociação com outros rebeldes.
### Negociar itens
Os rebeldes poderão negociar itens entre eles.
Para isso, eles devem respeitar a tabela de preços abaixo, onde o valor do item é descrito em termos de pontos;
Ambos os lados deverão oferecer a mesma quantidade de pontos. Por exemplo, 1 arma e 1 água (1 x 4 + 1 x 2) valem 6 comidas (6 x 1) ou 2 munições (2 x 3).
A negociação em si não será armazenada, mas os itens deverão ser transferidos de um rebelde a outro.

|    ITEM    |  PONTOS   |
|:----------:|:---------:|
|   1 Arma   |     4     |
| 1 Munição  |     3     |
|   1 Água   |     2     |
|  1 Comida  |     1     |
### Relatórios
  1. Porcentagem de traidores
  2. Porcentagem de rebeldes
  3. Quantidade média de cada tipo de recurso por rebelde (Ex: 2 armas por rebelde)
  4. Pontos perdidos devido a traidores
## Requisitos não funcionais
1. Utilizar Java, Spring boot, Spring Data, Hibernate (pode usar H2) e gradle ou maven
2. Não será necessário autenticação
3. Código limpo demonstrará que você é um soldado digno da resistencia atraves de suas habilidades
4. Sua API deve estar minimamente coberta por testes (Unitários e/ou integração).

## Estrutura do projeto
### Camadas
* #### config
Nessa camada colocamos as configurações que são utilizadas para algumas funcionalidades do projeto, como por exemplo o *Swagger*, transferimos a responsabilidade por gerenciar a instancia dessas classes para o *Spring container*.
* #### controller
Aqui é onde cadastramos as rotas para os end-points e o método responsável de retornar uma resposta quando o end-point for chamado.
* #### dto
Os dtos são os objetos que serão utilizados para transferir os dados que são recebidos no controller para a camada de **services** e vice-versa. Por exemplo quando um rebelde está sendo cadastrado não é preciso que seja passado o id dele, pois esse dado será gerenciado pelo própio banco de dados.
* #### exception
As exceptions que a api pode emitir são cadastradas nessa camada para organizar o código. Elas herdam de RuntimeException para que não fosse necessário que fossemos obrigados a usar bloco try-catch ou throws na assinatura dos métodos que lançam essas excessões.
* #### handler
Nessa camada é onde temos um ControllerAdvice que vai pegar as nossas exceções e permitir que a tratemos e retornemos uma mensagem mais amigável para o usuário da API e evitar que o stacktrace apareça na tela.
* #### mapper
Como estamos utilizando dtos, temos a necessidade de sempre convertermos ele para a entity e vice-versa dependendo da necessidade, então para facilitar utilizamos uma lib chamada **MapStruct** para lidar com essa conversão.
* #### models
Aqui estão as nossas entities, dentro dessa camada marcamos as classes com @Entity e setamos as configurações de cada campo para que ele corresponda com o mesmo no banco de dados, também criamos relacionamento entre as classes, de modo que isso por meio do **Jpa** irão ser convertidas em relacionamentos também no banco de dados.
* #### repository
Como estamos utilizando o Jpa que é um ORM para conectar com o banco de dados, criamos as interfaces extendendo de JpaRepository<Object, ObjectId>, e sim não precisamos implementar os métodos mais básicos, pois o proprio Jpa ja os implementa, sim as interfaces podem implementar métodos default, mas caso precisemos conseguimos implementa-los utilizando a anotação @Query ou até mesmo sobrescrevendo os métodos ja implementados pela propria interface do Jpa.
* #### service
Nessa camada tratamos a interação entre os repositories e os controllers, de modo que as regras de negócios e o acesso aos dados aconteçam aqui, dessa forma os controllers ficam agnosticos, afinal e se trocarmos a tecnologia do banco de dados né? Precisariamos alterar somente nessa camada o que fosse necessário e o controller não seria afetado.
* #### resouces/application.yml
Podemos lembrar desse arquivo como se fosse um .env, nele podemos colocar variaveis de ambiente e configurações do banco de dados, caching, log e muito mais.
#### Testes
Realizei os testes unitários nas classes **Report e Rebel**.

## Ao avaliador
### Orientação:
Coloquei na pasta resources um arquivo do postman contendo todos os testes necessários para testar as rotas do projeto.
### Requisitos do projeto:
- [x] Rota para cadastro de novos rebeldes
- [x] Rota para atualizar a localização do rebelde
- [x] Rota para realizar troca de itens entre os rebeldes
- [x] Rota para denunciar rebeldes como traidores
- [x] Rota para gerar relatórios
- [x] Realizado os testes unitários na camada de services
- [x] Boas práticas do SOLID (talvez)

## Projeto
### Pré requisitos
- Java 17
- Maven

### Instalação
Clone o repositório e entrando dentro da pasta raiz utilize o próprio maven wrapper para conseguir roda-lo.
>./mvnw spring-boot:run

### Como utilizar
Após inicia-lo por meio do comando citado acima na parte de *Instalação* será necessário que acesse a documentação das rotas para entender como fazer requisições aos **End Points**. Caso precise alterar a porta que o projeto está usando altere no arquivo **resources/application.yml**. Para isso acesse o seguinte endereço:
> http://localhost:8080/api/starwars/swagger-ui.html

## End points
### /rebels
> GET http://localhost:8080/api/starwars/rebels para listar todos rebeldes

> POST http://localhost:8080/api/starwars/rebels para salvar novo rebelde

Request body:
```
{
    "name": "DWIGHT SCHURUTE",
    "age": 41,
    "gender": "MALE",
    "location": {
        "latitude": 1.555,
        "longitude": 56.655,
        "name": "Netuno"
    },
    "resources": [
        {
            "resource": "BULLET",
            "quantity": 2
        }, {
            "resource": "WEAPON",
            "quantity": 4
        }, {
            "resource": "FOOD",
            "quantity": 7
        }, {
            "resource": "WATER",
            "quantity": 9
        }
    ]
}
```
> GET http://localhost:8080/api/starwars/rebels/{id} para retornar o rebelde pelo id

> PATCH http://localhost:8080/api/starwars/rebels/{id}/location para atualizar a localização do rebelde

Request body:
```
{
    "latitude": 1.555,
    "longitude": 5.555,
    "name": "Lets code"
}
```

> POST http://localhost:8080/api/starwars/rebels/{id}/negotiate/{idOtherRebel} negociar itens entre rebeldes

Request body:
```
{
    "send": [
        {
            "resource": "WATER",
            "quantity": 2
        }
    ]
    ,
    "receive": [
        {
            "resource": "WEAPON",
            "quantity": 1
        }
    ]
}
```

### /report
> POST http://localhost:8080/api/starwars/reports para denunciar rebelde

Request body:
```
{
  "idAccused": 1,
  "idAccuser": 2
}
```

### /dashboard
> GET http://localhost:8080/api/starwars/dashboard

Response body:
```
{
    "dashboardMap": {
        "Percentage Of Rebels And Betrayers": {
            "Rebel": 0.8333333333333334,
            "Betrayer": 0.16666666666666666
        },
        "Lost Points": {
            "Because Of Betrayers": 31.0
        },
        "Mean Resources Per Rebel": {
            "weapon": 5.2,
            "bullet": 4.0,
            "water": 5.4,
            "food": 6.2
        }
    }
}
```