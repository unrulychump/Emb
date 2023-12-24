package com.example.emb.entity;

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
@ApiModel(value="Classdtable对象", description="")
public class Classdtable implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @TableField("classId")
    private int classId;

    @TableField("className")
    private String className;

    @TableField("stuId")
    private Long stuId;

    @TableField("stuName")
    private String stuName;

    @TableField("groupId")
    private Integer groupId;

    @TableField("groupName")
    private String groupName;


}
