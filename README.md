# Projeto de API feito para Clínica veterinária

Projeto de RESTful API em Java realizado como abstração.

O projeto foi realizado para entregar o desafio de projeto final do Bootcamp Santander Back End em Java da plataforma DIO

Este projeto foi idealizado em diferentes formas em algumas vezes em projetos de matérias da faculdade, e por ver a oportunidade de treino, realizei uma API para utilizar no projeto.

Aprendi muito sobre injeção de dependências ao decorrer do projeto, e sobre a inversão de controle do Spring Framework.


```mermaid
classDiagram
    class Dono {
        - id: Long
        - nome: String
        - email: String
        - cpf: String
        - formaPagamento: String
        - contato: String
    }
    class Pet {
        - id: Long
        - nome: String
        - especie: String
        - numeroConveniado: String
        - raca: String
        - dataNascimento: LocalDate
    }
    class Veterinario {
        - id: Long
        - nome: String
        - crmv: String
        - status: String
        - especialidade: String
    }
    class Unidade {
        - id: Long
        - nome: String
        - cep: String
        - rua: String
        - bairro: String
        - cidade: String
        - uf: String
        - numero: String
    }
        class Agendamento {
        - id: Long
        - data: LocalDate
        - horario: LocalTime
        - especialidade: String 
    }

    Dono "1" -- "*" Pet : has
    Dono "1" -- "*" Agendamento : makes
    Pet "1" -- "*" Agendamento : participates in
    Veterinario "1" -- "*" Agendamento : performs
    Unidade "1" -- "*" Veterinario : has
    Unidade "1" -- "*" Agendamento : hosts
```
