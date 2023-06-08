package com.example.controlhabitspring.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSpecs {
    public enum SearchKey {
        ID("userId"),
        NAME("userName"),
        EMAIL("userEmail");

        private final String value;

        SearchKey(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static Specification<User> searchWith(Map<SearchKey, Object> searchKeyword) {
        return (Specification<User>) ((root, query, builder) -> {
                List<Predicate> predicate = getPredicateWithKeyword(searchKeyword, root, builder);
                return builder.and(predicate.toArray(new Predicate[0]));
            });
    }

    private static List<Predicate> getPredicateWithKeyword(Map<SearchKey, Object> searchKeyword, Root<User> root, CriteriaBuilder builder) {
        List<Predicate> predicate = new ArrayList<>();
        for (Map.Entry<SearchKey, Object> entry : searchKeyword.entrySet()) {
            SearchKey key = entry.getKey();
            switch (key) {
            case ID:
            case NAME:
            case EMAIL:
                predicate.add(builder.like(root.get(key.value), "%" + searchKeyword.get(key) + "%"));
                break;
            }
        }
        return predicate;
    }
}
