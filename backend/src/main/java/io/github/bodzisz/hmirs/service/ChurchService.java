package io.github.bodzisz.hmirs.service;

import io.github.bodzisz.hmirs.entity.Church;
import io.github.bodzisz.hmirs.entity.HolyMass;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface ChurchService {
    List<Church> getChurches();

    List<HolyMass> getHolyMasses(final int id) throws ResponseStatusException;

    Church getChurch(final int id);

    Church addChurch(final Church church);

    Church deleteChurch(final int id);

    void updateChurch(final int id, final Church church);
}
