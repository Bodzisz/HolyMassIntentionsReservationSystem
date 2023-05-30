package io.github.bodzisz.hmirs.service;

import io.github.bodzisz.hmirs.dto.NewHolyMassDTO;
import io.github.bodzisz.hmirs.dto.NewHolyMassForYearDTO;
import io.github.bodzisz.hmirs.entity.HolyMass;

import java.util.List;

public interface HolyMassService {
    
    List<HolyMass> getHolyMasses();

    HolyMass getHolyMass(final int id);

    HolyMass addHolyMass(final NewHolyMassDTO holyMass);

    HolyMass deleteHolyMass(final int id);

    void updateHolyMass(final int id, final NewHolyMassDTO holyMass);

    List<HolyMass> addHolyMassesForYear(final NewHolyMassForYearDTO holyMassDTO, int year, boolean forSundays);
}
