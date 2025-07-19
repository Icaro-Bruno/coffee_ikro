package com.restaurante.restaurante.ThymealeafController;

import com.restaurante.restaurante.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String mostrarLogin(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String fazerLogin(@RequestParam String usuario, @RequestParam String senha,Model model){
        if (adminService.autenticar(usuario, senha)) {
            model.addAttribute("usuarioLogado", usuario);
            return "redirect:/admin/dashboard";
        }
        model.addAttribute("erro", "Dados inválidos");
        return "admin/login";
    }

    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model) {
        model.addAttribute("totalPedidos", pedidoService.contarTodos());
        model.addAttribute("pedidosPendentes", pedidoService.contarPorStatus("PENDENTE"));
        model.addAttribute("pedidosEnviados", pedidoService.contarPorStatus("ENTREGUE"));
        model.addAttribute("pedidosCancelados", pedidoService.contarPorStatus("CANCELADO"));

        model.addAttribute("totalProdutos", produtoService.contar());
        model.addAttribute("totalClientes", clienteService.contar());
        model.addAttribute("totalPromocoes", promocaoService.contarAtivas());
        return "admin/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/admin/login";
    }
}
