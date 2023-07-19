package com.swm.lito.problem.application.service.comparator;

import com.swm.lito.problem.application.port.out.response.ProblemPageWithFavoriteQResponseDto;
import com.swm.lito.problem.domain.enums.ProblemStatus;

import java.util.Comparator;

public class ProblemStatusWithFavoriteComparator implements Comparator<ProblemPageWithFavoriteQResponseDto> {

    @Override
    public int compare(ProblemPageWithFavoriteQResponseDto o1, ProblemPageWithFavoriteQResponseDto o2) {
        ProblemStatus status1 = o1.getProblemStatus();
        ProblemStatus status2 = o2.getProblemStatus();

        switch (status1) {
            case PROCESS -> {
                if (status2 == ProblemStatus.NOT_SEEN || status2 == ProblemStatus.COMPLETE) {
                    return 1;
                }
            }
            case NOT_SEEN -> {
                if (status2 == ProblemStatus.PROCESS) {
                    return -1;
                } else if (status2 == ProblemStatus.COMPLETE) {
                    return 1;
                }
            }
            case COMPLETE -> {
                if (status2 == ProblemStatus.PROCESS) {
                    return -1;
                }
            }
        }

        return status1.compareTo(status2);
    }
}
