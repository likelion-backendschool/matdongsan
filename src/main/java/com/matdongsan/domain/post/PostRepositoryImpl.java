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
    public Page<Post> searchTitle(String keyword, Pageable pageable) {
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

    // 내용 검색
    @Override
    public Page<Post> searchContent(String keyword, Pageable pageable) {
        JPAQuery<Post> postQuery = jpaQueryFactory
                .selectFrom(post)
                .where(
                        post.content.contains(keyword)
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
                .where(post.content.contains(keyword));

        return PageableExecutionUtils.getPage(posts, pageable, postCountQuery::fetchOne);
    }

    // 작성자
    @Override
    public Page<Post> searchAuthor(String keyword, Pageable pageable) {
        JPAQuery<Post> postQuery = jpaQueryFactory
                .selectFrom(post)
                .where(
                        post.author.nickname.contains(keyword)
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
                .where(post.author.nickname.contains(keyword));

        return PageableExecutionUtils.getPage(posts, pageable, postCountQuery::fetchOne);
    }

    @Override
    public List<Post> findPostTop5() {
        JPAQuery<Post> postQuery = jpaQueryFactory
                .select(post)
                .from(post)
                .where(post.privateStatus.eq(true))
                .orderBy(post.postLike.size().desc())
                .limit(5);

        List<Post> posts = postQuery.fetch();

        return posts;
    }
}
