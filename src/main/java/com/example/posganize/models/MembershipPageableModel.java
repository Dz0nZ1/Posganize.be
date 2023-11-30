package com.example.posganize.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipPageableModel {

    List<MembershipModel> memberships;
    private int pageNumber;
    private int pageSize;
    private Long numberOfMemberships;
    private int totalPages;
    private boolean isLast;
    private boolean isFirst;
    private boolean hasPrevious;
    private boolean hasNext;
}
