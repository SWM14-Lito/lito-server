package com.lito.core.problem.application.port.out;

import com.lito.core.problem.domain.Favorite;

public interface FavoriteCommandPort {

    void save(Favorite favorite);
}
