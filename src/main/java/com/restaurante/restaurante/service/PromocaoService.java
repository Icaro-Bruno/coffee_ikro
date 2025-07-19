package com.restaurante.restaurante.service;

import com.restaurante.restaurante.dto.PromocaoRequest;
import com.restaurante.restaurante.dto.PromocaoResponse;
import com.restaurante.restaurante.model.PromocaoModel;
import com.restaurante.restaurante.repository.PromocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromocaoService {

    @Autowired
    private PromocaoRepository repository;

    public PromocaoResponse criarPromocao(PromocaoRequest request) {
        PromocaoModel promocao = new PromocaoModel(); //model
        promocao.setTitulo(request.getTitulo());
        promocao.setImgUrl(request.getImgUrl());
        promocao.setAtivo(request.getAtivo());
        promocao.setDataFim(request.getDataFim());
        promocao.setDataInicio(request.getDataInicio());

        promocao = repository.save(promocao); //save model
        return converterToResponse(promocao); //transformando model em dto nvmt
    }

    public PromocaoResponse atualizarPromocao(Long id, PromocaoRequest request) {
        PromocaoModel promocao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promocao não existe"));

        promocao.setTitulo(request.getTitulo());
        promocao.setImgUrl(request.getImgUrl());
        promocao.setAtivo(request.getAtivo());
        promocao.setDataFim(request.getDataFim());
        promocao.setDataInicio(request.getDataInicio());

        promocao = repository.save(promocao);
        return converterToResponse(promocao);
    }

    public List<PromocaoResponse> buscarPorTitulo(String titulo) {
        return repository.findByTituloContainingIgnoreCase(titulo)
                .stream().map(this::converterToResponse).collect(Collectors.toList());
    }

    public void alterarStatusPromo(Long id, boolean ativo) {
        PromocaoModel promocao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promoção não localizada"));
        promocao.setAtivo(ativo);
        repository.save(promocao);
    }

    public List<PromocaoResponse> listarTodas() {
        return repository.findAll()
                .stream().map(this::converterToResponse).collect(Collectors.toList());
    }

    public List<PromocaoResponse> listarRecentes() {
        return repository.findAllByOrderByDataInicioDesc()
                .stream().map(this::converterToResponse).collect(Collectors.toList());
    }

    private PromocaoResponse converterToResponse(PromocaoModel promocao) {
        return new PromocaoResponse(
                promocao.getId(),
                promocao.getTitulo(),
                promocao.getImgUrl(),
                promocao.getAtivo(),
                promocao.getDataInicio(),
                promocao.getDataFim()
        );
    }

    public void deletarPromocao(Long id) {
        repository.deleteById(id);
    }

    public long contarAtivas() {
        return repository.countByAtivoTrue();
    }
}

