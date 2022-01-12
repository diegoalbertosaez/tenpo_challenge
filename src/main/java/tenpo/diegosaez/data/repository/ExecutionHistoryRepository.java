package tenpo.diegosaez.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import tenpo.diegosaez.data.entity.ExecutionHistory;

@Repository
public interface ExecutionHistoryRepository extends PagingAndSortingRepository<ExecutionHistory, Integer> {

}
