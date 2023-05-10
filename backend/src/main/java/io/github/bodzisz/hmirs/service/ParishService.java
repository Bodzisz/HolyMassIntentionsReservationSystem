package io.github.bodzisz.hmirs.service;

import io.github.bodzisz.hmirs.entity.Parish;

import java.util.List;

public interface ParishService {
    List<Parish> getParishes();

    Parish getParish(final int id);

    Parish addParish(final Parish parish);

    Parish deleteParish(final int id);

    void updateParish(final int id, final Parish parish);
}
