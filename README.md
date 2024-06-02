```mermaid
    Dono "1" -- "*" Pet : has
    Dono "1" -- "*" Agendamento : makes
    Pet "1" -- "*" Agendamento : participates in
    Veterinario "1" -- "*" Agendamento : performs
    Unidade "1" -- "*" Veterinario : has
    Unidade "1" -- "*" Agendamento : hosts
    class Dono {
    +Long id
    +String nome
    +String email
    +String cpf
    +String formaPagamento
    +String contato
    }
    class Pet {
    +Long id
    +String nome
    +String especie
    +String numeroConveniado
    +String raca
    +LocalDate dataNascimento
    }
    class Veterinario {
    +Long id
    +String nome
    +String crmv
    +String status
    +String especialidade
    }
    class Unidade {
    +Long id
    +String nome
    +String cep
    +String rua
    +String bairro
    +String cidade
    +String uf
    +String numero
    }
    class Agendamento {
    +Long id
    +LocalDate data
    +LocalTime horario
    +String especialidade
}```