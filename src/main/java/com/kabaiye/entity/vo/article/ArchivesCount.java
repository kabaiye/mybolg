package com.kabaiye.entity.vo.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchivesCount {
      private String yearMonth;
      private Integer articleCount;
}
