package com.example.posganize.services.clubRules;

import com.example.posganize.models.ClubRulesModel;

import java.util.List;

public interface ClubRulesService {

    List<ClubRulesModel> getAllClubRules();

    ClubRulesModel getClubRule(Long clubRuleId);

    ClubRulesModel createClubRule(ClubRulesModel clubRules);

    ClubRulesModel updateClubRule(ClubRulesModel clubRulesModel, Long clubRuleId);
    void deleteClubRule(Long clubRuleId);

}
