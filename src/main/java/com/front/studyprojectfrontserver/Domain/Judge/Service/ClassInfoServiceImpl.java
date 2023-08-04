package com.front.studyprojectfrontserver.Domain.Judge.Service;

import com.front.studyprojectfrontserver.Domain.Judge.Adapter.ClassAdapter;
import com.front.studyprojectfrontserver.Domain.Member.Adapter.MemberAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassInfoServiceImpl {
    private final MemberAdapter memberAdapter;
    private final ClassAdapter classAdapter;
}
