package com.restaurante.restaurante.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.restaurante.restaurante.form.ProdutoForm;
import com.restaurante.restaurante.dto.ProdutoRequest;
import com.restaurante.restaurante.dto.ProdutoResponse;
import com.restaurante.restaurante.model.ProdutoModel;
import com.restaurante.restaurante.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;
    @Autowired
    private Cloudinary cloudinary;

    public void salvarComImagem(ProdutoForm form, MultipartFile imagem) throws IOException {
        ProdutoModel produto = new ProdutoModel();
        produto.setNome(form.getNome());
        produto.setDescricao(form.getDescricao());
        produto.setPreco(form.getPreco());
        produto.setCategoria(form.getCategoria());

        if (!imagem.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(imagem.getBytes(), ObjectUtils.emptyMap());
            String url = uploadResult.get("secure_url").toString();
            produto.setImgUrl(url);
        }

        repository.save(produto);
    }

    public void atualizarComImagem(Long id, ProdutoForm form, MultipartFile imagem) throws IOException {
        ProdutoModel produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setNome(form.getNome());
        produto.setDescricao(form.getDescricao());
        produto.setPreco(form.getPreco());
        produto.setCategoria(form.getCategoria());

        if (!imagem.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(imagem.getBytes(), ObjectUtils.emptyMap());
            String url = uploadResult.get("secure_url").toString();
            produto.setImgUrl(url);
        }

        repository.save(produto);
    }

    public List<ProdutoResponse> listarTodos() {
        List<ProdutoModel> produtos = repository.findAll();

        return produtos.stream()
                .map(this::converterToResponse)
                .collect(Collectors.toList());
    }


    public ProdutoResponse criarProduto(ProdutoRequest produtoRequest) {
        ProdutoModel produto = new ProdutoModel(); //instanciando obj e pegando dds do dto
        produto.setNome(produtoRequest.getNome());
        produto.setPreco(produtoRequest.getPreco());
        produto.setDescricao(produtoRequest.getDescricao());
        produto.setCategoria(produtoRequest.getCategoria());
        produto.setImgUrl(produtoRequest.getImgUrl());
        produto.setVendaPor(produtoRequest.getVendaPor());

        produto = repository.save(produto); //save
        return converterToResponse(produto); //volta a ser dto nvmt
    }

    public ProdutoResponse atualizarProduto(Long id,ProdutoRequest produtoRequest){
        ProdutoModel produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não existe"));

        produto.setNome(produtoRequest.getNome());
        produto.setPreco(produtoRequest.getPreco());
        produto.setDescricao(produtoRequest.getDescricao());
        produto.setCategoria(produtoRequest.getCategoria());
        produto.setImgUrl(produtoRequest.getImgUrl());
        produto.setVendaPor(produtoRequest.getVendaPor());

        produto = repository.save(produto);
        return converterToResponse(produto);
    }

    public List<ProdutoResponse> buscarNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(this::converterToResponse)
                .collect(Collectors.toList());
    }

    public ProdutoResponse buscarPorId(Long id){
        ProdutoModel produto = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Produto não encontrado com id"+id));
        return converterToResponse(produto);
    }

    public List<ProdutoResponse> buscarCategoria(String categoria){
        return repository.findByCategoriaIgnoreCase(categoria)
                .stream()
                .map(this::converterToResponse)
                .collect(Collectors.toList());
    }

    public ProdutoResponse converterToResponse(ProdutoModel produto){
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getImgUrl(),
                produto.getCategoria(),
                produto.getDescricao(),
                produto.getVendaPor()
        );
    }

    public boolean deletarProduto(Long id) {
        if (repository.existsById(id)) {  // Busca se o produto existe
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public long contar() {
        return repository.count();
    }

}
