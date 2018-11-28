package ru.otus.elena.receipt.rest;

import io.micrometer.core.instrument.Counter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.elena.receipt.domain.Receipt;
import ru.otus.elena.receipt.dao.ReceiptRepository;
import ru.otus.elena.receipt.domain.ReceiptDto;
import ru.otus.elena.receipt.service.ReceiptService;

@RestController
public class ReceiptControllerRest {

    Logger logger = LoggerFactory.getLogger(ReceiptControllerRest.class);

    @Autowired
    private Counter warnCounter;

    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private ReceiptService receiptService;
    @Autowired
    private MessageSource messageSource;

    @PostMapping("/receipt/save")
    public ResponseEntity<List<String>> saveReceipt(@RequestBody Receipt receipt
    ) {
        try {
            if ((receipt.getType().trim()).equalsIgnoreCase("")
                    || (receipt.getName().trim()).equalsIgnoreCase("")
                    || (receipt.getComponent().trim()).equalsIgnoreCase("")
                    || (receipt.getDescription().trim()).equalsIgnoreCase("")) {
                return new ResponseEntity<>(new ArrayList<String>() {
                    {
                        add(messageSource.getMessage("savereceipt.data", new String[]{}, Locale.getDefault()));
                    }
                }, HttpStatus.BAD_REQUEST);
            } else {
                receiptRepository.insert(new Receipt(receipt.getType(), receipt.getName(), receipt.getComponent(), receipt.getDescription(), receipt.getUrl()));
                logger.info(messageSource.getMessage("savereceipt.success", new String[]{}, Locale.getDefault()));
                return new ResponseEntity<>(new ArrayList<String>() {
                    {
                        add(messageSource.getMessage("savereceipt.saved", new String[]{}, Locale.getDefault()));
                    }
                }, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.warn(messageSource.getMessage("savereceipt.exception", new String[]{e.getMessage()}, Locale.getDefault()));
            warnCounter.increment();
            return new ResponseEntity<>(new ArrayList<String>() {
                {
                    add(messageSource.getMessage("savereceipt.not.saved", new String[]{}, Locale.getDefault()));
                }
            }, HttpStatus.OK);
        }
    }

    @RequestMapping("/receipt/select")
    public ResponseEntity<ReceiptDto> selectReceipt(@RequestParam(value = "id") String id) {
        try {
            Receipt receipt = receiptRepository.findById(id).orElseThrow(NotFoundException::new);
            ReceiptDto dto = ReceiptDto.toReceiptDto(receipt);
            dto.setMessage("");
            if (dto.getUrl().trim().equalsIgnoreCase("") || receipt.getUrl() == null) {
                dto.setUrl("image/cucumber.jpg");
                dto.setMessage(messageSource.getMessage("savereceipt.picture", new String[]{}, Locale.getDefault()));
            }
            logger.info(messageSource.getMessage("selectreceipt.success", new String[]{}, Locale.getDefault()));
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn(messageSource.getMessage("selectreceipt.exception", new String[]{e.getMessage()}, Locale.getDefault()));
            warnCounter.increment();
            return new ResponseEntity<>(new ReceiptDto("", "", "", "", "", ""), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/receipt/delete")
    public ResponseEntity<List<String>> deleteReceipt(@RequestParam(value = "id") String id) {
        try {
            receiptRepository.deleteById(id);
            logger.info(messageSource.getMessage("deletereceipt.success", new String[]{}, Locale.getDefault()));
            return new ResponseEntity<>(new ArrayList<String>() {
                {
                    add(id);
                }
            }, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn(messageSource.getMessage("deletereceipt.exception", new String[]{e.getMessage()}, Locale.getDefault()));
            warnCounter.increment();
            return new ResponseEntity<>(new ArrayList<String>() {
                {
                    add("");
                }
            }, HttpStatus.BAD_REQUEST);
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
            logger.info(messageSource.getMessage("findreceipt.success", new String[]{}, Locale.getDefault()));
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            logger.warn(messageSource.getMessage("findreceipt.exception", new String[]{e.getMessage()}, Locale.getDefault()));
            warnCounter.increment();
            return new ResponseEntity<>(new ArrayList<Receipt>(), HttpStatus.OK);
        }
    }

}
