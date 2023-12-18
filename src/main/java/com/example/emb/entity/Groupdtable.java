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

    private static final long serialVersionUID = 1L;

    @TableId("Uid")
    private Long Uid;

    @TableField("classId")
    private Integer classId;

    @TableField("className")
    private String className;

    private String name;


}
