package ru.otus.elena.cactusmessage.service;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import ru.otus.elena.cactusmessage.domain.CactusDto;
import com.opencsv.CSVIterator;
import com.opencsv.CSVReader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.elena.cactusmessage.dao.CactusDaoJdbc;
import ru.otus.elena.cactusmessage.domain.Cactus;

@Service
public class ImageService {

    @Autowired
    private CactusDaoJdbc cactusDao;

    private Map<String, List<CactusDto>> cactusMap = new HashMap<>();

    {
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader("src/main/resources/cactus.csv"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImageService.class.getName()).log(Level.SEVERE, null, ex);
        }
        CSVIterator iterator = (CSVIterator) reader.iterator();
        while (iterator.hasNext()) {
            String[] line = iterator.next();
            CactusDto cactus = new CactusDto(line[1], line[2]);
            String key = line[0];
            List<CactusDto> list = cactusMap.getOrDefault(key, new ArrayList<CactusDto>());
            list.add(cactus);
            cactusMap.put(key, list);
        }
        try {
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(ImageService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Map<String, List<CactusDto>> getCactusMap() {
        return cactusMap;
    }

    public List<CactusDto> getCactusList(String cactusType) {
        return cactusMap.getOrDefault(cactusType, new ArrayList<CactusDto>());
    }

    public String saveCactus(CactusDto cactusdto) {
        try {
            List<Cactus> list = cactusDao.getByName(cactusdto.getCactusname());
            if (!list.isEmpty()) {
                return "already exists";
            }
            File file = new File("src/main/resources/static/".concat(cactusdto.getUrl()));
            BufferedImage image = ImageIO.read(file);
            Cactus cactus = new Cactus(cactusdto.getCactusname(), cactusdto.getUrl(), image);
            long id = cactusDao.insert(cactus);
            return "saved";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
