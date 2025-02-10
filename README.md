# WatchFlix

**Projeto Desafio - Plataforma de Streaming Escalável**

## Visão Geral
WatchFlix é uma plataforma de streaming desenvolvida como um projeto desafio para a Watch Brasil. O objetivo do projeto é demonstrar conhecimentos em desenvolvimento Full Stack com foco em:
- Linguagem Kotlin e Spring Boot
- Arquitetura de microsserviços
- Containerização com Docker e Docker Compose
- Implantação na AWS (EC2)
- Integração com pipeline CI/CD (GitHub Actions)
- Monitoramento com Prometheus e Grafana

## Arquitetura do Sistema
O sistema é composto pelos seguintes microsserviços e componentes:
- **users-service:** Responsável pelo gerenciamento de usuários (cadastro, login, perfil).
- **Gateway:** Responsável pelo roteamento das requisições para os microsserviços.
- **Banco de Dados PostgreSQL:** Armazena os dados dos usuários.
- **Monitoramento:** Implementado com Prometheus e Grafana para coleta e visualização de métricas.
- **Pipeline CI/CD:** Automatização do build, testes e deploy via GitHub Actions.

## Tecnologias Utilizadas
- **Linguagem:** Kotlin
- **Framework:** Spring Boot
- **Banco de Dados:** PostgreSQL
- **Containerização:** Docker e Docker Compose
- **Deploy:** AWS EC2
- **CI/CD:** GitHub Actions
- **Monitoramento:** Prometheus, Grafana
- **Segurança:** Spring Security com JWT e BCrypt

## Configuração e Execução do Projeto

### Requisitos
- Java 17 ou superior
- Docker e Docker Compose
- Git

### Execução Local
1. Clone o repositório:
   ```bash
   git clone https://github.com/cleytonseles/WatchFlix.git
   cd WatchFlix/users-service

