package com.example.posganize.services.membership;


import com.example.posganize.entities.Membership;
import com.example.posganize.exceptions.MembershipNotFoundException;
import com.example.posganize.exceptions.UserNotFoundException;
import com.example.posganize.mappers.MembershipMapper;
import com.example.posganize.mappers.TrainingMapper;
import com.example.posganize.models.CreateMembershipModel;
import com.example.posganize.models.MembershipModel;
import com.example.posganize.models.MembershipPageableModel;
import com.example.posganize.models.TrainingModel;
import com.example.posganize.repository.MembershipRepository;
import com.example.posganize.repository.TrainingRepository;
import com.example.posganize.repository.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;

    private final UsersRepository usersRepository;

    private final TrainingRepository trainingRepository;



    public MembershipServiceImpl(MembershipRepository membershipRepository, UsersRepository usersRepository, TrainingRepository trainingRepository) {
        this.membershipRepository = membershipRepository;
        this.usersRepository = usersRepository;
        this.trainingRepository = trainingRepository;
    }

    @Override
    public List<MembershipModel> getAllMemberships() {
        return MembershipMapper.mapMembershipListToMembershipModelList(membershipRepository.findAll());
    }

    @Override
    public MembershipModel getMembership(Long membershipId) {
        return MembershipMapper.mapMembershipToMembershipModel(membershipRepository.findById(membershipId).orElseThrow(() -> new NullPointerException("Membership not found")));
    }

    @Override
    public MembershipPageableModel getAllMembershipsByUserId(Long userId, int pageNumber, int pageSize, boolean ascending) {
        Sort.Direction direction = ascending ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction,"expire_date"));
        Page<Membership> pagedMemberships = membershipRepository.findAllMembershipByUserId(userId, pageable);

        return MembershipPageableModel.builder()
                .memberships(MembershipMapper.mapMembershipPageableToMembershipModel(pagedMemberships))
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .numberOfMemberships(pagedMemberships.getTotalElements())
                .totalPages(pagedMemberships.getTotalPages())
                .isLast(pagedMemberships.isLast())
                .isFirst(pagedMemberships.isFirst())
                .hasPrevious(pagedMemberships.hasPrevious())
                .hasNext(pagedMemberships.hasNext())
                .build();
    }

    @Override
    public Map<String, Object> getRevenueAndMembers(LocalDate fromDate, LocalDate toDate) {
        Map<String, Object> response = new HashMap<>();
        if (fromDate != null && toDate != null) {
            List<Map<String, Object>> result1 = membershipRepository.getRevenueAndMembersByMonth(fromDate, toDate);
            Map<String, Object> result2 = membershipRepository.getTotalRevenueAndMembers(fromDate, toDate);
            Long result3 = trainingRepository.countTrainingsBetweenDates(fromDate, toDate);
            String result4 = trainingRepository.findTrainingNameWithMaxRevenueBetweenDates(fromDate, toDate);
            response.put("revenue_and_members", result1);
            response.put("RevenueAndMembersByMonth", result2);
            response.put("NumberOfActiveTrainings", result3);
            response.put("MostPopularTraining", result4);
        } else {
            Long totalMembers = membershipRepository.countMembers();
            Double totalRevenue = membershipRepository.calculateTotalRevenue();
            response.put("total_members", totalMembers);
            response.put("total_revenue", totalRevenue);
        }

        return response;
    }


    @Override
    public MembershipModel createMembership(CreateMembershipModel membershipModel) {
        var user = usersRepository.findById(membershipModel.getUserId()).orElseThrow(() -> new MembershipNotFoundException("Membership not found"));
        var entity = MembershipMapper.mapCreateMembershipModelToMembership(membershipModel);
        entity.setUser(user);
        entity.setTrainings(TrainingMapper.mapTrainingModelSetToTrainingSet(membershipModel.getTrainings()));
        double price = 0;
        for(TrainingModel training : membershipModel.getTrainings()){
            price += training.getPrice();
        }
        entity.setPrice(price);
        entity.setActive(true);
        membershipRepository.save(entity);
        return MembershipMapper.mapMembershipToMembershipModel(entity);
    }

    @Override
    public MembershipModel updateMembership(MembershipModel membershipModel, Long membershipId) {
        var entity = MembershipMapper.mapMembershipModelToMembership(membershipModel);
        var newMembership = membershipRepository.findById(membershipId).orElseThrow(() -> new MembershipNotFoundException("Membership not found"));
        if(entity.getUser() != null) {
            newMembership.setUser(entity.getUser());
        }
        if(entity.getStartDate() != null) {
            newMembership.setStartDate(entity.getStartDate());
        }
        if(entity.getExpireDate() != null) {
            newMembership.setExpireDate(entity.getExpireDate());
        }

        if(entity.getPrice() != null) {
            newMembership.setPrice(entity.getPrice());
        }

        if(entity.getActive() != null) {
            newMembership.setActive(entity.getActive());
        }

        return MembershipMapper.mapMembershipToMembershipModel(newMembership);
    }

    @Override
    public void deleteMembership(Long membershipId) {
        membershipRepository.deleteById(membershipId);
    }

    @Override
    public Map<String, Boolean> isActiveMembershipByUserId(Long userId) {
        var user = usersRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        var membership = membershipRepository.findByUser(user);
        var map = new HashMap<String, Boolean>();
        if(membership != null) {
            map.put("active", membership.getActive());
            return map;
        }
        map.put("active", false);
        return map;
    }
}
