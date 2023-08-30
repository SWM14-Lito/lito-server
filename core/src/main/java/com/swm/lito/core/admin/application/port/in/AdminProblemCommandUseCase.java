package com.swm.lito.core.admin.application.port.in;

import com.swm.lito.core.admin.application.port.in.request.PostRequestDto;

public interface AdminProblemCommandUseCase {

    void create(PostRequestDto postRequestDto);
}
