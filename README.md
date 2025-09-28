# 🚗 Sistema de Aluguel de Carros

Projeto desenvolvido como parte da disciplina **Laboratório de Desenvolvimento de Software** (PUC Minas), por uma equipe de **4 integrantes**.

O objetivo do sistema é apoiar a gestão de aluguéis de automóveis, permitindo que clientes façam pedidos de aluguel e que agentes (empresas e bancos) analisem e validem contratos.


## 📋 Funcionalidades

* Cadastro de usuários (clientes).
* CRUD de clientes.
* Realização, modificação, consulta e cancelamento de pedidos de aluguel.
* Análise de pedidos por empresas e bancos.
* Associação de contratos a pedidos, com vínculo a empresas e automóveis.
* Registro de informações dos contratantes (CPF, RG, nome, profissão, rendimentos etc.).
* Registro de automóveis (placa, marca, modelo, ano).


## 🏗 Arquitetura

O sistema segue a arquitetura **MVC** (Model-View-Controller), com:

* **Backend**: [Spring Boot](https://spring.io/projects/spring-boot)
* **Frontend**: HTML, CSS e JavaScript
* **Banco de dados**: PostgreSQL
* **Hospedagem**:

  * **Frontend** no [Vercel](https://vercel.com/)
  * **Backend** via [Docker](https://www.docker.com/)


## 🚀 Execução do Projeto

### 🔹 1. Frontend (Vercel)

O frontend já está hospedado na Vercel. Para rodar localmente:

```bash
# Clone o repositório
git clone https://github.com/Js3Silva/facilita_aluguel.git
cd frontend

# Abra o index.html no navegador
```
Vercel:
```bash
https://facilita-aluguel.vercel.app/index.html
```


### 🔹 2. Backend (Docker + Spring Boot)

O backend foi containerizado para facilitar a execução.

#### Pré-requisitos

* [Docker](https://docs.docker.com/get-docker/) instalado
* [Docker Compose](https://docs.docker.com/compose/) instalado

#### Passo a passo

1. Clone o repositório:

   ```bash
   git clone https://github.com/Js3Silva/facilita_aluguel.git
   cd Codigo/Backend
   ```

2. Suba os containers (backend + banco de dados):

   ```bash
   docker-compose up --build
   ```

3. O backend estará disponível em:

   ```
   http://localhost:8080
   ```


## 📂 Estrutura do Projeto

```
/
├── backend/       # Código Spring Boot
│   ├── src/       # Fontes Java
│   ├── Dockerfile
│   └── docker-compose.yml
├── frontend/      # Arquivos HTML, CSS, JS
│   └── index.html
└── README.md
```


## 👥 Equipe

Projeto desenvolvido por 4 integrantes da disciplina **Laboratório de Desenvolvimento de Software** (PUC Minas).


## 📑 Requisitos Atendidos

* Sistema web em **Java + Spring Boot**.
* Modelagem UML (Casos de Uso, Classes, Pacotes, Componentes, Implantação).
* CRUD de clientes.
* Gestão de pedidos.
* Hospedagem em ambiente real (Vercel + Docker).

