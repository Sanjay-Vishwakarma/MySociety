package com.pz.mysociety.common.specification;



import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SpecificationService {

    public  static  <T> Specification<T> equalSpecification(T targetClass, String entityColumn, Object object){
        return (root, query, builder) -> builder.equal(root.get(entityColumn), object);
    }

    public  static  <T> Specification<T> likeSpecification(T targetClass, String entityColumn, Object object){
        return (root, query, builder) -> builder.like(root.get(entityColumn), object + "%");
    }
    public  static  <T> Specification<T> lessThanOrEqualToSpecification(T targetClass, String entityColumn, Object object){
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(entityColumn), object + "%");
    }
    public  static  <T> Specification<T> greaterThanOrEqualToSpecification(T targetClass, String entityColumn, Object object){
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(entityColumn), object + "%");
    }
    public static <T> Specification<T> descendingOrder(T targetClass, String entityColumn) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.orderBy(criteriaBuilder.desc(root.get(entityColumn)));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public  static  <T> Specification<T> groupBySpecification(T targetClass, String entityColumn){
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.groupBy(root.get(entityColumn));
            return builder.and(predicates.toArray(new Predicate[0]));
        };

    }

    public  static  <T> Specification<T> greaterThanOrEqualToDateSpecification(T targetClass, String entityColumn, Date object){
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(entityColumn), object);
    }

    public  static  <T> Specification<T> lessThanOrEqualToDateSpecification(T targetClass, String entityColumn, Date object){
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(entityColumn), object);
    }

//    public  static  <T> Specification<T> date(T targetClass, String entityColumn){
//        return (root, query, builder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//            query.(root.get(entityColumn));
//            return builder.and(predicates.toArray(new Predicate[0]));
//        };
//    }
}
