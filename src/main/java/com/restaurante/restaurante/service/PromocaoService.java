package com.restaurante.restaurante.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.restaurante.restaurante.dto.PromocaoRequest;
import com.restaurante.restaurante.dto.PromocaoResponse;
import com.restaurante.restaurante.form.PromocaoForm;
import com.restaurante.restaurante.model.PromocaoModel;
import com.restaurante.restaurante.repository.PromocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PromocaoService {

    @Autowired
    private PromocaoRepository repository;private final Cloudinary cloudinary;

    public PromocaoService(Cloudinary cloudinary, PromocaoRepository repository) {
        this.cloudinary = cloudinary;
        this.repository = repository;
    }

    public void salvarViaForm(PromocaoForm form) {
        PromocaoModel promocao = new PromocaoModel();

        promocao.setTitulo(form.getTitulo());
        promocao.setDescricao(form.getDescricao());
        promocao.setAtivo(form.isAtivo());
        promocao.setDataInicio(form.getDataInicio());
        promocao.setDataFim(form.getDataFim());

        if (form.getImagem() != null && !form.getImagem().isEmpty()) {
            try {
                Map uploadResult = cloudinary.uploader()
                        .upload(form.getImagem().getBytes(), ObjectUtils.emptyMap());
                String url = uploadResult.get("secure_url").toString();
                promocao.setImgUrl(url);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao fazer upload da imagem", e);
            }
        }

        repository.save(promocao);
    }

    public void editarViaForm(Long id, PromocaoForm form) {
        PromocaoModel promocao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promoção não encontrada"));

        // Atualiza os campos da promoção com os dados do form
        promocao.setTitulo(form.getTitulo());
        promocao.setDescricao(form.getDescricao());
        promocao.setAtivo(form.isAtivo());
        promocao.setDataInicio(form.getDataInicio());
        promocao.setDataFim(form.getDataFim());

        // Se tem imagem nova no form, faz upload e atualiza URL
        if (form.getImagem() != null && !form.getImagem().isEmpty()) {
            try {
                Map uploadResult = cloudinary.uploader().upload(form.getImagem().getBytes(), ObjectUtils.emptyMap());
                String url = (String) uploadResult.get("secure_url");
                promocao.setImgUrl(url);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao fazer upload da imagem", e);
            }
        }

        repository.save(promocao);
    }

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

    public PromocaoResponse buscarPorId(Long id){
        PromocaoModel promocao = repository.findById(id)
                .orElseThrow(()->new RuntimeException("A Promoção não foi encontrada."));
        return converterToResponse(promocao);
    }

    public PromocaoModel buscarEntidadePorId(Long id){
        return repository.findById(id).orElseThrow(()->new RuntimeException("A Promoção não foi encontrada."));
    }

    public void alterarStatusPromo(Long id, boolean ativo) {
        PromocaoModel promocao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promoção não localizada"));
        promocao.setAtivo(ativo);
        repository.save(promocao);
    }

    public List<PromocaoResponse> listarTodas() {
        return Optional.ofNullable(repository.findAll())
                .orElse(Collections.emptyList())
                .stream()
                .map(this::converterToResponse)
                .collect(Collectors.toList());
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
                promocao.isAtivo(),
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

