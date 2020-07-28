package net.gittab.fsmsample.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.gittab.fsmsample.domain.Issue;

/**
 * IssueRepository.
 *
 * @author xiaohua zhou
 * @date 2020/7/28 1:32 下午
 **/
public interface IssueRepository extends JpaRepository<Issue, Long> {

    Issue getById(Long id);
}
