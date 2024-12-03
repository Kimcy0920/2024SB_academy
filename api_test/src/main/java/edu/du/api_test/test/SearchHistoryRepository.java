package edu.du.api_test.test;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    SearchHistory findByQuery(String query);
}