package com.matdongsan.domain.post;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.matdongsan.domain.post.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    // 제목
    @Override
    public Page<Post> searchKeyword(String keyword, Pageable pageable) {
        JPAQuery<Post> postQuery = jpaQueryFactory
                .selectFrom(post)
                .where(
                        post.title.contains(keyword)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) { // ?
            PathBuilder pathBuilder = new PathBuilder(post.getType(), post.getMetadata());
            postQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }

        List<Post> posts = postQuery.fetch();

        JPAQuery<Long> postCountQuery = jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(post.title.contains(keyword));

        return PageableExecutionUtils.getPage(posts, pageable, postCountQuery::fetchOne);
    }

    // 제목 + 내용

    // 작성자

    // 맛집 이름
}
