package com.example.posganize.services.clubRules;

import com.example.posganize.models.clubRules.ClubRulesModel;
import com.example.posganize.models.clubRules.CreateClubRulesModel;
import com.example.posganize.models.clubRules.UpdateClubRulesModel;

import java.util.List;

public interface ClubRulesService {

    List<ClubRulesModel> getAllClubRules();

    ClubRulesModel getClubRule(Long clubRuleId);

    ClubRulesModel createClubRule(CreateClubRulesModel clubRules);

    ClubRulesModel updateClubRule(UpdateClubRulesModel clubRulesModel, Long clubRuleId);
    void deleteClubRule(Long clubRuleId);

}
