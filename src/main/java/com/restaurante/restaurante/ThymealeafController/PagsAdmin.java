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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
