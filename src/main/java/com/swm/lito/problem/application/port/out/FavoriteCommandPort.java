package com.swm.lito.problem.application.port.out;

import com.swm.lito.problem.domain.Favorite;

public interface FavoriteCommandPort {

    void save(Favorite favorite);
}
