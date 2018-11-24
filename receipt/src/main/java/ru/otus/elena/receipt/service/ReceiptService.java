package ru.otus.elena.receipt.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.elena.receipt.dao.ReceiptRepository;
import ru.otus.elena.receipt.domain.Receipt;

@Service
public class ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;

    public List<Receipt> findReceipt(Map<String, String> map) {
        if (map.getOrDefault("type", "").equalsIgnoreCase("")) {
            if (map.getOrDefault("name", "").equalsIgnoreCase("")) {
                if (map.getOrDefault("component", "").equalsIgnoreCase("")) {
                    return receiptRepository.findAll();
                } else {
                    return receiptRepository.findByComponent(map.get("component"));
                }
            } else {
                if (map.getOrDefault("component", "").equalsIgnoreCase("")) {
                    return receiptRepository.findByName(map.get("name"));
                } else {
                    return receiptRepository.findByNameAndComponent(map.get("name"), map.get("component"));
                }
            }

        } else {
            if (map.getOrDefault("name", "").equalsIgnoreCase("")) {
                if (map.getOrDefault("component", "").equalsIgnoreCase("")) {
                    return receiptRepository.findByType(map.get("type"));
                } else {
                    return receiptRepository.findByTypeAndComponent(map.get("type"), map.get("component"));
                }
            } else {
                if (map.getOrDefault("component", "").equalsIgnoreCase("")) {
                    return receiptRepository.findByTypeAndName(map.get("type"), map.get("name"));
                } else {
                    return receiptRepository.findByTypeAndNameAndComponent(map.get("type"), map.get("name"), map.get("component"));
                }
            }
        }

    }

}
