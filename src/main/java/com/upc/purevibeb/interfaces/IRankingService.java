package com.upc.purevibeb.interfaces;

import com.upc.purevibeb.dtos.FamiliaRankingDTO;
import com.upc.purevibeb.dtos.InstitucionRankingDTO;
import com.upc.purevibeb.dtos.RankingDTO;

import java.util.List;

public interface IRankingService {

    List<RankingDTO> getRankingPersonal(int page, int size);

    List<FamiliaRankingDTO> getRankingFamiliar();

    List<InstitucionRankingDTO> getRankingInstitucional();
}