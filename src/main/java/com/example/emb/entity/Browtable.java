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
@ApiModel(value="Browtable对象", description="")
public class Browtable implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @TableField("mId")
    private Integer mId;

    @TableField("groupId")
    private String groupId;

    @TableField("browTime")
    private Date browTime;

    @TableField("isReturned")
    private Date isReturned;

    @TableField("broNum")
    private int broNum;


}
