package com.restaurante.restaurante.ThymealeafController;

import com.restaurante.restaurante.dto.*;
import com.restaurante.restaurante.form.ProdutoForm;
import com.restaurante.restaurante.form.PromocaoForm;
import com.restaurante.restaurante.model.PromocaoModel;
import com.restaurante.restaurante.model.StatusPedido;
import com.restaurante.restaurante.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class PagsAdmin {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private PromocaoService promocaoService;
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(value= "erro", required = false)
                               String erro, Model model){
        if(erro != null) {
            model.addAttribute("erro", "Usuário ou senha inválidos");
        }
        return "admin/login";
    }

    @PostMapping("/login")
    public String fazerLogin(@RequestParam String usuario, @RequestParam String senha,
                             HttpSession session, RedirectAttributes redirect) {
        if (adminService.autenticar(usuario, senha)) {
            session.setAttribute("adminLogado", true);
            return "redirect:/admin/dashboard";
        }

        redirect.addAttribute("erro", "true");
        return "redirect:/admin/login";
    }

    @GetMapping("/dashboard")
    public String mostrarDashboard(HttpSession session,Model model) {
        if(session.getAttribute("adminLogado") == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("totalPedidos", pedidoService.contarTodos());
        model.addAttribute("pedidosPendentes", pedidoService.contarPorStatus("PENDENTE"));
        model.addAttribute("pedidosEnviados", pedidoService.contarPorStatus("ENTREGUE"));
        model.addAttribute("pedidosCancelados", pedidoService.contarPorStatus("CANCELADO"));
        model.addAttribute("pedidosACaminho", pedidoService.contarPorStatus("A_CAMINHO"));

        model.addAttribute("totalProdutos", produtoService.contar());
        model.addAttribute("totalClientes", clienteService.contar());
        model.addAttribute("totalPromocoes", promocaoService.contarAtivas());
        return "admin/dashboard";
    }
    //PEDIDOS
    @GetMapping("/pedidos")
    public String pedidos(@RequestParam(required = false)StatusPedido status,@RequestParam(required = false) String ordem,HttpSession session, Model model) {
        if(session.getAttribute("adminLogado") == null) {
            return "redirect:/admin/login";
        }

        List<PedidoResponse> pedidos = pedidoService.filtrarPorStatusEOrdenacao(status,ordem);

        model.addAttribute("pedidos", pedidos);
        model.addAttribute("statusAtual", status);
        model.addAttribute("ordemAtual", ordem);
        return "admin/pedidos";
    }

    @GetMapping("/detalhespedido/{id}")
    public String detalhesPedido(@PathVariable Long id, HttpSession session, Model model){
        if (session.getAttribute("adminLogado") == null) {
            return "redirect:/admin/login";
        }
        PedidoResponse pedido = pedidoService.buscarPorId(id);
        model.addAttribute("pedido", pedido);
        StatusPedido[] statusList = StatusPedido.values();
        model.addAttribute("statusList", statusList);
        return "admin/detalhespedido";
    }

    @PostMapping("/pedido/atualizarStatus")
    public String atualizarStatusPedido(@RequestParam Long pedidoId,@RequestParam StatusPedido novoStatus) {
        pedidoService.atualizarStatus(pedidoId, novoStatus);
        return "redirect:/admin/detalhespedido/" + pedidoId;
    }

    @GetMapping("/pedidos/telefone")
    public String buscarPorTelefone(@RequestParam("telefone") String telefone, Model model) {
        List<PedidoResponse> pedidos = pedidoService.buscarPorTelefone(telefone);
        model.addAttribute("pedidos", pedidos);
        return "admin/pedidos";
    }

    //PRODUTOS
    @GetMapping("produto/listar")
    public String listarProdutos(Model model,HttpSession session){
        if(session.getAttribute("adminLogado") == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("produtos", produtoService.listarTodos());
        return "admin/produtos";
    }

    @GetMapping("/produtos")
    public String redirecionarProdutos() {
        return "redirect:/admin/produto/listar";
    }

    @GetMapping("produto/buscar")
    public String buscarProduto(@RequestParam("nome") String nome,Model model,HttpSession session){
        if(session.getAttribute("adminLogado")==null){
            return "redirect:/admin/login";
        }
        model.addAttribute("produtos", produtoService.buscarNome(nome));
        return "admin/produtos";
    }

    @GetMapping("produto/filtrar")
    public String filtrarCategorias(@RequestParam("categoria") String categoria,Model model,HttpSession session) {
        if (session.getAttribute("adminLogado") == null) {
            return "redirect:/admin/login";
        }
        List<ProdutoResponse> produtos;
        if(categoria.equalsIgnoreCase("todos")){
            produtos = produtoService.listarTodos();
        }else {
            produtos = produtoService.buscarCategoria(categoria);
        }
        model.addAttribute("produtos", produtos);
        return "admin/produtos";
    }

    @GetMapping("produto/criar")
    public String mostrarFormulario(Model model,HttpSession session){
        if(session.getAttribute("adminLogado") == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("produto", new ProdutoForm());
        return "admin/criarproduto";
    }

    @PostMapping("produto/salvar")
    public String salvarProduto(@ModelAttribute ProdutoForm produtoForm, @RequestParam("imagem")MultipartFile imagem,RedirectAttributes redirect,HttpSession session){
        if(session.getAttribute("adminLogado") == null){
            return "redirect:/admin/login";
        }
        try{
            produtoService.salvarComImagem(produtoForm,imagem);
            redirect.addFlashAttribute("sucesso","Produto cadastrado.");
        } catch (Exception e){
            redirect.addFlashAttribute("erro","Erro ao tentar salvar prodouto: " + e.getMessage());
        }
        return "redirect:/admin/produto/listar";
    }

    @GetMapping("produto/editar/{id}")
    public String editarProduto(@PathVariable Long id, Model model, HttpSession session) {
        if(session.getAttribute("adminLogado") == null) {
            return "redirect:/admin/login";
        }
        ProdutoResponse produto = produtoService.buscarPorId(id);
        model.addAttribute("produto", produto);
        return "admin/editarproduto";
    }

    @PostMapping("/produto/editar/{id}")
    public String atualizarProduto(@PathVariable Long id,
                                   @ModelAttribute ProdutoForm produtoForm,
                                   @RequestParam("imagem") MultipartFile imagem,
                                   RedirectAttributes redirect,
                                   HttpSession session) {
        if (session.getAttribute("adminLogado") == null) {
            return "redirect:/admin/login";
        }

        try {
            produtoService.atualizarComImagem(id, produtoForm, imagem); // método que você deve implementar
            redirect.addFlashAttribute("sucesso", "Produto atualizado com sucesso.");
        } catch (Exception e) {
            redirect.addFlashAttribute("erro", "Erro ao atualizar produto: " + e.getMessage());
        }

        return "redirect:/admin/produto/listar";
    }

    @GetMapping("produto/excluir/{id}")
    public String excluirProduto(@PathVariable Long id, RedirectAttributes redirect, HttpSession session) {
        if(session.getAttribute("adminLogado") == null) {
            return "redirect:/admin/login";
        }
        produtoService.deletarProduto(id);
        redirect.addFlashAttribute("sucesso", "Produto excluído com sucesso.");
        return "redirect:/admin/produto/listar";
    }

    //CLIENTE
    @GetMapping("/clientes")
    public String listarClientes(Model model, HttpSession session) {
        if(session.getAttribute("adminLogado") == null) {
            return "redirect:/admin/login";
        }
        List<ClienteResponse> clientes = clienteService.listarTodos();
        model.addAttribute("clientes", clientes);
        return "admin/clientes";
    }

    //PROMOCOES
    @GetMapping("/promocoes")
    public String listarPromocoes(Model model, HttpSession session){
        if(session.getAttribute("adminLogado") == null) {
            return "redirect:/admin/login";
        }
        List<PromocaoResponse> promocoes = promocaoService.listarTodas();
        model.addAttribute("promocoes", promocoes);
        return "admin/promocoes";
    }


    @GetMapping("promocoes/criar")
    public String mostrarFormularioCriar(Model model, HttpSession session) {
        if (session.getAttribute("adminLogado") == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("promocao", new PromocaoForm()); // DTO de entrada
        return "admin/criarpromocao";
    }

    @PostMapping("promocoes/salvar")
    public String salvarPromocao(@ModelAttribute PromocaoForm form) {
        promocaoService.salvarViaForm(form);
        return "redirect:/admin/promocoes";
    }

    @GetMapping("promocoes/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("adminLogado") == null) {
            return "redirect:/admin/login";
        }

        PromocaoModel promocao = promocaoService.buscarEntidadePorId(id);
        PromocaoForm form = new PromocaoForm();

        form.setTitulo(promocao.getTitulo());
        form.setAtivo(promocao.isAtivo());
        form.setDataInicio(promocao.getDataInicio());
        form.setDataFim(promocao.getDataFim());
        form.setImgUrl(promocao.getImgUrl()); // usado pra manter imagem antiga se não trocar

        model.addAttribute("promocaoId", id);
        model.addAttribute("promocao", form);

        return "admin/editarpromocao";
    }

    @PostMapping("promocoes/editar/{id}")
    public String editarPromocao(@PathVariable Long id, @ModelAttribute PromocaoForm form) {
        promocaoService.editarViaForm(id, form);
        return "redirect:/admin/promocoes";
    }

    @GetMapping("promocoes/deletar/{id}")
    public String deletarPromocao(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("adminLogado") == null) {
            return "redirect:/admin/login";
        }

        promocaoService.deletarPromocao(id);
        return "redirect:/admin/promocoes";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/admin/login";
    }
}
