package br.com.dio.controller.dto;

import br.com.dio.domain.model.Unidade;

public record UnidadeDto(
        Long id,
        String nome,
        String cep,
        String rua,
        String bairro,
        String cidade,
        String uf,
        String numero
) {
    public UnidadeDto(Unidade model) {
        this(model.getId(), model.getNome(), model.getCep(), model.getRua(), model.getBairro(), model.getCidade(), model.getUf(), model.getNumero());
    }

    public Unidade toModel() {
        Unidade model = new Unidade();
        model.setId(this.id);
        model.setNome(this.nome);
        model.setCep(this.cep);
        model.setRua(this.rua);
        model.setBairro(this.bairro);
        model.setCidade(this.cidade);
        model.setUf(this.uf);
        model.setNumero(this.numero);
        return model;
    }
}