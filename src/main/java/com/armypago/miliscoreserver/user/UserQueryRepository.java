package com.armypago.miliscoreserver.user;

import com.armypago.miliscoreserver.domain.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.armypago.miliscoreserver.domain.branch.QBranch.*;
import static com.armypago.miliscoreserver.domain.user.QEducation.*;
import static com.armypago.miliscoreserver.domain.user.QUser.*;

@RequiredArgsConstructor
@Repository
public class UserQueryRepository {

    private final JPAQueryFactory queryFactory;

    public User findByEmail(String email){
        return queryFactory.selectFrom(user)
                .join(user.branch, branch).fetchJoin()
                .join(user.education, education).fetchJoin()
                .where(user.email.eq(email)).fetchOne();
    }

    public User findById(Long id){
        return queryFactory.selectFrom(user)
                .join(user.branch, branch).fetchJoin()
                .join(user.education, education).fetchJoin()
                .where(user.id.eq(id)).fetchOne();
    }

    public List<User> findAll(){
        return queryFactory.selectFrom(user)
                .join(user.branch, branch).fetchJoin()
                .join(user.education, education).fetchJoin()
                .fetch();
    }
}
