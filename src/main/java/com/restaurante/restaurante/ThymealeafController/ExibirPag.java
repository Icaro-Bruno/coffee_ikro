package com.restaurante.restaurante.ThymealeafController;

import com.restaurante.restaurante.dto.PedidoResponse;
import com.restaurante.restaurante.dto.ProdutoResponse;
import com.restaurante.restaurante.dto.PromocaoResponse;
import com.restaurante.restaurante.service.ClienteService;
import com.restaurante.restaurante.service.PedidoService;
import com.restaurante.restaurante.service.ProdutoService;
import com.restaurante.restaurante.service.PromocaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/")
    public String home(Model model, Authentication authentication){
        List<PromocaoResponse> promocoes = promocaoService.buscarPorTitulo("Ofertas Da Semana");
        model.addAttribute("promocoes", promocoes);

        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User oAuth2User) {
            String foto = (String) oAuth2User.getAttributes().get("picture");
            String nome = (String) oAuth2User.getAttributes().get("given_name");
            String email = (String) oAuth2User.getAttributes().get("email");

            model.addAttribute("foto", foto);
            model.addAttribute("nome", nome);
            model.addAttribute("email", email);
        }
        return "index";
    }

    @GetMapping("/sobre")
    public String sobreNos(){
        return "sobrenos";
    }

    @GetMapping("/politica")
    public String politicaPriv(){
        return "politica";
    }

    @GetMapping("/termos")
    public String termosUso(){
        return "termos";
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

    @GetMapping("/carrinho")
    public String verCarrinho() {
        return "carrinho";
    }

    @GetMapping("/detalhespedido/{id}")
    public String verPedido(@PathVariable Long id, Model model){
        PedidoResponse pedido = pedidoService.buscarPorId(id);
        model.addAttribute("pedido",pedido);
        return "status-pedido";
    }

    @GetMapping("/meuspedidos")
    public String todosPedidos(Model model, @AuthenticationPrincipal OAuth2User user){
        String email = user.getAttribute("email");
        List<PedidoResponse> pedidos = pedidoService.listarPedEmail(email);

        model.addAttribute("pedidos", pedidos);
        model.addAttribute("email", email);
        return "meuspedidos";
    }
}
