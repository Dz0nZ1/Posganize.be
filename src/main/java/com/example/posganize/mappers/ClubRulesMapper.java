package com.example.posganize.mappers;

import com.example.posganize.entities.ClubRules;
import com.example.posganize.models.ClubRulesModel;

import java.util.ArrayList;
import java.util.List;

public class ClubRulesMapper {

    public static ClubRulesModel mapClubRulesToClubRulesModel(ClubRules clubRules) {
        return ClubRulesModel.builder()
                .name(clubRules.getName())
                .description(clubRules.getDescription())
                .build();
    }


    public static ClubRules mapClubRulesModelToClubRules(ClubRulesModel clubRulesModel) {
        return ClubRules.builder()
                .name(clubRulesModel.getName())
                .description(clubRulesModel.getDescription())
                .build();
    }


    public static List<ClubRulesModel> mapClubRulesListToClubRulesModelList(List<ClubRules> clubRules) {
        List<ClubRulesModel> modelList = new ArrayList<>();
        for (ClubRules entity : clubRules) {
            modelList.add(mapClubRulesToClubRulesModel(entity));
        }
        return modelList;

    }


}
