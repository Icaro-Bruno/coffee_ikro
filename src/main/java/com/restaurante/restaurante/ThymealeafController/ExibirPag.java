package com.restaurante.restaurante.ThymealeafController;

import com.restaurante.restaurante.dto.ProdutoResponse;
import com.restaurante.restaurante.dto.PromocaoResponse;
import com.restaurante.restaurante.service.ProdutoService;
import com.restaurante.restaurante.service.PromocaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ExibirPag {

    @Autowired
    private PromocaoService promocaoService;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/")
    public String home(Model model){
        List<PromocaoResponse> promocoes = promocaoService.listarTodas();
        model.addAttribute("promocoes", promocoes);
        return "index";
    }

    @GetMapping("/cardapio")
    public String mostrarCardapio(Model model){
        List<ProdutoResponse> produtos = produtoService.listarTodos();
        model.addAttribute("produtos", produtos);
        return "cardapio";
//"produtos" é um nome que será utilizado no html,porem o produtos é o objeto;
    }

    @GetMapping("/produto/{id}")
    public String pagProduto(@PathVariable Long id, Model model){
        ProdutoResponse paginaProd = produtoService.buscarPorId(id);
        model.addAttribute("pagProduto", paginaProd);
        return "pagprod";
    }
}
