package com.swm.lito.problem.application.service.comparator;

import com.swm.lito.problem.application.port.out.response.ProblemPageQueryDslResponseDto;

import java.util.Comparator;

public class ProblemStatusComparator implements Comparator<ProblemPageQueryDslResponseDto> {

    @Override
    public int compare(ProblemPageQueryDslResponseDto o1, ProblemPageQueryDslResponseDto o2) {
        String status1 = o1.getProblemStatus().getName();
        String status2 = o2.getProblemStatus().getName();

        switch (status1) {
            case "풀이중" -> {
                if (status2.equals("풀지않음") || status2.equals("풀이완료")) {
                    return 1;
                }
            }
            case "풀지않음" -> {
                if (status2.equals("풀이중")) {
                    return -1;
                } else if (status2.equals("풀이완료")) {
                    return 1;
                }
            }
            case "풀이완료" -> {
                if (status2.equals("풀이중")) {
                    return -1;
                }
            }
        }

        return status1.compareTo(status2);
    }
}
