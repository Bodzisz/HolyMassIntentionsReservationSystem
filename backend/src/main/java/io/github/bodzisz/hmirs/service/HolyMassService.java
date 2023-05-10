package io.github.bodzisz.hmirs.service;

import io.github.bodzisz.hmirs.entity.HolyMass;

import java.util.List;

public interface HolyMassService {
    
    List<HolyMass> getHolyMasses();

    HolyMass getHolyMass(final int id);

    HolyMass addHolyMass(final HolyMass holyMass);

    HolyMass deleteHolyMass(final int id);

    void updateHolyMass(final int id, final HolyMass holyMass);
}
