# <h1 align="center"> TicketMasterAPI </h1>
<p align="center">
<img src="http://img.shields.io/static/v1?label=STATUS&message=%20FINALIZADO&color=black&style=for-the-badge"/>
</p>

<p align="center">
<img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white"/>
<img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white"/>
<img src="https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/PayPal-00457C?style=for-the-badge&logo=paypal&logoColor=white">
</p>
<hr>

#### Projeto
A TicketMasterAPI é uma API para gerenciamento de eventos e venda de ingressos, desenvolvida com Java e Spring Boot, permitindo aos usuários a compra de Ingressos ou se tornar um Organizador para criação de Eventos. 
O sistema oferece as funcionalidades de autenticação, gerenciamento de usuários, eventos, ingressos, organizadores e endereços.

#
#### Principais Recursos

- Gerenciamento de usuários: Possibilidade de cadastrar, consultar, atualizar e excluir informações de usuários.

- Gerenciamento de endereços: Possibilidade de cadastrar, consultar, atualizar e excluir informações de endereços.

- Gerenciamento de eventos: Possibilidade de cadastrar, consultar, atualizar e excluir informações de eventos.

- Grenciamento de ingressos: Possibilidade de comprar ingressos, listar ingressos do usuário e concluir o pagamento de ingressos.

- Gerenciamento de organizadores: Possibilidade de cadastrar, atualizar e listar organizadores. __Os organizadores são usuários que recebem a ROLE_ORGANIZADOR para ter acesso a funcionalidades específicas.__

- Autenticação e autorização: Implementação de autenticação e autorização de usuários com base em roles utilizando o Spring Security.

- Notificação por e-mail quando é realizado a reserva de Ingresso, compra de Ingresso e atualização de Evento.


<hr>

#### URL Deploy
> 

<hr>


### Rotas
#
### Autenticação
| Método      | Rota        | Descrição | JSON |
| ----------- | ----------- | ---------- | ----------  |
| POST         | /api/auth       | Retornar o Bearer token <br> necessário nas requisições |  <pre>{<br>"email": "brunolutterbach13@gmail.com",<br>"senha": "senha123"<br>}</pre>|

<hr>


### 1 Usuário
#### 1.1 Cadastrar Usuário
| Método | Rota | Descrição            | JSON | 
| --- | --- |----------------------| --- | 
|POST | /api/users/ | Cadastrar um Usuário | <pre>{<br>  "nome": "Bruno Lutterbach",<br/>  "email": "brunolutterbach13@gmail.com",<br/>  "senha": "senha123",<br/>  "enderecos": [<br>     {<br>        "logradouro": "Rua Ulisses Lengruber", <br>        "cep": "28640-000",<br>        "numero": "383",<br>        "cidade": "Carmo"<br>      },  <br>      {<br>        "logradouro": "Rua Ulisses Lengruber",<br>        "cep": "28640-000",<br>        "numero": "175",<br>        "cidade": "Carmo"<br>      }<br>   ]<br>}</p>
</pre> |

| Nome  | Descrição   | 
| --- |-------------| 
|nome | Obrigatório |
|email | Obrigatório |
|senha | Obrigatório |
|enderecos | Opcional    |

![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/7e9df6b6-983f-41eb-9db7-9514536ead9c)

#
#### 1.2 Atualizar Usuário


| Método | Rota            | Descrição            | JSON                                                                                     | 
|--------|-----------------|----------------------|------------------------------------------------------------------------------------------| 
| PUT    | /api/users | Atualizar um Usuário | <pre>{<br> "nome": "Bruno Lutterbach Pereira Marques",<br> "email": brunolutterbach10@gmail.com <br>}</pre> |

| Nome  | Descrição   | 
| --- |-------------| 
|nome | Opcional    |
|email | Opcional |
|senha | Opcional |

![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/6cc6f3b0-0883-439a-923e-46b503ddb065)

#
#### 1.3 Obter Usuário por ID

| Método | Rota            | Descrição                  | 
| --- |-----------------|----------------------------| 
|GET | /api/users/{id} | Retornar um Usuário por id |

![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/4e76ff08-a58e-4b98-a51b-44a12a9c8975)

#

#### 1.4 Obter Usuário com Endereços Por ID

| Método | Rota                      | Descrição                           |
|--------|---------------------------|-------------------------------------|
| GET    | /api/users/{id}/enderecos | Retornar o Usuário e seus Endereços |

![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/2240320d-3f26-4580-a4d4-b26bd82dbb28)

#

#### 1.6 Deletar Usuário
| Método | Rota | Descrição              |
| --- | --- |------------------------|
|DEL | /api/users/{id} | Deletar Usuário por Id |

<hr>

