package com.admin.common.response;


import java.util.List;

import lombok.Data;

@Data
public class DatatablesViewPage<T> {
    private List<T> data; //aaData 与datatales 加载的“dataSrc"对应
    private int recordsTotal; //总数
    private int recordsFiltered;//查询总数
    private int draw;//多少条
  
}