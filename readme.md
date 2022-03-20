# Starwars Resistance API
Projeto desenvolvido para auxiliar os rebeldes envolvidos na luta contra o império.

# Disclaimer
Haha na verdade é um projeto para conclusão do modulo de desenvolvimento web do curso de _Desenvolvimento full stack_ da _lets code_ em parceria com o _Santander_. O intuito do projeto é avaliar o aprendizado durante o módulo.

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
>./mvnw exec:java -Dexec.mainClass="br.com.kikuchi.henrique.StarwarsApiApplication"

### Como utilizar
Após inicia-lo por meio do comando citado acima na parte de *Instalação* será necessário que acesse a documentação das rotas para entender como fazer requisições aos **End Points**. Caso precise alterar a porta que o projeto está usando altere no arquivo **resources/application.yml**. Para isso acesse o seguinte endereço:
> http://localhost:8080/api/starwars/swagger-ui.html
