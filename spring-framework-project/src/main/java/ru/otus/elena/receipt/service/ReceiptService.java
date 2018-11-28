package ru.otus.elena.receipt.service;

import io.micrometer.core.instrument.Timer;
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
    @Autowired
    private Timer findTimer;
    private boolean checkPicture;

    public boolean isCheckPicture() {
        return checkPicture;
    }

    public void setCheckPicture(boolean checkPicture) {
        this.checkPicture = checkPicture;
    }
    
    public List<Receipt> findReceipt(Map<String, String> map) {
        if (map.getOrDefault("type", "").equalsIgnoreCase("")) {
            if (map.getOrDefault("name", "").equalsIgnoreCase("")) {
                if (map.getOrDefault("component", "").equalsIgnoreCase("")) {
                    return findTimer.record(() -> {
                        return receiptRepository.findAll();
                    });
                } else {
                    return findTimer.record(() -> {
                        return receiptRepository.findByComponent(map.get("component"));
                    });
                }
            } else {
                if (map.getOrDefault("component", "").equalsIgnoreCase("")) {
                    return findTimer.record(() -> {
                        return receiptRepository.findByName(map.get("name"));
                    });
                } else {
                    return findTimer.record(() -> {
                        return receiptRepository.findByNameAndComponent(map.get("name"), map.get("component"));
                    });
                }
            }

        } else {
            if (map.getOrDefault("name", "").equalsIgnoreCase("")) {
                if (map.getOrDefault("component", "").equalsIgnoreCase("")) {
                    return findTimer.record(() -> {
                        return receiptRepository.findByType(map.get("type"));
                    });
                } else {
                    return findTimer.record(() -> {
                        return receiptRepository.findByTypeAndComponent(map.get("type"), map.get("component"));
                    });
                }
            } else {
                if (map.getOrDefault("component", "").equalsIgnoreCase("")) {
                    return findTimer.record(() -> {
                        return receiptRepository.findByTypeAndName(map.get("type"), map.get("name"));
                    });
                } else {
                    return findTimer.record(() -> {
                        return receiptRepository.findByTypeAndNameAndComponent(map.get("type"), map.get("name"), map.get("component"));
                    });
                }
            }
        }
    }
}
