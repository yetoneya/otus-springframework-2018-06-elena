package ru.otus.elena.cactusmessage.rest;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.elena.cactusmessage.domain.CactusDto;

import ru.otus.elena.cactusmessage.config.FirstGateway;

import ru.otus.elena.cactusmessage.config.SaveGateway;

@RestController
public class CactusControllerRest {

    @Autowired
    private FirstGateway imageGateway;
    private List<CactusDto> list = new ArrayList<>();

    @Autowired
    private SaveGateway saveGateway;

    @PostMapping("/save")
    public ResponseEntity<List<String>> saveCactus(@RequestBody CactusDto cactusdto) {
        String saved = saveGateway.saveCactus(cactusdto);
        return new ResponseEntity<>(new ArrayList<String>() {
            {
                add(cactusdto.getCactusname() + " " + saved);
            }
        }, HttpStatus.OK);
    }

    @RequestMapping("/next")
    public ResponseEntity<CactusDto> nextCactus() {
        if (list.size() > 1) {
            return new ResponseEntity<>(list.remove(0), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(list.get(0), HttpStatus.OK);
        }
    }

    @RequestMapping("/select")
    public ResponseEntity<CactusDto> selectType(@RequestParam(value = "selection") String selection) {
        List<CactusDto> cactus = imageGateway.photos(selection);
        list.clear();
        list.addAll(cactus);
        if (list.size() > 1) {
            return new ResponseEntity<>(list.remove(0), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(list.get(0), HttpStatus.OK);
        }

    }

}
