package com.swm.lito.core.problem.application.port.out;

import com.swm.lito.core.problem.domain.Favorite;

public interface FavoriteCommandPort {

    void save(Favorite favorite);
}
