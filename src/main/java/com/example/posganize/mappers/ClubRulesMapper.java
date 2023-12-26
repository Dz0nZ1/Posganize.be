package com.example.posganize.mappers;

import com.example.posganize.entities.ClubRules;
import com.example.posganize.models.clubRules.ClubRulesModel;
import com.example.posganize.models.clubRules.CreateClubRulesModel;
import com.example.posganize.models.clubRules.UpdateClubRulesModel;

import java.util.ArrayList;
import java.util.List;

public class ClubRulesMapper {

    public static ClubRulesModel mapClubRulesToClubRulesModel(ClubRules clubRules) {
        return ClubRulesModel.builder()
                .id(clubRules.getRules_id())
                .image(clubRules.getImage())
                .name(clubRules.getName())
                .description(clubRules.getDescription())
                .build();
    }


    public static ClubRules mapClubRulesModelToClubRules(ClubRulesModel clubRulesModel) {
        return ClubRules.builder()
                .rules_id(clubRulesModel.getId())
                .image(clubRulesModel.getImage())
                .name(clubRulesModel.getName())
                .description(clubRulesModel.getDescription())
                .build();
    }

    public static ClubRules mapCreateClubRulesModelToClubRules(CreateClubRulesModel clubRulesModel) {
        return ClubRules.builder()
                .image(clubRulesModel.getImage())
                .name(clubRulesModel.getName())
                .description(clubRulesModel.getDescription())
                .build();
    }

    public static ClubRules mapUpdateClubRulesModelToClubRules(UpdateClubRulesModel clubRulesModel) {
        return ClubRules.builder()
                .image(clubRulesModel.getImage())
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
