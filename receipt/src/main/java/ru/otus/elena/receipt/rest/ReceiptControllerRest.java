package ru.otus.elena.receipt.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.elena.receipt.domain.Receipt;
import ru.otus.elena.receipt.dao.ReceiptRepository;
import ru.otus.elena.receipt.service.ReceiptService;

@RestController
public class ReceiptControllerRest {

    Logger logger = LoggerFactory.getLogger(ReceiptControllerRest.class);

    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private ReceiptService receiptService;

    @RequestMapping("/receipt/save")
    public ResponseEntity<List<String>> saveReceipt(
            @RequestParam(value = "type") String type,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "component") String component,
            @RequestParam(value = "description") String description) {
        try {
            if ((type = type.trim()).equalsIgnoreCase("")
                    || (name = name.trim()).equalsIgnoreCase("")
                    || (component = component.trim()).equalsIgnoreCase("")
                    || (description = description.trim()).equalsIgnoreCase("")) {
                return new ResponseEntity<>(new ArrayList<String>() {
                    {
                        add("any fields are not filled");
                    }
                }, HttpStatus.BAD_REQUEST);
            } else {
                receiptRepository.insert(new Receipt(type, name, component, description));
                logger.info("saveReceipt was successful");
                return new ResponseEntity<>(new ArrayList<String>() {
                    {
                        add("reseipt has been saved");
                    }
                }, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.warn("saveReceipt wasn't successful " + e.getMessage());
            return new ResponseEntity<>(new ArrayList<String>() {
                {
                    add("reseipt hasn't been saved");
                }
            }, HttpStatus.OK);
        }
    }

    @RequestMapping("/receipt/select")
    public ResponseEntity<Receipt> select(@RequestParam(value = "id") String id) {
        try {
            Receipt receipt = receiptRepository.findById(id).orElseThrow(NotFoundException::new);
            logger.info("findById was successful");
            return new ResponseEntity<>(receipt, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn("findById wasn't successful " + e.getMessage());
            return new ResponseEntity<>(new Receipt("", "", "", ""), HttpStatus.OK);
        }
    }

    @RequestMapping("/receipt/delete")
    public ResponseEntity<List<String>> delete(@RequestParam(value = "id") String id) {
        try {
            receiptRepository.deleteById(id);
            logger.info("delete was successful");
            return new ResponseEntity<>(new ArrayList<String>() {
                {
                    add(id);
                }
            }, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn("delete wasn't successful " + e.getMessage());
            return new ResponseEntity<>(new ArrayList<String>() {
                {
                    add("");
                }
            }, HttpStatus.OK);
        }
    }

    @RequestMapping("/receipt/find")
    public ResponseEntity<List<Receipt>> findReceipt(@RequestParam(value = "type") String type,
            @RequestParam(value = "name") String name, @RequestParam(value = "component") String component) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("type", type);
            map.put("name", name);
            map.put("component", component);
            List<Receipt> list = receiptService.findReceipt(map);
            Collections.sort(list, Comparator.comparing(b -> b.getName()));
            logger.info("findReceiptBy was successful");
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn("findReceiptBy wasn't successful " + e.getMessage());
            return new ResponseEntity<>(new ArrayList<Receipt>(), HttpStatus.OK);
        }
    }

}
