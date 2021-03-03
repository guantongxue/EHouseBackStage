package com.minjiang.ehouse.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页
 *      {
 *          “success”：“成功”，
 *          “code”：10000
 *          “message”：“ok”，
 *          ”data“：{
 *              total：//总条数
 *              rows ：//数据列表
 *          }
 *      }
 *
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    private Long total;//总数
    private Long pageSize; //总页数
    private List<T> rows;//分页信息
}