### 2 Endereços

#### 2.1 Cadastrar Endereços
| Método | Rota               | Descrição                     | JSON |
|--------|--------------------|-------------------------------|------|
| POST   | /api/endereco/{id} | Cadastrar Endereco no Usuário | <pre>{<br>    "logradouro": "Rua ABC", <br>    "cep": "12345-678",<br>    "numero": "123",<br>    "cidade": "São Paulo"<br> }  <br></p>
 </pre> |

| Nome  | Descrição   | 
| --- |-------------| 
|logradouro | Obrigatório |
|cep | Obrigatório |
|numero | Obrigatório |
|cidade | Obrigatório |

![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/dd63782e-bd53-4eff-9f8d-d1f4d4dc6510)

#
#### 2.2 Atualizar Endereço

| Método | Rota               | Descrição                    | JSON |
|--------|--------------------|------------------------------|------|
| PUT    | /api/endereco/{id} | Atualizar Endereco | <pre>{<br>    "logradouro": "Rua ABCDEF",  <br>    "numero": "566"<br> }  <br></p>
 </pre> |

| Nome  | Descrição   |
| --- |-------------|
|logradouro | Opcional |
|cep | Opcional |
|numero | Opcional |
|cidade | Opcional |

![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/bec1f5c2-21d9-4da0-908c-76317c7a460e)

#
#### 2.3 Buscar Endereço por ID
| Método | Rota            | Descrição                  | 
| --- |-----------------|----------------------------| 
|GET | /api/endereco/{id} | Retornar um Endereço por id |

![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/361b67ad-ed1e-4470-9212-25049b3dbd24)

#
#### 2.4 Deletar Endereço

| Método | Rota | Descrição        | 
| --- | --- |------------------| 
|DEL | /api/endereco/{id} | Deletar Endereço |

<hr>

### 3 Organizador

#### 3.1 Obter ROLE_ORGANIZADOR

| Método | Rota | Descrição        |  JSON |
| --- | --- |------------------| ------|
|POST | /api/organizador | Obter ROLE_ORGANIZADOR para cadastro de Eventos | <pre>{<br>  "nomeEmpresa": "Exemplo Empresa", <br>  "email": "exemplo@empresa.com", <br>  "site": "www.exemplo.com", <br>  "cnpj": "12.345.678/0001-90", <br>  "telefone": "(99) 9999-9999" <br> }</pre>  | 

![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/30c89d70-ea3e-4a30-8647-0c13c90ac38b)

#
#### 3.2 Atualizar Organizador

| Método | Rota               | Descrição                    | JSON |
|--------|--------------------|------------------------------|------|
| PUT    | /api/organizador/{id} | Atualizar Endereco | <pre>{<br>    "nomeEmpresa": "Exemplo Empresa Atualizado",  <br>    "site": "www.exemploatualizado.com"<br> }  <br></p>
 </pre> |
 
 | Nome  | Descrição   |
| --- |-------------|
|nomeEmpresa | Opcional |
|email | Opcional |
|site | Opcional |
|cnpj | Opcional |
|telefone | Opcional |
 
 ![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/f4d5a72c-9a94-4111-9c75-8441d25e3017)
 
 #
 #### 3.3 Listar Organizadores
| Método | Rota            | Descrição                  | 
| --- |-----------------|----------------------------| 
|GET | /api/organizador | Retornar todos os Organizadores |

##### Ordenação
```
/api/organizador?sort=id,desc
```
##### Paginação
```
/api/organizador?page=0&size=2
```

![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/65bf6502-65f3-4231-9430-fb0e76f30e89)

<hr>

### 4 Evento

#
#### 4.1 Cadastrar Evento
| Método | Rota | Descrição            | JSON | 
| --- | --- |----------------------| --- | 
|POST | /api/evento/ | Cadastrar um Evento |  <pre>{ <br>  "nome": "Evento Teste 1",<br>  "descricao": "Descrição do evento teste 1",<br>  "images": [<br>     "[https://www.example.com/image1.jpg](https://exemplo.com/imagem1.jpg)",<br>     "https://www.example.com/image2.jpg"<br> ], <br>"quantidadeIngressoDisponivel": 30,<br>"valorIngresso": 50.0,<br>"dataEvento": "30/05/2023",<br>"horaEvento": "20:00",<br>"statusEvento": "AGENDADO",<br>"categoriaEvento": "MUSICA",<br> "endereco": {<br>   "logradouro": "Rua Exemplo",<br>   "cep": "12345-678",<br>   "numero": "100",<br>   "cidade": "São Paulo"<br>  } <br> }</p></pre> |

 | Nome  | Descrição   |
