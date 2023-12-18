package com.example.emb.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author KLE1NBLUE&LackOxy
 * @since 2023-12-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Worktable对象", description="")
public class Worktable implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @TableField("classId")
    private String classId;

    @TableField("modifiedTime")
    private Date modifiedTime;

    @TableField("endTime")
    private Date endTime;

    private String model;

    private String name;

    private String detailUrl;

    @TableField("isFinished")
    private Integer isFinished;

    @TableField("isFinal")
    private Integer isFinal;

    @TableField("repeatRate")
    private Double repeatRate;


}
