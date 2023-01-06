package application.service;

import application.domain.Shop;
import application.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShopService {
    @Autowired
    private ShopRepository shopRepository;

    @Transactional
    public List<Shop> findAllByName(String name) {
        return shopRepository.getAllByName(name);
    }
}