| --- |-------------|
|nome | Obrigatório |
|descricao | Obrigatório |
|imagens | Obrigatório |
|quantidadeIngressoDisponivel | Obrigatório |
|valorIngresso | Obrigatório |
|dataEvento | Obrigatório |
|horaEvento | Obrigatório |
|statusEvento | Obrigatório |
|categoriaEvento | Obrigatório |
|endereco | Obrigatório |

![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/aea685d7-25a3-4a1b-95b9-a1e97bbd8547)

#
#### 4.2 Atualizar Evento
| Método | Rota               | Descrição                    | JSON |
|--------|--------------------|------------------------------|------|
| PUT    | /api/evento/{id} | Atualizar Evento | <pre>{<br>    "nome": "Evento Teste 1 Atualizado",  <br>    "descricao": "Descrição do Evento Teste 1 Atualizado",<br>    "categoriaEvento": "TEATRO"<br> }  <br></p>
 </pre> |
 
 ![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/3c407ece-27b5-4d5f-b30b-cb0c6ffb8460)

##### E-mail encaminhado a todos os Usuários que realizaram a compra de um Ingresso do Evento que foi atualizado
![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/bd2ac103-f742-4976-bbc7-d7db1cea76c5)


#
#### 4.3 Listar Eventos
| Método | Rota            | Descrição                  | 
| --- |-----------------|----------------------------| 
|GET | /api/eventos | Retornar todos os Eventos |
 
![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/0568aa2d-97ee-4741-be29-5e29e2d18eff)

#
#### 4.4 Obter Evento por ID
| Método | Rota            | Descrição                  | 
| --- |-----------------|----------------------------| 
|GET | /api/evento/{id} | Retornar um Evento por id |

![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/fb6fd764-ab21-41ac-8f70-873fe5b739aa)

#
#### 4.5
| Método | Rota | Descrição        | 
| --- | --- |------------------| 
|DEL | /api/evento/{id} | Deletar Evento |

<hr>

### 5 Ingresso

#
#### 5.1 Comprar Ingresso
| Método | Rota            | Descrição                  | 
| --- |-----------------|----------------------------| 
|POST | /api/ingresso/comprar | Gera link de pagamento para o Ingresso |

 | Nome  | Descrição   |
| --- |-------------|
|idEvento | Obrigatório |
|quantidade | Obrigatório |

![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/41d7edbf-59b5-443a-8b38-e4e346c8aee8)

###### Ao solicitar a compra do Ingresso, no banco de dados, o Ingresso é atribuido ao usuário por 5 minutos e status alterado para AGUARDANDO_PAGAMENTO, caso o pagamento não seja concluído, o Ingresso é liberado para compra novamente.
![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/4d7528c9-9189-49f2-b408-6657a8ab139e)

###### Quando o pagamento é realizado, o status é alterado para PAGO.
![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/2c96ffe3-1a2b-418c-982c-8b096b4687c3)

##### E-mail recebido após solicitar a compra do Ingresso
![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/e190c3c2-22eb-4ce6-a85e-a293ebc9a3e2)

##### E-mail recebido com o QR CODE após finalizar o pagamento no PayPal
![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/787ff6d4-e190-4e1b-8304-896ee3f534f4)

##### O QR CODE em anexo contém o UUID de cada Ingresso comprado e o nome do Usuário que realizou a compra
![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/353a80b5-d9ca-48f2-8843-fe3dad11df87)

<pre>[<br> DadosIngressoQrCode[uuid=051ab7d2-7b62-40ff-b7e6-5db5c54ffc54, nomeComprador=Bruno Lutterbach Pereira Marques],<br> DadosIngressoQrCode[uuid=08462828-19c9-4332-a40e-0f8eb0e5fb8c, nomeComprador=Bruno Lutterbach Pereira Marques],<br> DadosIngressoQrCode[uuid=0f37e2e4-1d26-4490-b3ed-5bd6b994eea3, nomeComprador=Bruno Lutterbach Pereira Marques]<br>]</pre>

#
#### 5.2 Atualizar valor do Ingresso
| Método | Rota            | Descrição                  | JSON |
| --- |-----------------|----------------------------| ------ |
|PUT | /api/ingresso/{id} | Atualiza o valor de todos os Ingressos disponíveis | <pre>{<br>   "valor": 35.0,  <br>}  <br></p>
 </pre> |
 
  | Nome  | Descrição   |
| --- |-------------|
 |valor | Obrigatório |
 
 #
 #### 5.3 Listar Ingressos do Usuário por ID
 | Método | Rota | Descrição        | 
| --- | --- |------------------| 
|GET | /api/ingresso/{id} | Obter os Ingresso do Usuário por ID |

![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/ef7d498f-c695-41b3-a888-4b2d2965047e)



 




