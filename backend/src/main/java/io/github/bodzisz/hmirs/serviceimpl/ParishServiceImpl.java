package io.github.bodzisz.hmirs.serviceimpl;

import io.github.bodzisz.hmirs.entity.Parish;
import io.github.bodzisz.hmirs.repository.ParishRepository;
import io.github.bodzisz.hmirs.service.ParishService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ParishServiceImpl implements ParishService {

    private final ParishRepository parishRepository;

    public ParishServiceImpl(ParishRepository parishRepository){this.parishRepository = parishRepository;}

    @Override
    public List<Parish> getParishes() {
        return parishRepository.findAll();
    }

    @Override
    public Parish getParish(int id) {
        Optional<Parish> search = parishRepository.findById(id);
        if (search.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Parish of id=%d was not found", id));
        return search.get();
    }

    @Override
    public Parish addParish(Parish parish) {
        return parishRepository.save(parish);
    }

    @Override
    public Parish deleteParish(int id) {
        Optional<Parish> toDelete = parishRepository.findById(id);
        if (toDelete.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Parish of id=%d was not found", id));
        parishRepository.deleteById(id);
        return toDelete.get();
    }

    @Override
    public void updateParish(final int id, Parish parish) {
        Parish existingParish = parishRepository.findById(id).orElse(null);
        if (existingParish != null) {
            existingParish.setName(parish.getName());
            existingParish.setMainPriest(parish.getMainPriest());
            parishRepository.save(existingParish);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Parish of id=%d was not found", id));
        }
    }
}
