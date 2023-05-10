package io.github.bodzisz.hmirs.service;

import io.github.bodzisz.hmirs.entity.Church;

import java.util.List;

public interface ChurchService {
    List<Church> getChurches();

    Church getChurch(final int id);

    Church addChurch(final Church church);

    Church deleteChurch(final int id);

    void updateChurch(final Church church);
}
