package com.restaurante.restaurante.ThymealeafController;

import com.restaurante.restaurante.dto.PromocaoResponse;
import com.restaurante.restaurante.model.PromocaoModel;
import com.restaurante.restaurante.service.PromocaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private PromocaoService service;

    @GetMapping("/")
    public String home(Model model){
        List<PromocaoResponse> promocoes = service.listarTodas();
        model.addAttribute("promocoes", promocoes);
        return "index";
    }
}
