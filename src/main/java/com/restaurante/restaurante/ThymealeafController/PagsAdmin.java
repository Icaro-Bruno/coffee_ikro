package com.restaurante.restaurante.ThymealeafController;

import com.restaurante.restaurante.dto.PedidoResponse;
import com.restaurante.restaurante.model.PedidoModel;
import com.restaurante.restaurante.model.StatusPedido;
import com.restaurante.restaurante.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    /*@GetMapping("/pedidos/recentes")
    public String listarRecentes(Model model) {
            List<PedidoResponse> pedidos= pedidoService.listarRecentes();
            model.addAttribute("pedidos", pedidos);
            return "admin/pedidos";
    }

    @GetMapping("/pedidos/antigos")
    public String listarAntigos(Model model) {
        List<PedidoResponse> pedidos = pedidoService.listarAntigos();
        model.addAttribute("pedidos", pedidos);
        return "admin/pedidos";
    }*/

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/admin/login";
    }
}
