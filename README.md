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
#### 1.2 Obter Usuário por ID

| Método | Rota            | Descrição                  | 
| --- |-----------------|----------------------------| 
|GET | /api/users/{id} | Retornar um Usuário por id |

![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/4e76ff08-a58e-4b98-a51b-44a12a9c8975)

#

#### 1.3 Obter Usuário com Endereços Por ID

| Método | Rota                      | Descrição                           |
|--------|---------------------------|-------------------------------------|
| GET    | /api/users/{id}/enderecos | Retornar o Usuário e seus Endereços |

![image](https://github.com/BrunoLutterbach/TicketMasterAPI/assets/95001637/2240320d-3f26-4580-a4d4-b26bd82dbb28)

#





