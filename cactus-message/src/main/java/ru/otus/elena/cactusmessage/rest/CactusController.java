package ru.otus.elena.cactusmessage.rest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.elena.cactusmessage.domain.Cactus;
import ru.otus.elena.cactusmessage.domain.CactusDto;

@Controller
public class CactusController {

    @GetMapping("/")
    public String startPage(Model model) throws IOException {
        CactusDto cactus = new CactusDto("aprocactus_hybr", "image/aprocactus_hybr.jpg");
        model.addAttribute("cactus", cactus);
        return "cactus";
    }

}
