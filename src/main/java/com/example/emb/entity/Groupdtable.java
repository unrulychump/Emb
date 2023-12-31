package com.example.emb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="Groupdtable对象", description="")
public class Groupdtable implements Serializable {

    private static final long serialVersionID = 1L;

    @TableField("id")
    private int id;

    @TableId("Uid")
    private Long Uid;

    @TableField("GroupId")
    private int GroupId;

    @TableField("classId")
    private Integer classId;

    @TableField("className")
    private String className;

    @TableField("GroupName")
    private String GroupName;

    private String name;


}
