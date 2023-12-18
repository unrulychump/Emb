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
@ApiModel(value="Grouptable对象", description="")
public class Grouptable implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @TableField("classId")
    private Integer classId;

    @TableField("className")
    private String className;

    @TableField("groupNum")
    private Integer groupNum;

    private Date modifiedtime;


}
