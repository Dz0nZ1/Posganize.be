package com.example.posganize.models.clubNews;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubNewsPageableModel {
    List<ClubNewsModel> clubNews;
    private int pageNumber;
    private int pageSize;
    private Long numberOfNews;
    private int totalPages;
    private boolean isLast;
    private boolean isFirst;
    private boolean hasPrevious;
    private boolean hasNext;
}
