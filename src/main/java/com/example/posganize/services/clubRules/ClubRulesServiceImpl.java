package com.example.posganize.services.clubRules;

import com.example.posganize.mappers.ClubRulesMapper;
import com.example.posganize.models.clubRules.ClubRulesModel;
import com.example.posganize.models.clubRules.CreateClubRulesModel;
import com.example.posganize.models.clubRules.UpdateClubRulesModel;
import com.example.posganize.repository.ClubRulesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubRulesServiceImpl implements ClubRulesService{

    private final ClubRulesRepository clubRulesRepository;

    public ClubRulesServiceImpl(ClubRulesRepository clubRulesRepository) {
        this.clubRulesRepository = clubRulesRepository;
    }

    @Override
    public List<ClubRulesModel> getAllClubRules() {
        return ClubRulesMapper.mapClubRulesListToClubRulesModelList(clubRulesRepository.findAll());
    }

    @Override
    public ClubRulesModel getClubRule(Long clubRuleId) {
        var clubRule = clubRulesRepository.findById(clubRuleId).orElseThrow(() -> new NullPointerException("News not found"));
        return ClubRulesMapper.mapClubRulesToClubRulesModel(clubRule);
    }

    @Override
    public ClubRulesModel createClubRule(CreateClubRulesModel clubRuleModel) {
        var clubRule = ClubRulesMapper.mapCreateClubRulesModelToClubRules(clubRuleModel);
        clubRulesRepository.save(clubRule);
        return ClubRulesMapper.mapClubRulesToClubRulesModel(clubRule);
    }

    @Override
    public ClubRulesModel updateClubRule(UpdateClubRulesModel clubRulesModel, Long clubRuleId) {
        var clubRule = clubRulesRepository.findById(clubRuleId).orElseThrow(() -> new NullPointerException("News not found"));
        if(clubRulesModel.getName() != null) {
            clubRule.setName(clubRulesModel.getName());
        }
        if(clubRulesModel.getDescription() != null) {
            clubRule.setDescription(clubRulesModel.getDescription());
        }
        return ClubRulesMapper.mapClubRulesToClubRulesModel(clubRule);
    }

    @Override
    public void deleteClubRule(Long clubRuleId) {
        clubRulesRepository.deleteById(clubRuleId);
    }
}
