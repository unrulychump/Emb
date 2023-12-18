package com.example.emb.entity;

import java.util.Date;
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
@ApiModel(value="Classtable对象", description="")
public class Classtable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("classId")
    private Integer classId;

    @TableField("className")
    private String className;

    @TableField("modifiedTime")
    private Date modifiedTime;

    @TableField("endTime")
    private Date endTime;


}
