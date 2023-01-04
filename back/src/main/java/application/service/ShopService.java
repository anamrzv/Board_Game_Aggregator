package application.service;

import application.domain.Shop;
import application.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ShopService {
    @Autowired
    private ShopRepository shopRepository;

    public List<Shop> findAllByName(String name) {
        return shopRepository.getAllByName(name);
    }
}
