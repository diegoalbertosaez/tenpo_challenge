package tenpo.diegosaez.controller.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "Page", description = "Representa una página de elementos")
public class PageDTO<T> {
	@ApiModelProperty("Lista de elementos")
	private List<T> elements;
	
	@ApiModelProperty("Total de elementos")
	private Long totalElements;
	
	@ApiModelProperty("Total de páginas")
	private Integer totalPages;
}
