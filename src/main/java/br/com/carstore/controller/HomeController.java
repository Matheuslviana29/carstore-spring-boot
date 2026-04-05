package br.com.carstore.controller;

import br.com.carstore.dto.CarDTO;
import br.com.carstore.service.CarService;
import br.com.carstore.service.CarServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
public class HomeController {

    private final CarServiceImpl service;

    @Autowired
    public HomeController(CarServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("carDTO", new CarDTO());
        return "index";
    }

    //Adicionei uma verificação para caso o usuário não preencha algum campo.
    @PostMapping("/cars")
    public String createCar(@Valid CarDTO carDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "index";
        }
        service.save(carDTO);
        return "redirect:/cars";
    }

    @GetMapping("/cars")
    public String getCars(Model model) {
        List<CarDTO> allCars = service.findAll();
        model.addAttribute("cars", allCars);
        return "dashboard";
    }

    // Rota para deletar.
    @GetMapping("/cars/delete/{id}")
    public String deleteCar(@PathVariable String id) {
        service.deleteById(id);
        return "redirect:/cars"; // Recarrega o dashboard
    }

    // Rota para carregar os dados de edição.
    @GetMapping("/cars/edit/{id}")
    public String editCarForm(@PathVariable String id, Model model) {
        CarDTO carDTO = service.findById(id);
        if (carDTO == null) {
            return "redirect:/cars";
        }
        model.addAttribute("carDTO", carDTO);
        return "index";
    }

    // Rota para salvar a edição.
    @PostMapping("/cars/update/{id}")
    public String updateCar(@PathVariable String id,@Valid CarDTO carDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "index";
        }
        service.update(id, carDTO);
        return "redirect:/cars";
    }

}