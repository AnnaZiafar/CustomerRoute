package demo.messageroute.tier;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TierService {

    TierRepository repository;

    public TierService(TierRepository repository){
        this.repository = repository;
    }

    public List<Tier> getAll() {
        return repository.findAll();
    }

}
