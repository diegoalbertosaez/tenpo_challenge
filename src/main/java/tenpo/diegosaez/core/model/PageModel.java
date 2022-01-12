package tenpo.diegosaez.core.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageModel<T> {
	private List<T> elements;
	private Long totalElements;
	private Integer totalPages;
}
